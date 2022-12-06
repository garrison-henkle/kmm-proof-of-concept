import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import dev.icerock.moko.kswift.plugin.feature.SealedToSwiftEnumFeature
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import org.jetbrains.kotlin.konan.properties.loadProperties

val localProperties = loadProperties(rootProject.file("local.properties").absolutePath)

buildscript {
    dependencies {
        classpath(ClasspathDeps.kotlinGradle)
        classpath(ClasspathDeps.buildKonfig)
        classpath(ClasspathDeps.kotlinxSerialization)
//        classpath(ClasspathDeps.sqldelight)
    }
}

plugins {
    id(Plugins.androidLibrary).version(Versions.androidPlugin)
    id(Plugins.buildKonfig).version(Versions.buildKonfig)
    id(Plugins.kmmBridge).version(Versions.kmmBridge)
    id(Plugins.kotlinMultiplatform).version(Versions.kotlin)
    id(Plugins.kotlinSerialization).version(Versions.kotlin)
    id(Plugins.kswift).version(Versions.kswift)
//    id(Plugins.sqldelight).version(Versions.sqldelight)
}

kotlin {
    android()

//    val xcf = XCFramework()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
//            xcf.add(this)
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Deps.kotlinxCoroutinesCore)
                implementation(Deps.kotlinxDatetime)
                implementation(Deps.ktorClientCore)
                implementation(Deps.ktorClientContentNegotiation)
                implementation(Deps.ktorClientLogging)
                implementation(Deps.ktorSerializationKotlinxJson)
                implementation(Deps.multiplatformSettingsCoroutines)
                implementation(Deps.uuid)

                api(Deps.kermit) //need api to export to iOS
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(Deps.kermitTest)
                implementation(Deps.kotlinTestCommon)
                implementation(Deps.kotlinTestAnnotationsCommon)
                implementation(Deps.kotlinxCoroutinesTest)
                implementation(Deps.multiplatformSettingsTest)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(Deps.ktorClientAndroid)
                implementation(Deps.ktorEngineAndroid)
//                implementation(Deps.sqldelightAndroid)
            }
        }
        val androidTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)

            dependencies {
                implementation(Deps.ktorClientIos)
                implementation(Deps.ktorEngineDarwin)
//                implementation(Deps.sqldelightIos)
            }
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }


}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

android {
    namespace = Config.packageName
    compileSdk = Config.compileSdkVersion
    defaultConfig {
        minSdk = Config.minSdkVersion
        targetSdk = Config.targetSdkVersion
    }
}

buildkonfig {
    packageName = Config.packageName

    val apiDomain = "apiDomain"

    defaultConfigs {
        buildConfigField(
            type = STRING,
            name = apiDomain,
            value = localProperties[apiDomain] as? String
                ?: throw Exception("'$apiDomain' is not set")
        )
    }
}

kmmbridge {
    githubReleaseVersions()
    githubReleaseArtifacts()
    spm()
    versionPrefix.set("0.0")
}

kswift {
    install(SealedToSwiftEnumFeature)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

task("publishKMMArtifacts", type = GradleBuild::class) {
    val token = localProperties.getProperty("githubPublishToken")
    val repo = localProperties.getProperty("githubRepo")
    startParameter.projectProperties = mapOf(
        "GITHUB_PUBLISH_TOKEN" to token,
        "GITHUB_REPO" to repo
    )
    tasks = listOf("kmmBridgePublish")
}
