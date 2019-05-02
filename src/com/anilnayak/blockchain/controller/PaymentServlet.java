package com.anilnayak.blockchain.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.anilnayak.blockchain.common.RandomString;
import com.anilnayak.blockchain.dao.PaymentDao;
import com.anilnayak.blockchain.model.Block;
import com.anilnayak.blockchain.model.Payment;

public class PaymentServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private PaymentDao dao;

    public PaymentServlet() {
        super();
        dao = new PaymentDao();
    }
  /*  public static List<Block> blockchains = new ArrayList<Block>();*/
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		PrintWriter pw         = resp.getWriter();
		/*Get the Request*/
		String debitorName     = req.getParameter("Debitor_Name");
		String debitorAccNo    = req.getParameter("Debitor_Accno");
		float debitorAmount   = Float.parseFloat(req.getParameter("Dbt_Amount"));
		String creditorName    = req.getParameter("Creditor_Name");
		String creditorAccNo   = req.getParameter("Creditor_Accno");
		String transactionDate = req.getParameter("Transaction_Date");
		
		Payment payment = new Payment();
		payment.setDebitorName(debitorName);
		payment.setDebitorAccNo(debitorAccNo);
		payment.setAmount(debitorAmount);
		payment.setCreditorName(creditorName);
		payment.setCreditorAccNo(creditorAccNo);
		payment.setTxnDate(transactionDate);
		
		/*Add Payment Details into Database*/
		String txnId = RandomString.getAlphaNumericString(8);
		int addPayment = dao.addPayment(payment,txnId);
		if(addPayment==1){
			System.out.println("Payment Added !!!!!!");
			//pw.println("Payment Added Successfully !!!!!");
		}
		else{
			System.out.println("payment Not added !!!!");
		}
		int difficulty = 5;
		String data = debitorName+debitorAccNo+debitorAmount+creditorName+creditorAccNo+transactionDate;
		
		
    /*Get Row count*/
		
		int count = dao.countRows();
		if(count>0){
			System.out.println("Record available!!!!");
		}
		else{
			System.out.println("No record available!!!!");
		}
		
		System.out.println("Count Value:::"+count);
		/*Get Previous HashValue*/
		String previousHashValues = dao.getPreviousHashValue(count);
		System.out.println("PreviousHashValue::::"+previousHashValues);
		Block b = new Block(data, previousHashValues);
		b.mineBlock(difficulty);
		String previousHashValue = b.getPreviousHashValue();
		String currentHashValue = b.getCurrentHashValue();
		
		int hashAdded = dao.addHashValue(b, debitorAmount,txnId);
		
		if(hashAdded==1){
			System.out.println("HashValue Added !!!!!!");
			pw.println("HashValue Added Successfully !!!!!");
		}
		else{
			System.out.println("HashValue Not added !!!!");
		}
		
		
	/*	
		blockchains = dao.getAllTransactions();
		
		
		
		String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchains);
		System.out.println("\nThe block chain: ");
		pw.println(blockchainJson);
		System.out.println(blockchainJson);*/
		
	}
	
	

}
