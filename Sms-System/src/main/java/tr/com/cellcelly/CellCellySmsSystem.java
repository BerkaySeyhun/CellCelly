package tr.com.cellcelly;


public class CellCellySmsSystem implements SmsSystem {

	
	@Override
	public boolean connectToMW() {

		System.out.println("MiddleWare'e bağlanıyor....");
		try {

			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	
		System.out.println("MiddleWare'e bağlandı!");
		
		
		return true;

	}

	@Override
	public int checkBalance(String gsmNo) {

		System.out.println("Kalan SMS miktarı sorgulanıyor.." + gsmNo);
		
		try {

			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		int balance = (int) (Math.random() * 1000);
		
		
		return balance;

	}
	

	@Override
	public boolean sendSMS(String gsmNo,int balance) {

		System.out.println( gsmNo + " sms gönderiliyor...");
		try {

			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	
		System.out.println("Sms gönderme başarılı!");
		
		
		return true;

	}

}
