android {
    compileSdkVersion 34

    defaultConfig {
        applicationId "com.bc.tvappvlc"
        minSdk 24
        targetSdk 34
        versionCode 1
        versionName "1.0"
    }

    buildFeatures {
        compose true
    }
    composeOptions {
        kotlinCompilerExtensionVersion '1.5.4' // Usa la versión que tengas disponible
    }
}

dependencies {
    implementation "androidx.core:core-ktx:1.12.0"
    implementation "androidx.lifecycle:lifecycle-runtime-ktx:2.6.2"
    implementation "androidx.activity:activity-compose:1.7.2"

    // Compose
    implementation(platform("androidx.compose:compose-bom:2023.10.01"))
    implementation "androidx.compose.material3:material3"
    implementation "androidx.compose.foundation:foundation"
    implementation "androidx.compose.ui:ui-tooling-preview"
    debugImplementation "androidx.compose.ui:ui-tooling"

    // JSON
    implementation 'org.json:json:20230618'

    // Coroutines
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3'
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3'
}
