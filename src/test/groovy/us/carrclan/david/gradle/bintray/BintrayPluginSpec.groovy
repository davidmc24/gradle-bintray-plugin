package us.carrclan.david.gradle.bintray

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class BintrayPluginSpec extends Specification {
    Project project = ProjectBuilder.builder().build()

    def "repositories extension is registered"() {
        when: "the plugin is applied to a project"
        project.apply(from: new File('Bintray.gradle').toURI().toURL().toExternalForm())

        then: "the repositories extension is registered"
        project.repositories.bintray != null
        project.buildscript.repositories.bintray != null
    }
}
