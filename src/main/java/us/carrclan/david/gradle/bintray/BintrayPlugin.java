package us.carrclan.david.gradle.bintray;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.dsl.RepositoryHandler;
import org.gradle.api.internal.plugins.DslObject;
import org.gradle.api.plugins.ExtensionContainer;

public class BintrayPlugin implements Plugin<Project> {
    public void apply(Project project) {
        RepositoryHandler repositories = project.getRepositories();
        ExtensionContainer extensions = new DslObject(repositories).getExtensions();
        extensions.create(BintrayRepositoriesExtension.NAME, BintrayRepositoriesExtension.class, repositories);
    }
}
