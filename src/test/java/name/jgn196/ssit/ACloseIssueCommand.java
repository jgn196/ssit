package name.jgn196.ssit;

import org.junit.Test;
import org.mockito.Mockito;

public class ACloseIssueCommand extends StandardOutCapturingTests {

    private static final String[] INVALID_ARGUMENTS = new String[0];
    private static final String[] VALID_ARGUMENTS = new String[]{"close", "1"};

    private final IssueStore mockStore = Mockito.mock(IssueStore.class);

    @Test(expected = BadCommandArguments.class)
    public void throwsAnErrorIfIncorrectNumberOfArgumentsSupplied() {
        new CloseIssueCommand(INVALID_ARGUMENTS, () -> mockStore).run();
    }

    @Test(expected = BadCommandArguments.class)
    public void throwsAnErrorIfIssueIdIsNotANumber() {
        new CloseIssueCommand(new String[]{"close", "foo"}, () -> mockStore).run();
    }

    @Test
    public void closesAnIssueById() {
        standardOutFrom(new CloseIssueCommand(VALID_ARGUMENTS, () -> mockStore));

        Mockito.verify(mockStore).close(1);
    }

    @Test
    public void printsCloseSuccess() {
        new CloseIssueCommand(VALID_ARGUMENTS, () -> mockStore).run();
    }
}
