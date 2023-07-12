package se.kindred.dice.game.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
public class BetResult {
    private String ssn;
    private Integer currentBalance;
    private Integer diceResult;
    private String result;

}
