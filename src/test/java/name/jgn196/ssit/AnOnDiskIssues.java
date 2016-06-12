package name.jgn196.ssit;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;

public class AnOnDiskIssues {

    private File testDirectory;

    @Before
    public void createTestDirectory() throws IOException {
        testDirectory = Files.createTempDirectory("ssit_test").toFile();
    }

    @Test
    public void tracksNewIssues() throws IOException, NoSsitProject {
        final IssueStore store = new OnDiskIssues(testDirectory);

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
        final IssueStore store = new OnDiskIssues(testDirectory);

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

    @After
    public void deleteTestDirectory() throws IOException {

        if (testDirectory.exists() && testDirectory.isDirectory()) {

            FileUtils.deleteDirectory(testDirectory);
        }
    }
}
