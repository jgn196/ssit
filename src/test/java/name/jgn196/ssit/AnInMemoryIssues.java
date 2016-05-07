package name.jgn196.ssit;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AnInMemoryIssues {
    @Test
    public void closesIssuesById() {
        final IssueStore store = new InMemoryIssues();

        store.newIssue("Issue 1");
        store.newIssue("Issue 2");
        store.newIssue("Issue 3");

        store.close(1);
        store.close(2);
        store.close(3);

        assertThat(store.outstandingIssues()).isEmpty();
    }
}
