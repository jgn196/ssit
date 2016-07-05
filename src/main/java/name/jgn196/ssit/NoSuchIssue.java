package name.jgn196.ssit;

class NoSuchIssue extends SsitFailure {

    NoSuchIssue(final int issueId) {
        super("No issue with ID " + issueId + " found.");
    }
}
