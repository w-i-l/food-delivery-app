package Services;

import Models.Order.Order;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

public class OrderService {

    private Dictionary<Integer, Order> orders;

    public OrderService(List<Order> orders) {
        this.orders = new Hashtable<Integer, Order>();
        for (Order order : orders) {
            this.orders.put(order.getCustomer().getId(), order);
        }
    }

    public OrderService() {
        this.orders = new Hashtable<Integer, Order>();
    }

    public void addOrder(Order order) {
        this.orders.put(order.getCustomer().getId(), order);
    }

    public void removeOrder(Integer id) {
        this.orders.remove(id);
    }

    public Order getOrderById(Integer id) {
        return this.orders.get(id);
    }

    public Dictionary<Integer, Order> getOrders() {
        return this.orders;
    }

    public void listAllOrders() {
        Enumeration<Order> orderEnumeration = this.orders.elements();
        while (orderEnumeration.hasMoreElements()) {
            Order order = orderEnumeration.nextElement();
            order.showOrderDetails();
        }
    }
}
