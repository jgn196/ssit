package name.jgn196.ssit;

import java.io.File;
import java.util.Optional;

class ProjectFinder {

    static final String DIRECTORY_NAME = ".todo";

    private final File searchRoot;

    ProjectFinder(final File searchRoot) {

        this.searchRoot = searchRoot;
    }

    static File projectDirectoryInWorkingDirectory() {
        return new File(DIRECTORY_NAME);
    }

    Optional<File> find() {

        File candidateDirectory = searchRoot;

        while (doesNotContainProjectDirectory(candidateDirectory)) {

            if (doesNotHaveParent(candidateDirectory)) return Optional.empty();

            candidateDirectory = candidateDirectory.getParentFile();
        }

        return Optional.of(projectDirectoryIn(candidateDirectory));
    }

    private static boolean doesNotContainProjectDirectory(final File directory) {
        return !projectDirectoryIn(directory).exists();
    }

    private static boolean doesNotHaveParent(final File file) {
        return file.getParentFile() == null;
    }

    private static File projectDirectoryIn(final File directory) {

        return directory.toPath().resolve(DIRECTORY_NAME).toFile();
    }
}
