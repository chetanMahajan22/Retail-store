package org.retail.store.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@Document(collection = "users")
public class User {
    private String name;
    @Id
    private int id;
    private UserType type;
    private LocalDate registeredDate;

    @Transient
    public final static String SEQUENCE_NAME ="user_seq";

    public User(){
        //default constructor
    }

    public User(String name, int id, UserType type, LocalDate registeredDate) {
        this.name = name;
        this.id = id;
        this.type = type;
        this.registeredDate = registeredDate;
    }
}
