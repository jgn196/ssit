package name.jgn196.ssit;

class NoSsitProject extends SsitFailure {

    NoSsitProject() {
        super("There is no SSIT project in the current directory (or any of its parents).");
    }
}
