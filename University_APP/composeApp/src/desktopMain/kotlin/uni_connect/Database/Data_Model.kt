package uni_connect.Database

import kotlinx.serialization.Serializable


data class User(
    val id: Int,
    val username: String,
    val email: String
)

@Serializable
data class UserProfile(
    val name: String,
    val surname: String
)

data class GradeData(
    val class_name: String,
    val grade1: Int?,
    val grade2: Int?,
    val grade3: Int?,
    val grade4: Int?,
    val grade5: Int?,
    val grade6: Int?,
    val grade7: Int?
)
