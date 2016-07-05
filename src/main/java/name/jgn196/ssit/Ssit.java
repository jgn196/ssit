package name.jgn196.ssit;

import java.io.File;
import java.util.Optional;

public class Ssit {

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
        issueStoreInWorkingDirectory().init();
        System.out.println("Project initialised.");
    }

    private static IssueStore issueStoreInWorkingDirectory() {
        return new OnDiskIssues(ProjectFinder.projectDirectoryInWorkingDirectory());
    }

    private static void addNewIssue(final String args[]) throws NoSsitProject {
        if (args.length != 2) {
            System.out.println("No new issue description provided.\n" +
                    "\n" +
                    "usage: ssit todo <issue description>");
            return;
        }

        findIssueStore().newIssue(args[1]);

        System.out.println("Issue added.");
    }

    private static IssueStore findIssueStore() throws NoSsitProject {
        final Optional<File> projectDirectory = new ProjectFinder(new File(".").getAbsoluteFile()).find();

        if(projectDirectory.isPresent()) return new OnDiskIssues(projectDirectory.get());

        throw new NoSsitProject();
    }

    private static void printOutstandingIssues() throws NoSsitProject{
        findIssueStore().printOutstanding(System.out);
    }

    private static void closeIssue(final String[] args) throws NoSsitProject {
        if (args.length != 2) {
            System.out.println("No issue ID provided.\n" +
                    "\n" +
                    "usage: ssit close <issue ID>");
            return;
        }

        findIssueStore().close(Integer.parseInt(args[1]));

        System.out.println("Issue closed.");
    }
}