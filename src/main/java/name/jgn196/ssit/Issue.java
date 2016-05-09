package name.jgn196.ssit;

class Issue {
    private final int id;
    private final String description;

    public Issue(final int id, final String description) {
        this.id = id;
        this.description = description;
    }

    public boolean matchesId(final int id) {
        return this.id == id;
    }

    @Override
    public String toString() {
        return id + ":\t" + description;
    }

    public void exportTo(final IssueExporter exporter) {
        exporter.withId(id)
                .withDescription(description)
                .export();
    }

    public interface IssueExporter {

        IssueExporter withId(int id);

        IssueExporter withDescription(String value);

        void export();
    }
}

