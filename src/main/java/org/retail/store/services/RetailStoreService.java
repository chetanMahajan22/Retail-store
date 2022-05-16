package org.retail.store.services;

import org.retail.store.exception.BillNotFoundException;
import org.retail.store.exception.UserNotFoundException;
import org.retail.store.model.Bill;
import org.retail.store.model.ProductType;
import org.retail.store.model.User;
import org.retail.store.repository.BillMongoRepository;
import org.retail.store.repository.UserMongoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class RetailStoreService {

    Logger log = LoggerFactory.getLogger(RetailStoreService.class);
    @Autowired
    private UserMongoRepository userRepository;
    @Autowired
    private BillMongoRepository billRepository;
    @Autowired
    private DiscountCalculator calculator;

    public BigDecimal calculateDiscountWithId(int userID, int billId){
        Optional<User> userOptional = userRepository.findById(userID);
        Optional<Bill> billOptional = billRepository.findById(billId);
        BigDecimal discountedAmount = new BigDecimal(0);
        if(userOptional.isPresent() && billOptional.isPresent()){
            discountedAmount = calculateDiscount(userOptional.get(), billOptional.get());
        }else if(!userOptional.isPresent()){
            log.info("User not found with id :"+userID);
            throw new UserNotFoundException("User not found with id :"+userID);
        }else{
            log.info("User not found with id :"+userID);
            throw new BillNotFoundException("Bill not found with id :"+billId);
        }
        return discountedAmount;
    }

    private BigDecimal calculateDiscount(User user, Bill bill) {
        BigDecimal discountedAmount;
        log.info("calculating  bill amount");
        BigDecimal totalAmount = totalBillAmount(bill);
        BigDecimal groceryBillAmount = grocerryBillAmount(bill);
        BigDecimal nonGroceryBillAmount = totalAmount.subtract(groceryBillAmount);
        log.info("total  amount :{}, grocery amount: {}", totalAmount, groceryBillAmount);
        int userDiscountPercentage =calculator.calculateUserDiscount(user);
        BigDecimal userBillAmountAfterDiscount = calculator.calculateUserBillAmountDiscount(nonGroceryBillAmount, userDiscountPercentage);
        BigDecimal defaultBillAmountAfterDiscount = calculator.calculateDefaultBillAmountDiscount(totalAmount);
        BigDecimal userBillAmountWithGrocery = userBillAmountAfterDiscount.add(groceryBillAmount);
        log.info("userDiscountPercentage :{}", userDiscountPercentage);
        log.info("userBillAmountWithGrocery :{}", userBillAmountWithGrocery);
        log.info("defaultBillAmountAfterDiscount :{}", defaultBillAmountAfterDiscount);
        if(userBillAmountWithGrocery.doubleValue() > defaultBillAmountAfterDiscount.doubleValue())
            discountedAmount = defaultBillAmountAfterDiscount;
        else
            discountedAmount = userBillAmountWithGrocery;

        return discountedAmount;
    }

    private BigDecimal totalBillAmount(Bill bill){
        return bill.getProducts().stream().map(prd -> prd.getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal grocerryBillAmount(Bill bill){
        return bill.getProducts().stream().filter( prd -> ProductType.GROCERY.equals(prd.getType())).
                map(prd -> prd.getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
