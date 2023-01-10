package com.ellie.jetportfolio.ui.component

import android.content.res.Configuration
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.ellie.jetportfolio.ui.theme.*

// Multiple Timeline Card
@Composable
fun ExpandableMultipleTimelineCard(
    modifier: Modifier = Modifier,
    thumbnailUrl: String?,
    titleText: String,
    subtitleText: List<String>,
    label1Text: List<String?>,
    label2Text: List<String?>,
    descriptionText: String? = null,
) {
    ExpandableTimelineCard(
        modifier = modifier,
        thumbnailUrl = thumbnailUrl,
        titleText = titleText,
        descriptionText = descriptionText,
    ) {
        subtitleText.forEachIndexed { i, _ ->
            TimelineContent(
                isLastIndex = i != subtitleText.size - 1,
                titleText = subtitleText[i],
                label1Text = label1Text[i],
                label2Text = label2Text[i],
            )
        }
    }
}

@Composable
fun SubtitleLabelContent(
    subtitleText: String?,
    label1Text: String? = null,
    label2Text: String? = null,
) {
    subtitleText?.let {
        Text(
            text = subtitleText,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.fillMaxWidth(),
        )
    }
    label1Text?.let {
        Text(
            text = label1Text,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = SpaceExtraSmall),
        )
    }
    label2Text?.let {
        Text(
            text = label2Text,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

// Single Timeline Card
@Composable
fun ExpandableSingleTimelineCard(
    modifier: Modifier = Modifier,
    thumbnailUrl: String?,
    titleText: String,
    subtitleText: String?,
    label1Text: String? = null,
    label2Text: String? = null,
    descriptionText: String? = null,
) {
    ExpandableTimelineCard(
        modifier = modifier,
        thumbnailUrl = thumbnailUrl,
        titleText = titleText,
        descriptionText = descriptionText,
    ) {
        SubtitleLabelContent(
            subtitleText = subtitleText,
            label1Text = label1Text,
            label2Text = label2Text,
        )
    }
}


// Title section
@Composable
private fun ExpandableTimelineCard(
    modifier: Modifier = Modifier,
    thumbnailUrl: String?,
    titleText: String,
    descriptionText: String?,
    content: (@Composable () -> Unit)? = null,
) {
    var expanded by remember { mutableStateOf(false) }

    ColumnCard(
        modifier = modifier,
        selected = expanded,
    ) {
        ConstraintLayout(
            modifier = Modifier
                .padding(start = SpaceSmall, bottom = SpaceMedium)
                .fillMaxWidth()
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMediumLow,
                    )
                ),
        ) {
            //  Create guideline
            val topGuideline = createGuidelineFromTop(SpaceMedium)
            val endGuideline = createGuidelineFromEnd(SpaceSmall)

            // Create references for the composable to constrain
            val (
                thumbnailRef,
                arrowRef,
                titleRef,
                contentRef,
                descriptionRef,
            ) = createRefs()

            // Company / School Logo whit White BG
            NetworkImage(
                url = thumbnailUrl,
                contentScale = ContentScale.Inside,
                modifier = Modifier
                    .clip(MaterialTheme.shapes.extraSmall)
                    .background(Color.White)
                    .size(64.dp)
                    .constrainAs(thumbnailRef) {
                        top.linkTo(topGuideline)
                    },
            )
            // Arrow
            descriptionText?.let {
                ExpandArrowButton(
                    isExpanded = expanded,
                    onClick = { expanded = !expanded },
                    modifier = Modifier.constrainAs(arrowRef) {
                        end.linkTo(parent.end)
                    },
                )
            }
            // titleText
            Text(
                text = titleText,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.constrainAs(titleRef) {
                    top.linkTo(thumbnailRef.top)
                    linkTo(
                        start = thumbnailRef.end,
                        end = arrowRef.start,
                        startMargin = SpaceMedium,
                    )
                    width = Dimension.fillToConstraints
                },
            )
            // Other content
            content?.let {
                Column(modifier = Modifier
                    .padding(top = SpaceMedium)
                    .constrainAs(contentRef) {
                        top.linkTo(titleRef.bottom)
                        start.linkTo(titleRef.start)
                        end.linkTo(endGuideline)
                        width = Dimension.fillToConstraints
                    }) {
                    content()
                }
            }
            // description (expendable)
            descriptionText?.let {
                if (expanded) {
                    Text(
                        text = descriptionText,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.constrainAs(descriptionRef) {
                            if (content != null) {
                                top.linkTo(contentRef.bottom, margin = SpaceSmall)
                            } else {
                                top.linkTo(titleRef.bottom, margin = SpaceSmall)
                            }
                            start.linkTo(titleRef.start)
                            end.linkTo(endGuideline)
                            width = Dimension.fillToConstraints
                        },
                    )
                }
            }
        }
    }
}

// Timeline item
@Composable
private fun TimelineContent(
    modifier: Modifier = Modifier,
    isLastIndex: Boolean = true,
    titleText: String,
    label1Text: String?,
    label2Text: String?,
) {
    val iconSize = IconExtraSmall
    val divSize = 4.dp
    val timelineTopOffset by remember { mutableStateOf(iconSize / 2F) }
    val timelineStartOffset by remember { mutableStateOf((iconSize / 2F).minus(divSize / 2F)) }
    val iconStartOffset by remember { mutableStateOf((iconSize / 2F).minus(iconSize / 2F)) }

    ConstraintLayout(modifier = modifier.fillMaxWidth()) {
        //  Create guideline
        val labelStartGuideline = createGuidelineFromStart(iconSize + SpaceSmall)
        val timelineStartGuideline = createGuidelineFromStart(timelineStartOffset)
        val iconStartGuideline = createGuidelineFromStart(iconStartOffset)

        // Create references for the composable to constrain
        val (
            labelContentRef,
            bottomSpaceRef,
            divRef,
            iconRef,
        ) = createRefs()

        // Labels
        Column(
            modifier = Modifier.constrainAs(labelContentRef) {
                start.linkTo(labelStartGuideline)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            },
        ) {
            SubtitleLabelContent(
                subtitleText = titleText,
                label1Text = label1Text,
                label2Text = label2Text,
            )
        }
        Spacer(modifier = Modifier
            .height(SpaceMedium)
            .constrainAs(bottomSpaceRef) {
                top.linkTo(labelContentRef.bottom)
            })
        // Timeline icons
        val indicatorColor = MaterialTheme.colorScheme.primary
        Divider(
            modifier = Modifier
                .width(divSize)
                .constrainAs(divRef) {
                    start.linkTo(timelineStartGuideline)
                    top.linkTo(parent.top, margin = timelineTopOffset)
                    bottom.linkTo(bottomSpaceRef.bottom)
                    height = Dimension.fillToConstraints
                },
            color = if (isLastIndex) indicatorColor else Color.Transparent,
        )
        Icon(
            imageVector = Icons.Default.Circle,
            contentDescription = null,
            tint = indicatorColor,
            modifier = Modifier
                .size(iconSize)
                .constrainAs(iconRef) {
                    start.linkTo(iconStartGuideline)
                    top.linkTo(parent.top)
                },
        )
    }
}


@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MultipleTimelineContentPreview() {
    JetPortfolioTheme {
        TimelineContent(
            modifier = Modifier.fillMaxWidth(),
            titleText = "item.title",
            label1Text = "item.durationHeadline",
            label2Text = "item.location",
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ExpandableMultipleTimelineCardPreview() {
    JetPortfolioTheme {
        ExpandableMultipleTimelineCard(
            thumbnailUrl = "item.thumbnailUrl",
            titleText = "item.title",
            subtitleText = listOf(
                "item.companyNameHeadline",
                "item.companyNameHeadline",
                "item.companyNameHeadline",
                "item.companyNameHeadline",
            ),
            label1Text = listOf(
                null,
                "item.durationHeadline",
                "item.durationHeadline",
                "item.durationHeadline",
            ),
            label2Text = listOf(
                "item.location",
                "item.location",
                "item.location",
                null,
            ),
            descriptionText = "item.description",
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ExpandableSingleTimelineCardPreview() {
    JetPortfolioTheme {
        ExpandableSingleTimelineCard(
            thumbnailUrl = "item.thumbnailUrl",
            titleText = "item.title",
            subtitleText = "item.companyNameHeadline",
            label1Text = "item.durationHeadline",
            label2Text = "item.location",
            descriptionText = "item.description",
        )
    }
}