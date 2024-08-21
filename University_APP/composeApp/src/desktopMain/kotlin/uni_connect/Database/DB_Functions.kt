package uni_connect.Database

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uni_connect.screen.LoginScreen


private var _users = mutableStateOf<List<User>>(emptyList())
val users: State<List<User>> get() = _users

private var _grades = mutableStateOf<List<GradeData>>(emptyList())
val grades: State<List<GradeData>> get() = _grades

// Fetch users from Supabase
suspend fun fetchUsers() {
    withContext(Dispatchers.IO) {
        try {
            // Fetch the data from Supabase
            val fetchedUsers: List<User> = supabase
                .from("users")
                .select()
                .decodeList()


                _users.value = fetchedUsers
                println("Fetched Users: $fetchedUsers")
            } catch (e: Exception) {

                e.printStackTrace()
                _users.value = emptyList()
            }

    }
}

suspend fun fetchCurrUserGrades(user:String) {
    withContext(Dispatchers.IO) {
        try {
            // Fetch the data from Supabase
            val fetchedgrades: List<GradeData> = supabase
                .from("user_$user")
                .select()
                .decodeList()


            _grades.value = fetchedgrades
            println("Fetched Users: $fetchedgrades")
        } catch (e: Exception) {

            e.printStackTrace()
            _grades.value = emptyList()
        }
    }
}


suspend fun insertDisplayName(userName: String,userSurname: String) {
    val currUser = supabase.auth.currentUserOrNull()
    supabase.from("profiles").update(
        {
            set("name", userName)
            set("surname", userSurname)
        }
    ) {
        filter {
            if (currUser != null) {
                eq("email", currUser.email!!)
            }
        }
    }
}

private var _currentUserProfile = mutableStateOf<List<UserProfile>>(emptyList())
val currentUserProfile: State<List<UserProfile>> get() = _currentUserProfile

suspend fun fetchCurrentUsername() {
    return withContext(Dispatchers.IO) {
        try {
            // Get the current user from Supabase
            val currUser = supabase.auth.currentUserOrNull()

            if (currUser?.email != null) {
                // Fetch the user profile data
                val response: List<UserProfile> = supabase.from("profiles")
                    .select(columns = Columns.type<UserProfile>()){
                        filter {
                            eq("email", currUser.email!!)
                        }
                    }.decodeList()

                _currentUserProfile.value = response
                println("Fetched Users: $response")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            _currentUserProfile.value = emptyList()
        }
    }
}

suspend fun signUpNewUserWithEmail(email: String, password: String) {
    supabase.auth.signUpWith(Email) {
        this.email = email
        this.password = password
    }
}

suspend fun signInWithEmail(email: String, password: String) {
    supabase.auth.signInWith(Email) {
        this.email = email
        this.password = password
    }
}

@Composable
fun logoutUser(isLoggedIn: Boolean ) {
    val navigator = LocalNavigator.currentOrThrow
    navigator.replace(LoginScreen())
}
