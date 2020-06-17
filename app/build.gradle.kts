plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-android-extensions")
    id("com.apollographql.apollo")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
    id("kotlin-android")
}

apollo {
    generateKotlinModels.set(true)
}

android {
    compileSdkVersion(Sdk.COMPILE_SDK_VERSION)

    defaultConfig {
        minSdkVersion(Sdk.MIN_SDK_VERSION)
        targetSdkVersion(Sdk.TARGET_SDK_VERSION)

        applicationId = AppCoordinates.APP_ID
        versionCode = AppCoordinates.APP_VERSION_CODE
        versionName = AppCoordinates.APP_VERSION_NAME
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    lintOptions {
        isWarningsAsErrors = true
        isAbortOnError = false
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(Modules.LIB))

    implementation(kotlin("stdlib-jdk7"))
    implementation(SupportLibs.ANDROIDX_APPCOMPAT)
    implementation(SupportLibs.ANDROIDX_CONSTRAINT_LAYOUT)
    implementation(SupportLibs.ANDROIDX_CORE_KTX)
    implementation(SupportLibs.RECYCLER_VIEW)
    implementation(SupportLibs.ANDROIDX_LEGACY_SUPPORT)
    implementation(SupportLibs.FRAGMENT_KTX)
    implementation(SupportLibs.ACTIVITY_KTX)
    implementation(SupportLibs.BROWSER)
    implementation(Libraries.MATERIAL)
    implementation(Libraries.COIL)
    implementation(Libraries.CIRCLE_IMAGE_VIEW)
    implementation(Libraries.DAGGER_HILT)
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    kapt(Libraries.DAGGER_HILT_COMPLIER)
    implementation(Libraries.DAGGER_HILT_VIEWMODEL)
    implementation(Libraries.DAGGER_HILT_WORKMANGER)
    kapt(Libraries.DAGGER_HILT_JETPACK_COMPLIER)
    implementation(Libraries.NAVIGATION_FRAGMENT)
    implementation(Libraries.NAVIGATION_UI)
    implementation(Libraries.LIFECYCLE_LIVEDATA)
    implementation(Libraries.LIFECYCLE_VIEWMODEL)
    implementation(Libraries.LIFECYCLE_VIEWMODEL_SAVEDSTATE)
    implementation(Libraries.KOTLIN_COROUTINES_CORE)
    implementation(Libraries.KOTLIN_COROUTINES_ANDROID)
    implementation(Libraries.OKHTTP)
    implementation(Libraries.OKHTTP_LOGGING_INTERCEPTOR)
    implementation(Libraries.RETROFIT)
    implementation(Libraries.RETROFIT_MOSHI_CONVERTER)
    implementation(Libraries.RETROFIT_COROUTINES_ADAPTER)
    implementation(Libraries.MOSHI)
    kapt(Libraries.MOSHI_CODEGEN)
    implementation(Libraries.APOLLO_RUNTIME)
    implementation(Libraries.APOLLO_COROUTINES)
    implementation(Libraries.APOLLO_NORMALIZED_CASH)
    implementation(Libraries.APOLLO_ANDROID_SUPPORT)
    implementation(Libraries.PAGING)
    implementation(Libraries.TIMBER)
    implementation(Libraries.WORK_MANAGER)

    testImplementation(TestingLib.JUNIT)
    testImplementation(TestingLib.TRUTH)
    testImplementation(TestingLib.MOCKK)
    testImplementation(TestingLib.ANDROIDX_ARCH_CORE)
    testImplementation(TestingLib.KOTLIN_COROUTINE_TEST)
    testImplementation(TestingLib.MOCK_WEB_SERVER)

    androidTestImplementation(AndroidTestingLib.ANDROIDX_TEST_EXT_JUNIT)
    androidTestImplementation(AndroidTestingLib.ANDROIDX_TEST_RULES)
    androidTestImplementation(TestingLib.ANDROIDX_ARCH_CORE)
    androidTestImplementation(AndroidTestingLib.ESPRESSO_CORE)
    androidTestImplementation(AndroidTestingLib.WORK_MANAGER)
}
