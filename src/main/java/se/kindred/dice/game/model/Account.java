package se.kindred.dice.game.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder(setterPrefix = "with")
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Document("accounts")
public class Account {
    @NonNull
    @Id
    private String accountNumber;
    @Builder.Default
    private Integer balance = 10;
    @Builder.Default
    private AccountStatus status = AccountStatus.ACTIVE;

    public synchronized boolean deposit(String threadName, int change) {
        balance += change;
        System.out.println("01：" + threadName + ", deposit amount is " + change + ", balance is " + balance + " after deposit!");
        return true;
    }

    public synchronized boolean withdraw(String threadName, int money) {
        if (balance <= 0 || balance < money) {
            System.out.println(threadName + ", balance is" + balance + ", withdraw amount is " + money + ", withdraw is not allowed!");
            return false;
        } else {
            balance -= money;
            System.out.println("02：" + threadName + ", withdraw amount is " + money + ", balance is " + balance + " after withdraw!");
            return true;
        }
    }
}
