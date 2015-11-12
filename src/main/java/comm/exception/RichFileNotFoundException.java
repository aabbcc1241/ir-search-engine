package comm.exception;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by beenotung on 11/12/15.
 */
public class RichFileNotFoundException extends FileNotFoundException {
    public final String path;

    public RichFileNotFoundException(String path) {
        this.path = path;
    }

    public RichFileNotFoundException(File file) {
        this(file.getPath());
    }

    protected String reason() {
        return "File not found!";
    }

    @Override
    public String getLocalizedMessage() {
        return reason() + " (" + path + ")";
    }
}
