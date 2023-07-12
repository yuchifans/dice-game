package se.kindred.dice.game.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import se.kindred.dice.game.model.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String> {}
