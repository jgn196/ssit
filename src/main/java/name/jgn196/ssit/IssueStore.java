package name.jgn196.ssit;

import java.io.PrintStream;

interface IssueStore {
    void init();

    Issue newIssue(final String description);

    Iterable<Issue> outstandingIssues();

    void close(int issueId);

    void printOutstanding(PrintStream printStream);
}
