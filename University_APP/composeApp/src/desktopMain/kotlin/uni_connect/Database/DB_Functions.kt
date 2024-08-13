package uni_connect.Database

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


private var _users = mutableStateOf<List<User>>(emptyList())
val users: State<List<User>> get() = _users

// Fetch users from Supabase
suspend fun fetchUsers() {
    withContext(Dispatchers.IO) {
        try {
            // Fetch the data from Supabase
            val fetchedUsers: List<User> = supabase
                .from("users")
                .select()
                .decodeList() // Directly decode the response into a list of User

                // Update the state with the fetched data
                _users.value = fetchedUsers
                println("Fetched Users: $fetchedUsers")
            } catch (e: Exception) {
                // Handle exceptions and log them
                e.printStackTrace()
                _users.value = emptyList() // Ensure the state is set to an empty list on error
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
