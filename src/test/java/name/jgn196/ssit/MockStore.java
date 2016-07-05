package name.jgn196.ssit;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

class MockStore implements IssueStore {

    private final List<String> newIssueDescriptions = new ArrayList<>();

    @Override
    public void init() {

    }

    @Override
    public Issue newIssue(String description) throws NoSsitProject {
        newIssueDescriptions.add(description);

        return new Issue(0, description);
    }

    @Override
    public void close(int issueId) throws NoSsitProject {

    }

    @Override
    public void printOutstanding(PrintStream printStream) throws NoSsitProject {

    }

    Iterable<String> newIssueDescriptions() {
        return newIssueDescriptions;
    }
}
