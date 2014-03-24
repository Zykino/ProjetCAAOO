package metier;

public class Offre {
	private Utilisateur personne; // la personne qui fait l'offre
	private double prix;

	public Offre(double prix, Utilisateur acheteur)
			throws InvalidArgumentException {
		if (acheteur != null) {
			if (prix > 0) {
				this.prix = prix;
				this.personne = acheteur;
			} else {
				throw new InvalidArgumentException("Le prix doit être positif.");
			}
		} else {
			throw new InvalidArgumentException("L'acheteur ne peut être null.");
		}
	}

	public Utilisateur getPersonne() {
		return personne;
	}

	public double getPrix() {
		return prix;
	}

}
