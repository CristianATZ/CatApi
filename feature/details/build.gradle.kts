plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    // ksp
    alias(libs.plugins.google.devtools.ksp)
    // hilt
    alias(libs.plugins.dagger.hilt)
}

android {
    namespace = "com.devtorres.details"
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
    buildFeatures {
        compose = true
    }
}

dependencies {
    // core
    implementation(project(":core:navigation"))
    implementation(project(":core:data"))
    implementation(project(":core:model"))
    // feature
    implementation(project(":feature:common"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    // hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    // hilt viewmodel
    implementation(libs.androidx.hilt.navigation.compose)

    // viewmodel
    implementation(libs.androidx.lifecycle.viewModelCompose)

    // compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // icons
    implementation(libs.androidx.material.icons.extended.android)

    // navigation
    implementation(libs.androidx.navigation.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}