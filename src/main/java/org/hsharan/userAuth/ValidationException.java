package org.hsharan.userAuth;

public class ValidationException extends Exception{
    public ValidationException(String errorMessage){
        super(errorMessage);
    }
}
