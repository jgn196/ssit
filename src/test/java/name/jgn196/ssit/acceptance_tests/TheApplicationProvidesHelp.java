package name.jgn196.ssit.acceptance_tests;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/*
As a Software Developer,
I want to know how to use SSIT,
So I can track my issues.
 */
public class TheApplicationProvidesHelp {

    private ApplicationRun applicationRun;

    @Before
    public void createApplicationRun() throws IOException {
        applicationRun = new ApplicationRun();
    }

    /*
    Scenario:
        When the developer runs the application with no arguments,
        Then the application's usage is printed.
     */
    @Test
    public void byProvidingCommandLineUsage() throws IOException, InterruptedException {
        assertThat(applicationRun.runSsit())
                .contains("usage")
                .contains("init")
                .contains("todo")
                .contains("close")
                .contains("list");
    }

    @Test
    public void byProvidingSpecificHelpWhenCommandsAreMissingArguments() throws IOException, InterruptedException {
        assertThat(applicationRun.runSsit("todo"))
                .contains("usage")
                .contains("todo")
                .contains("issue description");

        assertThat(applicationRun.runSsit("close"))
                .contains("usage")
                .contains("close")
                .contains("issue ID");
    }

    @Test
    public void byPointingOutWhenItIsRunInADirectoryWithNoSsitProject() throws IOException, InterruptedException {
        assertThat(applicationRun.runSsit("todo", "ignored"))
                .contains("no SSIT project");

        assertThat(applicationRun.runSsit("list"))
                .contains("no SSIT project");
    }
}
