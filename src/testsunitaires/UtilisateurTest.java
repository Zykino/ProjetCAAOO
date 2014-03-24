package testsunitaires;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import metier.InvalidArgumentException;

import metier.Utilisateur;

import org.junit.Before;
import org.junit.Test;

public class UtilisateurTest {

	private Set<Utilisateur> listeUtilisateur;
	private Utilisateur utilisateur;

	@Before
	public void setUp() throws Exception {
		listeUtilisateur = new HashSet<Utilisateur>();

	}

	// test sur le nombre d'utilisateurs (les doublons sont supprimés) logins,nom,prenom,égaux
	@Test
	public void nombreUtilisateurs() {

		try {
			utilisateur = new Utilisateur("Hong", "Traing", "Heihong");
			this.listeUtilisateur.add(utilisateur);

			utilisateur = new Utilisateur("Vince", "Guillebaud", "Vincent");
			this.listeUtilisateur.add(utilisateur);

			utilisateur = new Utilisateur("Ju", "Baron", "Julien");
			this.listeUtilisateur.add(utilisateur);

		} catch (InvalidArgumentException e) {

			e.printStackTrace();
		}
		assertEquals(3, listeUtilisateur.size(), 0);
	}

	@Test
	public void nombreUtilisateursDoublon() {

		try {
			utilisateur = new Utilisateur("Hong", "Traing", "Heihong");
			this.listeUtilisateur.add(utilisateur);

			utilisateur = new Utilisateur("Vince", "Guillebaud", "Vincent");
			this.listeUtilisateur.add(utilisateur);

			utilisateur = new Utilisateur("Ju", "Baron", "Julien");
			this.listeUtilisateur.add(utilisateur);

			// Ajout d'un doublon qui ne doit pas être pris en compte dans la liste des utilisateurs
			utilisateur = new Utilisateur("Hong", "Traing", "Heihong");
			this.listeUtilisateur.add(utilisateur);

		} catch (InvalidArgumentException e) {

			e.printStackTrace();
		}
		assertEquals(3, listeUtilisateur.size(), 0);
	}

	// test sur le nombre d'utilisateurs avec même nom/prénom mais login différent
	@Test
	public void seulPseudoDiffere() {
		try {
			utilisateur = new Utilisateur("bomi", "Miller", "Bob");
			this.listeUtilisateur.add(utilisateur);
			utilisateur = new Utilisateur("bobby", "Miller", "Bob");
			this.listeUtilisateur.add(utilisateur);
			utilisateur = new Utilisateur("milly", "Miller", "Bob");
			this.listeUtilisateur.add(utilisateur);
			utilisateur = new Utilisateur("popol", "Miller", "Paul");
			this.listeUtilisateur.add(utilisateur);

		} catch (InvalidArgumentException e) {

			e.printStackTrace();
		}

		assertEquals(4, listeUtilisateur.size(), 0);
	}

	// test sur un login null
	@Test
	public void loginNULL() {
		try {
			utilisateur = new Utilisateur(null, "Miller", "Bob");
			this.listeUtilisateur.add(utilisateur);

		} catch (InvalidArgumentException e) {

			assertEquals(e.getMessage(),
					"Le pseudo ne doit pas être null ou vide.");
		}

	}

	// test sur un login vide
	@Test
	public void loginVide() {
		try {
			utilisateur = new Utilisateur("", "Miller", "Bob");
			this.listeUtilisateur.add(utilisateur);
		} catch (InvalidArgumentException e) {
			assertEquals(e.getMessage(),
					"Le pseudo ne doit pas être null ou vide.");
		}
	}

	
	// on créer une enchère et on vérifie que l'acheter recoit des alert
	@Test
	public void alerte() throws InvalidArgumentException {
		Utilisateur azerty = new Utilisateur("azerty", "aze", "rty");
		/*
		Enchere mEnchere = new Enchere(new Objet("Table", "Table de salon en bois."),
				new Date(2014-1900, 2, 30),
				new Utilisateur("login", "nom", "prenom"), 10., 20.);
		mEnchere.publier();
		mEnchere.faireOffre(new Offre(5, azerty));
		mEnchere.annuler();
		
		String lastAlert = azerty.getDerniereAlertRecue();
		assertEquals(lastAlert, "Le vendeur vient d'annuler l'enchère : "+mEnchere.getObjet().getNom());
		*/
		
	}
}
