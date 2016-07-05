package name.jgn196.ssit;

class UnknownCommand extends Command {

    static final String ERROR_MESSAGE = "Unknown command.";

    @Override
    public void run() {
        System.out.println(ERROR_MESSAGE);
    }
}
