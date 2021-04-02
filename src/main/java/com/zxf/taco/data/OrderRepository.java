package com.zxf.taco.data;


import com.zxf.taco.Order;

public interface OrderRepository {
    Order save(Order order);
}
