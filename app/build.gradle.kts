plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.fiek.medicalapplication_paisjemobile"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.fiek.medicalapplication_paisjemobile"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    packagingOptions {
        exclude ("META-INF/NOTICE.md")
        exclude ("META-INF/LICENSE.md")
        exclude ("META-INF/LICENSE")
    }

    buildTypes {
        debug {
            isDebuggable = true // Shënohet me `isDebuggable`
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures{
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    packagingOptions {
        exclude("META-INF/NOTICE.md")
        exclude("META-INF/LICENSE.md")
    }

}

dependencies {

    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0")
    implementation("com.google.firebase:firebase-auth:23.1.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    implementation("pl.droidsonroids.gif:android-gif-drawable:1.2.24")
    implementation ("pl.droidsonroids.gif:android-gif-drawable:1.2.23")
    implementation ("com.google.android.material:material:1.6.0")
    implementation("com.sun.mail:android-mail:1.6.7")
    implementation("com.sun.mail:android-activation:1.6.7")
    implementation ("org.mindrot:jbcrypt:0.4")



}
