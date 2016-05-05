package name.jgn196.ssit;

interface IssueStore {
    void init();

    Issue newIssue(final String description);

    Iterable<Issue> outstandingIssues();

    void close(int issueId);
}
