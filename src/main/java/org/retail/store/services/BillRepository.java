package org.retail.store.services;

import org.retail.store.model.Bill;
import org.retail.store.model.Product;
import org.retail.store.model.ProductType;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class BillRepository {

    private List<Bill> billsRepo = new ArrayList<>();
    private int billCounter=1;

    {

        Product pr = new Product("Ginger", new BigDecimal(5), ProductType.GROCERY);
        Product pr1 = new Product("Bitter gourd", new BigDecimal(10), ProductType.GROCERY);
        Product pr2 = new Product("Reebok Shoes", new BigDecimal(175), ProductType.FOOTWEAR);
        Product pr3 = new Product(  "Relaxo", new BigDecimal(70), ProductType.FOOTWEAR );
        Product pr4 = new Product("Dhoop", new BigDecimal(5), ProductType.OTHER);
        Product pr5 = new Product("Adidas Tshirt", new BigDecimal(90), ProductType.CLOTHING);
        Product pr6 = new Product("Levis Jeans", new BigDecimal(120), ProductType.CLOTHING);

        Bill bill1 = new Bill();
        bill1.setId(billCounter++);
        bill1.addProduct(pr1);
        bill1.addProduct(pr3);
        bill1.addProduct(pr5);

        Bill bill2 = new Bill();
        bill2.setId(billCounter++);
        bill2.addProduct(pr2);
        bill2.addProduct(pr4);
        bill2.addProduct(pr5);

        Bill bill3 = new Bill();
        bill3.setId(billCounter++);
        bill3.addProduct(pr4);
        bill3.addProduct(pr6);
        bill3.addProduct(pr5);

        billsRepo.add(bill1);
        billsRepo.add(bill2);
        billsRepo.add(bill3);
    }
    public List<Bill> getBills(){
        return billsRepo;
    }

    public Bill getBill(int id){
        Bill bill = null;
        Optional<Bill> matchedBill = billsRepo.stream().filter(bil -> bil.getId() == id).findFirst();
        if(matchedBill.isPresent()){
            bill = matchedBill.get();
        }
        return  bill;
    }

    public Bill createBill(List<Product> products){
        Bill bill = new Bill();
        if(products != null){
            bill.setId(billCounter++);
            bill.setProducts(products);
            billsRepo.add(bill);
        }
        return bill;
    }

    public Bill addProductsToBill(int billId, List<Product> products){
        Bill bill =getBill(billId);
        if(bill != null){
            bill.getProducts().addAll(products);
        }
        return bill;
    }

}
