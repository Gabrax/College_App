package uni_connect.Database

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.serializer.MoshiSerializer
import io.github.jan.supabase.storage.Storage
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*


val supabase = createSupabaseClient(SUPABASE_URL, SUPABASE_KEY) {
    install(Postgrest)
    install(Auth){
        alwaysAutoRefresh = false
        autoLoadFromStorage = false
    }
    install(Storage)
    install(Realtime)
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




