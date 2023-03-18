package com.ellie.jetportfolio.ui.profile

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.ellie.jetportfolio.R
import com.ellie.jetportfolio.data.api.model.Profile
import com.ellie.jetportfolio.data.model.BusinessCard
import com.ellie.jetportfolio.ui.businesscard.ContactInfoContent
import com.ellie.jetportfolio.ui.component.*
import com.ellie.jetportfolio.ui.theme.*
import com.ellie.jetportfolio.utils.StringFormatter
import com.ellie.jetportfolio.utils.isFirstItemVisible
import com.ellie.jetportfolio.utils.toString
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import java.time.YearMonth
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel(),
    navToBusinessCard: (BusinessCard) -> Unit = { },
) {
    val uiState by viewModel.uiState.collectAsState()
    val lazyListState = rememberLazyListState()

    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        floatingActionButton = {
            uiState.profile?.let {
                IconTextFloatingActionButton(
                    onClick = { navToBusinessCard(viewModel.generateBusinessCard()) },
                    showText = lazyListState.isFirstItemVisible(),
                    imageVector = Icons.Default.Badge,
                    textId = R.string.business_card,
                )
            }
        },
    ) {
        SwipeRefresh(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            state = rememberSwipeRefreshState(uiState.isRefreshing),
            onRefresh = { viewModel.refresh() },
        ) {
            if (uiState.isRefreshing) {
                // Loading bar
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            } else {
                // Page content
                AnimatedVisibility(visible = uiState.profile != null) {
                    ProfileContent(
                        lazyListState = lazyListState,
                        profile = uiState.profile!!,
                    )
                }
                AnimatedVisibility(visible = uiState.isShowFullScreenErrorMessage) {
                    ErrorMessage(
                        modifier = Modifier.fillMaxSize(),
                        errorMessageState = uiState.errorMessageState!!,
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProfileContent(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState,
    profile: Profile,
) {
    val context = LocalContext.current

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        state = lazyListState,
        contentPadding = PaddingValues(top = SpaceLarge, bottom = SpaceExtraLarge),
        verticalArrangement = Arrangement.spacedBy(1.dp),
    ) {
        // Personal Info
        item(key = profile.id) {
            ProfileCard(
                modifier = Modifier.padding(horizontal = SpaceMedium),
                coverPhotoUrl = profile.coverPhotoUrl,
                profilePicUrl = profile.pictureUrl,
                displayName = profile.displayName,
                headline = profile.headline,
                location = profile.location,
                phoneNumber = profile.phoneNumber,
                email = profile.email,
                socialMedias = profile.socialMedia,
                technicalSkill = profile.technicalSkillSet,
            )
        }
        // About
        stickyHeader(key = R.string.about) {
            ColumnSectionHeader(title = stringResource(id = R.string.about))
        }
        item(key = profile.professionalSummary) {
            AboutContent(
                modifier = Modifier.padding(horizontal = SpaceMedium),
                about = profile.professionalSummary
            )
        }
        // Work
        profile.workingExperience?.let {
            stickyHeader(key = R.string.work_experience) {
                ColumnSectionHeader(title = stringResource(id = R.string.work_experience))
            }
            items(items = profile.workingExperience, key = { it.id }) { work ->
                if (work.position.size > 1) {
                    val titles: List<String> by remember { mutableStateOf(work.position.map { it.title }) }
                    val empTypes: List<String?> by remember { mutableStateOf(work.position.map { it.locationEmploymentType }) }
                    ExpandableMultipleTimelineCard(
                        modifier = Modifier.padding(horizontal = SpaceMedium),
                        thumbnailUrl = work.thumbnailUrl,
                        titleText = work.companyName,
                        subtitleText = titles,
                        label1Text = empTypes,
                        label2Text = work.position.map {
                            formatYMDuration(
                                startYM = it.startYearMonth,
                                endYM = it.endYearMonth,
                            )
                        },
                        descriptionText = work.description,
                    )
                } else {
                    ExpandableSingleTimelineCard(
                        modifier = Modifier.padding(horizontal = SpaceMedium),
                        thumbnailUrl = work.thumbnailUrl,
                        titleText = work.companyName,
                        subtitleText = work.position[0].title,
                        label1Text = work.position[0].locationEmploymentType,
                        label2Text = formatYMDuration(
                            startYM = work.position[0].startYearMonth,
                            endYM = work.position[0].endYearMonth,
                        ),
                        descriptionText = work.description,
                    )
                }
            }
        }
        // School
        stickyHeader(key = R.string.education) {
            ColumnSectionHeader(title = stringResource(id = R.string.education))
        }
        items(items = profile.education, key = { it.id }) { school ->
            val graduateYr: String by remember(school) {
                mutableStateOf(
                    "${context.getString(R.string.graduated_in)} ${
                        school.endYearMonth?.toString(
                            StringFormatter.YMOutFormat
                        ) ?: context.getString(R.string.present)
                    }"
                )
            }
            ExpandableSingleTimelineCard(
                modifier = Modifier.padding(horizontal = SpaceMedium),
                thumbnailUrl = school.thumbnailUrl,
                titleText = school.school,
                subtitleText = school.degreeTitle,
                label1Text = school.location,
                label2Text = graduateYr,
                descriptionText = school.description,
            )
        }
    }
}

@Composable
private fun ProfileCard(
    modifier: Modifier = Modifier,
    coverPhotoUrl: String?,
    profilePicUrl: String?,
    displayName: String,
    headline: String,
    location: String,
    phoneNumber: String,
    email: String,
    socialMedias: List<Profile.SocialMedia>? = null,
    technicalSkill: Profile.TechnicalSkillSet? = null,
) {
    val divColor = MaterialTheme.colorScheme.surfaceVariant
    val cornerSize = 4

    ColumnCard(
        modifier = modifier.clip(
            shape = RoundedCornerShape(
                topStartPercent = cornerSize,
                topEndPercent = cornerSize,
            )
        )
    ) {
        // --- Profile Picture ---
        HeadlineSummaryContent(
            coverPhotoUrl = coverPhotoUrl,
            profilePicUrl = profilePicUrl,
            displayName = displayName,
            headline = headline,
            location = location,
            modifier = Modifier.padding(bottom = SpaceMedium),
        )

        // --- Contact info ---
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = SpaceMedium),
            thickness = 1.dp,
            color = divColor,
        )
        ProfileCardSubsection(
            sectionText = R.string.contact_information,
            modifier = modifier.padding(vertical = SpaceMedium),
        ) {
            ContactInfoContent(
                phoneNumber = phoneNumber,
                email = email,
                socialMedias = socialMedias,
                arrangementSpace = SpaceExtraSmall,
            )
        }

        // --- Skills ---
        technicalSkill?.let {
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = SpaceMedium),
                thickness = 1.dp,
                color = divColor,
            )
            ProfileCardSubsection(
                sectionText = R.string.technical_skills,
                modifier = modifier.padding(vertical = SpaceMedium),
            ) {
                TechnicalSkillContent(technicalSkill = technicalSkill)
            }
        }
    }
}


@Composable
private fun ProfileCardSubsection(
    modifier: Modifier = Modifier,
    @StringRes sectionText: Int,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(SpaceMedium),
    ) {
        Text(
            text = stringResource(id = sectionText),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
        content()
    }
}

@Composable
private fun AboutContent(
    modifier: Modifier = Modifier,
    about: String,
) {
    ColumnCard(modifier = modifier) {
        Text(
            text = about,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(SpaceMedium),
        )
    }
}

@Composable
private fun TechnicalSkillContent(
    modifier: Modifier = Modifier,
    technicalSkill: Profile.TechnicalSkillSet,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        technicalSkill.programmingLanguage?.let {
            TechnicalSkillCard(
                titleId = R.string.skill_programming_language,
                skills = technicalSkill.programmingLanguage,
            )
        }

        technicalSkill.developmentFramework?.let {
            TechnicalSkillCard(
                modifier = Modifier.padding(top = SpaceSmall),
                titleId = R.string.skill_development_framework,
                skills = technicalSkill.developmentFramework,
            )
        }

        technicalSkill.expandedSupport?.let {
            TechnicalSkillCard(
                modifier = Modifier.padding(top = SpaceSmall),
                titleId = R.string.skill_expanded_support,
                skills = technicalSkill.expandedSupport,
            )
        }

        technicalSkill.cloudDatabase?.let {
            TechnicalSkillCard(
                modifier = Modifier.padding(top = SpaceSmall),
                titleId = R.string.skill_cloud_database,
                skills = technicalSkill.cloudDatabase,
            )
        }
    }
}


@Composable
private fun TechnicalSkillCard(
    modifier: Modifier = Modifier,
    @StringRes titleId: Int,
    skills: List<Profile.TechnicalSkillSet.Skill>,
) {
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        mainAxisSize = SizeMode.Expand,
        crossAxisSpacing = SpaceExtraSmall, // ver space
        mainAxisSpacing = SpaceExtraSmall, // hor space
        crossAxisAlignment = FlowCrossAxisAlignment.Center, // hor align
    ) {
        Text(
            text = "${stringResource(id = titleId)}: ",
            style = MaterialTheme.typography.titleSmall,
        )
        skills.forEach { skill ->
            val starIcon by remember(skill) {
                mutableStateOf(
                    if (skill.yearOfExperience >= 3) {
                        Icons.Default.Star
                    } else {
                        null
                    }
                )
            }
            HashTagChip(
                text = formatSkillExperienceYear(skill = skill),
                icon = starIcon,
            )
        }
    }
}

@Composable
private fun HeadlineSummaryContent(
    modifier: Modifier = Modifier,
    coverPhotoUrl: String?,
    profilePicUrl: String?,
    displayName: String,
    headline: String,
    location: String,
) {
    ConstraintLayout(modifier = modifier.fillMaxWidth()) {
        //  Create guideline
        val startGuideline = createGuidelineFromStart(SpaceMedium)
        val endGuideline = createGuidelineFromEnd(SpaceMedium)

        // Create references for the composable to constrain
        val (
            coverRef,
            profilePicRef,
            nameRef,
            positionRef,
            locationRef,
        ) = createRefs()

        // Cover
        NetworkImage(
            url = coverPhotoUrl,
            placeholderColor = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier
                .height(120.dp)
                .constrainAs(coverRef) {
                    linkTo(
                        start = parent.start,
                        end = parent.end,
                    )
                    width = Dimension.fillToConstraints
                },
        )
        // Profile pic
        ProfileImage(
            url = profilePicUrl,
            size = 120.dp,
            modifier = Modifier.constrainAs(profilePicRef) {
                start.linkTo(parent.start, margin = SpaceLarge)
                top.linkTo(parent.top, margin = SpaceExtraLarge)
            },
        )
        // Name
        Text(
            text = displayName,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.constrainAs(nameRef) {
                top.linkTo(profilePicRef.bottom, margin = SpaceSmall)
                start.linkTo(startGuideline)
                end.linkTo(endGuideline)
                width = Dimension.fillToConstraints
            },
        )
        // Headline
        Text(
            text = headline,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .padding(top = SpaceSmall)
                .constrainAs(positionRef) {
                    top.linkTo(nameRef.bottom)
                    start.linkTo(startGuideline)
                    end.linkTo(endGuideline)
                    width = Dimension.fillToConstraints
                },
        )
        // Location
        Text(
            text = location,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier.constrainAs(locationRef) {
                top.linkTo(positionRef.bottom, margin = SpaceExtraSmall)
                start.linkTo(startGuideline)
                end.linkTo(endGuideline)
                width = Dimension.fillToConstraints
            },
        )
    }
}

@Composable
fun formatYMDuration(
    startYM: YearMonth,
    endYM: YearMonth?,
) = StringFormatter.formatYMDuration(
    context = LocalContext.current,
    startYM = startYM,
    endYM = endYM,
)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun formatSkillExperienceYear(
    skill: Profile.TechnicalSkillSet.Skill,
): String {
    val yrStr: String = skill.yearOfExperience.toString(StringFormatter.DecimalOutFormat)
    val yrLabel: String = pluralStringResource(
        R.plurals.yr, skill.yearOfExperience.roundToInt()
    )
    return "${skill.skill} ${yrStr}${yrLabel}"
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    JetPortfolioTheme {
        ProfileScreen()
    }
}

