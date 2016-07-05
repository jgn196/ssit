package name.jgn196.ssit;

class NewIssueCommand extends Command {

    private final String[] args;

    NewIssueCommand(final String[] args) {
        this.args = args;
    }

    @Override
    public void run() {
        if (args.length != 2) {
            System.out.println("No new issue description provided.\n" +
                    "\n" +
                    "usage: ssit todo <issue description>");
            return;
        }

        findIssueStore().newIssue(args[1]);

        System.out.println("Issue added.");
    }
}
