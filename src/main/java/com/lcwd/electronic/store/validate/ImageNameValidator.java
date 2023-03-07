package com.lcwd.electronic.store.validate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ImageNameValidator implements ConstraintValidator<ImageNewValid,String> {
    private Logger logger= LoggerFactory.getLogger(ImageNameValidator.class);
    @Override
    public void initialize(ImageNewValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        logger.info("message from is valid :{}",value);
        //logic
        if(value.isBlank()){
            return false;
        }else {
            return true;
        }
       // return false;
    }
}
