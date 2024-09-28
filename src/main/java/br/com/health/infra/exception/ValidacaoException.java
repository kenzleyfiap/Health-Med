package br.com.health.infra.exception;

public class ValidacaoException extends RuntimeException {
    public ValidacaoException(String message) {
    super(message);
    }
}
