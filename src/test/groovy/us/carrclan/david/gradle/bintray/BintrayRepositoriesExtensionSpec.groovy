package us.carrclan.david.gradle.bintray

import org.gradle.api.InvalidUserDataException
import org.gradle.api.Project
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class BintrayRepositoriesExtensionSpec extends Specification {
    Project project

    def setup() {
        project = ProjectBuilder.builder().build()
        project.apply(plugin: BintrayPlugin)
    }

    def "bintray.jcenter creates jcenter repository"() {
        when:
        project.with {
            repositories {
                bintray.jcenter()
            }
        }
        def repo = project.repositories.BintrayJCenter

        then:
        repo instanceof MavenArtifactRepository
        repo.url.toString() == BintrayRepositoriesExtension.JCENTER_URL
    }

    def "bintray.repo requires repoOwner arg"() {
        when:
        project.with {
            repositories {
                bintray.repo(repoName: 'gradle-bintray-plugin')
            }
        }

        then:
        InvalidUserDataException ex = thrown()
        ex.message == "'repoOwner' must be specified"
    }

    def "bintray.repo requires repoName arg"() {
        when:
        project.with {
            repositories {
                bintray.repo(repoOwner: 'davidmc24')
            }
        }

        then:
        InvalidUserDataException ex = thrown()
        ex.message == "'repoName' must be specified"
    }

    def "bintray.repo creates private bintray repository"() {
        def repo

        when:
        project.with {
            repositories {
                bintray.repo(repoOwner: 'davidmc24', repoName: 'gradle-bintray-plugin')
            }
        }
        repo = project.repositories.BintrayDavidmc24GradleBintrayPlugin

        then:
        repo instanceof MavenArtifactRepository
        repo.url.toString() == 'http://dl.bintray.com/content/davidmc24/gradle-bintray-plugin'
    }
}
