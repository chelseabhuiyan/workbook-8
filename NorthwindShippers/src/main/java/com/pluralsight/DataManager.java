package com.pluralsight;

import com.pluralsight.models.Shipper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataManager {

    public int insertShipper(String name, String phone) {
        String sql = "INSERT INTO shippers (CompanyName, Phone) VALUES (?, ?)";

        try (Connection conn = Database.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, name);
            stmt.setString(2, phone);
            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("Insert failed: " + e.getMessage());
        }
        return -1;
    }

    public List<Shipper> getAllShippers() {
        List<Shipper> list = new ArrayList<>();
        String sql = "SELECT ShipperID, CompanyName, Phone FROM shippers ORDER BY ShipperID";

        try (Connection conn = Database.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(new Shipper(
                        rs.getInt("ShipperID"),
                        rs.getString("CompanyName"),
                        rs.getString("Phone")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
        return list;
    }

    public void updateShipperPhone(int id, String phone) {
        String sql = "UPDATE shippers SET Phone = ? WHERE ShipperID = ?";

        try (Connection conn = Database.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, phone);
            stmt.setInt(2, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Update failed: " + e.getMessage());
        }
    }

    public void deleteShipper(int id) {
        if (id <= 3) {
            System.out.println(" You cannot delete shippers 1 to 3. Try another ID.");
            return;
        }

        String sql = "DELETE FROM shippers WHERE ShipperID = ?";

        try (Connection conn = Database.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Delete failed: " + e.getMessage());
        }
    }
}
