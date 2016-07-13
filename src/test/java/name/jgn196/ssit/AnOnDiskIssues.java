package name.jgn196.ssit;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class AnOnDiskIssues {

    private static final String TEMP_DIRECTORY_PATH = System.getProperty("java.io.tmpdir");
    private static final String TEST_DIRECTORY_NAME = "ssit_test";
    private static final File TEST_DIRECTORY = Paths.get(TEMP_DIRECTORY_PATH, TEST_DIRECTORY_NAME).toFile();

    @Test
    public void startsEmpty() throws IOException {
        final IssueStore store = new OnDiskIssues(TEST_DIRECTORY);

        store.init();

        try (final ByteArrayOutputStream results = new ByteArrayOutputStream()) {
            try (final PrintStream printStream = new PrintStream(results)) {
                store.printOutstanding(printStream);
            }

            assertThat(new String(results.toByteArray())).isEmpty();
        }
    }

    @Test
    public void tracksNewIssues() throws IOException, NoSsitProject {
        final IssueStore store = new OnDiskIssues(TEST_DIRECTORY);

        store.init();

        store.newIssue("Issue 1");

        try (final ByteArrayOutputStream results = new ByteArrayOutputStream()) {
            try (final PrintStream printStream = new PrintStream(results)) {
                store.printOutstanding(printStream);
            }

            assertThat(new String(results.toByteArray())).contains("Issue 1");
        }
    }

    @Test
    public void closesIssuesById() throws IOException, NoSsitProject {
        final IssueStore store = new OnDiskIssues(TEST_DIRECTORY);

        store.init();

        store.newIssue("Issue 1");
        store.newIssue("Issue 2");
        store.newIssue("Issue 3");

        store.close(1);
        store.close(2);
        store.close(3);

        try (final ByteArrayOutputStream results = new ByteArrayOutputStream()) {
            try (final PrintStream printStream = new PrintStream(results)) {
                store.printOutstanding(printStream);
            }

            assertThat(new String(results.toByteArray())).isEmpty();
        }
    }

    @Test(expected = ProjectAlreadyInitialised.class)
    public void throwsAnErrorIfReinitialisingProject() {
        final IssueStore store = new OnDiskIssues(TEST_DIRECTORY);

        store.init();
        store.init();
    }

    @Test(expected = NoSuchOpenIssue.class)
    public void throwsAnErrorIfClosingAnIssueInEmptyProject() {
        final IssueStore store = new OnDiskIssues(TEST_DIRECTORY);

        store.init();

        store.close(1);
    }

    @Test(expected = NoSuchOpenIssue.class)
    public void throwsAnErrorIfClosingAnIssueThatDoesNotExist() {
        final IssueStore store = new OnDiskIssues(TEST_DIRECTORY);

        store.init();
        store.newIssue("new issue");

        store.close(2);
    }

    @After
    public void deleteTestDirectory() throws IOException {

        if (TEST_DIRECTORY.exists() && TEST_DIRECTORY.isDirectory()) {

            FileUtils.deleteDirectory(TEST_DIRECTORY);
        }
    }
}
