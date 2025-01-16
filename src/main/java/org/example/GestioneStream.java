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
        System.out.println("Ordini per cliente:");
        ordiniPerCliente();


        // ESERCIZIO 2
        System.out.println("Spese per ogni ordine:");
        stampaTotaleImportoOrdini();


        // ESERCIZIO 3
        System.out.println("I 4 prodotti più costosi sono:");
        prodottiCostosi();


        // ESERCIZIO 4
        System.out.println("La media degli ordini è :");
        mediaOrdini();


        //ESERCIIO 5
        System.out.println("Somma Importi prodotti per categoria:");
        sommaProdottiXCat();

    }

    public static List<Product> getBooks() {
        List<Product> listaBooks = productList.stream()
                .filter(product -> product.getCategory().equals("Books") && product.getPrice() > 100)
                .collect(Collectors.toList());
        return listaBooks;
    }

    public static List<Order> getBabyOrders() {
        List<Order> listaBaby = orderList.stream()
                .filter(order -> order.getProducts()
                        .stream().anyMatch(product -> product.getCategory().equals("Baby")))
                .toList();
        return listaBaby;
    }

    public static List<Product> scontoBoys() {
        List<Product> listaBoys = productList.stream().filter(product -> product.getCategory().equals("Boys")).map(product -> new Product(product.getId(), product.getName(), product.getCategory(), product.getPrice() * 0.9)).toList();
        return listaBoys;
    }

    public static List<Order> prodOrdinati() {
        LocalDate dataMin = LocalDate.of(2021, 2, 1);
        LocalDate dataMax = LocalDate.of(2021, 4, 1);
        List<Order> listaOrdine = orderList.stream().filter(order -> order.getCustomer().getTier() == 2 && order.getOrderDate().isAfter(dataMin) && order.getOrderDate().isBefore(dataMax)).toList();
        return listaOrdine;
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

    public static void ordiniPerCliente() {
        Map<Customer, List<Order>> listaOrdXCli = orderList.stream().collect(Collectors.groupingBy(Order::getCustomer));
        listaOrdXCli.forEach((key, value) -> {
                    System.out.println("Cliente: " + key.getName());
                    value.forEach(order -> System.out.println("Ordine: " + order.getId() + ", Data ordine: " + order.getOrderDate() + ", e spedito il " + order.getDeliveryDate() + " ( " + order.getProducts() + " )"));
                }
        );
    }

        public static void stampaTotaleImportoOrdini() {
            Map<Customer, Double> spesaPerCliente = orderList.stream().collect(Collectors.groupingBy(Order::getCustomer, Collectors.summingDouble(order -> order.getProducts().stream().mapToDouble(Product::getPrice).sum())));
            spesaPerCliente.forEach((cliente, spesa) -> System.out.println("Il cliente: " + cliente.getName() + " ha speso " + spesa + "€"));


        }
        public static void prodottiCostosi(){
             List<Product> prodottiCostosi = productList.stream().sorted(Comparator.comparingDouble(Product::getPrice).reversed()).limit(4).toList();
        prodottiCostosi.forEach((prodotto) -> System.out.println("Prodotto: " + prodotto.getName() + " Costo: " + prodotto.getPrice() + "€"));
    }
    public static void mediaOrdini(){
         Double mediaCostoOrdini = orderList.stream().mapToDouble(order -> order.getProducts().stream().mapToDouble(Product::getPrice).sum()).average().orElse(0);
        System.out.println(mediaCostoOrdini);
    }
    public static void sommaProdottiXCat(){
        Map<String, Double> listaperCat = productList.stream().collect(Collectors.groupingBy(Product::getCategory,Collectors.summingDouble(Product::getPrice)));
      listaperCat.forEach((categoria,pricefinal)-> System.out.println("Categoria:" + categoria + " Prezzo totale: " + pricefinal));

    }

}
