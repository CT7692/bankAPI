package bankAPI.exception;

import org.springframework.http.HttpStatus;

public class BankAPIException extends RuntimeException {
    private HttpStatus httpStatus;
    private String message;

    public BankAPIException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
