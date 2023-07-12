package se.kindred.dice.game.model;

import java.time.LocalDate;

public class Mock {
    public static final String MOCKED_SSN = "198607203215";
    public static Customer mockCustomer(CustomerStatus status) {
        return Customer.builder()
                .withSsn(MOCKED_SSN)
                .withPassword("password")
                .withCustomerId("customerId")
                .withStatus(status)
                .withLastName("lastname")
                .withFirstName("firstname")
                .withAccountNumber(MOCKED_SSN)
                .withBirthday(LocalDate.of(1989,12,3))
                .build();
    }

    public static Account mockAccount(Integer balance) {
        return Account.builder()
                .withAccountNumber(MOCKED_SSN)
                .withBalance(balance)
                .withStatus(AccountStatus.ACTIVE)
                .build();
    }

}
