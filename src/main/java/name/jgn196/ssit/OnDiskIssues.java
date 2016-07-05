package name.jgn196.ssit;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.stream.Collectors;

class OnDiskIssues implements IssueStore {

    private final File todoDirectory;
    private final Path nextIdFile;
    private final Path openIssuesFile;

    OnDiskIssues(final File todoDirectory) {
        this.todoDirectory = todoDirectory;
        nextIdFile = todoDirectory.toPath().resolve("nextId.txt");
        openIssuesFile = todoDirectory.toPath().resolve("open_issues.txt");
    }

    @Override
    public void init() {
        if (!todoDirectory.exists()) {
            if (!todoDirectory.mkdir()) throw new RuntimeException("Failed to create issue storage.");
        }
    }

    @Override
    public Issue newIssue(final String description) throws NoSsitProject {
        checkForSsitProject();

        final int issueId = nextIssueId();
        final Issue result = new Issue(issueId, description);
        result.exportTo(new FileIssueExporter(todoDirectory));

        try {
            if (openIssuesFile.toFile().exists()) {
                Files.write(openIssuesFile, (Integer.toString(issueId) + "\n").getBytes("UTF-8"), StandardOpenOption.APPEND);
            } else {
                Files.write(openIssuesFile, (Integer.toString(issueId) + "\n").getBytes("UTF-8"));
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to update open issues file.", e);
        }

        return result;
    }

    private void checkForSsitProject() throws NoSsitProject {
        if (!todoDirectory.exists()) throw new NoSsitProject();
    }

    private int nextIssueId() {
        try {
            final int result;
            if (!nextIdFile.toFile().exists()) {
                result = 1;
            } else {
                result = Integer.parseInt(new String(Files.readAllBytes(nextIdFile), "UTF-8"));
            }
            Files.write(nextIdFile, Integer.toString(result + 1).getBytes("UTF-8"));
            return result;
        } catch (IOException e) {
            throw new RuntimeException("Failed to get and increment next ID file.", e);
        }
    }

    @Override
    public void close(final int issueId) throws NoSsitProject {
        checkForSsitProject();

        try {
            Files.write(
                    openIssuesFile,
                    Files.readAllLines(openIssuesFile)
                            .stream()
                            .filter(id -> !id.equals(Integer.toString(issueId)))
                            .collect(Collectors.toList()));
        } catch (final IOException e) {
            throw new RuntimeException("Failed to update open issues file.", e);
        }
    }

    @Override
    public void printOutstanding(final PrintStream printStream) throws NoSsitProject {
        checkForSsitProject();

        if(openIssuesFile.toFile().exists()) {
            try {
                for (final String issueId : Files.readAllLines(openIssuesFile)) {
                    final int id = Integer.parseInt(issueId);
                    final String description = new String(
                            Files.readAllBytes(todoDirectory.toPath().resolve(issueId + ".txt")), "UTF-8");
                    printStream.println(new Issue(id, description).toString());
                }
            } catch (final IOException e) {
                throw new RuntimeException("Failed to read open issues file.", e);
            }
        }
    }
}