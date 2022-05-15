package org.retail.store.controller;

import org.retail.store.services.RetailStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class RetailStoreController {

    @Autowired
    private RetailStoreService service;


    @GetMapping("/bills/discount/{user_id}/{bill_id}")
    public String calculateDiscount(@PathVariable("user_id") int userID, @PathVariable("bill_id") int billId){
        BigDecimal aDouble = service.calculateDiscountWithId(userID, billId);
        System.out.println("Discounted amount :"+aDouble);
        return "Calculated Discount : "+aDouble;
    }
}
