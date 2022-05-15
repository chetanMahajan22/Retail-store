package org.retail.store.controller;

import org.retail.store.model.Bill;
import org.retail.store.model.Product;
import org.retail.store.repository.BillMongoRepository;
import org.retail.store.services.BillRepository;
import org.retail.store.services.SequenceGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class BillingController {

    @Autowired
    private BillMongoRepository mongoRepository;
    @Autowired
    private SequenceGenerator sequenceGenerator;

    @GetMapping("/bills")
    public List<Bill> retrieveAllBills(){
        return mongoRepository.findAll();
    }

    @GetMapping("/bills/{id}")
    public Bill retrieveAllBill(@PathVariable int id){
        Optional<Bill> byId = mongoRepository.findById(id);
        if(byId.isPresent()){
            return byId.get();

        }else{
            throw new RuntimeException("Bill not found");
        }
    }


    @PostMapping("/bills")
    public ResponseEntity<Bill> createBill(@RequestBody List<Product> products){
        Bill bill = new Bill(products);
        bill.setId(sequenceGenerator.getSequenceNumber(Bill.SEQUENCE_NAME));
        bill = mongoRepository.save(bill);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").
                buildAndExpand(bill.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/bills/{id}")
    public void addProductToBill(@PathVariable int id, @RequestBody List<Product> products){
        Optional<Bill> billOp = mongoRepository.findById(id);

        if(billOp.isPresent()){
             Bill bill = billOp.get();
             products.forEach(prd -> bill.addProduct(prd));
             mongoRepository.save(bill);
        }else{
            throw new RuntimeException("Bill not found");
        }
    }
}
