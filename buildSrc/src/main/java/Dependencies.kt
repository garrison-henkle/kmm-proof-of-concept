object Versions {
    const val androidActivityCompose = "1.6.1"
    const val androidCompose = "1.3.0"
    const val androidComposeCompiler = "1.3.2"
    const val androidDatastorePreferences = "1.0.0"
    const val androidPlugin = "7.3.1"
    const val androidTest = "1.4.0"
    const val androidTestJunitExt = "1.1.3"
    const val buildKonfig = "0.13.3"
    const val junit = "4.13.2"
    const val kermit = "1.1.3"
    const val kmmBridge = "0.3.3"
    const val kmmSettings = "1.0.0-RC"
    const val kotlin = "1.7.20"
    const val kotlinx = "1.6.4"
    const val kotlinxDatetime = "0.4.0"
    const val kotlinxSerialization = "1.4.1"
    const val kswift = "0.6.1"
    const val ktor = "2.1.3"
    const val sqldelight = "1.5.3"
    const val uuid = "0.5.0"
}

object Deps {
    const val androidxActivityCompose =
        "androidx.activity:activity-compose:${Versions.androidActivityCompose}"
    const val androidxComposeFoundation =
        "androidx.compose.foundation:foundation:${Versions.androidCompose}"
    const val androidxComposeMaterial =
        "androidx.compose.material:material:${Versions.androidCompose}"
    const val androidxComposeMaterialIconsCore =
        "androidx.compose.material:material-icons-core:${Versions.androidCompose}"
    const val androidxComposeMaterialIconsExtended =
        "androidx.compose.material:material-icons-extended:${Versions.androidCompose}"
    const val androidxComposeUi = "androidx.compose.ui:ui:${Versions.androidCompose}"
    const val androidxComposeUiTestJunit4 =
        "androidx.compose.ui:ui-test-junit4:${Versions.androidCompose}"
    const val androidxComposeUiTestManifest =
        "androidx.compose.ui:ui-test-manifest:${Versions.androidCompose}"
    const val androidxComposeUiTooling = "androidx.compose.ui:ui-tooling:${Versions.androidCompose}"
    const val androidxComposeUiToolingPreview =
        "androidx.compose.ui:ui-tooling-preview:${Versions.androidCompose}"
    const val androidxTestJunit = "androidx.test.ext:junit:${Versions.androidTestJunitExt}"
    const val androidxTestOrchestrator = "androidx.test:orchestrator:${Versions.androidTest}"
    const val androidxTestRunner = "androidx.test:runner:${Versions.androidTest}"
    const val junit = "junit:junit:${Versions.junit}"
    const val kermit = "co.touchlab:kermit:${Versions.kermit}"
    const val kermitTest = "co.touchlab:kermit-test:${Versions.kermit}"
    const val kotlinTestAnnotationsCommon =
        "org.jetbrains.kotlin:kotlin-test-annotations-common:${Versions.kotlin}"
    const val kotlinTestCommon = "org.jetbrains.kotlin:kotlin-test-common:${Versions.kotlin}"
    const val kotlinxCoroutinesCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinx}"
    const val kotlinxCoroutinesTest =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.kotlinx}"
    const val kotlinxDatetime = "org.jetbrains.kotlinx:kotlinx-datetime:${Versions.kotlinxDatetime}"
    const val kotlinxSerialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.kotlinxSerialization}"
    const val ktorClientAndroid = "io.ktor:ktor-client-android:${Versions.ktor}"
    const val ktorClientContentNegotiation =
        "io.ktor:ktor-client-content-negotiation:${Versions.ktor}"
    const val ktorClientCore = "io.ktor:ktor-client-core:${Versions.ktor}"
    const val ktorClientIos = "io.ktor:ktor-client-ios:${Versions.ktor}"
    const val ktorClientLogging = "io.ktor:ktor-client-logging:${Versions.ktor}"
    const val ktorEngineAndroid = "io.ktor:ktor-client-android:${Versions.ktor}"
    const val ktorEngineDarwin = "io.ktor:ktor-client-darwin:${Versions.ktor}"
    const val ktorSerializationKotlinxJson =
        "io.ktor:ktor-serialization-kotlinx-json:${Versions.ktor}"
    const val multiplatformSettingsCoroutines =
        "com.russhwolf:multiplatform-settings-coroutines:${Versions.kmmSettings}"
    const val multiplatformSettingsTest =
        "com.russhwolf:multiplatform-settings-test:${Versions.kmmSettings}"
    const val sqldelightAndroid = "com.squareup.sqldelight:android-driver:${Versions.sqldelight}"
    const val sqldelightIos = "com.squareup.sqldelight:native-driver:${Versions.sqldelight}"
    const val uuid = "com.benasher44:uuid:${Versions.uuid}"
}

object ClasspathDeps {
    const val buildKonfig =
        "com.codingfeline.buildkonfig:buildkonfig-gradle-plugin:${Versions.buildKonfig}"
    const val kotlinGradle =
        "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val kotlinxSerialization =
        "org.jetbrains.kotlin:kotlin-serialization:${Versions.kotlin}"
    const val sqldelight = "com.squareup.sqldelight:gradle-plugin:${Versions.sqldelight}"
}

object Plugins {
    const val androidApplication = "com.android.application"
    const val androidLibrary = "com.android.library"
    const val buildKonfig = "com.codingfeline.buildkonfig"
    const val kmmBridge = "co.touchlab.faktory.kmmbridge"
    const val kotlinAndroid = "org.jetbrains.kotlin.android"
    const val kotlinMultiplatform = "org.jetbrains.kotlin.multiplatform"
    const val kotlinSerialization = "org.jetbrains.kotlin.plugin.serialization"
    const val kswift = "dev.icerock.moko.kswift"
    const val sqldelight = "com.squareup.sqldelight"
}