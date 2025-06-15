package com.pluralsight;

import com.pluralsight.models.Shipper;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DataManager manager = new DataManager();

        // Step 1: Insert new shipper
        System.out.print("Enter company name for new shipper: ");
        String name = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phone = scanner.nextLine();
        int newId = manager.insertShipper(name, phone);
        System.out.println(" New shipper inserted with ID: " + newId);

        // Step 2: Display all shippers
        printShippers(manager);

        // Step 3: Update shipper phone
        System.out.print("\nEnter shipper ID to update phone: ");
        int updateId = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter new phone number: ");
        String newPhone = scanner.nextLine();
        manager.updateShipperPhone(updateId, newPhone);

        // Step 4: Display again
        printShippers(manager);

        // Step 5: Delete shipper
        System.out.print("\nEnter shipper ID to delete (not 1–3): ");
        int deleteId = Integer.parseInt(scanner.nextLine());
        manager.deleteShipper(deleteId);

        // Step 6: Final display
        printShippers(manager);

        scanner.close();
    }

    private static void printShippers(DataManager manager) {
        System.out.println("\n Current Shippers:");
        List<Shipper> shippers = manager.getAllShippers();
        for (Shipper s : shippers) {
            System.out.println(s);
        }
    }
}
