package name.jgn196.ssit;

class InitialiseCommand extends Command {

    @Override
    public void run() {
        initialiseIssueStore();
    }

    private static void initialiseIssueStore() {
        issueStoreInWorkingDirectory().init();
        System.out.println("Project initialised.");
    }

    private static IssueStore issueStoreInWorkingDirectory() {
        return new OnDiskIssues(ProjectFinder.projectDirectoryInWorkingDirectory());
    }
}
