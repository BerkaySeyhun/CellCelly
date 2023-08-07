package tr.com.cellcelly;


public interface SmsSystem {
	
	 boolean connectToMW();
	 int checkBalance(String gsmNo);
	 boolean sendSMS(String gsmNo,int balance);

}
