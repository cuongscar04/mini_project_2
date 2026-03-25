plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.miniproject2"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.miniproject2"
        minSdk = 24
        targetSdk = 36
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
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
<<<<<<< HEAD

    // Room Database
    // Room Database
    val room_version = "2.5.2"
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
=======
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler) // Dùng annotationProcessor vì bạn code Java
>>>>>>> 3ca8ad7785e38533e753f50dd77445108896c8e9
}