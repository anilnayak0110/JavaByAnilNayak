package com.anilnayak.blockchain.dao;

/*mysql> desc Transactions;
+------------------+-------------+------+-----+---------+-------+
| Field            | Type        | Null | Key | Default | Extra |
+------------------+-------------+------+-----+---------+-------+
| Debitor_Name     | varchar(64) | YES  |     | NULL    |       |
| Debitor_Accno    | varchar(64) | YES  |     | NULL    |       |
| Dbt_Amount       | float       | YES  |     | NULL    |       |
| Creditor_Name    | varchar(64) | YES  |     | NULL    |       |
| Creditor_Accno   | varchar(64) | YES  |     | NULL    |       |
| Transaction_Date | varchar(24) | YES  |     | NULL    |       |
+------------------+-------------+------+-----+---------+-------+
6 rows in set (0.36 sec)

mysql> desc HashValue;
+---------------------+--------------+------+-----+---------+----------------+
| Field               | Type         | Null | Key | Default | Extra          |
+---------------------+--------------+------+-----+---------+----------------+
| id                  | int(11)      | NO   | PRI | NULL    | auto_increment |
| Amount              | float        | YES  |     | NULL    |                |
| Previous_Hash_Value | varchar(128) | YES  |     | NULL    |                |
| Current_Hash_Value  | varchar(128) | YES  |     | NULL    |                |
| Hash_id             | int(11)      | YES  |     | NULL    |                |
+---------------------+--------------+------+-----+---------+----------------+
5 rows in set (0.07 sec)
*/

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.anilnayak.blockchain.model.Block;
import com.anilnayak.blockchain.model.Payment;

public class PaymentDao {
	 private  Connection connection;

	    public PaymentDao() {
	        connection = Dbutil.getConnection();
	    }
      
	    public  int addPayment(Payment payment) {
	    	 int res = 0;
	        try {
	            PreparedStatement preparedStatement = connection
	                    .prepareStatement("insert into Transactions(Debitor_Name,Debitor_Accno,Dbt_Amount,Creditor_Name,Creditor_Accno,Transaction_Date) values (?, ?, ?, ?, ?, ? )");
	            // Parameters start with 1
	            preparedStatement.setString(1, payment.getDebitorName());
	            preparedStatement.setString(2, payment.getDebitorAccNo());
	            preparedStatement.setFloat(3, payment.getAmount());
	            preparedStatement.setString(4, payment.getCreditorName());
	            preparedStatement.setString(5, payment.getCreditorAccNo());
	            preparedStatement.setString(6, payment.getTxnDate());
	            res = preparedStatement.executeUpdate();
	            System.out.println("Res:::"+res);
	        	return res;
	        	
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return 0;
	        }
		
	    }
	    
	    
	    public  int addHashValue(Block block,float amount) {
	    	 int res = 0;
	        try {
	            PreparedStatement preparedStatement = connection
	                    .prepareStatement("insert into Hashvalue(Amount,Previous_Hash_Value,Current_Hash_Value,Hash_id) values (?, ?, ?, ?)");
	            // Parameters start with 1
	            preparedStatement.setFloat(1, amount);
	            preparedStatement.setString(2,block.getPreviousHashValue());
	            preparedStatement.setString(3, block.getCurrentHashValue());
	            preparedStatement.setInt(4,1);
	         
	            res = preparedStatement.executeUpdate();
	            System.out.println("Res:::"+res);
	        	return res;
	        	
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return 0;
	        }
		
	    }
	    public int countRows(){
	    	int count =0;
	    	try {
	    		System.out.println("Hiii");
				PreparedStatement preparedStatement = connection.prepareStatement("Select count(*) from Hashvalue" );
				ResultSet rs = preparedStatement.executeQuery();
				
				while (rs.next()) {
					 count= rs.getInt("COUNT(*)");
				System.out.println("COUNT(*)="+rs.getInt("COUNT(*)"));
					
				}
				
				return count;
			} catch (SQLException e) {
				
				e.printStackTrace();
				return count;
			}
	    	
	    }
	    
	    public String getPreviousHashValue(int rowCount){
	    	String currentHashValue = "0";
	    	try {
				PreparedStatement preparedStatement = connection.prepareStatement("SELECT Current_Hash_Value,(SELECT Current_Hash_Value FROM Hashvalue s1 WHERE s1.id < s.id ORDER BY id DESC LIMIT 1) as Current_Hash_Value FROM Hashvalue s WHERE id = ?" );
				preparedStatement.setInt(1, rowCount);
				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()) {
					currentHashValue = rs.getString("Current_Hash_Value");
					System.out.println("CuurrentHashValue:::"+currentHashValue);
				}
				
				if(currentHashValue==null){
					return currentHashValue;
				}
				else{
					return currentHashValue;
				}
			} catch (SQLException e) {
				
				e.printStackTrace();
				return e.getMessage();
			}
	    	
	    }
	    
	    public List<Block> getAllTransactions() {
	        List<Block> block = new ArrayList<Block>();
	        try {
	        	PreparedStatement preparedStatement = connection.prepareStatement("select * from Hashvalue");
	            ResultSet rs = preparedStatement.executeQuery();
	            while (rs.next()) {
	            	Block b = new Block();
	                b.setCurrentHashValue(rs.getString("Current_Hash_Value"));
	                b.setPreviousHashValue(rs.getString("Previous_Hash_Value"));
	                b.setAmount(rs.getFloat("Amount"));
	               
	                block.add(b);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return block;
	    }


}
