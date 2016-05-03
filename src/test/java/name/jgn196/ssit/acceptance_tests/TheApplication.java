package name.jgn196.ssit.acceptance_tests;

import name.jgn196.ssit.Ssit;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

/*
As a Software Developer,
I want to track the issues I have left to fix,
So I don't forget any.
 */
public class TheApplication {
    /*
    Scenario:
         Given a project has already been initialised for SSIT,
         When the developer adds an issue,
         And then asks for a list of outstanding issues,
         Then the results contain the new issue.
     */
    @Test
    public void tracksTodoIssues() {
        givenAnEmptyInitialisedProject();
        addIssue("Create splash screen.");
        addIssue("Fix bug.");
        assertThat(outstandingIssuesReport()).contains("Create splash screen.", "Fix bug.");
    }

    private void givenAnEmptyInitialisedProject() {
        Ssit.main(new String[]{"init"});
    }

    private void addIssue(final String issueText) {
        Ssit.main(new String[]{"todo", issueText});
    }

    private String outstandingIssuesReport() {
        final PrintStream sout = System.out;
        final ByteArrayOutputStream results = new ByteArrayOutputStream();
        System.setOut(new PrintStream(results));
        try {
            Ssit.main(new String[]{"list"});
        } finally {
            System.setOut(sout);
        }
        return new String(results.toByteArray());
    }
}
