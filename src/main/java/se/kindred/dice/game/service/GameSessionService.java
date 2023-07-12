package se.kindred.dice.game.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import se.kindred.dice.game.exception.AccountNotFoundException;
import se.kindred.dice.game.exception.CustomerNotFoundException;
import se.kindred.dice.game.model.*;
import se.kindred.dice.game.repository.AccountRepository;
import se.kindred.dice.game.repository.CustomerRepository;

import java.util.Objects;

@Service
public class GameSessionService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private Environment environment;

    public GameSessionService(AccountRepository accountRepository, CustomerRepository customerRepository, Environment environment) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.environment = environment;
    }

    public BetResult runGame(String ssn, Integer betAmount, Integer betDiceNumber, HttpSession httpSession) {
        Customer customer = customerRepository.findById(ssn).orElseThrow(CustomerNotFoundException::new);
        Account account = accountRepository.findById(ssn).orElseThrow(AccountNotFoundException::new);
        if (customer.getSsn().equals(httpSession.getAttribute("ssn")) && customer.getStatus() == CustomerStatus.ACTIVE) {
            GameSession gameSession = GameSession.builder()
                    .withCustomer(customer)
                    .withAccount(account)
                    .withBet(Bet.builder()
                            .withBetAmount(betAmount)
                            .withBetDiceNumber(betDiceNumber)
                            .build())
                    .withMinBetAmount(Integer.parseInt(Objects.requireNonNull(environment.getProperty("game.minBetAmount"))))
                    .withMaxBetAmount(Integer.parseInt(Objects.requireNonNull(environment.getProperty("game.maxBetAmount"))))
                    .withRollingDice(new RollingDice(0))
                    .build();
            gameSession.run();
            accountRepository.save(gameSession.getAccount());
            return gameSession.getResult();
        } else {
            throw new RuntimeException("The customer is not allowed to play the game!");
        }
    }
}
