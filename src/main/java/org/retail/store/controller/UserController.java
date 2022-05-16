package org.retail.store.controller;

import org.retail.store.exception.UserNotFoundException;
import org.retail.store.model.User;
import org.retail.store.services.SequenceGenerator;
import org.retail.store.repository.UserMongoRepository;
import org.retail.store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UserRepository repository;
    @Autowired
    private UserMongoRepository mongoRepository;
    @Autowired
    private SequenceGenerator sequenceGenerator;


    @GetMapping("/users")
    public List<User> retriveUsers(){
        return mongoRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public User retriveUser(@PathVariable int id){
        Optional<User> byId = mongoRepository.findById(id);
        if(byId.isPresent()){
             return byId.get();
        }else{
            throw new UserNotFoundException("User not found");
        }
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable int id ){
        Optional<User> byId = mongoRepository.findById(id);
        if(byId.isPresent()){
            mongoRepository.deleteById(id);
            StringBuilder builder = new StringBuilder("User with id ");
            builder.append(id).append(" deleted.");
            return builder.toString();
        }else{
            throw new UserNotFoundException("User not found");
        }
    }

    @PostMapping("/users")
    public ResponseEntity createUser(@Valid @RequestBody User usr){
        usr.setId(sequenceGenerator.getSequenceNumber(User.SEQUENCE_NAME));
        User user = mongoRepository.save(usr);
        //to return status of request as created
        //Building URI of created user
        URI uri = ServletUriComponentsBuilder.
                fromCurrentRequest().
                path("/{id}").
                buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
