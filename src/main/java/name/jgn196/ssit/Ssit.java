package name.jgn196.ssit;

public class Ssit {

    private static final IssueStore issues = new InMemoryIssues();

    public static void main(final String[] args) {
        if (args.length == 0) return; // TODO - Print usage

        switch (args[0]) {
            case "init":
                initialiseIssueStore();
                break;
            case "todo":
                addNewIssue(args);
                break;
            case "list":
                printOutstandingIssues();
                break;
        }
    }

    private static void initialiseIssueStore() {
        issues.init();
    }

    private static void addNewIssue(final String args[]) {
        if (args.length != 2) {
            System.err.println("No new issue description provided.");
            return;
        }

        issues.newIssue(args[1]);
    }

    private static void printOutstandingIssues() {
        issues.outstandingIssues().forEach(System.out::println);
    }
}
