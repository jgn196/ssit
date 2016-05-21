package name.jgn196.ssit.acceptance_tests;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/*
As a Software Developer,
I want to track the issues I have left to fix,
So I don't forget any.
 */
public class TheApplicationTracksIssues {

    public static final Path SSIT_CLASS_PATH = Paths.get(".", "build", "classes", "main").toAbsolutePath();
    private File testDirectory;

    @Before
    public void createTestDirectory() throws IOException {
        testDirectory = Files.createTempDirectory("ssit_test").toFile();
        if (testDirectory.exists()) return;

        assertThat(testDirectory.mkdir()).isTrue();
    }

    /*
    Scenario:
         Given a project has already been initialised for SSIT,
         When the developer adds an issue,
         And then asks for a list of outstanding issues,
         Then the results contain the new issue.
     */
    @Test
    public void byListingNewIssues() throws IOException, InterruptedException {
        givenAnEmptyInitialisedProject();

        addIssue("Create splash screen.");
        addIssue("Fix bug.");

        assertThat(outstandingIssuesReport()).contains("Create splash screen.", "Fix bug.");
    }

    private void givenAnEmptyInitialisedProject() throws IOException, InterruptedException {
        runSsit("init");

        assertTestDirectoryContainsTodoDirectory();
    }

    private void assertTestDirectoryContainsTodoDirectory() {
        final File todoDirectory = testDirectory.toPath().resolve(".todo").toFile();

        assertThat(todoDirectory).exists().isDirectory();
    }

    private String runSsit(final String... commands) throws IOException, InterruptedException {
        final File outputFile = temporaryOutputFile();
        final Process process = new ProcessBuilder(ssitProcessArguments(commands))
                .directory(testDirectory)
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

    private void addIssue(final String issueText) throws IOException, InterruptedException {
        runSsit("todo", issueText);
    }

    private String outstandingIssuesReport() throws IOException, InterruptedException {
        return runSsit("list");
    }

    /*
    Scenario:
        Given a project has already been initialised for SSIT,
        And it contains a known issue,
        When the developer marks that issue as fixed,
        And asks for a list of outstanding issues,
        Then the known issue is not in the list.
     */
    @Test
    public void byNotListingClosedIssues() throws IOException, InterruptedException {
        givenAnEmptyInitialisedProject();

        addIssue("Create splash screen.");
        addIssue("Fix bug.");

        closeIssue(1);

        assertThat(outstandingIssuesReport()).contains("Fix bug.").doesNotContain("Create splash screen.");
    }

    private void closeIssue(int issueId) throws IOException, InterruptedException {
        runSsit("close", Integer.toString(issueId));
    }

    @After
    public void deleteTestDirectory() throws IOException {
        if (testDirectory.exists() && testDirectory.isDirectory()) {

            FileUtils.deleteDirectory(testDirectory);
        }
    }
}
