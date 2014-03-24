package metier;

import java.util.ArrayList;
import java.util.Date;

public class Enchere {

	private Date dateLimite;
	private Objet objet;
	private double prixMinimum;
	private double prixDeReserve; // a voir si on garde pas qu'une variable + une autre pour dire si on est en reserve ou prix mini
	private Utilisateur vendeur;
	private EtatEnchere etatEnchere;
	private Offre meilleurOffre;
	private ArrayList<Utilisateur> acheteurs;

	public Enchere(Objet objet, Date limite, Utilisateur vendeur,
			double prixMinimum, double prixDeReserve)
			throws InvalidArgumentException {

		if (null == objet || null == limite || null == vendeur)
			throw new InvalidArgumentException(
					"L'enchère doit avoir au moins un objet, un vendeur et une date limite.");

		if (prixDeReserve <= 0 || prixMinimum <= 0)
			throw new InvalidArgumentException(
					"L'enchère doit avoir au moins un objet, un vendeur et une date limite.");

		this.objet = objet;
		this.dateLimite = limite;
		this.vendeur = vendeur;
		meilleurOffre = null;
		this.prixDeReserve = prixDeReserve;
		this.prixMinimum = prixMinimum;
		etatEnchere = EtatEnchere.CREEE;
		acheteurs = new ArrayList<Utilisateur>();
	}

	public Objet getObjet() {
		return objet;
	}

	public EtatEnchere getEtatEnchere() {
		return etatEnchere;
	}

	public Offre getMeilleurOffre() {
		return meilleurOffre;
	}

	// publication de l'enchère
	public void publier() {
		etatEnchere = EtatEnchere.PUBLIEE;
	}

	// mise à jour de l'etat de l'enchère
	public void MAJetat() {
		if (dateLimiteAtteinte() && etatEnchere == EtatEnchere.PUBLIEE)
			etatEnchere = EtatEnchere.TERMINEE;
	}

	public void annuler() throws InvalidArgumentException {
		if (prixReserveAtteint() && etatEnchere == EtatEnchere.PUBLIEE)
			throw new InvalidArgumentException(
					"Prix de réserve atteint. Annulation impossible.");

		else if (etatEnchere == EtatEnchere.TERMINEE)
			throw new InvalidArgumentException(
					"Enchère terminée. Annulation impossible.");

		else if (!prixReserveAtteint() && etatEnchere == EtatEnchere.PUBLIEE){
			etatEnchere = EtatEnchere.ANNULEE;
			alerteTous("Le vendeur vient d'annuler l'enchère : "+this.objet.getNom());

		}

		else if (etatEnchere != EtatEnchere.PUBLIEE
				&& etatEnchere != EtatEnchere.TERMINEE){
			etatEnchere = EtatEnchere.ANNULEE;
			alerteTous("Le vendeur vient d'annuler l'enchère : "+this.objet.getNom());
		}
		else
			throw new InvalidArgumentException(
					"Enchère terminée. Annulation impossible.");

	}

	public boolean prixReserveAtteint() {
		if (null == meilleurOffre)
			return false;

		if (meilleurOffre.getPrix() >= prixDeReserve)
			return true;
		return false;
	}

	public boolean prixMinimumAtteint() {
		if (null == meilleurOffre)
			return false;

		if (meilleurOffre.getPrix() >= prixMinimum)
			return true;
		return false;
	}

	public boolean dateLimiteAtteinte() {

		if (Time.getTime().getTime() > dateLimite.getTime())
			return true;
		return false;
	}

	public boolean enchereFinie() {
		MAJetat();
		if (etatEnchere == EtatEnchere.TERMINEE)
			return true;
		return false;
	}

	public boolean enchereValidee() {
		if (enchereFinie() && prixMinimumAtteint())
			return true;
		return false;
	}

	public void alerteTous(String message) {
		if(null != acheteurs)
		for (Utilisateur a : acheteurs) {
			a.alerte(message);
		}
	}
	public void alerteUser(String message, Utilisateur user) {
		user.alerte(message);
		
	}

	public void faireOffre(Offre acheteur) throws InvalidArgumentException {

		if (null == acheteur)
			throw new InvalidArgumentException("L'acheteur ne peut être null.");

		if (enchereFinie())
			throw new InvalidArgumentException("L'enchère est deja terminée.");

		if (etatEnchere != EtatEnchere.PUBLIEE)
			throw new InvalidArgumentException(
					"Enchère non disponible (non publiée).");

		if (vendeur.equals(acheteur.getPersonne()))
			throw new InvalidArgumentException(
					"Le vendeur ne peut pas enchérir sur son enchère.");

		if (acheteur.getPrix() < prixMinimum)
			throw new InvalidArgumentException(
					"Prix inférieur au minimum demandé.");

		// première offre on l'enregistre
		if (null == meilleurOffre) {
			meilleurOffre = acheteur;
			acheteurs.add(acheteur.getPersonne());
		}
		// une meilleur offre est proposée
		else if (meilleurOffre.getPrix() < acheteur.getPrix()) {
			meilleurOffre = acheteur;
			acheteurs.add(acheteur.getPersonne());
			alerteTous("L'utilisateur "+acheteur.getPersonne().getLogin()+" vient de faire une offre sur l'enchère "+this.objet.getNom()+" nouveau prix : "+acheteur.getPrix());
			if(acheteur.getPrix()>prixDeReserve || acheteur.getPrix()>prixMinimum){
				alerteTous("Le prix minimal de l'enchère "+objet.getNom() + " vient d'être atteint.");
				alerteUser("Le prix minimal sur votre enchère "+objet.getNom() + " vient d'être atteint.", vendeur);
			}
			
		} else
			throw new InvalidArgumentException(
					"Prix inférieur à la meilleur proposition.");

		
		return;
	}
}