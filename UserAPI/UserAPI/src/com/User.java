package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.ws.rs.GET;
import javax.ws.rs.Path;


public class User {
	
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, FirstName, password
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/paf","root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertUser(String FirstName, String LastName, String Address, String AccountNo, String ContactNo,String Email)  
	{   
		String output = ""; 	 
		try   
		{    
			Connection con = connect(); 
	 
			if (con == null)    
			{return "Error while connecting to the database for inserting."; } 
	 
			// create a prepared statement 
			String query = " insert into user(`UserID`,`FirstName`,`LastName`,`Address`,`AccountNo`,`ContactNo`,`Email`)" + " values (?, ?, ?, ?, ?, ?, ?)"; 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
			// binding values    
			 preparedStmt.setInt(1, 0);
			 preparedStmt.setString(2, FirstName);
			 preparedStmt.setString(3, LastName);
			 preparedStmt.setString(4, Address);
			 preparedStmt.setString(5, AccountNo);
			 preparedStmt.setString(6, ContactNo);
			 preparedStmt.setString(7, Email);
			
			
			// execute the statement    
			preparedStmt.execute();    
			con.close(); 
	   
			String newUser = readUser(); 
			output =  "{\"status\":\"success\", \"data\": \"" + newUser + "\"}";    
		}   
		catch (Exception e)   
		{    
			output =  "{\"status\":\"error\", \"data\": \"Error while inserting the User.\"}";  
			System.err.println(e.getMessage());   
		} 		
	  return output;  
	} 	
	
	public String readUser()  
	{   
		String output = ""; 
		try   
		{    
			Connection con = connect(); 
		
			if (con == null)    
			{
				return "Error while connecting to the database for reading."; 
			} 
	 
			// Prepare the html table to be displayed    
			output = "<table border=\'1\'><tr><th>User First Name</th><th>User Last Name</th><th>User Address</th><th>User Account No</th><th>User Contact No</th><th>User Email</th><th>Update</th><th>Remove</th></tr>";
	  
			String query = "select * from user";    
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
	 
			// iterate through the rows in the result set    
			while (rs.next())    
			{     
				 String UserID = Integer.toString(rs.getInt("UserID"));
				 String FirstName = rs.getString("FirstName");
				 String LastName = rs.getString("LastName");
				 String Address = rs.getString("Address");
				 String AccountNo = rs.getString("AccountNo");
				 String ContactNo = rs.getString("ContactNo");
				 String Email = rs.getString("Email");
			
				 
				// Add into the html table 
				output += "<tr><td><input id=\'hidUserIDUpdate\' name=\'hidUserIDUpdate\' type=\'hidden\' value=\'" + UserID + "'>" 
							+ FirstName + "</td>"; 
				output += "<td>" + LastName + "</td>";
				output += "<td>" + Address + "</td>";
				output += "<td>" + AccountNo + "</td>";
				output += "<td>" + ContactNo + "</td>";
				output += "<td>" + Email + "</td>";
				
				
				// buttons     
				output +="<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"       
						+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-userid='" + UserID + "'>" + "</td></tr>"; 
			
			}
			con.close(); 
	   
			output += "</table>";   
		}   
		catch (Exception e)   
		{    
			output = "Error while reading the User.";    
			System.err.println(e.getMessage());   
		} 	 
		return output;  
	}
	
	public String updateUser(String UserID, String FirstName, String LastName, String Address, String AccountNo, String ContactNo, String Email)  
	{   
		String output = "";  
		try   
		{    
			Connection con = connect(); 
	 
			if (con == null)    
			{return "Error while connecting to the database for updating."; } 
	 
			// create a prepared statement    
			String query = "UPDATE user SET FirstName=?,LastName=?,Address=?,AccountNo=?,ContactNo=?,Email=?"  + "WHERE UserID=?";  	 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
			// binding values    
			preparedStmt.setString(1, FirstName);
			 preparedStmt.setString(2, LastName);
			 preparedStmt.setString(3, Address);
			 preparedStmt.setString(4, AccountNo);
			 preparedStmt.setString(5, ContactNo);
			 preparedStmt.setString(6, Email);
		
			 preparedStmt.setInt(7, Integer.parseInt(UserID)); 
	 
			// execute the statement    
			preparedStmt.execute();    
			con.close();  
			String newUser = readUser();    
			output = "{\"status\":\"success\", \"data\": \"" + newUser + "\"}";    
		}   
		catch (Exception e)   
		{    
			output =  "{\"status\":\"error\", \"data\": \"Error while updating the User.\"}";   
			System.err.println(e.getMessage());   
		} 	 
	  return output;  
	} 
	
	public String deleteUser(String UserID)   
	{   
		String output = ""; 
	 
		try   
		{    
			Connection con = connect(); 
	 
			if (con == null)    
			{
				return "Error while connecting to the database for deleting."; 			
			} 
	 
			// create a prepared statement    
			String query = "delete from user where UserID=?"; 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
			// binding values    
			preparedStmt.setInt(1, Integer.parseInt(UserID)); 
	 
			// execute the statement    
			preparedStmt.execute();    
			con.close(); 
	 
			String newUser = readUser();    
			output = "{\"status\":\"success\", \"data\": \"" +  newUser + "\"}";    
		}   
		catch (Exception e)   
		{    
			output = "Error while deleting the User.";    
			System.err.println(e.getMessage());   
		} 
	 
		return output;  
	}
	
}
