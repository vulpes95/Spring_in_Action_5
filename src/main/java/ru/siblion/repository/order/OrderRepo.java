package ru.siblion.repository.order;

import ru.siblion.tacos.Order;

public interface OrderRepo {
    Order save(Order order);
}
