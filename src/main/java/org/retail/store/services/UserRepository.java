package org.retail.store.services;

import org.retail.store.model.UserType;
import org.retail.store.model.User;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

@Repository
public class UserRepository extends Repositories {
    private static List<User> repo= new ArrayList<>();
    private int userIdCounter=1;


    {
        User user = new User("Shyam", userIdCounter++ , UserType.CUSTOMER, LocalDate.now());
        User user1 = new User("Mandeep", userIdCounter++ , UserType.CUSTOMER, LocalDate.now());
        User user2 = new User("Ramesh", userIdCounter++ , UserType.CUSTOMER, LocalDate.now());
        User user3 = new User("Tushar", userIdCounter++ , UserType.CUSTOMER, LocalDate.now());
        repo.add(user);
        repo.add(user1);
        repo.add(user2);
        repo.add(user3);

    }
    public UserRepository(ListableBeanFactory factory) {
        super(factory);
    }

    public List<User> getAllUsers() {
        return repo;
    }

    public User getUser(int id) {
        Optional<User> first = repo.stream().filter(usr -> usr.getId() == id).findFirst();
        if(first.isPresent())
            return first.get();
        return null;
    }

    public User deleteUser(int id){
        User usr= null;
        Iterator<User> iterator = repo.iterator();
        while(iterator.hasNext()){
            usr= iterator.next();
            if(usr.getId() ==id){
                iterator.remove();
                break;
            }
        }
       return usr;
    }

    public User saveUser(User usr){
        if (usr != null) {
            usr.setId(userIdCounter++);
            repo.add(usr);
        }
        return usr;
    }
}
