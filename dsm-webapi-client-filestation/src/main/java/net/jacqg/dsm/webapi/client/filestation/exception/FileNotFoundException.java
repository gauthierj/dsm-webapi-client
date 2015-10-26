package net.jacqg.dsm.webapi.client.filestation.exception;

import net.jacqg.dsm.webapi.client.exception.DsmWebApiErrorException;

public class FileNotFoundException extends DsmWebApiErrorException {

    public FileNotFoundException() {
        super("No such file or directory", 408);
    }
}
