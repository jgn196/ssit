package name.jgn196.ssit;

import org.apache.commons.io.FileUtils;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ApplicationRun implements Closeable {

    private static final Path SSIT_CLASS_PATH = Paths.get(".", "build", "classes", "main").toAbsolutePath();

    private final File testDirectory;

    public ApplicationRun() throws IOException {
        testDirectory = Files.createTempDirectory("ssit_test").toFile();
        if (testDirectory.exists()) return;

        assertThat(testDirectory.mkdir()).isTrue();
    }

    public File workingDirectory() {
        return testDirectory;
    }

    public String runSsit(final String... commands) throws IOException, InterruptedException {
        return runSsitIn(testDirectory, commands);
    }

    public String runSsitIn(final File workingDirectory, final String... commands) throws IOException, InterruptedException {
        final File outputFile = temporaryOutputFile();
        final Process process = new ProcessBuilder(ssitProcessArguments(commands))
                .directory(workingDirectory)
                .redirectError(ProcessBuilder.Redirect.INHERIT)
                .redirectOutput(ProcessBuilder.Redirect.to(outputFile))
                .start();

        assertThat(process.waitFor()).isZero();

        final String result = FileUtils.readFileToString(outputFile);

        System.out.print(result);

        return result;
    }

    private File temporaryOutputFile() throws IOException {
        final File result = Files.createTempFile("ssit_out", ".txt").toFile();
        result.deleteOnExit();

        return result;
    }

    private List<String> ssitProcessArguments(final String... commands) {
        final List<String> result = new ArrayList<>(
                Arrays.asList("java", "-cp", SSIT_CLASS_PATH.toString(), "name.jgn196.ssit.Ssit"));
        result.addAll(Arrays.asList(commands));

        return result;
    }

    @Override
    public void close() throws IOException {
        if (testDirectory.exists() && testDirectory.isDirectory()) {

            FileUtils.deleteDirectory(testDirectory);
        }
    }
}
