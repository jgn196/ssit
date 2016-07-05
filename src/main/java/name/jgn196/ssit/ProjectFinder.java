package name.jgn196.ssit;

import java.io.File;
import java.util.Optional;

class ProjectFinder {

    private final File searchRoot;

    ProjectFinder(final File searchRoot) {

        this.searchRoot = searchRoot;
    }

    Optional<File> find() {

        File candidateDirectory = searchRoot;

        while(!projectDirectoryIn(candidateDirectory).exists()) {
            candidateDirectory = candidateDirectory.getParentFile();

            if(candidateDirectory == null) return Optional.empty();
        }

        return Optional.of(projectDirectoryIn(candidateDirectory));
    }

    private File projectDirectoryIn(final File directory) {

        return directory.toPath().resolve(Ssit.DIRECTORY_NAME).toFile();
    }
}
