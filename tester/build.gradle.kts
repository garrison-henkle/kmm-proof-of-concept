plugins {
    id(Plugins.androidApplication).version(Versions.androidPlugin)
    id(Plugins.kotlinAndroid).version(Versions.kotlin)
}

android {
    namespace = "com.you.tester"
    compileSdk = Config.compileSdkVersion

    defaultConfig {
        applicationId = "com.you.tester"
        minSdk = Config.minSdkVersion
        targetSdk = Config.targetSdkVersion
        versionCode = Config.versionCode
        versionName = Config.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.androidComposeCompiler
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(Deps.androidxActivityCompose)
    implementation(Deps.androidxComposeFoundation)
    implementation(Deps.androidxComposeMaterial)
    implementation(Deps.androidxComposeMaterialIconsCore)
    implementation(Deps.androidxComposeMaterialIconsExtended)
    implementation(Deps.androidxComposeUi)

    debugImplementation(Deps.androidxComposeUiTooling)
    debugImplementation(Deps.androidxComposeUiToolingPreview)

    testImplementation(Deps.androidxTestJunit)
    testImplementation(Deps.androidxTestOrchestrator)
    testImplementation(Deps.androidxTestRunner)

    androidTestImplementation(Deps.androidxComposeUiTestJunit4)
    androidTestImplementation(Deps.androidxComposeUiTestManifest)
}