plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    // ksp
    alias(libs.plugins.google.devtools.ksp)
    // hilt
    alias(libs.plugins.dagger.hilt)
    // serialization
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.devtorres.network"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    // hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    // network
    implementation(platform(libs.retrofit.bom))
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    // json parsing
    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}