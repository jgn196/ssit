package name.jgn196.ssit;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ANewIssueCommand extends StandardOutCapturingTests {

    private static final String[] INVALID_ARGUMENTS = new String[0];
    private static final String ISSUE_DESCRIPTION = "foo";
    private static final String[] VALID_ARGUMENTS = new String[]{"todo", ISSUE_DESCRIPTION};

    @Test
    public void printsUsageIfIncorrectNumberOfArgumentsSupplied() {
        assertThat(standardOutFrom(new NewIssueCommand(INVALID_ARGUMENTS, () -> null)))
                .startsWith("No new issue description provided");
    }

    @Test
    public void createsANewIssueFromASuppliedDescription() {
        final MockStore issueStore = new MockStore();

        new NewIssueCommand(VALID_ARGUMENTS, () -> issueStore).run();

        assertThat(issueStore.newIssueDescriptions()).contains(ISSUE_DESCRIPTION);
    }

    @Test
    public void printsIssueCreationSuccess() {
        assertThat(standardOutFrom(new NewIssueCommand(VALID_ARGUMENTS, MockStore::new))).isEqualTo("Issue added.");
    }
}