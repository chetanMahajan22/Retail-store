package org.retail.store.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.retail.store.model.User;
import org.retail.store.model.UserType;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DiscountCalculatorTest {

    private DiscountCalculator calculator = new DiscountCalculator();


    @Test
    public void testCalculateUserDiscount_Employee(){
        User usr = new User("Ramesh", 2001, UserType.EMPLOYEE, LocalDate.now());
        int discountPercent = calculator.calculateUserDiscount(usr);
        Assertions.assertEquals(DiscountCalculator.EMPLOYEE_DISCOUNT_PERCENTAGE, discountPercent);
    }

    @Test
    public void testCalculateUserDiscount_Customer(){
        User usr = new User("Ramesh", 2001, UserType.CUSTOMER, LocalDate.now());
        int discountPercent = calculator.calculateUserDiscount(usr);
        Assertions.assertEquals(0, discountPercent);
    }

    @Test
    public void testCalculateUserDiscount_Customer2(){
        User usr = new User("Ramesh", 2001, UserType.CUSTOMER, LocalDate.of(2018, 1, 1));
        int discountPercent = calculator.calculateUserDiscount(usr);
        Assertions.assertEquals(DiscountCalculator.CUSTOMER_DISCOUNT_PERCENTAGE, discountPercent);
    }

    @Test
    public void testCalculateUserDiscount_AFFILIATE(){
        User usr = new User("Ramesh", 2001, UserType.AFFILIATE, LocalDate.of(2018, 1, 1));
        int discountPercent = calculator.calculateUserDiscount(usr);
        Assertions.assertEquals(DiscountCalculator.AFFILIATE_DISCOUNT_PERCENTAGE, discountPercent);
    }
    @Test
    public void testCalculateUserDiscountNullUser(){
        RuntimeException re  = Assertions.assertThrows(RuntimeException.class, () -> calculator.calculateUserDiscount(null));
        Assertions.assertEquals("User should not be null", re.getMessage());
    }

    @Test
    public void testCalculateDefaultBillAmountDiscount(){
        BigDecimal discountedValue  = calculator.calculateDefaultBillAmountDiscount(BigDecimal.valueOf(990));
        Assertions.assertEquals(945, discountedValue.intValue());
    }

    @Test
    public void testCalculateUserBillAmount_30(){
        BigDecimal discountedValue = calculator.calculateUserBillAmountDiscount(BigDecimal.valueOf(250), 30);
        Assertions.assertEquals(175, discountedValue.intValue());

    }

    @Test
    public void testCalculateUserBillAmount_10(){
        BigDecimal discountedValue = calculator.calculateUserBillAmountDiscount(BigDecimal.valueOf(900), 10);
        Assertions.assertEquals(810, discountedValue.intValue());
    }

    @Test
    public void testCalculateUserBillAmount_0(){
        BigDecimal discountedValue = calculator.calculateUserBillAmountDiscount(BigDecimal.valueOf(900), 0);
        Assertions.assertEquals(900, discountedValue.intValue());
    }
}
