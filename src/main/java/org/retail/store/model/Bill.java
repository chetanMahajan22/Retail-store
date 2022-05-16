package org.retail.store.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Document(collection = "bills")
public class Bill {

    @Id
    private int id;
    @NotEmpty
    private List<Product> products;
    public Bill(){
        products = new ArrayList<>();
    }
    @Transient
    public final static String SEQUENCE_NAME ="bill_seq";



    public void addProduct(Product product){
        products.add(product);
    }

    public Bill(List<Product> products){
        this.products = products;
    }
}
