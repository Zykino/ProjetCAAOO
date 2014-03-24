package metier;

public class Alert {
	
	public static String printMessage(String message){
		String m = "["+Time.getTime().toString()+"] : "+message;
		System.out.println(m);
		return m;
	}

}
