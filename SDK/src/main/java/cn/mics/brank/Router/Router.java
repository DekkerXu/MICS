package cn.mics.brank.Router;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hyperledger.fabric.sdk.Channel;

import cn.mics.brank.fabric.entity.SampleOrg;
import cn.mics.brank.sdk.First;




public class Router {
	static First first=new First();
	static Channel channel;
	private static Collection<SampleOrg> testSampleOrgs;
	static String Claim;
	static List<String> ReturnList = new ArrayList<String>();
	static List<String> ReturnListCheck = new ArrayList<String>();
	static List<String> ReturnListCheck2 = new ArrayList<String>();
	static List<String> ReturnListCheck1 = new ArrayList<String>();
	static List<String> ReturnListCheck4 = new ArrayList<String>();
	static String query;
	static String query1;
	static String query2;
	public static void getInformation(String methodName,String certifiNumber,String searchName,String info) {
		  String ID=certifiNumber+searchName;
		 switch(methodName) {
		   case "invoke": { 
			   int count=0;
		   		String invoke=first.queryapplication(channel,ID);
		   		System.out.println("888888888"+invoke);
		   		while(!invoke.equals("NULL")) {
		   			System.out.println("wrong invoke!!"+invoke);
		   			String TID=searchName+Integer.toString(count);
		   			ID=certifiNumber+TID;
		   		   count++;
		   			System.out.println("9999999999999999999999000000000000000"+ID);
		   			invoke=first.queryapplication(channel,ID);
		   			}
		   	String saveName="save"+searchName;
			   first.invokeapplication(channel,ID,info,saveName);
			   break;
		   }
		   case "query": {
		   		
		   		int count=0;
		      String query=first.queryapplication(channel,ID);
		      System.out.println("888888888"+query);
	   		while(!query.equals("NULL")) {
	   			System.out.println("querying!!"+query);
	   			String contractID=searchName+Integer.toString(count);
	   			count++;
	   			System.out.println("9999999999999999999999000000000000000"+query);
	   	      ReturnList.add(query);
	   	      ID=certifiNumber+contractID;
	   	      query=first.queryapplication(channel,ID);
	   		}
	   		System.out.println("please9999999:"+ReturnList);
			   break;
		   }
		  
		 }
}
	
	
	
	public static List<String> getReturnListCheck4() {
		return ReturnListCheck4;
	}



	public static void setReturnListCheck4(List<String> returnListCheck4) {
		ReturnListCheck4 = returnListCheck4;
	}



	public static List<String> getReturnListCheck1() {
		return ReturnListCheck1;
	}



	public static void setReturnListCheck1(List<String> returnListCheck1) {
		ReturnListCheck1 = returnListCheck1;
	}



	public static List<String> getReturnListCheck2() {
		return ReturnListCheck2;
	}

	public static void setReturnListCheck2(List<String> returnListCheck2) {
		ReturnListCheck2 = returnListCheck2;
	}

	public static List<String> getReturnList() {
		return ReturnList;
	}
	public static void setReturnList(List<String> returnList) {
		ReturnList = returnList;
	}
	public static List<String> getReturnListCheck() {
		return ReturnListCheck;
	}
	public static void setReturnListCheck(List<String> returnListCheck) {
		ReturnListCheck = returnListCheck;
	}
	public static void getClaimInformation(String certifiNumber,String contractID) {
		 String medicareID=certifiNumber+"medicalcare";
		 //String contractID=certifiNumber+"contract";
		 int count=0;
		 String check=first.checkapplication(channel, contractID,medicareID);
		 while(!check.equals("NULL")) {
			 System.out.println("checking!!"+check);
	   	 String medicare="medicalcare"+Integer.toString(count);
	   	count++;
	   	System.out.println("9999999999999999999999000000000000000"+check);
	   	String getcontractID = "{\"contractID\":"+"\""+contractID+"\""+"}";
	   	String getmedicareID = "{\"medicareID\":"+"\""+medicareID+"\""+"}";
//	   	check = check +","+ getcontractID+","+medicareID;
	   	ReturnListCheck.add(check);
	   	medicareID=certifiNumber+medicare;
	   	check=first.checkapplication(channel,contractID,medicareID);
			 }
		   System.out.println("99999999"+check);
	}
	
	public static void getClaimInformationDetail(String contractID,String medicareID) {
		 String check=first.checkapplication(channel, contractID,medicareID);
		 System.out.println("checking!!"+check);
	    ReturnListCheck4.add(check);
		 System.out.println("000000"+check);
	}
	
	 public static void getInformationnext(String ID) {
		    query=first.queryapplication(channel,ID);
		    ReturnListCheck2.add(query);
		    System.out.println("888888888"+query);
		    }
	 
	 public static void getInformationnext(String cID,String mID) {
		    query1=first.queryapplication(channel,cID);
		    query2=first.queryapplication(channel,mID);
		    ReturnListCheck1.add(query1);
		    ReturnListCheck1.add(query2);
		    System.out.println("7777777"+ReturnListCheck1);
		    }
	
	public final static void ExcuteFirst() {
		try {
			testSampleOrgs=first.checkConfig();
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException
				| MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		channel=first.setup(testSampleOrgs);
		
		first.clearConfig();
	}
}
