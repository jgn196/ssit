package name.jgn196.ssit;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class StandardOutCapturingTests {
    String standardOutFrom(final Runnable task) {
        final PrintStream stdOut = System.out;
        final ByteArrayOutputStream capturedOutput = new ByteArrayOutputStream();

        try (final PrintStream capturedOut = new PrintStream(capturedOutput)) {
            System.setOut(capturedOut);

            task.run();
        }

        System.setOut(stdOut);

        return new String(capturedOutput.toByteArray()).trim();
    }
}
