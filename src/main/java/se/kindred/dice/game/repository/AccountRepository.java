package se.kindred.dice.game.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import se.kindred.dice.game.model.Account;
public interface AccountRepository extends MongoRepository<Account, String> {}

