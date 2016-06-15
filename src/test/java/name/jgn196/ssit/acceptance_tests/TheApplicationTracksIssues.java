package name.jgn196.ssit.acceptance_tests;

import name.jgn196.ssit.ApplicationRun;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/*
As a Software Developer,
I want to track the issues I have left to fix,
So I don't forget any.
 */
public class TheApplicationTracksIssues {

    private ApplicationRun applicationRun;

    @Before
    public void createApplicationRun() throws IOException {
        applicationRun = new ApplicationRun();
    }

    /*
    Scenario:
         Given a project has already been initialised for SSIT,
         When the developer adds an issue,
         And then asks for a list of outstanding issues,
         Then the results contain the new issue.
     */
    @Test
    public void byListingNewIssues() throws IOException, InterruptedException {
        givenAnEmptyInitialisedProject();

        addIssue("Create splash screen.");
        addIssue("Fix bug.");

        assertThat(outstandingIssuesReport()).contains("Create splash screen.", "Fix bug.");
    }

    private void givenAnEmptyInitialisedProject() throws IOException, InterruptedException {
        final String commandOutput = applicationRun.runSsit("init");

        assertThat(commandOutput).as("Command output").contains("Project initialised");

        assertTestDirectoryContainsTodoDirectory();
    }

    private void assertTestDirectoryContainsTodoDirectory() {
        final File todoDirectory = applicationRun.workingDirectory().toPath().resolve(".todo").toFile();

        assertThat(todoDirectory).exists().isDirectory();
    }

    private void addIssue(final String issueText) throws IOException, InterruptedException {
        final String commandOutput = applicationRun.runSsit("todo", issueText);

        assertThat(commandOutput).as("Command output").contains("Issue added.");
    }

    private String outstandingIssuesReport() throws IOException, InterruptedException {
        return applicationRun.runSsit("list");
    }

    /*
    Scenario:
        Given a project has already been initialised for SSIT,
        And it contains a known issue,
        When the developer marks that issue as fixed,
        And asks for a list of outstanding issues,
        Then the known issue is not in the list.
     */
    @Test
    public void byNotListingClosedIssues() throws IOException, InterruptedException {
        givenAnEmptyInitialisedProject();

        addIssue("Create splash screen.");
        addIssue("Fix bug.");

        closeIssue(1);

        assertThat(outstandingIssuesReport()).contains("Fix bug.").doesNotContain("Create splash screen.");
    }

    private void closeIssue(int issueId) throws IOException, InterruptedException {
        assertThat(applicationRun.runSsit("close", Integer.toString(issueId))).contains("Issue closed.");
    }

    @After
    public void deleteTestDirectory() throws IOException {
        applicationRun.close();
    }
}
