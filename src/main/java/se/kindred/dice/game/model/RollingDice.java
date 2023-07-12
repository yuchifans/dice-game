package se.kindred.dice.game.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.security.SecureRandom;

@Getter
@Setter
@AllArgsConstructor
public class RollingDice {
    private int score = 0;
    public void roll() {
       SecureRandom random = new SecureRandom();
       this.score = random.nextInt(6) + 1;
    }
}