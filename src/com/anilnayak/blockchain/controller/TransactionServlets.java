package com.anilnayak.blockchain.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.anilnayak.blockchain.dao.PaymentDao;
import com.anilnayak.blockchain.model.Block;
import com.google.gson.GsonBuilder;

public class TransactionServlets extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	  private PaymentDao dao;
	  public TransactionServlets() {
	        super();
	        dao = new PaymentDao();
	    }
	 public static List<Block> blockchains = new ArrayList<Block>();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	
			throws ServletException, IOException {
		JSONObject jo = null;
		PrintWriter pw = resp.getWriter();
		//pw.println("Hello We are in Transactions !!!!");
		blockchains = dao.getAllTransactions();
		boolean flag = isChainValid(5);
		 jo = new JSONObject();
		String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchains);
		if(flag==true){
			 
			 try {
				jo.put("ISValid",flag);
				jo.put("Data", blockchainJson);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("Flag:::"+flag);
			System.out.println("******"+jo.toString());
		resp.getWriter().write(jo.toString());
		
		}
		else{
			System.out.println("Flag:::"+flag);
			resp.getWriter().write(jo.toString());
		}
	}
	
	public static Boolean isChainValid(int difficulty ) {
		Block currentBlock; 
		Block previousBlock;
		String hashTarget = new String(new char[difficulty]).replace('\0', '0');
		System.out.println("Blockcahin size:::"+blockchains.size());
		//loop through blockchain to check hashes:
		for(int i=1; i < blockchains.size(); i++) {
			String calculateHash = blockchains.get(i).mineBlock(difficulty);
			currentBlock = blockchains.get(i);
			previousBlock = blockchains.get(i-1);
			//compare registered hash and calculated hash:
			System.out.println("currentBlock:::"+currentBlock.currentHashValue);
			System.out.println("previousBlock:::"+calculateHash);
			if(!currentBlock.currentHashValue.equals(calculateHash) ){
				System.out.println("Current Hashes not equal");			
				return false;
			}
			//compare previous hash and registered previous hash
			if(!previousBlock.currentHashValue.equals(currentBlock.previousHashValue) ) {
				System.out.println("Previous Hashes not equal");
				return false;
			}
			//check if hash is solved
			if(!currentBlock.currentHashValue.substring( 0, difficulty).equals(hashTarget)) {
				System.out.println("This block hasn't been mined");
				return false;
			}
		}
		return true;
	}
	
	

}
