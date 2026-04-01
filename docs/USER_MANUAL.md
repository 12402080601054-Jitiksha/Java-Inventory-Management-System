# User Manual

## Java Inventory Management System

---

## 1. Installation Guide

### Step 1 — Install Java
1. Go to https://www.oracle.com/java/technologies/downloads/
2. Download **JDK 17** (or JDK 8+) for your operating system
3. Install it and set the `JAVA_HOME` environment variable

**Verify installation:**
```bash
java -version
```
Expected output: `java version "17.x.x" ...`

---

### Step 2 — Get the Project

**Option A: Clone from GitHub**
```bash
git clone https://github.com/YOUR_USERNAME/java-inventory-management.git
```

**Option B: Download ZIP**
- Go to the GitHub repository
- Click **Code → Download ZIP**
- Extract the ZIP file

---

### Step 3 — Compile the Project

Open terminal in the project folder:
```bash
cd java-inventory-management/src
javac in/ac/adit/pwj/miniproject/inventory/MainApp.java
```

---

### Step 4 — Run the Project

```bash
java in.ac.adit.pwj.miniproject.inventory.MainApp
```

---

## 2. User Guide

### Main Menu

When you run the program, this menu appears:
```
1.Add Product  2.Display  3.Search  4.Filter  5.Order  6.Exit
```

---

### Option 1 — Add a Product

**Steps:**
1. Enter `1` and press Enter
2. Enter: `ID Name Quantity Price Category`

**Example:**
```
Enter ID Name Qty Price Category(Electronics/Groceries): 
101 Laptop 15 45000.0 Electronics
```

**Rules:**
- ID must be a unique integer
- Category must be exactly `Electronics` or `Groceries`
- Price can be a decimal number

---

### Option 2 — Display All Products

**Steps:**
1. Enter `2` and press Enter
2. All products will be listed in this format:

```
101 Laptop [Electronics] Qty:15 Price:45000.0
102 Rice [Groceries] Qty:50 Price:120.0
```

---

### Option 3 — Search by Keyword

**Steps:**
1. Enter `3` and press Enter
2. Type any part of a product name

**Example:**
```
Enter keyword: lap
101 Laptop [Electronics] Qty:15 Price:45000.0
```

---

### Option 4 — Filter by Category

**Steps:**
1. Enter `4` and press Enter
2. Type `Electronics` or `Groceries`

**Example:**
```
Enter category: Groceries
102 Rice [Groceries] Qty:50 Price:120.0
103 Sugar [Groceries] Qty:30 Price:45.0
```

---

### Option 5 — Place an Order

**Steps:**
1. Enter `5` and press Enter
2. Enter the Product ID and the quantity you want

**Example:**
```
Enter product ID and quantity: 101 3
Order: Laptop Qty: 3 successful.
```

**Error cases:**
- If product ID doesn't exist: `Order Failed: Invalid Product ID`
- If not enough stock: `Order Failed: Insufficient stock for Laptop`

---

### Option 6 — Save and Exit

1. Enter `6` and press Enter
2. Data is saved to `inventory.txt` in the same folder
3. Next time you run the app, data loads automatically

---

## 3. FAQ

**Q: I get a compile error — "cannot find symbol"**  
A: Make sure you are inside the `src` folder when compiling, and the full package path `in/ac/adit/pwj/miniproject/inventory/` exists.

---

**Q: The app says "No previous data" on startup**  
A: This is normal for first-time use. Once you add products and exit with option 6, data will be saved and loaded next time.

---

**Q: I entered the wrong category name**  
A: Any input that is not `Electronics` (case-insensitive) will default to `Groceries`. Be careful with spelling.

---

**Q: Can two orders run at the same time?**  
A: The `processOrder` method is `synchronized`, meaning only one order processes at a time to prevent data corruption — even if multiple threads are used.

---

**Q: Where is the data saved?**  
A: In a file called `inventory.txt` in the directory where you ran the program.

---

**Q: Can I edit `inventory.txt` manually?**  
A: Yes. The format is: `id,name,quantity,price,category` — one product per line.

```
101,Laptop,15,45000.0,Electronics
102,Rice,50,120.0,Groceries
```
