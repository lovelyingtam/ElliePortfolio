package com.ellie.jetportfolio.data

import com.ellie.jetportfolio.data.api.model.Profile
import com.ellie.jetportfolio.data.model.BusinessCard

// Mock data sample for preview only
object LocalDataProvider {

    val businessCard = BusinessCard(
        pictureUrl = "pictureUrl",
        nickname = "nickname",
        fullName = "fullName",
        position = "position",
        phoneNumber = "phoneNumber",
        email = "email",
        socialMedia = listOf(Profile.SocialMedia("Link", "Media")),
    )

    val profile = Profile(
        coverPhotoUrl = "XXX",
        education = listOf(
            Profile.Education(
                activitiesAndSocieties = "xxx",
                degree = "xxx",
                description = "xxx",
                endDate = null,
                fieldOfStudy = "xxx",
                grade = "xxx",
                id = "xxx",
                location = "xxx",
                school = "xxx",
                startDate = "2022-01-01",
                thumbnailUrl = "xxx",
            )
        ),
        email = "XXX",
        headline = "XXX",
        firstName = "XXX",
        id = "XXX",
        lastName = "XXX",
        location = "XXX",
        nickname = "XXX",
        phoneNumber = "XXX",
        pictureUrl = "XXX",
        professionalSummary = "XXX",
        socialMedia = listOf(Profile.SocialMedia("Link", "Media")),
        technicalSkillSet = null,
        workingExperience = null,
    )
}