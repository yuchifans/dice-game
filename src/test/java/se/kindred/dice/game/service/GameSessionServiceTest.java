package se.kindred.dice.game.service;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.env.Environment;
import se.kindred.dice.game.model.BetResult;
import se.kindred.dice.game.model.CustomerStatus;
import se.kindred.dice.game.model.Mock;
import se.kindred.dice.game.repository.AccountRepository;
import se.kindred.dice.game.repository.CustomerRepository;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
public class GameSessionServiceTest {

    private CustomerRepository customerRepository = Mockito.mock(CustomerRepository.class);
    private AccountRepository accountRepository = Mockito.mock(AccountRepository.class);
    private Environment environment = Mockito.mock(Environment.class);
    private GameSessionService gameSessionService = new GameSessionService(accountRepository, customerRepository, environment);

    private HttpSession httpSession = Mockito.mock(HttpSession.class);

    @BeforeEach
    void mock() {
        Mockito.when(environment.getProperty("game.maxBetAmount")).thenReturn("6");
        Mockito.when(environment.getProperty("game.minBetAmount")).thenReturn("3");
        Mockito.when(customerRepository.findById(Mock.MOCKED_SSN)).thenReturn(Optional.of(Mock.mockCustomer(CustomerStatus.ACTIVE)));
        Mockito.when(httpSession.getAttribute("ssn")).thenReturn(Mock.MOCKED_SSN);
    }
    @Test
    void returnsGameResultWhenRunningGame() {
        Mockito.when(accountRepository.findById(Mock.MOCKED_SSN)).thenReturn(Optional.of(Mock.mockAccount(10)));
        BetResult result = gameSessionService.runGame(Mock.MOCKED_SSN, 5, 4, httpSession);
        assertNotNull(result);
        assertEquals(Mock.MOCKED_SSN, result.getSsn());
    }

    @Test
    void throwsAnIllegalArgumentExceptionWhenRunningGameWithABetAmountAboveMaxBetAmount() {
        Mockito.when(accountRepository.findById(Mock.MOCKED_SSN)).thenReturn(Optional.of(Mock.mockAccount(10)));
        assertThrows(IllegalArgumentException.class, () -> gameSessionService.runGame(Mock.MOCKED_SSN, 7, 4, httpSession), "Invalid bet amount!");
    }

    @Test
    void throwsAnIllegalArgumentExceptionWhenRunningGameWithNoBalanceInAccount() {
        Mockito.when(accountRepository.findById(Mock.MOCKED_SSN)).thenReturn(Optional.of(Mock.mockAccount(0)));
        assertThrows(IllegalArgumentException.class, () -> gameSessionService.runGame(Mock.MOCKED_SSN, 2, 4, httpSession), "The customer doesn't have enough money!");
    }

    @Test
    void throwsAnIllegalArgumentExceptionWhenRunningGameWithLowerBalanceThanBetAmount() {
        Mockito.when(accountRepository.findById(Mock.MOCKED_SSN)).thenReturn(Optional.of(Mock.mockAccount(1)));
        assertThrows(IllegalArgumentException.class, () -> gameSessionService.runGame(Mock.MOCKED_SSN, 5, 4, httpSession), "The customer doesn't have enough money!");
    }
}
