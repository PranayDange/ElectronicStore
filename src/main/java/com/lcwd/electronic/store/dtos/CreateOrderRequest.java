package com.lcwd.electronic.store.dtos;

import lombok.*;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateOrderRequest {
    @NotBlank(message = "cart id is required!!")
    private String cartId;
    @NotBlank(message = "user id is required!!")
    private String userId;


    private String orderStatus = "PENDING";
    private String paymentStatus = "NOTPAID";

    @NotBlank(message = "address is required!!")
    private String billingAddress;
    @NotBlank(message = "phone number is required!!")
    private String billingPhone;
    @NotBlank(message = "billing name is required!!")
    private String billingName;


    // private UserDto user;


}
