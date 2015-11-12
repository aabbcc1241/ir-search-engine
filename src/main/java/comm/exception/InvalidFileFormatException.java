package comm.exception;


import java.io.File;

/**
 * Created by beenotung on 11/12/15.
 */
public class InvalidFileFormatException extends RichFileNotFoundException {
    public InvalidFileFormatException(String path) {
        super(path);
    }

    public InvalidFileFormatException(File file) {
        super(file);
    }

    @Override
    protected String reason() {
        return "Invalid File Format!";
    }
}
