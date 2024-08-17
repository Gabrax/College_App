import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform) // Kotlin Multiplatform plugin alias
    alias(libs.plugins.jetbrainsCompose)    // JetBrains Compose plugin alias
    kotlin("plugin.serialization") version "2.0.0"
    alias(libs.plugins.compose.compiler) // This might be redundant if covered by JetBrains Compose
}

repositories {
    mavenCentral() // This is now the primary repository for JavaFx
    google() // For AndroidX dependencies
    //maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    maven("https://jitpack.io")
}

kotlin {
    jvm("desktop")

    sourceSets {
        val desktopMain by getting

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.material3)
            implementation(compose.ui)
            //implementation("androidx.compose.material3:material3:1.2.1")
            //implementation("androidx.compose.material3:material3-window-size-class:1.2.1")
            //implementation("androidx.compose.material3:material3-adaptive-navigation-suite:1.3.0-beta05")
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            //implementation("androidx.compose.ui:ui:1.6.8")
            implementation("com.squareup.moshi:moshi:1.15.1")
            implementation("com.squareup.moshi:moshi-kotlin:1.15.1")
            implementation("io.github.jan-tennert.supabase:serializer-moshi:VERSION")
            implementation(project.dependencies.platform("io.github.jan-tennert.supabase:bom:2.6.0-beta-1"))
            implementation("io.github.jan-tennert.supabase:postgrest-kt")
            implementation("io.github.jan-tennert.supabase:gotrue-kt")
            implementation("io.github.jan-tennert.supabase:realtime-kt")
            implementation("io.ktor:ktor-client-content-negotiation:2.3.4")
            implementation("io.ktor:ktor-client-cio:2.3.4")
            implementation("io.ktor:ktor-client-core:2.3.4")
            implementation("io.ktor:ktor-client-json:2.3.4")
            implementation("io.ktor:ktor-client-serialization:2.3.4")
//            implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
//            implementation("androidx.compose.runtime:runtime-livedata:$1.6.8")
            implementation("ch.qos.logback:logback-classic:1.2.3")
            implementation("cafe.adriel.voyager:voyager-navigator:1.1.0-beta02")
            implementation("cafe.adriel.voyager:voyager-screenmodel:1.1.0-beta02")
            implementation("cafe.adriel.voyager:voyager-bottom-sheet-navigator:1.1.0-beta02")
            implementation("cafe.adriel.voyager:voyager-tab-navigator:1.1.0-beta02")
            implementation("cafe.adriel.voyager:voyager-transitions:1.1.0-beta02")
            implementation("com.stevdza-san:messagebarkmp:1.0.5")
            implementation("com.github.dfloureiro:news-api-kotlin:v2.1")
            implementation("io.reactivex.rxjava2:rxjava:2.2.21")
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}

compose.desktop {
    application {
        mainClass = "uni_connect.MainKt"
        nativeDistributions {
            modules("java.sql")
            modules("jdk.crypto.ec")
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Uni_Connect"


        }
    }
}
