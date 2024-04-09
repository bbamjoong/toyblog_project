package klog.blog_project.exception;

public class ForbiddenUserException extends RuntimeException {
    public ForbiddenUserException(String message) {
        super(message);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}