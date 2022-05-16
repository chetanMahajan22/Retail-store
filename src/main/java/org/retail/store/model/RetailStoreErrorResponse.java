package org.retail.store.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class RetailStoreErrorResponse {

    private String message;
    private String details;
    private Date time;
    private List<String> errors;
}
