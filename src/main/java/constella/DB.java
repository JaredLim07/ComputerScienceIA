package constella;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class DB {
    ArrayList<String> companies = new ArrayList<String> (Arrays.asList("Google", "Amazon", "Netflix", "Oracle", "Facebook"));
    Connection conn;

    public DB() {
        try {
            Class.forName("org.duckdb.DuckDBDriver");
            conn = DriverManager.getConnection("jdbc:duckdb:/tmp/constella.duck");
            createTables();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createTables(){
        try {
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS companies (name VARCHAR, status VARCHAR, comment VARCHAR, startdate DATE, enddate DATE)");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> getCompanyName(){
        ArrayList<String> companyNames = new ArrayList<>();
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            try (ResultSet rs = stmt.executeQuery("SELECT name FROM companies ORDER BY name ASC")){
                while (rs.next()) {
                    companyNames.add(rs.getString("name"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
       return companyNames;
    }

    public void addCompany(String companyName) {
        try {
            Statement stmt = conn.createStatement();
            stmt.execute("INSERT INTO companies VALUES('" + companyName + "', '', '', current_date(), current_date())");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateCompany(String companyName, String status, String comment, Date startDate, Date endDate) {

        try {
            var stmt = conn.prepareStatement("UPDATE companies SET status = ?, comment = ?, startdate = ?, enddate =? WHERE name =?");
            stmt.setString(1, status);
            stmt.setString(2, comment);
            stmt.setDate(3, startDate);
            stmt.setDate(4, endDate);
            stmt.setString(5, companyName);

            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public HashMap<String, Object> getCompanyInfo(String companyName) {
        Statement stmt = null;
        HashMap<String, Object> companyInfo = new HashMap<>();
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM companies WHERE name = '" + companyName + "'");
            rs.next();
            String nameVal = rs.getString("name");
            String statusVal = rs.getString("status");
            String commentVal = rs.getString("comment");
            Date startDateVal = rs.getDate("startdate");
            Date endDateVal = rs.getDate("enddate");

            companyInfo.put("name", nameVal);
            companyInfo.put("status", statusVal);
            companyInfo.put("comment", commentVal);
            companyInfo.put("startDate", startDateVal);
            companyInfo.put("endDate", endDateVal);
            return companyInfo;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int satisfiedCompanies(){
        return 1;
    }
    public int okCompanies(){
        return 1;
    }
    public int dissatisfiedCompanies(){
        return 1;
    }
}


