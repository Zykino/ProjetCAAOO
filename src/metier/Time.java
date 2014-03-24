package metier;

import java.util.Date;

// classe permettant de simuler l'heure du système
public class Time {
	private static Date date;

	public Time() {
		date = new Date();
	}

	public static Date getTime() {
		return date;
	}

	public static void printDate() {
	
		System.out.println(date.toString());
	}

	@SuppressWarnings("deprecation")
	public static void ajouterMois() {
		
		int mois = date.getMonth();
		date.setMonth(mois+1);
		
		
		//date.setDate((int) (trenteJours.getTime() + date.getTime()));
	}
}
