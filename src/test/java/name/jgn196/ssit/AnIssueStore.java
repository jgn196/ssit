package name.jgn196.ssit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.util.ArrayList;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
public class AnIssueStore {

    @Parameters()
    public static Iterable<Object[]> parameters() {
        return Collections.singletonList(new Object[]{new InMemoryIssues()});
    }

    @Parameter
    public IssueStore store;

    @Test
    public void closesIssuesById() {
        store.newIssue("Issue 1");
        store.newIssue("Issue 2");
        store.newIssue("Issue 3");

        store.close(1);
        store.close(2);
        store.close(3);

        assertThat(store.outstandingIssues()).isEmpty();
    }
}
