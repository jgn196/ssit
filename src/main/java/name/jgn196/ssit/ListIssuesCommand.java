package name.jgn196.ssit;

class ListIssuesCommand extends Command {

    @Override
    public void run() {
        findIssueStore().printOutstanding(System.out);
    }
}
