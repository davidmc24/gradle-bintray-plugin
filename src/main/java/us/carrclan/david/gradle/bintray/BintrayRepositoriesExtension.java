package us.carrclan.david.gradle.bintray;

import groovy.lang.Closure;
import org.gradle.api.Action;
import org.gradle.api.artifacts.dsl.RepositoryHandler;
import org.gradle.api.artifacts.repositories.MavenArtifactRepository;
import org.gradle.api.internal.Actions;
import org.gradle.api.internal.ClosureBackedAction;

public class BintrayRepositoriesExtension {
    public static final String NAME = "bintray";
    public static final String JCENTER_URL = "http://jcenter.bintray.com";

    private final RepositoryHandler repositories;

    public BintrayRepositoriesExtension(RepositoryHandler repositories) {
        this.repositories = repositories;
    }

    public MavenArtifactRepository jcenter() {
        return repositories.maven(new Action<MavenArtifactRepository>() {
            public void execute(MavenArtifactRepository repository) {
                repository.setName("jcenter");
                repository.setUrl(JCENTER_URL);
            }
        });
    }

    public MavenArtifactRepository repo(String owner, String name) {
        return repo(owner, name, Actions.doNothing());
    }

    public MavenArtifactRepository repo(final String owner, final String name, final Action<? super MavenArtifactRepository> action) {
        return repositories.maven(new Action<MavenArtifactRepository>() {
            public void execute(MavenArtifactRepository repository) {
                repository.setName(determineRepositoryName(owner, name));
                repository.setUrl(determineRepositoryUrl(owner, name));
                action.execute(repository);
            }
        });
    }

    public MavenArtifactRepository repo(String owner, String name, Closure closure) {
        return repo(owner, name, new ClosureBackedAction(closure));
    }

    private String determineRepositoryName(String owner, String repoName) {
        return String.format("bintray%s%s", toTitleCase(owner), toTitleCase(repoName));
    }

    private String determineRepositoryUrl(String owner, String repoName) {
        return String.format("http://dl.bintray.com/content/%s/%s", owner, repoName);
    }

    private String toTitleCase(String string) {
        String retVal = string;
        if (string != null && !string.isEmpty()) {
            char firstChar = string.charAt(0);
            if (!Character.isTitleCase(firstChar)) {
                StringBuilder stringBuilder = new StringBuilder(string);
                stringBuilder.setCharAt(0, Character.toTitleCase(firstChar));
                retVal = stringBuilder.toString();
            }
        }
        return retVal;
    }
}
