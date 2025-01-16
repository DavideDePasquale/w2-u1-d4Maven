package org.example;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Hello world!
 *
 */
public class GestioneStream {

    private static List<Product> productList = new ArrayList<Product>();
    private static List<Customer> customerList = new ArrayList<Customer>();
    private static List<Order> orderList = new ArrayList<Order>();


    public static void main(String[] args) {
        // inizializzo i dati
        createProductList();
        createCustomerList();
        createOrderList();

       /* System.out.println("Products");
        productList.forEach(ele-> System.out.println(ele));
        System.out.println("Customers");
        customerList.forEach(System.out::println);
        System.out.println("Order");
        orderList.forEach(System.out::println);  */


        // ESERCIZIO 1
        System.out.println("ESERCIZIO 1");
        ordiniPerCliente().forEach((customer, orders) -> {
            System.out.println("Cliente: " + customer.getName() + " Ordini: " + orders.size());
            System.out.println(orders);
        });



        // ESERCIZIO 2
        System.out.println("ESERCIZIO 2");
        stampaTotaleImportoOrdini().forEach((customer, total) -> {
            System.out.println("Cliente: " + customer.getName() + " ha speso : " + total + "€");
        });



        // ESERCIZIO 3
        System.out.println("ESERCIZIO 3");
        prodottiCostosi();



        // ESERCIZIO 4
        System.out.println("ESERCIZIO 4");
        mediaOrdini();



        //ESERCIIO 5
        System.out.println("ESERCIZIO 5");
        sommaProdottiXCat();



        //ESERCIZIO 6




        //ESERCIZIO 7

    }

    public static void createProductList() {
        // Books - Baby - Boys
        Product p1 = new Product(1, "Iphone", "Smartphone", 1000);
        Product p2 = new Product(2, "abc", "Books", 127.15);
        Product p3 = new Product(3, "Pannolini", "Baby", 5.8);
        Product p4 = new Product(4, "Il Signore degli Anelli", "Books", 31);
        Product p5 = new Product(5, "Spiderman", "Boys", 100);
        Product p6 = new Product(6, "Ciuccio", "Baby", 2);
        productList.addAll(Arrays.asList(p1, p2, p3, p4, p5, p6));

    }

    public static void createCustomerList() {
        Customer c1 = new Customer(1, "Aldo Baglio", 1);
        Customer c2 = new Customer(2, "Giovanni Storti", 2);
        Customer c3 = new Customer(3, "Giacomo Poretti", 3);
        Customer c4 = new Customer(4, "Maria Massironi", 4);
        customerList.addAll(Arrays.asList(c1, c2, c3, c4));
    }

    public static void createOrderList() {
        Order o1 = new Order(1, customerList.get(0));
        Order o2 = new Order(2, customerList.get(1));
        Order o3 = new Order(3, customerList.get(2));
        Order o4 = new Order(4, customerList.get(3));
        Order o5 = new Order(5, customerList.get(2));

        Product p1 = productList.get(0);
        Product p2 = productList.get(1);
        Product p3 = productList.get(2);
        Product p4 = productList.get(3);
        Product p5 = productList.get(4);
        Product p6 = productList.get(5);

        o1.addProduct(p1);
        o1.addProduct(p3);
        o1.addProduct(p5);

        o2.addProduct(p1);
        o2.addProduct(p4);

        o3.addProduct(p2);
        o3.addProduct(p4);
        o3.addProduct(p2);
        o3.addProduct(p6);

        o4.addProduct(p2);
        o4.addProduct(p6);


        o5.addProduct(p1);
        o5.addProduct(p2);
        o5.addProduct(p4);


        orderList.addAll(Arrays.asList(o1, o2, o3, o4, o5));

    }

    //METODO ES1
    public static Map<Customer, List<Order>> ordiniPerCliente() {
        return orderList.stream().collect(Collectors.groupingBy(Order::getCustomer));
    }
    //METODO ES2
        public static Map<Customer, Double> stampaTotaleImportoOrdini() {
          return orderList.stream()
                  .collect(Collectors.groupingBy(Order::getCustomer, Collectors.summingDouble(Order::getTotal)));
        }
    //METODO ES3
        public static void prodottiCostosi(){
             List<Product> prodottiCostosi = productList.stream().sorted(Comparator.comparingDouble(Product::getPrice).reversed()).limit(4).toList();
        prodottiCostosi.forEach((prodotto) -> System.out.println("Prodotto: " + prodotto.getName() + " Costo: " + prodotto.getPrice() + "€"));
    }
    //METODO ES4
    public static void mediaOrdini(){
         Double mediaCostoOrdini = orderList.stream().mapToDouble(order -> order.getProducts().stream().mapToDouble(Product::getPrice).sum()).average().orElse(0);
        System.out.println(mediaCostoOrdini);
    }
    //METODO ES5
    public static void sommaProdottiXCat(){
        Map<String, Double> listaperCat = productList.stream().collect(Collectors.groupingBy(Product::getCategory,Collectors.summingDouble(Product::getPrice)));
      listaperCat.forEach((categoria,pricefinal)-> System.out.println("Categoria:" + categoria + " Prezzo totale: " + pricefinal));

    }

}
