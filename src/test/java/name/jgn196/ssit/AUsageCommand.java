package name.jgn196.ssit;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AUsageCommand extends StandardOutCapturingTests {

    @Test
    public void printsTheApplicationUsage() {
        assertThat(standardOutFrom(new UsageCommand())).startsWith("usage");
    }
}