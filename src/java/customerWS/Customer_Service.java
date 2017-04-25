/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *////////////////////////
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customerWS;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author shuhaib
 */
@WebService(serviceName = "Customer_Service")
public class Customer_Service {

    String accountNoRetrieved = "";
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Date myDate;
    java.sql.Date sqlDate;

    /**
     * Web service operation
     *
     * @param name
     * @param birthday
     * @param address
     * @param mobile
     * @param email
     * @param accType
     * @param accNo
     * @param sortCode
     * @param balance
     * @param card
     * @return
     */
    @WebMethod(operationName = "addCustomer")
    public boolean addCustomer(@WebParam(name = "name") String name, @WebParam(name = "birthday") String birthday, @WebParam(name = "address") String address, @WebParam(name = "mobile") String mobile, @WebParam(name = "email") String email, @WebParam(name = "accType") String accType, @WebParam(name = "accNo") String accNo, @WebParam(name = "sortCode") String sortCode, @WebParam(name = "balance") double balance, @WebParam(name = "card") String card) {
        boolean success = false;

        try {
            myDate = formatter.parse(birthday);
            sqlDate = new java.sql.Date(myDate.getTime());
            String sqlQuery = "INSERT INTO customer VALUES(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement stmt = DBConnection.connectDB().prepareStatement(sqlQuery);
            stmt.setString(1, name);
            stmt.setDate(2, sqlDate);
            stmt.setString(3, address);
            stmt.setString(4, mobile);
            stmt.setString(5, email);
            stmt.setString(6, accType);
            stmt.setString(7, accNo);
            stmt.setString(8, sortCode);
            stmt.setDouble(9, balance);
            stmt.setString(10, card);
            stmt.execute();
            success = true;
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        } catch (ParseException ex) {
            Logger.getLogger(Customer_Service.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }

    /**
     * Web service operation
     *
     * @param accNo
     * @param name
     * @param birthday
     * @param address
     * @param mobile
     * @param email
     * @return
     */
    @WebMethod(operationName = "editCustomer")
    public boolean editCustomer(@WebParam(name = "accNo") String accNo, @WebParam(name = "name") String name, @WebParam(name = "birthday") String birthday, @WebParam(name = "address") String address, @WebParam(name = "mobile") String mobile, @WebParam(name = "email") String email) {
        boolean success = false;
        try {
            myDate = formatter.parse(birthday);
            sqlDate = new java.sql.Date(myDate.getTime());
            String sqlQuery = "UPDATE customer SET name=?, birthday = ?, address = ?, mobile= ?, email= ? WHERE accNo=?";
            PreparedStatement stmt = DBConnection.connectDB().prepareStatement(sqlQuery);
            stmt.setString(1, name);
            stmt.setDate(2, sqlDate);
            stmt.setString(3, address);
            stmt.setString(4, email);
            stmt.setString(5, mobile);
            stmt.setString(6, String.valueOf(accNo));
            stmt.execute();
            success = true;
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        } catch (ParseException ex) {
            Logger.getLogger(Customer_Service.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }

    /**
     * Web service operation
     *
     * @param accNo
     * @return
     */
    @WebMethod(operationName = "deleteCustomer")
    public boolean deleteCustomer(@WebParam(name = "accNo") String accNo) {
        boolean success = false;
        try {
            String sqlQuery = "DELETE FROM customer WHERE accNO = ?";
            PreparedStatement stmt = DBConnection.connectDB().prepareStatement(sqlQuery);
            stmt.setString(1, accNo);
            stmt.execute();
            success = true;
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return success;
    }

    @WebMethod(operationName = "fetchCustomerOperation")
    public List fetchCustomerOperation() {
        return MyBean.fetchCustomerOperation();
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "customerDetails")
    public ArrayList customerDetails(@WebParam(name = "AccountNumber") int AccountNumber) {
        //TODO write your implementation code here:
        ArrayList customerDetails = new ArrayList();
        String sql = "Select * from customer where accNo="+AccountNumber;
        try {
            PreparedStatement ps = DBConnection.connectDB().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                customerDetails.add(rs.getString("name"));
                customerDetails.add(rs.getString("birthday"));
                customerDetails.add(rs.getString("address"));
                customerDetails.add(rs.getString("mobile"));
                customerDetails.add(rs.getString("email"));
                customerDetails.add(rs.getString("accType"));
                customerDetails.add(rs.getString("accNo"));
                customerDetails.add(rs.getString("sortCode"));
                customerDetails.add(rs.getString("balance"));
                customerDetails.add(rs.getString("card"));

            }
            return customerDetails;
        } catch (Exception e) {
        }
        return customerDetails;
    }

}

class MyBean {

    public static List fetchCustomerOperation() {
        List list = new ArrayList();

        String sql = "select * from Customer";

        try {

            Statement ps = DBConnection.connectDB().createStatement();
            ResultSet rs = ps.executeQuery(sql);
            while (rs.next()) {
                list.add(rs.getString("name") + "-" + rs.getString("accNo"));
            }
            DBConnection.connectDB().close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

}
