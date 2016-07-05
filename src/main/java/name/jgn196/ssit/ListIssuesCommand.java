package name.jgn196.ssit;

import java.util.function.Supplier;

class ListIssuesCommand extends Command {

    private final Supplier<IssueStore> storeSupplier;

    ListIssuesCommand(final Supplier<IssueStore> storeSupplier) {

        this.storeSupplier = storeSupplier;
    }

    @Override
    public void run() {
        storeSupplier.get().printOutstanding(System.out);
    }
}
