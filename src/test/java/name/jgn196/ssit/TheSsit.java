package name.jgn196.ssit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class TheSsit {

    private ApplicationRun applicationRun;

    @Before
    public void createApplicationRun() throws IOException {
        applicationRun = new ApplicationRun();
    }

    @Test
    public void canBeRunInAProjectSubdirectory() throws Exception {
        givenAnInitialisedProject();

        final File subdirectory = projectSubdirectory();

        assertThat(applicationRun.runSsitIn(subdirectory, "list")).doesNotContain("There is no SSIT project");
    }

    private String givenAnInitialisedProject() throws IOException, InterruptedException {
        return applicationRun.runSsit("init");
    }

    private File projectSubdirectory() {
        final File subdirectory = applicationRun.workingDirectory().toPath().resolve("subdirectory").toFile();
        assertThat(subdirectory.mkdir()).isTrue();
        return subdirectory;
    }

    @After
    public void deleteTestDirectory() throws IOException {
        applicationRun.close();
    }
}
