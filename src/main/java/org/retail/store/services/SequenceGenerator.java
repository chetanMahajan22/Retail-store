package org.retail.store.services;

import org.retail.store.model.DatabaseSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SequenceGenerator {

    @Autowired
    private MongoOperations mongoOperations;


    public int getSequenceNumber(String sequenceName) {
        //get sequence no
        Query query = new Query(Criteria.where("id").is(sequenceName));
        //update the sequence no
        Update update = new Update().inc("seq", 1);
        //modify in document
        DatabaseSequence counter = mongoOperations
                .findAndModify(query,
                        update, FindAndModifyOptions.options().returnNew(true).upsert(true),
                        DatabaseSequence.class);

        return !Objects.isNull(counter) ? counter.getSeq() : 1;
    }
}
