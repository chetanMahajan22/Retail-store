package org.retail.store.repository;

import org.retail.store.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserMongoRepository extends MongoRepository<User, Integer> {
}
