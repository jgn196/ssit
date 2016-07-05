package name.jgn196.ssit;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class AProjectFinder {

    private File testDirectory;
    private File searchRootDirectory;
    private File projectDirectory;

    @Test
    public void findsTheSsitProjectDirectoryInTheSearchRootDirectory() throws IOException {
        searchRootDirectoryContainsSsitDirectory();

        final Optional<File> result = new ProjectFinder(searchRootDirectory).find();

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get()).as("found project directory").isEqualTo(projectDirectory);
    }

    private void searchRootDirectoryContainsSsitDirectory() throws IOException {
        searchRootDirectoryIsLevelsAboveProjectDirectoryParent(0);
    }

    private void searchRootDirectoryIsLevelsAboveProjectDirectoryParent(final int levels) throws IOException {
        testDirectory = Files.createTempDirectory("ssit_test").toFile();

        searchRootDirectory = testDirectory;
        for(int i = 0; i < levels; i++) {
            searchRootDirectory = searchRootDirectory.toPath().resolve("" + i).toFile();
            assertThat(searchRootDirectory.mkdir()).isTrue();
        }

        projectDirectory = testDirectory.toPath().resolve(Ssit.DIRECTORY_NAME).toFile();
        assertThat(projectDirectory.mkdir()).isTrue();
    }

    @Test
    public void findsTheSsitProjectDirectoryInAParentOfTheSearchRootDirectory() throws IOException {
        searchRootDirectoryIsLevelsAboveProjectDirectoryParent(2);

        final Optional<File> result = new ProjectFinder(searchRootDirectory).find();

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get()).as("found project directory").isEqualTo(projectDirectory);
    }

    @Test
    public void throwsIfNoSsitProjectIsFound() throws IOException {
        noProjectDirectoryInOrAboveSearchRootDirectory();

        final Optional<File> result = new ProjectFinder(searchRootDirectory).find();

        assertThat(result.isPresent()).isFalse();
    }

    private void noProjectDirectoryInOrAboveSearchRootDirectory() throws IOException {
        testDirectory = Files.createTempDirectory("ssit_test").toFile();
        searchRootDirectory = testDirectory;
    }

    @After
    public void deleteTestDirectory() throws IOException {
        if (testDirectory != null) FileUtils.deleteDirectory(testDirectory);
    }
}
