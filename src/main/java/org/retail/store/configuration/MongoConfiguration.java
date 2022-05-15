package org.retail.store.configuration;

import org.retail.store.model.*;
import org.retail.store.repository.BillMongoRepository;
import org.retail.store.repository.UserMongoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;

@Configuration
public class MongoConfiguration {

    @Bean
    CommandLineRunner commandCreateUsers(UserMongoRepository repository){
        return strings ->{
            User user = new User("Shyam", 1001 , UserType.CUSTOMER, LocalDate.now());
            User user1 = new User("Mandeep", 1002 , UserType.CUSTOMER, LocalDate.now());
            User user2 = new User("Ramesh", 1003 , UserType.CUSTOMER, LocalDate.now());
            User user3 = new User("Tushar", 1004 , UserType.CUSTOMER, LocalDate.now());
            repository.save(user);
            repository.save(user1);
            repository.save(user2);
            repository.save(user3);
        };
    }

    @Bean
    CommandLineRunner commandCreateBills(BillMongoRepository repository){
        return strings ->{
            Product pr = new Product("Ginger", new BigDecimal(5), ProductType.GROCERY);
            Product pr1 = new Product("Bitter gourd", new BigDecimal(10), ProductType.GROCERY);
            Product pr2 = new Product("Reebok Shoes", new BigDecimal(175), ProductType.FOOTWEAR);
            Product pr3 = new Product(  "Relaxo", new BigDecimal(70), ProductType.FOOTWEAR );
            Product pr4 = new Product("Dhoop", new BigDecimal(5), ProductType.OTHER);
            Product pr5 = new Product("Adidas Tshirt", new BigDecimal(90), ProductType.CLOTHING);
            Product pr6 = new Product("Levis Jeans", new BigDecimal(120), ProductType.CLOTHING);

            Bill bill1 = new Bill();
            bill1.setId(1001);
            bill1.addProduct(pr1);
            bill1.addProduct(pr3);
            bill1.addProduct(pr5);

            Bill bill2 = new Bill();
            bill2.setId(1002);
            bill2.addProduct(pr2);
            bill2.addProduct(pr4);
            bill2.addProduct(pr5);

            Bill bill3 = new Bill();
            bill3.setId(1003);
            bill3.addProduct(pr4);
            bill3.addProduct(pr6);
            bill3.addProduct(pr5);

            repository.save(bill1);
            repository.save(bill2);
            repository.save(bill3);
        };
    }
}
