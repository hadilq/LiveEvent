/**
 * Copyright 2019 Hadi Lashkari Ghouchani

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

 * http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdkVersion(Versions.compileSdkVersion)
    defaultConfig {
        minSdkVersion(Versions.minSdkVersion)
        targetSdkVersion(Versions.targetSdkVersion)
        versionName = Versions.libVersion
    }
}

dependencies {
    implementation(Depends.kotlin)
    implementation(Depends.archComponents)

    testImplementation(Depends.kotlin)
    testImplementation(Depends.junit)
    testImplementation(Depends.coreTesting)
    testImplementation(Depends.mockito)
}

if (project.hasProperty("signing.keyId")) {
    apply { from("../build-system/deploy.gradle") }
}