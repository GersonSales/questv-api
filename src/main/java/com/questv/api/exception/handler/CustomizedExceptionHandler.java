package com.questv.api.exception.handler;

import com.questv.api.exception.InvalidJwtAuthenticationException;
import com.questv.api.exception.file.FileStorageException;
import com.questv.api.exception.user.AccountCreationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class CustomizedExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler({
      InvalidJwtAuthenticationException.class,
      AccountCreationException.class
  })
  public final ResponseEntity<?> handleJwtAuthenticationException(
      final Exception exception,
      final WebRequest webRequest
  ) {
    final ExceptionResponse exceptionResponse =
        getExceptionResponse(exception, webRequest);
    return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({
      BadCredentialsException.class
  })
  public final ResponseEntity<?> handleForbiddenException(
      final Exception exception,
      final WebRequest webRequest
  ) {
    final ExceptionResponse exceptionResponse =
        getExceptionResponse(exception, webRequest);
    return new ResponseEntity<>(exceptionResponse, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler({
      FileStorageException.class
  })
  public final ResponseEntity<?> handleNotAcceptable(
      final Exception exception,
      final WebRequest webRequest
  ) {
    final ExceptionResponse exceptionResponse =
        getExceptionResponse(exception, webRequest);
    return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_ACCEPTABLE);
  }

  private ExceptionResponse getExceptionResponse(final Exception exception,
                                                 final WebRequest webRequest) {
    return new ExceptionResponse(
        new Date(),
        exception,
        webRequest.getDescription(false)
    );
  }


}
