package ru.safronov.exceptions;

public class TooManyBooksException extends RuntimeException {

  public TooManyBooksException(String message) {
    super(message);
  }
}
