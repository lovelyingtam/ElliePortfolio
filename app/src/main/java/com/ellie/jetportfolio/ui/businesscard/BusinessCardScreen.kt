package com.ellie.jetportfolio.ui.businesscard

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Link
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ellie.jetportfolio.R
import com.ellie.jetportfolio.data.LocalDataProvider
import com.ellie.jetportfolio.data.api.model.Profile
import com.ellie.jetportfolio.data.model.BusinessCard
import com.ellie.jetportfolio.ui.component.ClipboardIconLabel
import com.ellie.jetportfolio.ui.component.ProfileImage
import com.ellie.jetportfolio.ui.theme.*
import timber.log.Timber

@Composable
fun BusinessCardScreen(
    modifier: Modifier = Modifier,
    businessCard: BusinessCard,
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceVariant) // light:inverseOnSurface or deep:surfaceVariant
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SummaryContent(
            pictureUrl = businessCard.pictureUrl,
            nickname = businessCard.nickname,
            fullName = businessCard.fullName,
            position = businessCard.position,
        )
        Spacer(modifier = Modifier.height(SpaceExtraLarge))
        ContactInfoContent(
            phoneNumber = businessCard.phoneNumber,
            email = businessCard.email,
            socialMedias = businessCard.socialMedia,
        )
    }
}

@Composable
private fun SummaryContent(
    modifier: Modifier = Modifier,
    pictureUrl: String?,
    nickname: String,
    fullName: String,
    position: String,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ProfileImage(
            url = pictureUrl,
            size = 184.dp,
            borderWidth = 4.dp,
        )
        Spacer(modifier = Modifier.height(SpaceLarge))
        Text(
            text = nickname,
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Text(
            text = fullName,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(modifier = Modifier.height(SpaceMedium))
        Text(
            text = position,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun ContactInfoContent(
    modifier: Modifier = Modifier,
    phoneNumber: String?,
    email: String?,
    socialMedias: List<Profile.SocialMedia>?,
    arrangementSpace: Dp = SpaceSmall,
) {
    val uriHandler = LocalUriHandler.current
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(arrangementSpace),
    ) {
        phoneNumber?.let {
            ClipboardIconLabel(
                icon = Icons.Default.Call,
                label = stringResource(R.string.contact_number),
                text = phoneNumber,
            )
        }
        email?.let {
            ClipboardIconLabel(
                icon = Icons.Default.Email,
                label = stringResource(R.string.email),
                text = email,
            )
        }
        socialMedias?.forEach {
            ClipboardIconLabel(
                icon = Icons.Default.Link,
                label = it.media,
                text = it.link,
                onClick = {
                    Timber.d("Clicked: $it")
                    uriHandler.openUri(it.link)
                },
            )
        }
    }
}


@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    JetPortfolioTheme {
        BusinessCardScreen(businessCard = LocalDataProvider.businessCard)
    }
}

