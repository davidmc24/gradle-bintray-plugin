/*
 * Copyright 2013 David M. Carr
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

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
