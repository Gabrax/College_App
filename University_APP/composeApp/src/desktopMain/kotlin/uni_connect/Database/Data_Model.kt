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
