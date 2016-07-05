package name.jgn196.ssit;

import org.junit.Test;
import org.mockito.Mockito;

public class AListIssuesCommand {

    @Test
    public void tellsTheIssueStoreToPrintOutstandingIssues() {
        final IssueStore issueStore = Mockito.mock(IssueStore.class);

        new ListIssuesCommand(() -> issueStore).run();

        Mockito.verify(issueStore).printOutstanding(System.out);
    }
}
