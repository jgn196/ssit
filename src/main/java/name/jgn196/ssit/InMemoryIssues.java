package name.jgn196.ssit;

import java.util.ArrayList;

class InMemoryIssues implements IssueStore {

    private ArrayList<Issue> issues = new ArrayList<>();

    @Override
    public void init() {
        issues.clear();
    }

    @Override
    public Issue newIssue(final String description) {
        final Issue result = new Issue(description);

        issues.add(result);

        return result;
    }

    @Override
    public Iterable<Issue> outstandingIssues() {
        return issues;
    }

    @Override
    public void close(int issueId) {
        issues.remove(issueId - 1);
    }
}
