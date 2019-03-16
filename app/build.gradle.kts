plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    compileSdkVersion(Versions.compileSdkVersion)
    defaultConfig {
        applicationId = "com.hadilq.liveevent.sample"
        minSdkVersion(Versions.minSdkVersion)
        targetSdkVersion(Versions.targetSdkVersion)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    kapt(Depends.archComponentsCompiler)

//    implementation(project(":lib"))
    implementation("${Versions.groupId}:${Versions.artifactId}:${Versions.libVersion}")

    implementation(Depends.kotlin)
    implementation(Depends.supportCompat)
    implementation(Depends.constraintLayout)
    implementation(Depends.archComponents)
}
