package comm.exception;

import java.io.File;

/**
 * Created by beenotung on 11/12/15.
 */
public class EssentialFileNotFoundException extends RichFileNotFoundException {
    public EssentialFileNotFoundException(String path) {
        super(path);
    }

    public EssentialFileNotFoundException(File file) {
        super(file);
    }

    @Override
    protected String reason() {
        return "Essential File Not Found!";
    }
}
