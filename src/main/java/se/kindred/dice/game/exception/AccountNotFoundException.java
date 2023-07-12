package se.kindred.dice.game.exception;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException() {
        super("No account Found!");
    }
}
