package name.jgn196.ssit;

import org.junit.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

public class ACloseIssueCommand extends StandardOutCapturingTests {

    private static final String[] INVALID_ARGUMENTS = new String[0];
    private static final String[] VALID_ARGUMENTS = new String[]{"close", "1"};

    private final IssueStore mockStore = Mockito.mock(IssueStore.class);

    @Test
    public void printsUsageIfIncorrectNumberOfArgumentsSupplied() {
        assertThat(standardOutFrom(new CloseIssueCommand(INVALID_ARGUMENTS, () -> mockStore)))
                .startsWith("No issue ID provided");
    }

    @Test
    public void closesAnIssueById() {
        standardOutFrom(new CloseIssueCommand(VALID_ARGUMENTS, () -> mockStore));

        Mockito.verify(mockStore).close(1);
    }

    @Test
    public void printsCloseSuccess() {
        assertThat(standardOutFrom(new CloseIssueCommand(VALID_ARGUMENTS, () -> mockStore)))
                .isEqualTo("Issue closed.");
    }
}
