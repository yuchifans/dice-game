package se.kindred.dice.game.model;

import lombok.*;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
public class GameSession implements Runnable{
    @Id
    private String sessionId;
    @NonNull
    private Bet bet;
    @NonNull
    private Account account;
    @NonNull
    private Customer customer;
    private Integer maxBetAmount ;
    private Integer minBetAmount;
    @NonNull
    private RollingDice rollingDice;
    private BetResult result;

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        Integer betAmount = bet.getBetAmount();
        if (betAmount > maxBetAmount || betAmount < minBetAmount) {
            throw new IllegalArgumentException("Invalid bet amount!");
        }
        if (account.getBalance() <= 0 || betAmount > account.getBalance()) {
            throw new IllegalArgumentException("The customer doesn't have enough money!");
        }
        rollingDice.roll();
        if (bet.getBetDiceNumber() == rollingDice.getScore()) {
            account.deposit(threadName, betAmount);
            this.result = BetResult.builder().withResult("Win")
                    .withSsn(this.customer.getSsn())
                    .withCurrentBalance(this.account.getBalance())
                    .withDiceResult(rollingDice.getScore())
                    .build();
        } else {
            account.withdraw(threadName, betAmount);
            this.result = BetResult.builder().withResult("Loss")
                    .withSsn(this.customer.getSsn())
                    .withCurrentBalance(this.account.getBalance())
                    .withDiceResult(rollingDice.getScore())
                    .build();
        }
    }
}
