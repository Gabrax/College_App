package uni_connect.Database

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


private var _users = mutableStateOf<List<User>>(emptyList())
val users: State<List<User>> get() = _users

private var _1ygrades = mutableStateOf<List<GradeData>>(emptyList())
val oneygrades: State<List<GradeData>> get() = _1ygrades

private var _2ygrades = mutableStateOf<List<GradeData>>(emptyList())
val twoygrades: State<List<GradeData>> get() = _2ygrades

private var _3ygrades = mutableStateOf<List<GradeData>>(emptyList())
val threeygrades: State<List<GradeData>> get() = _3ygrades

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

suspend fun fetchCurrUser1YGrades(user:String) {
    withContext(Dispatchers.IO) {
        try {
            // Fetch the data from Supabase
            val fetchedgrades: List<GradeData> = supabase
                .from("user_1y_$user")
                .select()
                .decodeList()


            _1ygrades.value = fetchedgrades
            //println("Fetched Users: $fetchedgrades")
        } catch (e: Exception) {

            e.printStackTrace()
            _1ygrades.value = emptyList()
        }
    }
}
suspend fun fetchCurrUser2YGrades(user:String) {
    withContext(Dispatchers.IO) {
        try {
            // Fetch the data from Supabase
            val fetchedgrades: List<GradeData> = supabase
                .from("user_2y_$user")
                .select()
                .decodeList()


            _2ygrades.value = fetchedgrades
            //println("Fetched Users: $fetchedgrades")
        } catch (e: Exception) {

            e.printStackTrace()
            _2ygrades.value = emptyList()
        }
    }
}
suspend fun fetchCurrUser3YGrades(user:String) {
    withContext(Dispatchers.IO) {
        try {
            // Fetch the data from Supabase
            val fetchedgrades: List<GradeData> = supabase
                .from("user_3y_$user")
                .select()
                .decodeList()


            _3ygrades.value = fetchedgrades
            //println("Fetched Users: $fetchedgrades")
        } catch (e: Exception) {

            e.printStackTrace()
            _3ygrades.value = emptyList()
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


