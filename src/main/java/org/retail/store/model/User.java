package org.retail.store.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@Document(collection = "users")
public class User {
    @NotNull
    @Size(min=2, message = "User name should have minimum 2 characters.")
    private String name;
    @Id
    private int id;
    @NotNull
    private UserType type;
    @PastOrPresent
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
