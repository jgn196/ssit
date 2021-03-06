package name.jgn196.ssit;

import java.io.File;
import java.util.Optional;

abstract class Command implements Runnable {
    static Command createFrom(final String[] args) {

        if (args.length == 0) return new UsageCommand();

        switch (args[0]) {
            case "init":
                return new InitialiseCommand();
            case "todo":
                return new NewIssueCommand(args, Command::findIssueStore);
            case "list":
                return new ListIssuesCommand(Command::findIssueStore);
            case "close":
                return new CloseIssueCommand(args, Command::findIssueStore);
            default:
                return new UnknownCommand();
        }
    }

    private static IssueStore findIssueStore() throws NoSsitProject {
        final File workingDirectory = new File(".").getAbsoluteFile();
        final Optional<File> projectDirectory = new ProjectFinder(workingDirectory).find();

        if (projectDirectory.isPresent()) return new OnDiskIssues(projectDirectory.get());

        throw new NoSsitProject();
    }
}
