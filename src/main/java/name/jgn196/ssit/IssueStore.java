package name.jgn196.ssit;

import java.io.PrintStream;

interface IssueStore {
    void init();

    Issue newIssue(String description);

    void close(int issueId);

    void printOutstanding(PrintStream printStream) throws NoSsitProject;
}
