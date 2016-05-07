package name.jgn196.ssit;

class Issue {
    private final int id;
    private final String description;

    public Issue(final int id, final String description) {
        this.id = id;
        this.description = description;
    }

    public int id() {
        return id;
    }

    @Override
    public String toString() {
        return id + ":\t" + description;
    }
}
