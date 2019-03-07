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
object Depends {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin_version}"
    const val supportCompat = "androidx.appcompat:appcompat:${Versions.androidx_lib_version}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraint_layout_version}"
    const val archComponents = "androidx.lifecycle:lifecycle-extensions:${Versions.androidx_lifecycle_lib_version}"
    const val archComponentsCompiler =
        "androidx.lifecycle:lifecycle-compiler:${Versions.androidx_lifecycle_lib_version}"
    const val junit = "junit:junit:${Versions.junit_version}"
    const val coreTesting = "androidx.arch.core:core-testing:${Versions.androidx_testing}"
    const val mockito = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockito_version}"
}