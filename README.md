# Java-Inventory-Management-System
Console-based Inventory Management System - PWJ Mini Project

# Java Inventory Management System

A console-based Inventory Management System built in Java as a mini project for the **PWJ (Programming with Java)** course at **ADIT College**.

---

## Project Description

This application allows users to manage a product inventory through a simple command-line interface. It supports adding products (Electronics & Groceries), searching, filtering by category, placing orders with stock validation, and persisting data to a file.

**Key Features:**
- Add Electronics and Groceries products
- Display all inventory items
- Search products by keyword
- Filter products by category
- Place orders with multi-threaded processing
- Save and load inventory from a file (`inventory.txt`)
- Custom exception handling for stock errors

---

## Installation Instructions

### Prerequisites
- Java JDK 8 or higher installed
- A terminal / command prompt

### Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/YOUR_USERNAME/java-inventory-management.git
   cd java-inventory-management
   ```

2. **Navigate to the source folder**
   ```bash
   cd src
   ```

3. **Compile the Java file**
   ```bash
   javac in/ac/adit/pwj/miniproject/inventory/MainApp.java
   ```

4. **Run the application**
   ```bash
   java in.ac.adit.pwj.miniproject.inventory.MainApp
   ```

---

## Usage Instructions

When the application starts, you will see a menu:

```
1.Add Product  2.Display  3.Search  4.Filter  5.Order  6.Exit
```

| Option | Description |
|--------|-------------|
| 1 | Add a new product (ID, Name, Qty, Price, Category) |
| 2 | Display all products in inventory |
| 3 | Search product by name keyword |
| 4 | Filter products by category (Electronics/Groceries) |
| 5 | Place an order by Product ID and quantity |
| 6 | Save data and exit |

### Example Input
```
Choose: 1
Enter ID Name Qty Price Category: 101 Laptop 10 45000.0 Electronics

Choose: 5
Enter product ID and quantity: 101 2
Order: Laptop Qty: 2 successful.
```

---

## Screenshots

> Screenshots are located in the `/assets` folder.

| Screen | Description |
|--------|-------------|
| `[Main Menu](assets/menu.png)` | Main menu screen |
| `assets/add_product.png` | Adding a product |
| `assets/order.png` | Placing an order |

---

## License

This project is licensed under the **MIT License** — see the [LICENSE](LICENSE) file for details.

---

## Author

**Jitiksha Patel (12402080601054)**  
ADIT College — B.Tech IT-A
Course: Programming with Java (PWJ)
