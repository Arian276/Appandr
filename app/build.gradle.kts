plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    // Si usás Glide con KAPT para código generado, descomenta:
    // id("kotlin-kapt")
}

android {
    namespace = "com.bc.tvappvlc"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.bc.tvappvlc"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        vectorDrawables { useSupportLibrary = true }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug { isMinifyEnabled = false }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions { jvmTarget = "17" }

    // Si en tu proyecto hay pantallas Compose, podés dejar esto en true.
    // Si no usás Compose para nada, podés quitar el bloque buildFeatures/composeOptions.
    buildFeatures {
        // compose = true
        viewBinding = true
    }
    // composeOptions {
    //     kotlinCompilerExtensionVersion = "1.5.14"
    // }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // AppCompat / Activity / Material
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.activity:activity-ktx:1.9.2")
    implementation("com.google.android.material:material:1.12.0")

    // RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.3.2")

    // Glide (para imágenes en Adapter)
    implementation("com.github.bumptech.glide:glide:4.16.0")
    // Si usás anotaciones de Glide:
    // kapt("com.github.bumptech.glide:compiler:4.16.0")

    // Media3 (ExoPlayer)
    implementation("androidx.media3:media3-exoplayer:1.4.1")
    implementation("androidx.media3:media3-ui:1.4.1")

    // (Opcional) Coil si lo prefieres en lugar de Glide
    // implementation("io.coil-kt:coil:2.6.0")

    // (Opcional) Compose – solo si lo estás usando en otras pantallas
    // implementation(platform("androidx.compose:compose-bom:2024.09.00"))
    // implementation("androidx.compose.ui:ui")
    // implementation("androidx.compose.material3:material3")
    // implementation("androidx.compose.ui:ui-tooling-preview")
    // debugImplementation("androidx.compose.ui:ui-tooling")
}
