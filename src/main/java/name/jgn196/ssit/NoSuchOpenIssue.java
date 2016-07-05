package name.jgn196.ssit;

class NoSuchOpenIssue extends SsitFailure {

    NoSuchOpenIssue(final int issueId) {
        super("No open issue with ID " + issueId + " found.");
    }
}
