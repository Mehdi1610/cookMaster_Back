package com.cookMaster.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FileExistsException extends RuntimeException{

    public FileExistsException(String message){
        super(message);
    }
}
