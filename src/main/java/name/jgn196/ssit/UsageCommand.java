package name.jgn196.ssit;

class UsageCommand extends Command {

    private static final String USAGE = "usage: ssit <command> [<args>]\n" +
            "\n" +
            "Commands:\n" +
            "\tclose\tClose an issue.\n" +
            "\tinit\tInitialise a new SSIT project.\n" +
            "\tlist\tList the outstanding issues.\n" +
            "\ttodo\tAdd a new issue.";

    @Override
    public void run() {
        System.out.println(USAGE);
    }
}
