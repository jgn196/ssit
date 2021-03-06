package name.jgn196.ssit;

import org.junit.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

public class ANewIssueCommand extends StandardOutCapturingTests {

    private static final String[] INVALID_ARGUMENTS = new String[0];
    private static final String ISSUE_DESCRIPTION = "foo";
    private static final String[] VALID_ARGUMENTS = new String[]{"todo", ISSUE_DESCRIPTION};

    private final IssueStore mockStore = Mockito.mock(IssueStore.class);

    @Test
    public void printsUsageIfIncorrectNumberOfArgumentsSupplied() {
        assertThat(standardOutFrom(new NewIssueCommand(INVALID_ARGUMENTS, () -> mockStore)))
                .startsWith("No new issue description provided");
    }

    @Test
    public void createsANewIssueFromASuppliedDescription() {
        new NewIssueCommand(VALID_ARGUMENTS, () -> mockStore).run();

        Mockito.verify(mockStore).newIssue(ISSUE_DESCRIPTION);
    }

    @Test
    public void printsIssueCreationSuccess() {
        assertThat(standardOutFrom(new NewIssueCommand(VALID_ARGUMENTS, () -> mockStore)))
                .isEqualTo("Issue added.");
    }
}