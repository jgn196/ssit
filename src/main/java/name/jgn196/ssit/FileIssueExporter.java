package name.jgn196.ssit;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class FileIssueExporter implements Issue.IssueExporter {

    private final File parentDirectory;
    private int issueId;
    private String description;

    public FileIssueExporter(final File parentDirectory) {
        this.parentDirectory = parentDirectory;
    }

    @Override
    public Issue.IssueExporter withId(final int id) {
        issueId = id;

        return this;
    }

    @Override
    public Issue.IssueExporter withDescription(final String description) {
        this.description = description;

        return this;
    }

    @Override
    public void export() {
        try {
            final Path issueFile = parentDirectory.toPath().resolve(issueId + ".txt");
            Files.write(issueFile, description.getBytes("UTF-8"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to write issue file.", e);
        }
    }
}
