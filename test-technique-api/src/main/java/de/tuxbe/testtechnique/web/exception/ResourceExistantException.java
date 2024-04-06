package de.tuxbe.testtechnique.web.exception;

public class ResourceExistantException extends RuntimeException{
    public ResourceExistantException(String message) {
        super(message);
    }
}
