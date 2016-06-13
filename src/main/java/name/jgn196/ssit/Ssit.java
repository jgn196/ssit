package name.jgn196.ssit;

import java.io.File;

public class Ssit {

    private static final IssueStore ISSUES = new OnDiskIssues(new File(".todo"));
    private static final String USAGE = "usage: ssit <command> [<args>]\n" +
            "\n" +
            "Commands:\n" +
            "\tclose\tClose an issue.\n" +
            "\tinit\tInitialise a new SSIT project.\n" +
            "\tlist\tList the outstanding issues.\n" +
            "\ttodo\tAdd a new issue.";

    public static void main(final String[] args) {
        if (args.length == 0) {
            System.out.println(USAGE);
            return;
        }

        try {
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
        } catch(final NoSsitProject error) {
            System.out.println("There is no SSIT project in the current directory (or any of its parents).");
        }
    }

    private static void initialiseIssueStore() {
        ISSUES.init();
        System.out.println("Project initialised.");
    }

    private static void addNewIssue(final String args[]) throws NoSsitProject {
        if (args.length != 2) {
            System.out.println("No new issue description provided.\n" +
                    "\n" +
                    "usage: ssit todo <issue description>");
            return;
        }

        ISSUES.newIssue(args[1]);

        System.out.println("Issue added.");
    }

    private static void printOutstandingIssues() throws NoSsitProject{
        ISSUES.printOutstanding(System.out);
    }

    private static void closeIssue(final String[] args) {
        if (args.length != 2) {
            System.out.println("No issue ID provided.\n" +
                    "\n" +
                    "usage: ssit close <issue ID>");
            return;
        }

        ISSUES.close(Integer.parseInt(args[1]));

        System.out.println("Issue closed.");
    }
}
