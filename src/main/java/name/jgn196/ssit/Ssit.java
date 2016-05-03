package name.jgn196.ssit;

import java.io.File;
import java.util.ArrayList;

public class Ssit {

    public static final String TODO_DIRECTORY_NAME = ".todo";

    private static final ArrayList<String> issues = new ArrayList<>();
    private static final File TODO_DIRECTORY = new File(TODO_DIRECTORY_NAME);

    public static void main(final String[] args) {
        if (args.length == 0) return; // TODO - Print usage

        switch (args[0]) {
            case "init":
                if (!TODO_DIRECTORY.exists())
                    TODO_DIRECTORY.mkdir();
                break;
            case "todo":
                if (args.length >= 2)
                    issues.add(args[1]);
                break;
            case "list":
                issues.forEach(System.out::println);
                break;
        }
    }
}
