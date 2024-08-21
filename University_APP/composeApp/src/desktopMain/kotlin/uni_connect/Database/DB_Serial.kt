package uni_connect.Database

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.serializer.MoshiSerializer
import io.github.jan.supabase.storage.Storage
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

const val SUPABASE_URL = "https://mmuxoqmhcekseyooghng.supabase.co"
const val SUPABASE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im1tdXhvcW1oY2Vrc2V5b29naG5nIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MjM1MTQwOTcsImV4cCI6MjAzOTA5MDA5N30.zNfrD6Gvr-sMcyu1CL6y17mDqoxYi73hubSLEv5EexA"


val supabase = createSupabaseClient(SUPABASE_URL, SUPABASE_KEY) {
    install(Postgrest)
    install(Auth){
        //alwaysAutoRefresh = false
        autoLoadFromStorage = false
    }
    install(Storage)
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




