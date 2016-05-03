package name.jgn196.ssit;

import java.util.ArrayList;

public class Ssit {
    private static final ArrayList<String> issues = new ArrayList<>();

    public static void main(final String[] args) {
        if (args.length == 0) return; // TODO - Print usage

        switch (args[0]) {
            case "init":
                // TODO - Set up .todo directory
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
