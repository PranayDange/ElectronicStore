package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.dtos.AddItemtoCartRequest;
import com.lcwd.electronic.store.dtos.CartDto;

public interface CartService {
    //add item to cart
    //case1 :cart for user is not available: we will create the cart and then add the item
    //case 2: if cart available then add the items to cart

    CartDto addItemToCart(String userId, AddItemtoCartRequest request);

    //remove item from cart
    void removeItemFromCart(String userId,int cartItem);

    //remove all item from cart
    void clearCart(String userId);

    CartDto getCartByUser(String userId);

}
