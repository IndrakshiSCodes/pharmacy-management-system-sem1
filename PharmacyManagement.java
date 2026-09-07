import java.util.*;

// Medicine class (parent class for Customer and Pharmacist class)
class Medicine {
    static Scanner sc = new Scanner(System.in); // single shared scanner avoids input stream conflicts

    int id;
    String name;
    String category;
    double price;
    static int size = 0; // shared across all Medicine/Pharmacist instances
    double stock;
    int quantity;

    void addMed(Medicine[] med) {
        // addition of new medicine
        if (size < med.length) {
            Medicine m1 = new Medicine();
            m1.set();
            med[size] = m1;
            size++;
            System.out.println("Medicine added successfully.");
        } else {
            System.out.println("Medicine list is full.");
        }
    }

    void set() {
        // input medicine information
        System.out.println("Enter medicine ID: ");
        id = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter category: ");
        category = sc.nextLine();
        System.out.println("Enter medicine name: ");
        name = sc.nextLine();
        System.out.println("Enter price of medicine: ");
        price = sc.nextDouble();
        System.out.println("Enter stock (number of strips): ");
        stock = sc.nextDouble();
    }

    void get() {
        // printing of medicine information
        System.out.println("ID: " + id);
        System.out.println("Category: " + category);
        System.out.println("Name: " + name);
        System.out.println("Price: " + price);
        System.out.println("Stock: " + stock);
    }

    void displayMed(Medicine[] med) {
        if (size == 0) {
            System.out.println("No medicines available.");
            return;
        }
        for (int i = 0; i < size; i++) {
            if (med[i] != null) {
                med[i].get();
            }
        }
    }
}

// child class of Medicine class
class Pharmacist extends Medicine {

    void lowStockWarning(Medicine[] med) {
        boolean found = false;
        for (int i = 0; i < size; i++) {
            if (med[i] != null && med[i].stock < 5) {
                if (!found) {
                    System.out.println("Medicines with low stock (less than 5 strips):");
                    found = true;
                }
                med[i].get();
            }
        }
        if (!found) {
            System.out.println("No medicines have low stock.");
        }
    }

    void generateBill(Medicine[] med) {
        double totalAmount = 0;
        boolean sold = false;

        while (true) {
            System.out.println("Press 1 to enter medicine, then press 2 to generate the bill:");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Enter medicine name:");
                    sc.nextLine();
                    String medName = sc.nextLine();
                    boolean found = false;

                    for (int i = 0; i < size; i++) {
                        if (med[i] != null && med[i].name.equalsIgnoreCase(medName)) {
                            System.out.println("Enter quantity:");
                            int qty = sc.nextInt();

                            if (qty <= med[i].stock) {
                                med[i].quantity += qty;
                                found = true;
                            } else {
                                System.out.println("Not enough stock available.");
                            }
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Medicine not found.");
                    }
                    break;

                case 2:
                    System.out.println("----- Bill Summary -----");
                    for (int i = 0; i < size; i++) {
                        if (med[i] != null && med[i].quantity > 0) {
                            double cost = med[i].quantity * med[i].price;
                            totalAmount += cost;
                            sold = true;

                            System.out.println(med[i].name + " | Qty: " + med[i].quantity + " | Cost: Rs." + cost);
                            med[i].stock -= med[i].quantity;
                            med[i].quantity = 0;
                        }
                    }
                    if (!sold) {
                        System.out.println("No medicines were sold.");
                    } else {
                        System.out.println("------------------------");
                        System.out.println("Total Bill: Rs." + totalAmount);
                    }
                    return;

                default:
                    System.out.println("Invalid choice. Please enter 1 or 2.");
            }
        }
    }

    void searchMed(Medicine[] med) {
        // search medicine information
        System.out.println("Press 1 to search by ID");
        System.out.println("Press 2 to search by name");
        System.out.println("Enter your choice");
        int ch = sc.nextInt();

        switch (ch) {
            case 1:
                System.out.println("Enter ID you want to search");
                int sId = sc.nextInt();
                boolean check = false;
                for (int i = 0; i < size; i++) {
                    if (med[i] != null && med[i].id == sId) {
                        med[i].get();
                        check = true;
                        break;
                    }
                }
                if (!check) {
                    System.out.println("ID not found.");
                }
                break;

            case 2:
                System.out.println("Enter name you want to search");
                sc.nextLine();
                String sName = sc.nextLine();
                boolean che = false;
                for (int i = 0; i < size; i++) {
                    if (med[i] != null && med[i].name.equals(sName)) {
                        med[i].get();
                        che = true;
                        break;
                    }
                }
                if (!che) {
                    System.out.println("Name not found.");
                }
                break;

            default:
                System.out.println("Invalid choice.");
        }
    }

    void updateMedDetails(Medicine[] med) {
        // update medicine details
        if (size == 0) {
            System.out.println("No medicines available to update.");
            return;
        }
        System.out.println("Enter the ID of the medicine to update:");
        int uId = sc.nextInt();

        boolean check = false;

        for (int i = 0; i < size; i++) {
            if (med[i] != null && med[i].id == uId) {
                check = true;
                sc.nextLine();
                System.out.println("Enter new name (or press Enter to keep unchanged):");
                String newName = sc.nextLine();
                if (!newName.isEmpty()) med[i].name = newName;

                System.out.println("Enter new category (or press Enter to keep unchanged):");
                String newCategory = sc.nextLine();
                if (!newCategory.isEmpty()) med[i].category = newCategory;

                System.out.println("Enter new price (or -1 to keep unchanged):");
                double newPrice = sc.nextDouble();
                if (newPrice >= 0) med[i].price = newPrice;

                System.out.println("Medicine details updated successfully.");
                break;
            }
        }
        if (!check) {
            System.out.println("Medicine ID not found.");
        }
    }

    void outOfStockMedicines(Medicine[] med) {
        // information of medicines which are out of stock
        System.out.println("Out-of-stock medicines:");
        boolean found = false;

        for (int i = 0; i < size; i++) {
            if (med[i] != null && med[i].stock == 0) {
                med[i].get();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No medicines are out of stock.");
        }
    }

    void updateStock(Medicine[] med) {
        // updation of stock of medicines
        if (size == 0) {
            System.out.println("No medicines available to update.");
            return;
        }
        System.out.println("Enter ID for which you want to update stock");
        int sId = sc.nextInt();
        boolean check = false;
        for (int i = 0; i < size; i++) {
            if (med[i] != null && med[i].id == sId) {
                System.out.println("Enter new stock quantity");
                med[i].stock = sc.nextInt();
                System.out.println("Stock updated successfully.");
                check = true;
                break;
            }
        }
        if (!check) {
            System.out.println("Medicine ID not found.");
        }
    }
}

class Main {
    public static void main(String[] args) {
        Scanner sc = Medicine.sc;
        Medicine[] med = new Medicine[100];

        System.out.println("Select user role.");
        System.out.println("Enter 1 if you're the pharmacist.");
        System.out.println("Enter 2 if you're a customer.\n3. Exit");
        int choice = sc.nextInt();

        switch (choice) {
            case 1: {
                Pharmacist p = new Pharmacist();
                while (true) {
                    System.out.println("Here's the select list for PHARMACIST.");
                    System.out.println("-------------------------------------------------------------------");
                    System.out.println("Enter your choice:\n1.Add medicine details.\n2.View medicine.\n3.Update medicine details.\n4.Update stock.\n5.Generate bill.\n6.Low stock medicine details.\n7.Out of stock medicine details.\n8.Search details.\n9.Exit");
                    System.out.println("-------------------------------------------------------------------");
                    int choice1 = sc.nextInt();
                    switch (choice1) {
                        case 1:
                            p.addMed(med);
                            break;
                        case 2:
                            p.displayMed(med);
                            break;
                        case 3:
                            p.updateMedDetails(med);
                            break;
                        case 4:
                            p.updateStock(med);
                            break;
                        case 5:
                            p.generateBill(med);
                            break;
                        case 6:
                            p.lowStockWarning(med);
                            break;
                        case 7:
                            p.outOfStockMedicines(med);
                            break;
                        case 8:
                            p.searchMed(med);
                            break;
                        case 9:
                            System.out.println("Exiting...");
                            System.exit(0);
                            break;
                        default:
                            System.out.println("Invalid choice.");
                    }
                }
            }

            case 2: {
                Medicine c = new Medicine();
                while (true) {
                    System.out.println("Here's the select list for CUSTOMER.");
                    System.out.println("-------------------------------------------------------------------");
                    System.out.println("Enter your choice.\n1. View existing details.\n2. Add medicine.\n3. Exit.");
                    System.out.println("-------------------------------------------------------------------");
                    int choice2 = sc.nextInt();
                    switch (choice2) {
                        case 1:
                            c.displayMed(med);
                            break;
                        case 2:
                            c.addMed(med);
                            break;
                        case 3:
                            System.out.println("Exiting...");
                            System.exit(0);
                            break;
                        default:
                            System.out.println("Invalid choice.");
                    }
                }
            }

            case 3:
                System.out.println("Exiting...");
                System.exit(0);
                break;

            default:
                System.out.println("Invalid choice.");
        }
    }
}
