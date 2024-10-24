package bankAPI.exception;

import bankAPI.payload.ErrorDetail;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetail> handleResourceNotFoundException(ResourceNotFoundException e, WebRequest wr) {
        ErrorDetail errorDetail = new ErrorDetail(
                new Date(),
                e.getMessage(),
                wr.getDescription(false)
        );
        return new ResponseEntity<>(errorDetail, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BankAPIException.class)
    public ResponseEntity<ErrorDetail> handleBankAPIException(BankAPIException e, WebRequest wr) {

        ErrorDetail errorDetail = new ErrorDetail(
                new Date(),
                e.getMessage(),
                wr.getDescription(false)
        );

        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
                                                                  HttpHeaders httpHeaders,
                                                                  HttpStatusCode httpStatusCode,
                                                                  WebRequest webRequest) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDetail> handleDataIntegrityException(DataIntegrityViolationException e,
                                                                    WebRequest webRequest) {
        ErrorDetail errorDetail = new ErrorDetail(
                new Date(),
                e.getMessage(),
                webRequest.getDescription(false)
        );

        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }

}
