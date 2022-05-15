package org.retail.store.repository;

import org.retail.store.model.Bill;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BillMongoRepository extends MongoRepository<Bill, Integer> {
}
