object Sdk {
    const val MIN_SDK_VERSION = 21
    const val TARGET_SDK_VERSION = 29
    const val COMPILE_SDK_VERSION = 29
}

object Versions {
    const val ANDROIDX_TEST_EXT = "1.1.1"
    const val ANDROIDX_TEST = "1.2.0"
    const val APPCOMPAT = "1.1.0"
    const val CONSTRAINT_LAYOUT = "1.1.3"
    const val RECYCLER_VIEW = "1.1.0"
    const val FRAGMENT = "1.2.3"
    const val ACTIVITY = "1.1.0"
    const val CORE_KTX = "1.2.0"
    const val LEGACY_SUPPORT = "1.0.0"
    const val MATERIAL = "1.2.0-alpha05"
    const val BROWSER = "1.0.0"
    const val KOTLIN_COROUTINES = "1.3.6"
    const val APOLLO_GRAPHQL = "2.0.0"
    const val COIL = "0.10.0"
    const val ESPRESSO_CORE = "3.2.0"
    const val JUNIT = "4.13"
    const val KTLINT = "0.36.0"
    const val LIFECYCLE = "2.3.0-alpha02"
    const val NAVIGATION = "2.3.0-alpha04"
    const val DAGGER = "2.27"
    const val ASSISTED_INJECT = "0.5.2"
    const val OKHTTP = "4.3.1"
    const val RETROFIT = "2.7.1"
    const val RETROFIT_COROUTINES = "0.9.2"
    const val MOSHI = "1.9.2"
    const val PAGING = "2.1.2"
    const val TIMBER = "4.7.1"
    const val CIRCLE_IMAGE = "3.1.0"
    const val MOCKK = "1.10.0"
    const val TRUTH = "1.0.1"
    const val ANDROIDX_ARCH_CORE = "2.1.0"
    const val WORK_MANAGER = "2.3.3"
    const val MOCK_WEB_SERVER = "4.7.2"
}

object BuildPluginsVersion {
    const val AGP = "3.6.3"
    const val KOTLIN = "1.3.72"
    const val KTLINT = "9.2.1"
    const val VERSIONS_PLUGIN = "0.28.0"
    const val APOLLO_PLUGIN = "2.0.0"
    const val NAVIGATION_PLUGIN = "2.3.0-alpha04"
}

object SupportLibs {
    const val ANDROIDX_APPCOMPAT = "androidx.appcompat:appcompat:${Versions.APPCOMPAT}"
    const val ANDROIDX_CONSTRAINT_LAYOUT =
        "com.android.support.constraint:constraint-layout:${Versions.CONSTRAINT_LAYOUT}"
    const val ANDROIDX_CORE_KTX = "androidx.core:core-ktx:${Versions.CORE_KTX}"
    const val ANDROIDX_LEGACY_SUPPORT =
        "androidx.legacy:legacy-support-v4:${Versions.LEGACY_SUPPORT}"
    const val RECYCLER_VIEW = "androidx.recyclerview:recyclerview:${Versions.RECYCLER_VIEW}"
    const val FRAGMENT_KTX = "androidx.fragment:fragment-ktx:${Versions.FRAGMENT}"
    const val ACTIVITY_KTX = "androidx.activity:activity-ktx:$${Versions.ACTIVITY}"
    const val BROWSER = "androidx.browser:browser:${Versions.BROWSER}"
}

object Modules {
    const val LIB = ":lib"
}

object Libraries {
    const val MATERIAL = "com.google.android.material:material:${Versions.MATERIAL}"

    const val LIFECYCLE_VIEWMODEL =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.LIFECYCLE}"
    const val LIFECYCLE_LIVEDATA = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.LIFECYCLE}"
    const val LIFECYCLE_VIEWMODEL_SAVEDSTATE =
        "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.LIFECYCLE}"
    const val LIFECYCLE_COMMON_JAVA_8 =
        "androidx.lifecycle:lifecycle-common-java8:${Versions.LIFECYCLE}"

    const val KOTLIN_COROUTINES_CORE =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.KOTLIN_COROUTINES}"
    const val KOTLIN_COROUTINES_ANDROID =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.KOTLIN_COROUTINES}"

    const val APOLLO_RUNTIME = "com.apollographql.apollo:apollo-runtime:${Versions.APOLLO_GRAPHQL}"
    const val APOLLO_COROUTINES =
        "com.apollographql.apollo:apollo-coroutines-support:${Versions.APOLLO_GRAPHQL}"
    const val APOLLO_NORMALIZED_CASH =
        "com.apollographql.apollo:apollo-normalized-cache-sqlite:${Versions.APOLLO_GRAPHQL}"
    const val APOLLO_ANDROID_SUPPORT =
        "com.apollographql.apollo:apollo-android-support:${Versions.APOLLO_GRAPHQL}"

    const val COIL = "io.coil-kt:coil:${Versions.COIL}"

    const val NAVIGATION_FRAGMENT =
        "androidx.navigation:navigation-fragment-ktx:${Versions.NAVIGATION}"
    const val NAVIGATION_UI = "androidx.navigation:navigation-ui-ktx:${Versions.NAVIGATION}"
    const val NAVIGATION_SAFE_ARGS =
        "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.NAVIGATION}"

    const val DAGGER = "com.google.dagger:dagger:${Versions.DAGGER}"
    const val DAGGER_COMPLIER = "com.google.dagger:dagger-compiler:${Versions.DAGGER}"

    const val ASSISTED_INJECT =
        "com.squareup.inject:assisted-inject-annotations-dagger2:${Versions.ASSISTED_INJECT}"
    const val ASSISTED_INJECT_PROCESSOR =
        "com.squareup.inject:assisted-inject-processor-dagger2:${Versions.ASSISTED_INJECT}"

    // OKHTTP
    const val OKHTTP = "com.squareup.okhttp3:okhttp:${Versions.OKHTTP}"
    const val OKHTTP_LOGGING_INTERCEPTOR =
        "com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP}"

    // RETROFIT
    const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
    const val RETROFIT_MOSHI_CONVERTER =
        "com.squareup.retrofit2:converter-moshi:${Versions.RETROFIT}"
    const val RETROFIT_COROUTINES_ADAPTER =
        "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.RETROFIT_COROUTINES}"

    const val MOSHI = "com.squareup.moshi:moshi:${Versions.MOSHI}"
    const val MOSHI_CODEGEN = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.MOSHI}"

    const val PAGING = "androidx.paging:paging-runtime-ktx:${Versions.PAGING}"

    const val TIMBER = "com.jakewharton.timber:timber:${Versions.TIMBER}"

    const val CIRCLE_IMAGE_VIEW = "de.hdodenhof:circleimageview:${Versions.CIRCLE_IMAGE}"

    const val WORK_MANAGER = "androidx.work:work-runtime-ktx:${Versions.WORK_MANAGER}"
}

object TestingLib {
    const val JUNIT = "junit:junit:${Versions.JUNIT}"
    const val TRUTH = "com.google.truth:truth:${Versions.TRUTH}"
    const val MOCKK = "io.mockk:mockk:${Versions.MOCKK}"
    const val KOTLIN_COROUTINE_TEST =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.KOTLIN_COROUTINES}"
    const val PAGING = "androidx.paging:paging-common-ktx:${Versions.PAGING}"

    // Test helpers for LiveData
    const val ANDROIDX_ARCH_CORE = "androidx.arch.core:core-testing:${Versions.ANDROIDX_ARCH_CORE}"

    const val MOCK_WEB_SERVER = "com.squareup.okhttp3:mockwebserver:${Versions.MOCK_WEB_SERVER}"
}

object AndroidTestingLib {
    const val ANDROIDX_TEST_CORE = "androidx.test:core-ktx:${Versions.ANDROIDX_TEST}"
    const val ANDROIDX_TEST_RULES = "androidx.test:rules:${Versions.ANDROIDX_TEST}"
    const val ANDROIDX_TEST_RUNNER = "androidx.test:runner:${Versions.ANDROIDX_TEST}"
    const val ANDROIDX_TEST_EXT_JUNIT = "androidx.test.ext:junit-ktx:${Versions.ANDROIDX_TEST_EXT}"
    const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO_CORE}"
    const val WORK_MANAGER = "androidx.work:work-testing:${Versions.WORK_MANAGER}"
}
