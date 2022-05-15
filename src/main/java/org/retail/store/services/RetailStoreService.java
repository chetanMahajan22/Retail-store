package org.retail.store.services;

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
        User user = userRepository.findById(userID).get();
        Bill bill = billRepository.findById(billId).get();
        log.info("user :{}, bill {}", user, bill);
        BigDecimal discountedAmount = new BigDecimal(0);
        if(user != null && bill != null){
            discountedAmount = calculateDiscount(user, bill);
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
