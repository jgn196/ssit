package name.jgn196.ssit;

import java.io.File;

class ProjectAlreadyInitialised extends SsitFailure {

    ProjectAlreadyInitialised(final File todoDirectory) {
        super("SSIT project already exists in '" + todoDirectory.getAbsoluteFile().getParent() + "'.");
    }
}
