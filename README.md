# Overview
The Bintray plugin is a [Gradle][] plugin to allow easier use of [Bintray][] repositories.  It is available for use under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).  If you have any issues, please check the [issue tracker](https://bitbucket.org/davidmc24/gradle-bintray-plugin/issues?status=new&status=open), and if there isn't anything relevant, submit a new issue.

# Usage
To use the plugin, configure your build.gradle based on the example below.

## build.gradle

Replace `VERSION` below with the desired version.

    :::groovy
    buildscript {
        apply from: 'https://bitbucket.org/davidmc24/gradle-bintray-plugin/raw/VERSION/Bintray.gradle'
    }

Note that the `apply` statement **MUST** be in the buildscript block and before any bintray repository definitions in order to work as intended.  If you apply the script outside of the buildscript block, the `bintray` extension to the buildscript `RepositoryHandler` will not be registered in time for use resolving buildscript dependencies.

## Versions

Creating this plugin required the usage of Gradle internal APIs which may not be stable across versions.  Below is a matrix of tested compatible versions.  In general, it's best to use the latest version that's shown as tested with your version of Gradle.

Gradle Version | Plugin Version
-------------: | --------------
1.4            | 0.1.0

If you want the latest development version, you can use `default` as a version.

# Configuration

The plugin adds special syntax in `repositories` blocks to declare Bintray repositories.

## Authentication

Bintray-owned repositories (such as the [JCenter][] repository) allow anonymous downloads via Gradle, but others don't.  For them, you'll need to configure authentication.  The recommended method is to add `bintrayUserName` and `bintrayApiKey` properties to your gradle.properties file, based on the example below:

    bintrayUserName = joe
    bintrayApiKey = 1234567890abcdef1234567890abcdef1234567890abcdef1234567890abcdef

## Configuration Example

    :::groovy
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

[gradle]: http://www.gradle.org/
[bintray]: https://www.bintray.com/
[jcenter]: https://bintray.com/repo/browse/bintray/jcenter
