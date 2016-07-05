package name.jgn196.ssit;

class SsitFailure extends RuntimeException {

    SsitFailure(final String message) {
        super(message);
    }

    SsitFailure(final String message, final Throwable cause) {
        super(message, cause);
    }
}
