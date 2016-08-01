package ru.tinkoff.school.network;

public class ApiException extends Exception {
    public ApiException(Exception cause) {
        super(cause);
    }
}
