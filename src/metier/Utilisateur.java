package metier;

import java.util.ArrayList;

public class Utilisateur {
	
	private ArrayList<String> alertesRecues;

	private String login;
	private String nom;
	private String prenom;
	private boolean recevoirAlert;
	
	
	public Utilisateur(String login, String nom, String prenom)
			throws InvalidArgumentException {
		if (null == login || login.equals(""))
			throw new InvalidArgumentException(
					"Le pseudo ne doit pas être null ou vide.");
		this.login = login;
		recevoirAlert = true;
		alertesRecues = new ArrayList<String>();
		this.nom = nom;
		this.prenom = prenom;
	}

	public String getDerniereAlertRecue () {
		return alertesRecues.get(alertesRecues.size()-1);
	}
	
	public String getLogin(){
		return this.login;
	}
	
	// redéfinition des méthodes equals et hashcode pour
	// ne pas avoir de doublons dans les logins
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		Utilisateur user = (Utilisateur) obj;
		if (!login.equals(user.login))
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		// seul l'identifiant (login) doit être unique
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		//result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		//result = prime * result + ((prenom == null) ? 0 : prenom.hashCode());
		return result;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [Login=");
		builder.append(login);
		builder.append(", nom=");
		builder.append(nom);
		builder.append(", prenom=");
		builder.append(prenom);
		builder.append("]");
		return builder.toString();
	}
	
	public void receptionAlert(){
		recevoirAlert = true;
	}

	public void nonreceptionAlert() {
		recevoirAlert = false;
	}
	
	public void alerte(String message) {
		if(recevoirAlert){
			Alert.printMessage(message);
			if(alertesRecues == null)
				System.out.println("HUDHUDHFHDFHIDH");
			alertesRecues.add(message);
		}
	}
}
