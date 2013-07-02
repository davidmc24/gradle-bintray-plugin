# Overview
The Bintray plugin is a [Gradle][] plugin to allow easier use of [Bintray][] repositories.  It is available for use under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).  If you have any issues, please check the [issue tracker](https://github.com/davidmc24/gradle-bintray-plugin/issues), and if there isn't anything relevant, submit a new issue.

This plugin is unlikely to receive further updates, as Gradle 1.7 looks like it will have this type of functionality built-in.

[![Build Status](https://drone.io/github.com/davidmc24/gradle-bintray-plugin/status.png)](https://drone.io/github.com/davidmc24/gradle-bintray-plugin/latest)

# Installation

If you aren't already using the [Gradle Wrapper](http://www.gradle.org/docs/current/userguide/gradle_wrapper.html), start now.  It makes it easy for people using your project to build with Gradle without requiring Gradle to be installed ahead of time, as well as providing an easy way to encourage use of the intended version of Gradle.

To use the Bintray plugin:

* Download "Bintray.gradle" into the `gradle` directory created by the Gradle Wrapper based on one of the download links below.
* Configure your build.gradle based on the example below.  Depending on your codebase (multi-project, etc.) you may need to tweak the path a little.

## build.gradle

```groovy
buildscript {
    apply from: 'gradle/Bintray.gradle'
}
```

Note that the `apply` statement **MUST** be in the buildscript block and before any bintray repository definitions in order to work as intended.  If you apply the script outside of the buildscript block, the `bintray` extension to the buildscript `RepositoryHandler` will not be registered in time for use resolving buildscript dependencies.

## Download Links

Creating this plugin required the usage of Gradle internal APIs which may not be stable across versions.  Below is a matrix of tested compatible versions.  In general, it's best to use the latest version that's shown as tested with your version of Gradle.

Gradle Version | Plugin Version
-------------: | --------------
1.4            | [0.1.0](http://dl.bintray.com/content/davidmc24/gradle-plugins/us/carrclan/david/gradle/gradle-bintray-plugin/0.1.0/Bintray.gradle?direct)

You can get the latest development version [here](https://raw.github.com/davidmc24/gradle-bintray-plugin/master/Bintray.gradle).

# Configuration

The plugin adds special syntax in `repositories` blocks to declare Bintray repositories.

## Authentication

Bintray-owned repositories (such as the [JCenter][] repository) allow anonymous downloads via Gradle, but others don't.  For them, you'll need to configure authentication.  The recommended method is to add `bintrayUserName` and `bintrayApiKey` properties to your gradle.properties file, based on the example below:

```
bintrayUserName = joe
bintrayApiKey = 1234567890abcdef1234567890abcdef1234567890abcdef1234567890abcdef
```

## Configuration Example

```groovy
repositories {
    bintray.jcenter() // Bintray-owned, allows anonymous access
    bintray.repo(repoOwner: 'davidmc24', repoName: 'gradle-plugins') // Uses credentials from gradle.properties
    bintray.repo(repoOwner: 'WowCorp', repoName: 'AmazingSoftware') {
        credentials {
            username = 'joe'
            password = '1234567890abcdef1234567890abcdef1234567890abcdef1234567890abcdef'
        }
    }
}
```

[gradle]: http://www.gradle.org/
[bintray]: https://www.bintray.com/
[jcenter]: https://bintray.com/repo/browse/bintray/jcenter
