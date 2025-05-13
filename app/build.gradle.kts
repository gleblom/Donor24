import java.util.*
plugins {
    alias(libs.plugins.android.application)
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin").version("2.0.1")

}


android {
    namespace = "com.example.test"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.test"
        minSdk = 29
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"

            )
            signingConfig = signingConfigs.getByName("debug")

        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation (libs.retrofit) // implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation (libs.converter.gson) //implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation(libs.logging.interceptor) // implementation ("com.squareup.okhttp3:logging-interceptor:4.9.3")
    implementation(libs.okhttp) // implementation ("com.squareup.okhttp3:okhttp:4.12.0")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.car.ui.lib)
    androidTestImplementation(libs.espresso.contrib)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation (libs.ext.junit)
    androidTestImplementation (libs.espresso.core)
    androidTestImplementation (libs.runner)
    androidTestImplementation (libs.test.rules)
}