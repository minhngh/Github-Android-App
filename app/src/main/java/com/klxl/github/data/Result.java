package com.klxl.github.data;

public class Result<T> {
    private Result(){}

    public static final class Success<T> extends Result{
        T data;

        public Success(T data) {
            this.data = data;
        }

        public T getData() {
            return data;
        }
    }
    public static final class Failure extends Result{
        Exception exception;

        public Failure(Exception exception) {
            this.exception = exception;
        }

        public Exception getException() {
            return exception;
        }
    }
}
