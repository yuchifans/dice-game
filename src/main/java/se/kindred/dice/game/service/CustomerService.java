package se.kindred.dice.game.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.kindred.dice.game.exception.CustomerNotFoundException;
import se.kindred.dice.game.model.Account;
import se.kindred.dice.game.model.Customer;
import se.kindred.dice.game.repository.AccountRepository;
import se.kindred.dice.game.repository.CustomerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private final CustomerRepository customerRepository;
    @Autowired
    private final AccountRepository accountRepository;

    public CustomerService(CustomerRepository customerRepository, AccountRepository accountRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
    }

    public Customer addCustomer(Customer customer) {
        if (customer == null)
            throw new IllegalArgumentException();
        Customer result = customerRepository.save(customer);
        accountRepository.save(Account.builder().withAccountNumber(customer.getSsn()).build());
        return result;
    }

    public Customer changeCustomerStatus(Customer customer) {
        if (customer == null)
            throw new IllegalArgumentException();
        return customerRepository.save(customer);
    }

    public void removeCustomer(String ssn) {
       customerRepository.deleteById(ssn);
    }

    public Optional<Customer> fetchCustomerBySsn(String ssn) {
        return customerRepository.findById(ssn);
    }

    public List<Customer> fetchAllCustomer() {
        return customerRepository.findAll();
    }
}
