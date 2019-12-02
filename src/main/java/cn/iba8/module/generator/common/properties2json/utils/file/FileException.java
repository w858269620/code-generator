package cn.iba8.module.generator.common.properties2json.utils.file;

import java.io.IOException;

/**
 * Wrapper class for IOException types.
 */
public class FileException extends RuntimeException {

    private static final long serialVersionUID = -2267356725601058L;

    public FileException(IOException cause) {
        super(cause);
    }

    public FileException(String message) {
        super(message);
    }
}
