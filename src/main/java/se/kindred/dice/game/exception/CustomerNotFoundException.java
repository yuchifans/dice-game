package se.kindred.dice.game.exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException() {
        super("No customer Found!");
    }
}
