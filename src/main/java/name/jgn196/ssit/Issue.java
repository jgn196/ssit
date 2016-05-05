package name.jgn196.ssit;

class Issue {
    private final String description;

    public Issue(final String description) {

        this.description = description;
    }

    public String description() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }
}
