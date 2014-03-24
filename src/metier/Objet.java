package metier;

public class Objet {

	private String description;
	private String nom;

	public Objet(String nom) throws InvalidArgumentException {
		if(nom == "" || null == nom)
			throw new InvalidArgumentException("Le nom de l'objet ne peut être nul ou vide.");
		else {
			this.nom = nom;
			this.description = "";
		}
	}

	public Objet(String nom, String description) throws InvalidArgumentException {
		if(nom == "" || null == nom)
			throw new InvalidArgumentException("Le nom de l'objet ne peut être nul ou vide.");
		else {
			this.nom = nom;
			this.description = description;
		}
	}

	public String getDescription() {
		return description;
	}

	public String getNom() {
		return nom;
	}

}
