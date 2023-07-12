package se.kindred.dice.game.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Builder(setterPrefix = "with")
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Document("customers")
public class Customer {
    @NonNull
    @Id
    private String ssn;
    @NonNull
    private LocalDate birthday;
    @Builder.Default
    private CustomerStatus status = CustomerStatus.INACTIVE;
    @NonNull
    private String customerId;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private String password;
    private String accountNumber;
}
