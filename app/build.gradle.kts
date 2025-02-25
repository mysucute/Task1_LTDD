plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.task1"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.task1"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)


    dependencies {
        implementation ("androidx.compose.ui:ui-tooling-preview:1.3.1") //cung cấp công cụ preview
        implementation ("androidx.compose.material:material:1.3.1") // button, card,topappbar, bottomnavigation
        implementation ("androidx.compose.foundation:foundation:1.3.1") // cơ bản lazy, lazzyrow, imgae, clickable, ..
        implementation ("androidx.compose.runtime:runtime:1.3.1") // quản lí trng thái (lưu dlieu fform, cập nhât ui khi có thay đổi) remember, mutableStateOf
        implementation ("androidx.navigation:navigation-compose:2.5.3") //điều hướng
        implementation ("androidx.compose.ui:ui-graphics:1.3.1")// hình ảnh, màuăsắc, animation
        implementation ("androidx.navigation:navigation-testing:2.7.5")
    }


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
