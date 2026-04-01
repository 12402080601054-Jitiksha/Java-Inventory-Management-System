package in.ac.adit.pwj.miniproject.inventory;

import java.io.*;
import java.util.*;

/**
 * Base class representing a generic Product in the inventory.
 * All product types (Electronics, Groceries) extend this class.
 */
class Product {
    protected int id;           // Unique product identifier
    protected String name;      // Product name
    protected int quantity;     // Available stock
    protected double price;     // Price per unit
    protected String category;  // Product category

    /**
     * Constructor to initialize all product fields.
     */
    public Product(int id, String name, int quantity, double price, String category) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.category = category;
    }

    /**
     * Displays product information in a readable format.
     */
    public void display() {
        System.out.println(id + " " + name + " [" + category + "] Qty:" + quantity + " Price:" + price);
    }
}

/**
 * Represents an Electronics product.
 * Inherits from Product and sets category to "Electronics".
 */
class Electronics extends Product {
    public Electronics(int id, String name, int quantity, double price) {
        super(id, name, quantity, price, "Electronics");
    }
}

/**
 * Represents a Groceries product.
 * Inherits from Product and sets category to "Groceries".
 */
class Groceries extends Product {
    public Groceries(int id, String name, int quantity, double price) {
        super(id, name, quantity, price, "Groceries");
    }
}

/**
 * Custom exception thrown when an order cannot be processed
 * due to invalid product ID or insufficient stock.
 */
class StockException extends Exception {
    public StockException(String msg) {
        super(msg);
    }
}

/**
 * Core class that manages the inventory.
 * Handles CRUD operations, file I/O, and order processing.
 */
class InventoryManager {

    // HashMap for O(1) product lookup by ID
    private Map<Integer, Product> inventory = new HashMap<>();

    // Stores a history of all successful orders
    private List<String> orderHistory = new ArrayList<>();

    /**
     * Adds a product to the inventory.
     * @param p The product to add
     */
    public void addProduct(Product p) {
        inventory.put(p.id, p);
    }

    /**
     * Displays all products currently in inventory.
     */
    public void displayProducts() {
        for (Product p : inventory.values()) {
            p.display();
        }
    }

    /**
     * Searches for products by name (case-insensitive partial match).
     * @param keyword The search keyword
     */
    public void searchProduct(String keyword) {
        boolean found = false;
        for (Product p : inventory.values()) {
            if (p.name.toLowerCase().contains(keyword.toLowerCase())) {
                p.display();
                found = true;
            }
        }
        if (!found) System.out.println("No product found.");
    }

    /**
     * Filters and displays products matching the given category.
     * @param category The category to filter by (e.g., "Electronics")
     */
    public void filterByCategory(String category) {
        boolean found = false;
        for (Product p : inventory.values()) {
            if (p.category.equalsIgnoreCase(category)) {
                p.display();
                found = true;
            }
        }
        if (!found) System.out.println("No products in this category.");
    }

    /**
     * Inner class that implements Runnable to process orders in a separate thread.
     * Enables concurrent order processing.
     */
    class OrderProcessor implements Runnable {
        int productId, qty;

        public OrderProcessor(int productId, int qty) {
            this.productId = productId;
            this.qty = qty;
        }

        /**
         * Thread entry point — calls processOrder and handles exceptions.
         */
        public void run() {
            try {
                processOrder(productId, qty);
            } catch (StockException e) {
                System.out.println("Order Failed: " + e.getMessage());
            }
        }
    }

    /**
     * Processes an order for a given product ID and quantity.
     * Synchronized to prevent race conditions in multi-threaded usage.
     *
     * @param id  Product ID
     * @param qty Quantity to order
     * @throws StockException if product not found or insufficient stock
     */
    public synchronized void processOrder(int id, int qty) throws StockException {
        // Validate product exists
        if (!inventory.containsKey(id))
            throw new StockException("Invalid Product ID");

        Product p = inventory.get(id);

        // Validate sufficient stock
        if (p.quantity < qty)
            throw new StockException("Insufficient stock for " + p.name);

        // Deduct quantity and record order
        p.quantity -= qty;
        String order = "Order: " + p.name + " Qty: " + qty;
        orderHistory.add(order);

        System.out.println(order + " successful.");
    }

    /**
     * Saves the current inventory to "inventory.txt" in CSV format.
     * Format: id,name,quantity,price,category
     */
    public void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("inventory.txt"))) {
            for (Product p : inventory.values()) {
                bw.write(p.id + "," + p.name + "," + p.quantity + "," + p.price + "," + p.category);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving file.");
        }
    }

    /**
     * Loads inventory data from "inventory.txt".
     * If the file doesn't exist, silently continues with empty inventory.
     */
    public void loadFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("inventory.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");
                Product p = new Product(
                        Integer.parseInt(d[0]),
                        d[1],
                        Integer.parseInt(d[2]),
                        Double.parseDouble(d[3]),
                        d[4]
                );
                inventory.put(p.id, p);
            }
        } catch (IOException e) {
            System.out.println("No previous data.");
        }
    }
}

/**
 * Main application entry point.
 * Provides a console-based menu for interacting with the InventoryManager.
 */
public class MainApp {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        InventoryManager manager = new InventoryManager();

        // Load existing data on startup
        manager.loadFromFile();

        int choice;

        do {
            System.out.println("\n1.Add Product  2.Display  3.Search  4.Filter  5.Order  6.Exit");
            choice = sc.nextInt();

            switch (choice) {

                case 1: // Add a new product
                    System.out.print("Enter ID Name Qty Price Category(Electronics/Groceries): ");
                    int id = sc.nextInt();
                    String name = sc.next();
                    int qty = sc.nextInt();
                    double price = sc.nextDouble();
                    String cat = sc.next();

                    // Create appropriate subclass based on category
                    if (cat.equalsIgnoreCase("Electronics"))
                        manager.addProduct(new Electronics(id, name, qty, price));
                    else
                        manager.addProduct(new Groceries(id, name, qty, price));
                    break;

                case 2: // Show all products
                    manager.displayProducts();
                    break;

                case 3: // Search by keyword
                    System.out.print("Enter keyword: ");
                    manager.searchProduct(sc.next());
                    break;

                case 4: // Filter by category
                    System.out.print("Enter category: ");
                    manager.filterByCategory(sc.next());
                    break;

                case 5: // Place an order using a new thread
                    System.out.print("Enter product ID and quantity: ");
                    int pid = sc.nextInt();
                    int q = sc.nextInt();

                    Thread t = new Thread(manager.new OrderProcessor(pid, q));
                    t.start();
                    try { t.join(); } catch (Exception e) {} // Wait for order to complete
                    break;

                case 6: // Save and exit
                    manager.saveToFile();
                    System.out.println("Data saved. Exiting...");
                    break;
            }

        } while (choice != 6);

        sc.close();
    }
}
