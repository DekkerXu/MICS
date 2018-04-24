package org.hyperledger.main.test;
public class mainTest {
    public boolean getInformation(String info) {
//    	message.setName("a");
//    	message.setNamevalue("100");
//    	message.setMoney("b");
//    	message.setMoneyvalue("300");
//    	String infoMe="";
//    	infoMe=JacksonUtil.toJSon(message);
    	boolean flag=false;
    	Contract getInfo=JacksonUtil.readValue(info, Contract.class);
    	System.out.println("我在fabric"+"============"+getInfo.getMoney()+"=============");
		//return NewTest;
    	if(getInfo!=null) {
    		flag=true;
    	}
    	return flag;
	}

}
