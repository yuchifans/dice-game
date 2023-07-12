package se.kindred.dice.game.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import se.kindred.dice.game.model.Customer;
import se.kindred.dice.game.model.CustomerStatus;
import se.kindred.dice.game.model.Mock;
import se.kindred.dice.game.repository.AccountRepository;
import se.kindred.dice.game.repository.CustomerRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerServiceTest {

    private CustomerRepository customerRepository = Mockito.mock(CustomerRepository.class);
    private AccountRepository accountRepository = Mockito.mock(AccountRepository.class);
    private CustomerService customerService = new CustomerService(customerRepository, accountRepository);

    @Test
    void throwsAnIllegalArgumentExceptionWhenAddingANullCustomer() {
        assertThrows(IllegalArgumentException.class, () -> customerService.addCustomer(null));
    }

    @Test
    void returnsTheAddedCustomerWhenAddingAValidCustomer() {
        Customer mocked = Mock.mockCustomer(CustomerStatus.INACTIVE);
        Mockito.when(customerRepository.save(mocked)).thenReturn(mocked);
        assertEquals(mocked, customerService.addCustomer(mocked));
    }

    @Test
    void returnsACustomerWhenFetchingCustomerWithAValidSsn(){
        Customer mocked = Mock.mockCustomer(CustomerStatus.INACTIVE);
        Mockito.when(customerRepository.findById(Mock.MOCKED_SSN)).thenReturn(Optional.of(mocked));
        assertEquals(mocked, customerService.fetchCustomerBySsn(Mock.MOCKED_SSN).get());
    }
}
