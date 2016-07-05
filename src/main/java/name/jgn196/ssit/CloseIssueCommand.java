package name.jgn196.ssit;

import java.util.function.Supplier;

class CloseIssueCommand extends Command {

    private final String[] args;
    private final Supplier<IssueStore> storeSupplier;

    CloseIssueCommand(final String[] args, final Supplier<IssueStore> storeSupplier) {
        this.args = args;
        this.storeSupplier = storeSupplier;
    }

    @Override
    public void run() {
        if (args.length != 2) {
            System.out.println("No issue ID provided.\n" +
                    "\n" +
                    "usage: ssit close <issue ID>");
            return;
        }

        storeSupplier.get().close(Integer.parseInt(args[1]));

        System.out.println("Issue closed.");
    }
}
