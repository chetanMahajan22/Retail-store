package org.retail.store.services;

import org.retail.store.model.UserType;
import org.retail.store.model.User;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Component
public class DiscountCalculator {

    protected static final int EMPLOYEE_DISCOUNT_PERCENTAGE = 30;
    protected static final int AFFILIATE_DISCOUNT_PERCENTAGE = 10;
    protected static final int CUSTOMER_DISCOUNT_PERCENTAGE = 5;

    private int CustomerDiscountEligibilityYear = 2;

    public int calculateUserDiscount(User user){
        int calculatedDiscount= 0;
        if(user == null){
            throw new RuntimeException("User should not be null");
        }

        UserType type = user.getType();

        switch (type){
            case CUSTOMER:  calculatedDiscount = calculateCustomerDiscount(user);
                            break;
            case AFFILIATE: calculatedDiscount = AFFILIATE_DISCOUNT_PERCENTAGE;
                            break;
            case EMPLOYEE:  calculatedDiscount = EMPLOYEE_DISCOUNT_PERCENTAGE;
                            break;
            default:
                    System.out.println("User type can not be identified!");
        }
        return  calculatedDiscount;
    }


    private int calculateCustomerDiscount(User user){
        int discount= 0;
        if(eligibleCustomerDiscount(user)){
            discount = CUSTOMER_DISCOUNT_PERCENTAGE;
        }
        return discount;
    }

    private boolean eligibleCustomerDiscount(User user){
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(user.getRegisteredDate(), currentDate);
        return period.getYears() > CustomerDiscountEligibilityYear;
    }

    public BigDecimal calculateDefaultBillAmountDiscount(BigDecimal totalAmount){
        Double discount = getDefaultDiscountOnBill(totalAmount.doubleValue());
        return totalAmount.subtract(BigDecimal.valueOf(discount));
    }

    private Double getDefaultDiscountOnBill(Double totalAmount) {
        Double discount =0d;
        while(totalAmount > 100){
            discount = discount + 5;
            totalAmount = totalAmount - 100;
        }
        return discount;
    }

    public BigDecimal calculateUserBillAmountDiscount(BigDecimal billAmount, int percentage){
        if(percentage <= 0){
            return billAmount;
        }
        BigDecimal discountedAmount = new BigDecimal(billAmount.doubleValue());
        BigDecimal discount = discountedAmount.multiply(new BigDecimal(percentage).divide(new BigDecimal(100)));
        billAmount=billAmount.subtract(discount);
        return billAmount;
    }


}
