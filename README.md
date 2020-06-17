# Product hunt client

Unofficial client for Product Hunt.

## Screenshots

<div style="text-align:center">
    <img src="metadata/screenshots/hunt_dark.png"/>
    <img src="metadata/screenshots/hunt_light.png"/>
</div>

## Motivation

The focus of this project is to learn how to integrate OAuth and GraphQL, also how to use Dagger 2 for dependency injection.

## Libraries Used

- [**ApolloGraphQL**](https://github.com/apollographql/apollo-android/)
- [**CircleImage**](https://github.com/hdodenhof/CircleImageView)
- [**Coil**](https://github.com/coil-kt/coil)
- [**Dagger Hilt**](https://dagger.dev/hilt/)
- [**KotlinCoroutines**](https://github.com/Kotlin/kotlinx.coroutines)
- [**Lifecycle**](https://developer.android.com/topic/libraries/architecture)
- [**LiveData**](https://developer.android.com/topic/libraries/architecture)
- [**Moshi**](https://github.com/square/moshi/)
- [**mockk**](https://github.com/mockk/mockk)
- [**Retrofit**](https://github.com/square/retrofit)
- [**Timber**](https://github.com/JakeWharton/timber)
- [**ViewBinding**](https://developer.android.com/topic/libraries/architecture)
- [**ViewModel**](https://developer.android.com/topic/libraries/architecture)
- [**WorkManager**](https://developer.android.com/topic/libraries/architecture)

## Gradle Setup 🐘

This project is using [**Gradle Kotlin DSL**](https://docs.gradle.org/current/userguide/kotlin_dsl.html) as well as the [Plugin DSL](https://docs.gradle.org/current/userguide/plugins.html#sec:plugins_block) to setup the build.

Dependencies are centralized inside the [Dependencies.kt](buildSrc/src/main/java/Dependencies.kt) file in the `buildSrc` folder. This provides convenient auto-completion when writing your gradle files.

## Static Analysis 🔍

The project uses [**ktlint**](https://github.com/pinterest/ktlint) with the [ktlint-gradle](https://github.com/jlleitschuh/ktlint-gradle) plugin to format your code.

## CI ⚙️

This template is using [**GitHub Actions**](https://github.com/cortinico/kotlin-android-template/actions) as CI.

There are currently the following workflows available:

- [Validate Gradle Wrapper](.github/workflows/gradle-wrapper-validation.yml) - Will check that the gradle wrapper has a valid checksum.
- [Build and check](.github/workflows/build-and-check.yaml) - Will run the `build` and `check` task.
- [Deploy staging](.github/workflows/deploy-staging.yaml) - Will deploy the app to Google play.

## Contributing 🤝

Feel free to open a issue or submit a pull request for any bugs/improvements.
