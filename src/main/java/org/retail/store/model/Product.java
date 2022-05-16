package org.retail.store.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @NotNull
    @Size(min=3, message="Product name should have atleast 3 characters")
    private String name;
    @PositiveOrZero
    private BigDecimal price;
    @NotNull
    private ProductType type;

}
