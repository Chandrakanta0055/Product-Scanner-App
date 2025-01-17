plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.posscanner"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.posscanner"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    buildFeatures {
        viewBinding =true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //Volly
    implementation ("com.android.volley:volley:1.2.1")

    //Scanner
    implementation ("com.journeyapps:zxing-android-embedded:4.3.0")

    //using gson
    implementation("com.google.code.gson:gson:2.8.8")

    //pdf
    implementation("com.itextpdf:itext7-core:7.1.16")



}