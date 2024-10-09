import com.android.build.api.dsl.ViewBinding
plugins {
    id("kotlin-kapt")
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {



    namespace = "com.mandrykevich.testapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.mandrykevich.testapp"
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
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures{
        viewBinding = true
    }
}

dependencies {


    implementation(project(":domain"))
    implementation(project(":data"))

    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
    implementation ("androidx.room:room-runtime:2.5.0")
    implementation(libs.androidx.ui.desktop)
    implementation(libs.androidx.databinding.runtime)
    kapt ("androidx.room:room-compiler:2.5.0")
    implementation ("androidx.room:room-ktx:2.5.0")


    implementation(libs.retrofit)
    implementation(libs.gson)


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}