package com.ellie.jetportfolio.data.api.model


import com.ellie.jetportfolio.utils.StringFormatter
import com.ellie.jetportfolio.utils.toString
import com.ellie.jetportfolio.utils.toYearMonth
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.YearMonth

@JsonClass(generateAdapter = true)
data class Profile(
    @Json(name = "coverPhotoUrl") val coverPhotoUrl: String,
    @Json(name = "education") val education: List<Education>,
    @Json(name = "email") val email: String,
    @Json(name = "headline") val headline: String,
    @Json(name = "firstName") val firstName: String,
    @Json(name = "id") val id: String,
    @Json(name = "lastName") val lastName: String,
    @Json(name = "location") val location: String,
    @Json(name = "nickname") val nickname: String,
    @Json(name = "phoneNumber") val phoneNumber: String,
    @Json(name = "pictureUrl") val pictureUrl: String,
    @Json(name = "professionalSummary") val professionalSummary: String,
    @Json(name = "socialMedia") val socialMedia: List<SocialMedia>?,
    @Json(name = "technicalSkillSet") val technicalSkillSet: TechnicalSkillSet?,
    @Json(name = "workingExperience") val workingExperience: List<WorkingExperience>?,
) {
    val fullName: String = "$firstName $lastName"
    val displayName: String = "$fullName ($nickname)"
    val displayPosition: String = workingExperience?.get(0)?.position?.get(0)?.title ?: ""

    @JsonClass(generateAdapter = true)
    data class Education(
        @Json(name = "activitiesAndSocieties") val activitiesAndSocieties: Any?,
        @Json(name = "degree") val degree: String,
        @Json(name = "description") val description: String?,
        @Json(name = "endDate") val endDate: String?,
        @Json(name = "fieldOfStudy") val fieldOfStudy: String,
        @Json(name = "grade") val grade: String?,
        @Json(name = "id") val id: String,
        @Json(name = "location") val location: String,
        @Json(name = "school") val school: String,
        @Json(name = "startDate") val startDate: String,
        @Json(name = "thumbnailUrl") val thumbnailUrl: String
    ) {
        //        val startYearMonth: YearMonth = startDate.toYearMonth(StringFormatter.YMInputFormat)
        val endYearMonth: YearMonth? = endDate?.toYearMonth(StringFormatter.YMInputFormat)
        val degreeTitle: String =
            "Graduated in ${endYearMonth?.toString(StringFormatter.YMOutFormat)}"
    }

    @JsonClass(generateAdapter = true)
    data class SocialMedia(
        @Json(name = "link") val link: String, @Json(name = "media") val media: String
    )

    @JsonClass(generateAdapter = true)
    data class TechnicalSkillSet(
        @Json(name = "cloudDatabase") val cloudDatabase: List<Skill>?,
        @Json(name = "developmentFramework") val developmentFramework: List<Skill>?,
        @Json(name = "expandedSupport") val expandedSupport: List<Skill>?,
        @Json(name = "programmingLanguage") val programmingLanguage: List<Skill>?
    ) {
        @JsonClass(generateAdapter = true)
        data class Skill(
            @Json(name = "id") val id: String,
            @Json(name = "skill") val skill: String,
            @Json(name = "yearOfExperience") val yearOfExperience: Double
        )
    }

    @JsonClass(generateAdapter = true)
    data class WorkingExperience(
        @Json(name = "companyName") val companyName: String,
        @Json(name = "description") val description: String,
        @Json(name = "id") val id: String,
        @Json(name = "industry") val industry: String,
        @Json(name = "position") val position: List<Position>,
        @Json(name = "thumbnailUrl") val thumbnailUrl: String
    ) {
        @JsonClass(generateAdapter = true)
        data class Position(
            @Json(name = "employmentType") val employmentType: String,
            @Json(name = "endDate") val endDate: String?,
            @Json(name = "location") val location: String,
            @Json(name = "startDate") val startDate: String,
            @Json(name = "title") val title: String
        ) {
            val startYearMonth: YearMonth = startDate.toYearMonth(StringFormatter.YMInputFormat)
            val endYearMonth: YearMonth? = endDate?.toYearMonth(StringFormatter.YMInputFormat)
            val locationEmploymentType: String = "$location, $employmentType"
        }
    }
}