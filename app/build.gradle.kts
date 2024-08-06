plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
}

android {
    namespace = "com.example.cloudkitchens"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.cloudkitchens"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            buildConfigField("String", "BASE_URL", "\"http://192.168.1.178:8080/\"")
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
        buildConfig = true
    }
    testOptions {
        unitTests.all {
            // Set max heap size for the JVM
            it.maxHeapSize = "2g" // You can increase this value as needed
        }
    }

}



dependencies {
    // Room
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")


    kapt("androidx.room:room-compiler:2.6.1")

    // Retrofit & Gson (excluding support library)
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")


    // Kotlin Coroutines (excluding support library)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0-RC")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0-RC")

    // Dagger
    implementation("com.google.dagger:dagger:2.51.1")
    kapt("com.google.dagger:dagger-compiler:2.51.1")

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.3")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.3")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.3")

    // AndroidX and UI
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.viewpager2:viewpager2:1.1.0-beta02")
    implementation("androidx.fragment:fragment-ktx:1.6.2")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.5")
    implementation(libs.androidx.swiperefreshlayout)

    // Other
    implementation("com.robinhood.spark:spark:1.2.0")
    implementation("com.airbnb.android:lottie:5.2.0")
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")


    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.junit)
    testImplementation(libs.junit.junit)
    testImplementation(libs.junit.junit)
    testImplementation(libs.junit.jupiter)

    testImplementation("junit:junit:4.13.2") // Or a newer version
    testImplementation("androidx.arch.core:core-testing:2.1.0") // Or a newer version
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0") // Or a newer version

    testImplementation("org.mockito:mockito-core:5.0.0")// Or a newer version
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
}
