package name.jgn196.ssit;

class CloseIssueCommand extends Command {

    private final String[] args;

    CloseIssueCommand(final String[] args) {

        this.args = args;
    }

    @Override
    public void run() {
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
