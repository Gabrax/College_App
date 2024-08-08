package uni_connect

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class UserViewModel : ViewModel() {
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
}
