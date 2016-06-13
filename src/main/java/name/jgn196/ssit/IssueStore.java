package name.jgn196.ssit;

import java.io.PrintStream;

interface IssueStore {
    void init();

    Issue newIssue(String description) throws NoSsitProject;

    void close(int issueId) throws NoSsitProject;

    void printOutstanding(PrintStream printStream) throws NoSsitProject;
}
