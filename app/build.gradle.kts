@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    id("com.google.dagger.hilt.android") version "2.51.1"
    id("com.google.devtools.ksp") version "2.0.0-1.0.23"
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0"
}

android {
    namespace = "dev.kiko.qrcodegenerator"
    compileSdk = 34

    defaultConfig {
        applicationId = "dev.kiko.qrcodegenerator"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "2.0.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "drawable/onboard_1.png"
            excludes += "drawable/onboard_2.png"
            excludes += "drawable/onboard_3.png"
            excludes += "drawable/onboard_5.png"
        }
    }
    hilt {
        enableAggregatingTask = true
    }
    ksp {
        arg("hilt.supportedInjectionTypes", "JVM_STATIC")
    }
}

dependencies {
    // QR Code генерация и сканирование
    implementation("com.google.zxing:core:3.5.2")
    implementation("com.journeyapps:zxing-android-embedded:4.3.0")
    
    // Управление разрешениями
    implementation("com.google.accompanist:accompanist-permissions:0.30.1")

    //UI
    implementation(libs.stories)
    implementation(libs.coil.compose)
    implementation(libs.skydoves.cloudy)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.moshi)

    // Local Database
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation("androidx.room:room-ktx:2.6.1")

    // Navigation
    implementation(libs.destinationCore)
    ksp(libs.destinationKsp)

    // Json
    ksp(libs.moshi.kotlin.codegen)
    implementation(libs.moshi)

    // DI
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)


    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}



