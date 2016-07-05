package name.jgn196.ssit;

import java.util.function.Supplier;

class NewIssueCommand extends Command {

    private final String[] args;
    private final Supplier<IssueStore> storeSupplier;

    NewIssueCommand(final String[] args, final Supplier<IssueStore> storeSupplier) {
        this.args = args;
        this.storeSupplier = storeSupplier;
    }

    @Override
    public void run() {
        if (args.length != 2) {
            System.out.println("No new issue description provided.\n" +
                    "\n" +
                    "usage: ssit todo <issue description>");
            return;
        }

        storeSupplier.get().newIssue(args[1]);

        System.out.println("Issue added.");
    }
}