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
        project.apply(from: new File('Bintray.gradle').toURI().toURL().toExternalForm())
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
        repo.url.toString() == 'http://jcenter.bintray.com'
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
        when:
        project.with {
            repositories {
                bintray.repo(repoOwner: 'davidmc24', repoName: 'gradle-bintray-plugin')
            }
        }
        def repo = project.repositories.BintrayDavidmc24GradleBintrayPlugin

        then:
        repo instanceof MavenArtifactRepository
        repo.url.toString() == 'http://dl.bintray.com/content/davidmc24/gradle-bintray-plugin'
    }

    def "bintray.repo allows specifying repository name"() {
        when:
        project.with {
            repositories {
                bintray.repo(name: 'MyRepo', repoOwner: 'davidmc24', repoName: 'gradle-bintray-plugin')
            }
        }
        def repo = project.repositories.MyRepo

        then:
        repo instanceof MavenArtifactRepository
        repo.url.toString() == 'http://dl.bintray.com/content/davidmc24/gradle-bintray-plugin'
    }

    def "bintray.repo allows specifying repository url"() {
        when:
        project.with {
            repositories {
                bintray.repo(url: 'http://mirrors.example.com/bintray/joe/maven', repoOwner: 'joe', repoName: 'maven')
            }
        }
        def repo = project.repositories.BintrayJoeMaven

        then:
        repo instanceof MavenArtifactRepository
        repo.url.toString() == 'http://mirrors.example.com/bintray/joe/maven'
    }

    def "bintray.repo allows specifying credentials"() {
        when:
        project.with {
            repositories {
                bintray.repo(repoOwner: 'joe', repoName: 'maven') {
                    credentials {
                        username = 'frank'
                        password = 'secret'
                    }
                }
            }
        }
        MavenArtifactRepository repo = project.repositories.BintrayJoeMaven

        then:
        repo.credentials.username == 'frank'
        repo.credentials.password == 'secret'
    }

    def "urls are determined based on repoOwner and repoName"(String repoOwner, String repoName, String url) {
        expect:
        project.repositories.bintray.determineRepositoryUrl(repoOwner, repoName) == url

        where:
        repoOwner   | repoName                  | url
        'bintray'   | 'jcenter'                 | 'http://dl.bintray.com/content/bintray/jcenter'
        'davidmc24' | 'gradle-bintray-plugin'   | 'http://dl.bintray.com/content/davidmc24/gradle-bintray-plugin'
    }

    def "names are determined based on repoOwner and repoName"(String repoOwner, String repoName, String name) {
        expect:
        project.repositories.bintray.determineRepositoryName(repoOwner, repoName) == name

        where:
        repoOwner   | repoName                  | name
        'bintray'   | 'jcenter'                 | 'BintrayBintrayJcenter'
        'davidmc24' | 'gradle-bintray-plugin'   | 'BintrayDavidmc24GradleBintrayPlugin'
        'joe'       | 'maven'                   | 'BintrayJoeMaven'
        'WowCorp'   | 'AmazingSoftware'         | 'BintrayWowCorpAmazingSoftware'
    }
}
