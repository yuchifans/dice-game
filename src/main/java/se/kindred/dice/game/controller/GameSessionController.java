package se.kindred.dice.game.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import se.kindred.dice.game.model.ApiResult;
import se.kindred.dice.game.model.ApiResultFactory;
import se.kindred.dice.game.model.BetResult;
import se.kindred.dice.game.service.GameSessionService;

@RestController
@RequestMapping(value = "/game", produces = MediaType.APPLICATION_JSON_VALUE)
public class GameSessionController {
    private static final String SSN_PATTERN = "^(19|20)(\\d{6}\\d{4})$";
    private static final String DICE_PATTERN = "^[1-6]$";
    private static final String BET_AMOUNT_PATTERN = "^(\\d)*$";
    @Autowired
    private final GameSessionService gameSessionService;


    public GameSessionController(GameSessionService gameSessionService) {
        this.gameSessionService = gameSessionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult<BetResult> startDiceGame(@RequestParam @Pattern(regexp = SSN_PATTERN) String ssn, @RequestParam @Pattern(regexp = BET_AMOUNT_PATTERN) Integer betAmount, @RequestParam @Pattern(regexp = DICE_PATTERN) Integer betDiceNumber, HttpSession httpSession) {
        try {
            BetResult betResult = gameSessionService.runGame(ssn, betAmount, betDiceNumber, httpSession);
            return new ApiResultFactory<BetResult>(true)
                    .entity(betResult)
                    .create();
        } catch (RuntimeException e) {
            return new ApiResultFactory<BetResult>(false).message(e.getMessage()).create();
        }
    }
}
