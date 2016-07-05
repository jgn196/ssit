package name.jgn196.ssit;

class BadCommandArguments extends SsitFailure {

    BadCommandArguments(final String message) {
        super(message);
    }

    BadCommandArguments(final String message, final Throwable cause) {
        super(message, cause);
    }
}
