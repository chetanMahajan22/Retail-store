package org.retail.store.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "databaseSequence")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DatabaseSequence {
    @Id
    private String  id;
    private int seq;
}