package dev.romario.proyecto.exceptions;

public class PartnerNotFoundException extends RuntimeException {
    public PartnerNotFoundException(String message) {
        super(message);
    }
}
