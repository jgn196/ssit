package name.jgn196.ssit;

public class Ssit {

    public static void main(final String[] args) {
        try {
            Command.createFrom(args).run();
        } catch (final SsitFailure error) {
            System.out.println(error.getMessage());
        }
    }
}