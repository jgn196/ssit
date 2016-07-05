package name.jgn196.ssit;

import java.util.function.Supplier;

class CloseIssueCommand extends Command {

    private static final int EXPECTED_ARGUMENT_COUNT = 2;
    private static final int ISSUE_ID_ARGUMENT_INDEX = 1;

    private final String[] args;
    private final Supplier<IssueStore> storeSupplier;

    CloseIssueCommand(final String[] args, final Supplier<IssueStore> storeSupplier) {
        this.args = args;
        this.storeSupplier = storeSupplier;
    }

    @Override
    public void run() {
        checkArguments();

        storeSupplier.get().close(issueId());

        System.out.println("Issue closed.");
    }

    private void checkArguments() {
        if (args.length != EXPECTED_ARGUMENT_COUNT) {

            throw new BadCommandArguments(
                    "No issue ID provided.\n" +
                            "\n" +
                            "usage: ssit close <issue ID>");
        }
    }

    private int issueId() {
        try {
            return Integer.parseInt(issueIdText());
        } catch (final NumberFormatException error) {
            throw new BadCommandArguments(quote(issueIdText()) + " is not a valid issue ID.", error);
        }
    }

    private String issueIdText() {
        return args[ISSUE_ID_ARGUMENT_INDEX];
    }

    private static String quote(final String text) {
        return "'" + text + "'";
    }
}