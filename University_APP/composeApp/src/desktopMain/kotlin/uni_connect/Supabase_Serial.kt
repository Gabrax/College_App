package uni_connect

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.serializer.MoshiSerializer
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

const val SUPABASE_URL = "https://vfqfeaefcvxxkopkkyil.supabase.co"
const val SUPABASE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InZmcWZlYWVmY3Z4eGtvcGtreWlsIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MjMxMjAyNjMsImV4cCI6MjAzODY5NjI2M30.vVZ5dMaDpvdxUadM_Uf-x4MDpHgBrJv_QRYL-XPigqE"

val supabase = createSupabaseClient(SUPABASE_URL, SUPABASE_KEY) {
    install(Postgrest) // Add the Postgrest plugin
    defaultSerializer = MoshiSerializer() // You can use MoshiSerializer if needed
}


suspend fun testConnection(): String {
    val client = HttpClient()
    return try {
        val response: HttpResponse = client.get(SUPABASE_URL)
        response.bodyAsText()
    } catch (e: Exception) {
        "Connection failed: ${e.message}"
    } finally {
        client.close()
    }
}

//// Function to fetch users from Supabase
//suspend fun fetchUsers(): List<User>? {
//    val moshi = Moshi.Builder()
//        .add(KotlinJsonAdapterFactory())
//        .build()
//    val jsonAdapter: JsonAdapter<List<User>> = moshi.adapter(
//        Types.newParameterizedType(List::class.java, User::class.java)
//    )
//
//    val client = HttpClient {
//        install(ContentNegotiation) {
//            json() // Ensure JSON is configured
//        }
//    }
//
//    return try {
//        val response: HttpResponse = client.get("$SUPABASE_URL/rest/v1/users") {
//            headers {
//                append(HttpHeaders.Authorization, "Bearer $SUPABASE_KEY")
//                append(HttpHeaders.Accept, ContentType.Application.Json.toString())
//            }
//        }
//        // Extract response body as a string and then parse it
//        val responseBody = response.bodyAsText()
//        jsonAdapter.fromJson(responseBody)
//    } catch (e: Exception) {
//        println("Error fetching users: ${e.message}")
//        null
//    } finally {
//        client.close()
//    }
//}


