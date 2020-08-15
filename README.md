# Spring Social SlideShare
[![GitHub Actions](https://github.com/t28hub/spring-social-slideshare/workflows/build/badge.svg)](https://github.com/t28hub/spring-social-slideshare/actions)
[![FOSSA Status](https://app.fossa.com/api/projects/custom%2B14538%2Fspring-social-slideshare.svg?type=shield)](https://app.fossa.com/projects/custom%2B14538%2Fspring-social-slideshare?ref=badge_shield)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=t28hub_spring-social-slideshare&metric=alert_status)](https://sonarcloud.io/dashboard?id=t28hub_spring-social-slideshare)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=t28hub_spring-social-slideshare&metric=coverage)](https://sonarcloud.io/dashboard?id=t28hub_spring-social-slideshare)
[![Apache 2.0](https://img.shields.io/badge/License-Apache%202.0-brightgreen.svg)](https://opensource.org/licenses/Apache-2.0)

The Spring Social SlideShare is an extension to Spring Social that enables integration with [SlideShare API](https://www.slideshare.net/developers).  
This library supports all operations officially provided by SlideShare.

## Table of Contents
* [Installation](#installation)
* [Usage](#usage)
  * [API Binding](#api-binding)
  * [Auto Configure](#auto-configure)
* [Examples](#examples)
  * [Retrieving a slideshow information](#retrieving-a-slideshow-information)
  * [Searching slideshows](#searching-slideshows)
  * [Retrieving tags by authenticated user](#retrieving-tags-by-authenticated-user)
  * [Checking whether a slideshow is favorited](#checking-whether-a-slideshow-is-favorited)
  * [Other examples](#other-examples)
* [Notes](#notes)
* [License](#license)

## Installation
The packages are provided by GitHub Package, and you can find a latest package on [packages](https://github.com/t28hub/spring-social-slideshare/packages/).  
See the following official documentations to install the package from GitHub Package.  
* [Apache Maven](https://docs.github.com/en/packages/using-github-packages-with-your-projects-ecosystem/configuring-apache-maven-for-use-with-github-packages)
* [Gradle](https://docs.github.com/en/packages/using-github-packages-with-your-projects-ecosystem/configuring-gradle-for-use-with-github-packages)

```kotlin
repositories {
    mavenCentral()
    jcenter()
    maven("https://repo.spring.io/libs-snapshot")
    maven {
        url = uri("https://maven.pkg.github.com/t28hub/spring-social-slideshare")
        credentials {
            username = YOUR_GITHUB_USERNAME
            password = YOUR_GITHUB_TOKEN
        }
    }
}

dependencies {
    implementation("io.t28.springframework.social.slideshare:spring-social-slideshare:1.0.0-SNAPSHOT")
}
```

## Usage
### API Binding
The Spring Social SlideShare offers integration with SlideShare API through the `SlideShare` interface and its implementation, `SlideShareTemplate`.  
See the [official documentation](https://www.slideshare.net/developers) to retrieve the API key and the shared secret.  
```kotlin
val slideShare = SlideShareTemplate(
    apiKey = "The API key provided by SlideShare", // Required
    sharedSecret = "The shared secret provided by SlideShare", // Required
    username = "The username of SlideShare", // Optional
    password = "The password of SlideShare" // Optional
)
```
Once instantiating a SlideShare class, you can perform the following operations:
* [`SlideshowOperations`](https://github.com/t28hub/spring-social-slideshare/blob/master/spring-social-slideshare/src/main/kotlin/io/t28/springframework/social/slideshare/api/SlideshowOperations.kt)
* [`UserOperations`](https://github.com/t28hub/spring-social-slideshare/blob/master/spring-social-slideshare/src/main/kotlin/io/t28/springframework/social/slideshare/api/UserOperations.kt)
* [`FavoriteOperations`](https://github.com/t28hub/spring-social-slideshare/blob/master/spring-social-slideshare/src/main/kotlin/io/t28/springframework/social/slideshare/api/FavoriteOperations.kt)

### Auto Configure
The Spring Social SlideShare also supports auto configuration.  
Supported properties are as following:

| Property| Description| Required |
|:---|:---|:---|
| `spring.social.slideshare.apiKey` | The API key provided by SlideShare. | `true` | 
| `spring.social.slideshare.sharedSecret` | The shared secret provided by SlideShare. | `true` | 
| `spring.social.slideshare.username` | The username of SlideShare. | `false` | 
| `spring.social.slideshare.password` | The password of SlideShare. | `false` | 

## Examples
### Retrieving a slideshow information
This program retrieves a detailed slideshow information by `https://www.slideshare.net/tatsuyamaki39/unit-testinandroid`.
```kotlin
val url = "https://www.slideshare.net/tatsuyamaki39/unit-testinandroid"
val options = GetSlideshowOptions(
    excludeTags = true,
    detailed = true
)
slideShare.slideshowOperations().getSlideshowById(url = url, options = options)
```

### Searching slideshows
This program searches for slideshows containing `kotlin` in English and sorts them by latest.
```kotlin
val slideShare = SlideShareTemplate("YOUR_API_KEY", "YOUR_SHARED_SECRET")
val options = SearchSlideshowsOptions(
    language = SearchSlideshowsOptions.Launguage.ENGLISH,
    sort = SearchSlideshowsOptions.Sort.LATEST,
    what = SearchSlideshowsOptions.SearchType.TEXT
)
slideShare.slideshowOperations().searchSlideshows("kotlin", options)
```

### Retrieving tags by authenticated user
This program retrieves tags by an authenticated user.
```kotlin
val slideShare = SlideShareTemplate("YOUR_API_KEY", "YOUR_SHARED_SECRET", "YOUR_USERNAME", "YOUR_PASSWORD")
slideShare.userOperations().getUserTags()
```

### Checking whether a slideshow is favorited
This program checks whether an authenticated user has already favorited a slideshow with the ID `49627175`.
```kotlin
val slideShare = SlideShareTemplate("YOUR_API_KEY", "YOUR_SHARED_SECRET", "YOUR_USERNAME", "YOUR_PASSWORD")
slideShare.favoriteOperations().checkFavorite("49627175")
```

### Other examples
See [spring-social-samples](https://github.com/t28hub/spring-social-slideshare/tree/master/spring-social-samples) module.  
The module is sample Spring Boot application and provides REST APIs using Spring Social SlideShare.

1. Get an API key and a shared secret
  * See the [official documentation](https://www.slideshare.net/developers).
2. Edit `application.properties`
  * The `application.properties` is located in [spring-social-samples/src/main/resources](https://github.com/t28hub/spring-social-slideshare/blob/master/spring-social-samples/src/main/resources/).
```properties
spring.social.slideshare.apiKey=YOUR_API_KEY
spring.social.slideshare.sharedSecret=YOUR_SHARED_SECRET
```
3. Run the application
```sh
$ ./gradlew :spring-social-samples:bootRun
```
4. Test the application
```sh
$ curl localhost:8080
```
5. Test Spring Social SlideShare
  * The application provides documentations using Swagger.
  * You can access the documentations at `http://localhost:8080/swagger-ui/` from your browser.

## Notes
Note that the SlideShare API puts your credentials (username and password) to query string as follows:
```
https://www.slideshare.net/api/2/get_slideshow?slideshow_id=1234567890&username=YOUR_USERNAME&password=YOUR_PASSWORD
```
This is described in the official documentation as follows:
> Requests that request private data from users, or that act on their behalf, must include the following parameters:
> * username: set this to the username of the account whose data is being requested.
> * password: set this to the password of the account whose data is being requested.

## License
[![FOSSA Status](https://app.fossa.com/api/projects/custom%2B14538%2Fspring-social-slideshare.svg?type=large)](https://app.fossa.com/projects/custom%2B14538%2Fspring-social-slideshare?ref=badge_large)
