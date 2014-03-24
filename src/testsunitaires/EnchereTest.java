package testsunitaires;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import metier.Enchere;
import metier.EtatEnchere;
import metier.InvalidArgumentException;
import metier.Objet;
import metier.Offre;
import metier.Time;
import metier.Utilisateur;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class EnchereTest {
	Enchere mEnchere;
	Time temps;

//	Enchere mSauvegarde;

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@SuppressWarnings("deprecation")
	@Before
	public void setUp() throws Exception {
		temps = new Time();
		
		mEnchere = new Enchere(new Objet("Table", "Table de salon en bois."),
				new Date(2014-1900, 2, 30),
				new Utilisateur("login", "nom", "prenom"), 10., 20.);
	}

	@SuppressWarnings("deprecation")
	@Test
	public final void testEnchere() throws InvalidArgumentException {
		new Enchere(new Objet("Table", "Table de salon en bois."), new Date(
				2014, 4, 15), new Utilisateur("login", "nom", "prenom"), 10.,
				20.);
		mEnchere.publier();
		assertEquals(EtatEnchere.PUBLIEE, mEnchere.getEtatEnchere());
	}

	// enchère sur un objet null
	@SuppressWarnings("deprecation")
	@Test(expected = InvalidArgumentException.class)
	public final void testEnchereObjetInvalide()
			throws InvalidArgumentException {
		new Enchere(null, new Date(2014, 4, 15), new Utilisateur("login",
				"nom", "prenom"), 10., 20.);

	}

	// enchère sur une date null
	@Test(expected = InvalidArgumentException.class)
	public final void testEnchereDateInvalide() throws InvalidArgumentException {
		new Enchere(new Objet("Table", "Table de salon en bois."), null,
				new Utilisateur("login", "nom", "prenom"), 10., 20.);

	}

	// enchère sur un vendeur null
	@SuppressWarnings("deprecation")
	@Test(expected = InvalidArgumentException.class)
	public final void testEnchereUtilisateurInvalide()
			throws InvalidArgumentException {
		new Enchere(new Objet("Table", "Table de salon en bois."), new Date(
				2014, 4, 15), null, 10., 20.);

	}

	//enchère avec prix minimal négatif
	@SuppressWarnings("deprecation")
	@Test(expected = InvalidArgumentException.class)
	public final void testEnchereMinimumNegatif()
			throws InvalidArgumentException {
		new Enchere(new Objet("Table", "Table de salon en bois."), new Date(
				2014, 4, 15), new Utilisateur("login", "nom", "prenom"), -0.1,
				20.);

	}

	//enchère avec prix minimal = 0
	@SuppressWarnings("deprecation")
	@Test(expected = InvalidArgumentException.class)
	public final void testEnchereMinimumInvalide()
			throws InvalidArgumentException {
		new Enchere(new Objet("Table", "Table de salon en bois."), new Date(
				2014, 4, 15), new Utilisateur("login", "nom", "prenom"), 0, 20.);

	}

	// enchère avec prix de reserve negatif
	@SuppressWarnings("deprecation")
	@Test(expected = InvalidArgumentException.class)
	public final void testEnchereReserveNegatif()
			throws InvalidArgumentException {
		Enchere enchere = new Enchere(new Objet("Table",
				"Table de salon en bois."), new Date(2014, 4, 15),
				new Utilisateur("login", "nom", "prenom"), 10., -0.1);
		assertEquals(null, enchere);
	}

	// enchère avec prix de reserve = 0
	@SuppressWarnings("deprecation")
	@Test(expected = InvalidArgumentException.class)
	public final void testEnchereReserveInvalide()
			throws InvalidArgumentException {
		Enchere enchere = new Enchere(new Objet("Table",
				"Table de salon en bois."), new Date(2014, 4, 15),
				new Utilisateur("login", "nom", "prenom"), 10., 0);
		assertEquals(null, enchere);
	}

	@SuppressWarnings("deprecation")
	@Test
	public final void testGetObjet() throws InvalidArgumentException {
		Objet table = new Objet("Table", "Table de salon en bois.");
		Enchere enchere = new Enchere(table, new Date(2014, 4, 15),
				new Utilisateur("login", "nom", "prenom"), 10., 20.);
		assertEquals(table, enchere.getObjet());
	}

	@Test
	public final void testPublier() {
		mEnchere.publier();
		assertEquals(EtatEnchere.PUBLIEE, mEnchere.getEtatEnchere());
	}

	@Test
	public final void testAnnulerNonPublie() throws InvalidArgumentException {
		mEnchere.annuler();
		assertEquals(EtatEnchere.ANNULEE, mEnchere.getEtatEnchere());
	}

	@Test
	public final void testAnnulerPublieSansOffre()
			throws InvalidArgumentException {
		mEnchere.publier();
		mEnchere.annuler();
		assertEquals(EtatEnchere.ANNULEE, mEnchere.getEtatEnchere());
	}

	@Test(expected = InvalidArgumentException.class)
	public final void testAnnulerPrixReserveDepasse()
			throws InvalidArgumentException {
		mEnchere.publier();
		mEnchere.faireOffre(new Offre(100, new Utilisateur("pseudo", "nom",
				"prenom")));
		mEnchere.annuler();

	}

	@Test(expected = InvalidArgumentException.class)
	public final void testOffreDateDepassee() throws InvalidArgumentException {
		Objet table = new Objet("Table", "Table de salon en bois.");
		Enchere enchere = new Enchere(table, new Date(2014, 4, 15),
				new Utilisateur("bobby", "bob", "prenom"), 10., 20.);
		enchere.publier();
		Time.ajouterMois();
		Time.ajouterMois();
		Time.ajouterMois();

		mEnchere.faireOffre(new Offre(100, new Utilisateur("pseudo", "nom",
				"prenom")));

	}
	
	@Test(expected = InvalidArgumentException.class)
	public final void testAnnulerDejaTermine() throws InvalidArgumentException {
		mEnchere.publier();
		temps.ajouterMois();
		mEnchere.MAJetat();
		mEnchere.annuler();

	
	}

	@Test(expected = InvalidArgumentException.class)
	public final void testAnnulerTempsDepaseEtatNonTermine()
			throws InvalidArgumentException {
		mEnchere.publier();
		Time.ajouterMois();
		mEnchere.MAJetat();
		Time.ajouterMois();
		mEnchere.annuler();
		assertEquals(EtatEnchere.TERMINEE, mEnchere.getEtatEnchere());
	}

	@Test
	public final void testPrixReserveNonAtteint()
			throws InvalidArgumentException {
		mEnchere.publier();
		mEnchere.faireOffre(new Offre(15, new Utilisateur("pseudo", "nom",
				"prenom")));
		assertEquals(false, mEnchere.prixReserveAtteint());
	}

	@Test
	public final void testPrixReserveAtteint() throws InvalidArgumentException {
		mEnchere.publier();
		mEnchere.faireOffre(new Offre(200, new Utilisateur("pseudo", "nom",
				"prenom")));

		assertEquals(true, mEnchere.prixReserveAtteint());
	}

	@Test
	public final void testPrixReserveAtteintDepase()
			throws InvalidArgumentException {
		mEnchere.publier();
		mEnchere.faireOffre(new Offre(30, new Utilisateur("pseudo", "nom",
				"prenom")));
		assertEquals(true, mEnchere.prixReserveAtteint());
	}

	@Test(expected = InvalidArgumentException.class)
	public final void testPrixMinimumNonAtteint()
			throws InvalidArgumentException {
		mEnchere.publier();
		mEnchere.faireOffre(new Offre(5, new Utilisateur("pseudo", "nom",
				"prenom")));
	}

	@Test
	public final void testPrixMinimumAtteint() throws InvalidArgumentException {
		mEnchere.publier();
		mEnchere.faireOffre(new Offre(100, new Utilisateur("pseudo", "nom",
				"prenom")));
		assertEquals(true, mEnchere.prixMinimumAtteint());
	}

	@Test
	public final void testPrixMinimumAtteintDepase()
			throws InvalidArgumentException {
		mEnchere.publier();
		mEnchere.faireOffre(new Offre(15, new Utilisateur("pseudo", "nom",
				"prenom")));
		assertEquals(true, mEnchere.prixMinimumAtteint());
	}

	@Test
	public final void testPrixMinimumAtteintDepaseBeaucoup()
			throws InvalidArgumentException {
		mEnchere.publier();
		mEnchere.faireOffre(new Offre(25, new Utilisateur("pseudo", "nom",
				"prenom")));
		assertEquals(true, mEnchere.prixMinimumAtteint());
	}

	@Test
	public final void testDateLimiteAtteinte() {
		mEnchere.publier();
		Time.ajouterMois();
		mEnchere.MAJetat();
		assertEquals(EtatEnchere.TERMINEE, mEnchere.getEtatEnchere());
	}

	@Test
	public final void testFaireOffre() throws InvalidArgumentException {
		mEnchere.publier();
		Offre offre0 = new Offre(50, new Utilisateur("pseudo", "nom", "prenom"));
		mEnchere.faireOffre(offre0);
		assertEquals(offre0, mEnchere.getMeilleurOffre());
	}

	@Test(expected = InvalidArgumentException.class)
	public final void testFaireOffreNULL() throws InvalidArgumentException {
		mEnchere.publier();
		mEnchere.faireOffre(null);
	}

	@Test(expected = InvalidArgumentException.class)
	public final void testFaireOffreSansAcheteur()
			throws InvalidArgumentException {
		mEnchere.publier();
		mEnchere.faireOffre(new Offre(15, null));
	}

	@Test(expected = InvalidArgumentException.class)
	public final void testFaireOffreSurNonPubliee()
			throws InvalidArgumentException {
		mEnchere.faireOffre(new Offre(15, new Utilisateur("pseudo", "nom",
				"prenom")));
	}

	@Test(expected = InvalidArgumentException.class)
	public final void testFaireOffreVendeurEncherit()
			throws InvalidArgumentException {
		mEnchere.publier();
		mEnchere.faireOffre(new Offre(100, new Utilisateur("login", "nom",
				"prenom")));
	}

	@Test(expected = InvalidArgumentException.class)
	public final void testFaireOffreInferieurMinimum()
			throws InvalidArgumentException {
		mEnchere.publier();
		mEnchere.faireOffre(new Offre(5, new Utilisateur("pseudo", "nom",
				"prenom")));
		assertEquals(null, mEnchere.getMeilleurOffre());
	}

	@Test
	public final void testFaireOffreInferieurReserve()
			throws InvalidArgumentException {
		mEnchere.publier();
		Offre offre0 = new Offre(15, new Utilisateur("pseudo", "nom", "prenom"));
		mEnchere.faireOffre(offre0);
		assertEquals(offre0, mEnchere.getMeilleurOffre());
	}

	@Test
	public final void testFaireOffreMeilleure() throws InvalidArgumentException {
		mEnchere.publier();
		mEnchere.faireOffre(new Offre(20, new Utilisateur("pseudo0", "nom",
				"prenom")));
		Offre meilleure = new Offre(30, new Utilisateur("pseudo1", "nom",
				"prenom"));
		
		mEnchere.faireOffre(meilleure);
		assertEquals(meilleure, mEnchere.getMeilleurOffre());
	}

	@Test
	public final void testFaireOffreInferieure() {
		mEnchere.publier();
		Offre meilleure = null;
		try {
			meilleure = new Offre(30, new Utilisateur("pseudo0", "nom",
					"prenom"));
			mEnchere.faireOffre(meilleure);
		} catch (InvalidArgumentException e) {
//			e.printStackTrace();
		}
		try {
			mEnchere.faireOffre(new Offre(20, new Utilisateur("pseudo1", "nom",
					"prenom")));
		} catch (InvalidArgumentException e) {
//			e.printStackTrace();
			assertEquals(meilleure, mEnchere.getMeilleurOffre());
		}
	}

}
