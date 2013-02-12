package us.carrclan.david.gradle.bintray;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.dsl.RepositoryHandler;
import org.gradle.api.internal.plugins.DslObject;
import org.gradle.api.plugins.ExtensionContainer;

public class BintrayPlugin implements Plugin<Project> {
    public void apply(Project project) {
        RepositoryHandler repositoryHandler = project.getRepositories();
        DslObject dslObject = new DslObject(repositoryHandler);
        ExtensionContainer extensionContainer = dslObject.getExtensions();
        extensionContainer.create(BintrayRepositoriesExtension.NAME, BintrayRepositoriesExtension.class, repositoryHandler);
    }
}
