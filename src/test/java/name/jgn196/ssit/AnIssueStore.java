package name.jgn196.ssit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
public class AnIssueStore {

    @Before
    public void clearIssueStore() {
        final File issueDirectory = new File(".todo");
        if(issueDirectory.exists()) {
            for (final File file : issueDirectory.listFiles()) {
                file.delete();
            }
            issueDirectory.delete();
        }
    }

    @Parameters()
    public static Iterable<Object[]> parameters() {
        return Arrays.asList(
                new Object[]{new InMemoryIssues()},
                new Object[]{new OnDiskIssues()});
    }

    @Parameter
    public IssueStore store;

    @Test
    public void tracksNewIssues() throws IOException {
        store.init();

        store.newIssue("Issue 1");

        try (final ByteArrayOutputStream results = new ByteArrayOutputStream()) {
            try(final PrintStream printStream = new PrintStream(results)){
                store.printOutstanding(printStream);
            }

            assertThat(new String(results.toByteArray())).contains("Issue 1");
        }
    }

    @Test
    public void closesIssuesById() throws IOException {
        store.init();

        store.newIssue("Issue 1");
        store.newIssue("Issue 2");
        store.newIssue("Issue 3");

        store.close(1);
        store.close(2);
        store.close(3);

        try (final ByteArrayOutputStream results = new ByteArrayOutputStream()) {
            try(final PrintStream printStream = new PrintStream(results)){
                store.printOutstanding(printStream);
            }

            assertThat(new String(results.toByteArray())).isEmpty();
        }
    }
}
