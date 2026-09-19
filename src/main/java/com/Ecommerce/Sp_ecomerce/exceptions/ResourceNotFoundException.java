package com.Ecommerce.Sp_ecomerce.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    private String resourceName;
    private String fieldName;
    private String field;
    private Long fieldId;


    // Constructor 1: String field value
    public ResourceNotFoundException(
            String resourceName,
            String fieldName,
            String field) {

        super(String.format(
                "%s not found with %s : %s",
                resourceName,
                fieldName,
                field
        ));

        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.field = field;
    }


    // Constructor 2: Long ID value
    public ResourceNotFoundException(
            String resourceName,
            String fieldName,
            Long fieldId) {

        super(String.format(
                "%s not found with %s : %d",
                resourceName,
                fieldName,
                fieldId
        ));

        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldId = fieldId;
    }
}