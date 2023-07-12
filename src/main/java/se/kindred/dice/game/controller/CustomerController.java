package se.kindred.dice.game.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import se.kindred.dice.game.model.ApiResult;
import se.kindred.dice.game.model.ApiResultFactory;
import se.kindred.dice.game.model.Customer;
import se.kindred.dice.game.model.CustomerStatus;
import se.kindred.dice.game.service.CustomerService;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/customer", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {
    private static final String SSN_PATTERN = "^(19|20)(\\d{6}\\d{4})$";
    private static final String BIRTHDAY_PATTERN = "^((19|2[0-9])[0-9]{2})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$";
    @Autowired
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{ssn}")
    public ApiResult<Customer> getCustomer(@PathVariable @Pattern(regexp = SSN_PATTERN) String ssn) {
        try {
            Optional<Customer> customer = customerService.fetchCustomerBySsn(ssn);
            return customer.isPresent() ? new ApiResultFactory<Customer>(true)
                    .entity(customer.get())
                    .create() :
                    new ApiResultFactory<Customer>(false)
                            .message("Customer does not exist!")
                            .create();
        } catch (RuntimeException e) {
            return new ApiResultFactory<Customer>(false)
                    .message("Failed to get customer by ssn: " + ssn + ".")
                    .create();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult<Customer> addCustomer(@RequestParam @Pattern(regexp = SSN_PATTERN) String ssn, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String password, @RequestParam @Pattern(regexp = BIRTHDAY_PATTERN) LocalDate birthday) {
        try {
            //Todo to check if the customer is an adult(age > 18 years old)
            Optional<Customer> result = customerService.fetchCustomerBySsn(ssn);
            if (result.isPresent()) {
                return new ApiResultFactory<Customer>(false)
                        .message("The customer already exists!")
                        .create();
            }
            Customer newCustomer = Customer.builder()
                    .withSsn(ssn)
                    .withCustomerId(generateCustomerId())
                    .withFirstName(firstName)
                    .withLastName(lastName)
                    .withBirthday(birthday)
                    .withPassword(password)
                    .withAccountNumber(ssn)
                    .build();
            Customer customer = customerService.addCustomer(newCustomer);
            return new ApiResultFactory<Customer>(true)
                    .entity(customer)
                    .create();
        } catch (RuntimeException e) {
            return new ApiResultFactory<Customer>(false)
                    .message("Failed to add customer by ssn: " + ssn + ".")
                    .create();
        }
    }

    @DeleteMapping("/{ssn}")
    public ApiResult<Customer> removeCustomer(@PathVariable @Pattern(regexp = SSN_PATTERN) String ssn) {
        try {
            customerService.removeCustomer(ssn);
            return new ApiResultFactory<Customer>(true)
                    .create();
        } catch (RuntimeException e) {
            return new ApiResultFactory<Customer>(false)
                    .message("Failed to remove customer by ssn: " + ssn + ".")
                    .create();
        }
    }

    @PostMapping("/login")
    public ApiResult<Customer> login(@RequestParam String ssn, @RequestParam String password, HttpSession httpSession) {
        try {
            Optional<Customer> customer = customerService.fetchCustomerBySsn(ssn);
            if (customer.isPresent() && password.equals(customer.get().getPassword())) {
                Customer customerToLoginIn = customer.get();
                customerToLoginIn.setStatus(CustomerStatus.ACTIVE);
                customerService.addCustomer(customerToLoginIn);
                httpSession.setAttribute("ssn", ssn);
                return new ApiResultFactory<Customer>(true)
                        .create();
            } else {
                httpSession.invalidate();
                return new ApiResultFactory<Customer>(false)
                        .message("You filled in wrong password!")
                        .create();
            }
        } catch (RuntimeException e) {
            httpSession.invalidate();
            return new ApiResultFactory<Customer>(false)
                    .message("Failed to login!")
                    .create();
        }
    }

    private String generateCustomerId() {
        return UUID.randomUUID().toString();
    }
}
