package name.jgn196.ssit;

import java.io.PrintStream;
import java.util.ArrayList;

class InMemoryIssues implements IssueStore {

    private ArrayList<Issue> issues = new ArrayList<>();

    private int nextIssueId = 1;

    @Override
    public void init() {
        nextIssueId = 1;
        issues.clear();
    }

    @Override
    public Issue newIssue(final String description) {
        final Issue result = new Issue(nextIssueId++, description);

        issues.add(result);

        return result;
    }

    @Override
    public void close(int issueId) {
        issues.removeIf(issue -> issue.matchesId(issueId));
    }

    @Override
    public void printOutstanding(final PrintStream printStream) {
        issues.forEach(printStream::println);
    }
}
