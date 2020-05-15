package com.questv.api.exception.handler;

import java.util.Date;

public class ExceptionResponse {
  private final Date date;
  private final Exception exception;
  private final String description;

  public ExceptionResponse(final Date date,
                           final Exception exception,
                           final String description) {
    this.date = date;
    this.exception = exception;
    this.description = description;
  }

  public Date getDate() {
    return date;
  }

  public String getException() {
    final StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(exception.getLocalizedMessage());
    final Throwable cause = exception.getCause();
    if (cause != null) {
      stringBuilder.append(" due to ");
      stringBuilder.append(cause.getLocalizedMessage());
    }

    return stringBuilder.toString();
  }

  public String getDescription() {
    return description;
  }
}
