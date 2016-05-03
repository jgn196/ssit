package name.jgn196.ssit;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

public class TheSsit {

    private static final File TODO_DIRECTORY = new File(Ssit.TODO_DIRECTORY_NAME);

    @Before
    public void removeDotTodoDirectory() {
        if(TODO_DIRECTORY.exists()) {
            TODO_DIRECTORY.delete();
        }

        assertThat(TODO_DIRECTORY).doesNotExist();
    }

    @Test
    public void storesDataInTheDotTodoDirectory() {
        Ssit.main(new String[]{"init"});

        assertThat(TODO_DIRECTORY).exists().isDirectory();
    }
}
