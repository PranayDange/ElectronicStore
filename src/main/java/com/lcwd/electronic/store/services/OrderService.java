package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.dtos.CreateOrderRequest;
import com.lcwd.electronic.store.dtos.OrderDto;
import com.lcwd.electronic.store.dtos.PageableResponse;

import java.util.List;

public interface OrderService {
    //create order
  //  OrderDto createOrder(CreateOrderRequest createOrderRequest, String userId, String cartId);
    OrderDto createOrder(CreateOrderRequest createOrderRequest);

    //remove order
    void removeOrder(String userId);

    //get orders of users
    List<OrderDto> getOrdersOfUsers(String userId);

    //get orders
    PageableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDirection);


}
