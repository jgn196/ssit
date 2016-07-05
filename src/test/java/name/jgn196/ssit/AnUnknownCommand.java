package name.jgn196.ssit;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AnUnknownCommand extends StandardOutCapturingTests {
    @Test
    public void printsAnErrorMessage() {
        assertThat(standardOutFrom(new UnknownCommand())).isEqualTo(UnknownCommand.ERROR_MESSAGE);
    }
}
