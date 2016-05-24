package name.jgn196.ssit;

import java.io.File;

public class Ssit {

    private static final IssueStore issues = new OnDiskIssues(new File(".todo"));

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
            case "close":
                closeIssue(args);
                break;
            default:
                System.out.println("Unknown command.");
                break;
        }
    }

    private static void initialiseIssueStore() {
        issues.init();
        System.out.println("Project initialised.");
    }

    private static void addNewIssue(final String args[]) {
        if (args.length != 2) {
            System.out.println("No new issue description provided.");
            return;
        }

        issues.newIssue(args[1]);

        System.out.println("Issue added.");
    }

    private static void printOutstandingIssues() {
        issues.printOutstanding(System.out);
    }

    private static void closeIssue(final String[] args) {
        if(args.length != 2) {
            System.out.println("No issue ID provided.");
            return;
        }

        issues.close(Integer.parseInt(args[1]));
    }
}
