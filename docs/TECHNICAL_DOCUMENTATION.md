# 🔧 Technical Documentation

## Inventory Management System — Java Mini Project

---

## 1. Class Diagram

```
+-------------------+
|      Product      |
+-------------------+
| # id: int         |
| # name: String    |
| # quantity: int   |
| # price: double   |
| # category: String|
+-------------------+
| + display(): void |
+-------------------+
        ^
        |  (inherits)
   _____|_____
   |         |
+-----------+  +-----------+
|Electronics|  | Groceries |
+-----------+  +-----------+
| (no extra |  | (no extra |
|  fields)  |  |  fields)  |
+-----------+  +-----------+

+-----------------------------+
|      StockException         |
+-----------------------------+
| (extends Exception)         |
| + StockException(msg:String)|
+-----------------------------+

+----------------------------------+
|        InventoryManager          |
+----------------------------------+
| - inventory: Map<Integer,Product>|
| - orderHistory: List<String>     |
+----------------------------------+
| + addProduct(p: Product): void   |
| + displayProducts(): void        |
| + searchProduct(kw: String): void|
| + filterByCategory(c: String):   |
|   void                           |
| + processOrder(id, qty): void    |
| + saveToFile(): void             |
| + loadFromFile(): void           |
+----------------------------------+
         |
         | (inner class)
         v
+----------------------------------+
|   InventoryManager.OrderProcessor|
+----------------------------------+
| (implements Runnable)            |
| - productId: int                 |
| - qty: int                       |
+----------------------------------+
| + run(): void                    |
+----------------------------------+

+----------------------------------+
|           MainApp                |
+----------------------------------+
| + main(args: String[]): void     |
+----------------------------------+
```

---

## 2. Sequence Diagram — Place an Order (Option 5)

```
User        MainApp       InventoryManager    OrderProcessor     Product
 |             |                 |                  |               |
 |--[input]--->|                 |                  |               |
 | "5, 101, 2" |                 |                  |               |
 |             |--new Thread()-->|                  |               |
 |             |                 |--new OrderProc-->|               |
 |             |                 |                  |               |
 |             |--t.start()----->|                  |               |
 |             |                 |--run()---------->|               |
 |             |                 |                  |--processOrder()|
 |             |                 |<-----------------+               |
 |             |                 |--check stock--->|               |
 |             |                 |                  |--p.quantity-->|
 |             |                 |                  |<--qty value---|
 |             |                 |--reduce qty----->|               |
 |             |                 |--add to history->|               |
 |             |                 |--print success-->|               |
 |<--output----|                 |                  |               |
 | "Order successful"            |                  |               |
```

---

## 3. Sequence Diagram — Add Product (Option 1)

```
User        MainApp       InventoryManager
 |             |                 |
 |--[input]--->|                 |
 | "1, 101,    |                 |
 |  Laptop,    |                 |
 |  10, 45000, |                 |
 |  Electronics"                 |
 |             |--new Electronics()|
 |             |--addProduct()--->|
 |             |                 |--inventory.put(id, product)
 |             |                 |
 |<--"Added"---|                 |
```

---

## 4. File I/O Flow

```
saveToFile():
  InventoryManager --> FileWriter --> inventory.txt
  Format: id,name,quantity,price,category

loadFromFile():
  inventory.txt --> BufferedReader --> InventoryManager
  Parses CSV line → creates Product → puts in HashMap
```

---

## 5. Threading Model

- `OrderProcessor` implements `Runnable`
- Each order runs in its own `Thread`
- `processOrder()` is `synchronized` to prevent race conditions
- `t.join()` in `main()` waits for order to complete before continuing

---

## 6. Code Comments Reference

Key sections in `MainApp.java`:

| Section | Purpose |
|---------|---------|
| `Product` class | Base entity with all product fields |
| `Electronics` / `Groceries` | Subclasses using inheritance |
| `StockException` | Custom checked exception |
| `InventoryManager.processOrder()` | Synchronized order handling |
| `OrderProcessor.run()` | Thread entry point for orders |
| `saveToFile()` / `loadFromFile()` | CSV-based file persistence |

---

## 7. API / Interface Documentation

> This is a console application; there are no HTTP APIs.  
> The public methods of `InventoryManager` serve as the internal API:

| Method | Parameters | Returns | Description |
|--------|-----------|---------|-------------|
| `addProduct(Product p)` | Product object | void | Adds to HashMap |
| `displayProducts()` | — | void | Prints all products |
| `searchProduct(String kw)` | keyword | void | Name-based search |
| `filterByCategory(String c)` | category name | void | Category filter |
| `processOrder(int id, int qty)` | product id, qty | void | Throws StockException |
| `saveToFile()` | — | void | Saves to inventory.txt |
| `loadFromFile()` | — | void | Loads from inventory.txt |
