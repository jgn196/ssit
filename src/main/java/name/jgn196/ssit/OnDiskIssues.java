package name.jgn196.ssit;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.stream.Collectors;

class OnDiskIssues implements IssueStore {

    private static final File TODO_DIRECTORY = new File(".todo");
    private static final Path NEXT_ID_FILE = TODO_DIRECTORY.toPath().resolve("nextId.txt");
    private static final Path OPEN_ISSUES_FILE = TODO_DIRECTORY.toPath().resolve("open_issues.txt");

    @Override
    public void init() {
        if (!TODO_DIRECTORY.exists()) {
            if (!TODO_DIRECTORY.mkdir()) throw new RuntimeException("Failed to create issue storage.");
        }
    }

    @Override
    public Issue newIssue(final String description) {
        final int issueId = nextIssueId();
        final Issue result = new Issue(issueId, description);
        result.exportTo(new FileIssueExporter(TODO_DIRECTORY));

        try {
            if (OPEN_ISSUES_FILE.toFile().exists()) {
                Files.write(OPEN_ISSUES_FILE, (Integer.toString(issueId) + "\n").getBytes("UTF-8"), StandardOpenOption.APPEND);
            } else {
                Files.write(OPEN_ISSUES_FILE, (Integer.toString(issueId) + "\n").getBytes("UTF-8"));
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to update open issues file.", e);
        }

        return result;
    }

    private int nextIssueId() {
        try {
            final int result;
            if (!NEXT_ID_FILE.toFile().exists()) {
                result = 1;
            } else {
                result = Integer.parseInt(new String(Files.readAllBytes(NEXT_ID_FILE), "UTF-8"));
            }
            Files.write(NEXT_ID_FILE, Integer.toString(result + 1).getBytes("UTF-8"));
            return result;
        } catch (IOException e) {
            throw new RuntimeException("Failed to get and increment next ID file.", e);
        }
    }

    @Override
    public void close(final int issueId) {
        try {
            Files.write(
                    OPEN_ISSUES_FILE,
                    Files.readAllLines(OPEN_ISSUES_FILE)
                            .stream()
                            .filter(id -> !id.equals(Integer.toString(issueId)))
                            .collect(Collectors.toList()));
        } catch (IOException e) {
            throw new RuntimeException("Failed to update open issues file.", e);
        }
    }

    @Override
    public void printOutstanding(final PrintStream printStream) {
        try {
            for (final String issueId : Files.readAllLines(OPEN_ISSUES_FILE)) {
                final int id = Integer.parseInt(issueId);
                final String description = new String(Files.readAllBytes(TODO_DIRECTORY.toPath().resolve(issueId + ".txt")), "UTF-8");
                printStream.println(new Issue(id, description).toString());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read open issues file.", e);
        }
    }
}
