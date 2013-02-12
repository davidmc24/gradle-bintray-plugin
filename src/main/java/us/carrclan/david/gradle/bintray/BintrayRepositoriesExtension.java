package us.carrclan.david.gradle.bintray;

import groovy.lang.Closure;
import org.gradle.api.Action;
import org.gradle.api.InvalidUserDataException;
import org.gradle.api.artifacts.dsl.RepositoryHandler;
import org.gradle.api.artifacts.repositories.MavenArtifactRepository;
import org.gradle.api.internal.Actions;
import org.gradle.api.internal.ClosureBackedAction;
import org.gradle.util.ConfigureUtil;

import java.util.HashMap;
import java.util.Map;

public class BintrayRepositoriesExtension {
    public static final String NAME = "bintray";
    public static final String JCENTER_URL = "http://jcenter.bintray.com";
    public static final String REPO_OWNER_ARG_NAME = "repoOwner";
    public static final String REPO_NAME_ARG_NAME = "repoName";
    public static final String URL_ARG_NAME = "url";
    public static final String NAME_ARG_NAME = "name";

    private final RepositoryHandler repositories;

    public BintrayRepositoriesExtension(RepositoryHandler repositories) {
        this.repositories = repositories;
    }

    public MavenArtifactRepository jcenter() {
        return repositories.maven(new Action<MavenArtifactRepository>() {
            public void execute(MavenArtifactRepository repository) {
                repository.setName("BintrayJCenter");
                repository.setUrl(JCENTER_URL);
            }
        });
    }

    public MavenArtifactRepository repo(Map<String, ?> args) {
        return repo(args, Actions.doNothing());
    }

    public MavenArtifactRepository repo(Map<String, ?> args, Closure closure) {
        return repo(args, new ClosureBackedAction(closure));
    }

    public MavenArtifactRepository repo(Map<String, ?> args, final Action<? super MavenArtifactRepository> action) {
        final Map<String, Object> modifiedArgs = new HashMap<String, Object>(args);
        String repoOwner = pullRequiredArg(modifiedArgs, REPO_OWNER_ARG_NAME);
        String repoName = pullRequiredArg(modifiedArgs, REPO_NAME_ARG_NAME);
        if (!modifiedArgs.containsKey(URL_ARG_NAME)) {
            modifiedArgs.put(URL_ARG_NAME, determineRepositoryUrl(repoOwner, repoName));
        }
        if (!modifiedArgs.containsKey(NAME_ARG_NAME)) {
            modifiedArgs.put(NAME_ARG_NAME, determineRepositoryName(repoOwner, repoName));
        }
        return repositories.maven(new Action<MavenArtifactRepository>() {
            public void execute(MavenArtifactRepository repository) {
                ConfigureUtil.configureByMap(modifiedArgs, repository);
                action.execute(repository);
            }
        });
    }

    private String pullRequiredArg(Map<String, ?> args, String argName) {
        Object objectValue = args.remove(argName);
        if (objectValue == null) throw new InvalidUserDataException(String.format("'%s' must be specified", argName));
        String stringValue = objectValue.toString();
        if (stringValue.isEmpty()) throw new InvalidUserDataException(String.format("'%s' must be non-empty", argName));
        return stringValue;
    }

    String determineRepositoryName(String repoOwner, String repoName) {
        return String.format("Bintray%s%s",
                toTitleCase(separatorsToCaps(repoOwner)),
                toTitleCase(separatorsToCaps(repoName)));
    }

    String determineRepositoryUrl(String repoOwner, String repoName) {
        return String.format("http://dl.bintray.com/content/%s/%s", repoOwner, repoName);
    }

    private String separatorsToCaps(String string) {
        StringBuilder stringBuilder = new StringBuilder(string);
        int pos = 0;
        while (pos < stringBuilder.length()) {
            char curChar = stringBuilder.charAt(pos);
            if (curChar == '_' || curChar == '-') {
                if (pos + 1 < stringBuilder.length()) {
                    changeCharToUpperCase(stringBuilder, pos + 1);
                }
                stringBuilder.deleteCharAt(pos);
            }
            pos++;
        }
        return stringBuilder.toString();
    }

    private String toTitleCase(String string) {
        if (string.isEmpty()) return string;
        StringBuilder stringBuilder = new StringBuilder(string);
        changeCharToTitleCase(stringBuilder, 0);
        return stringBuilder.toString();
    }

    private void changeCharToUpperCase(StringBuilder stringBuilder, int pos) {
        stringBuilder.setCharAt(pos, Character.toUpperCase(stringBuilder.charAt(pos)));
    }

    private void changeCharToTitleCase(StringBuilder stringBuilder, int pos) {
        stringBuilder.setCharAt(pos, Character.toUpperCase(stringBuilder.charAt(pos)));
    }
}
