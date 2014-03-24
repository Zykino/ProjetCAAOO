package testsunitaires;

import static org.junit.Assert.assertEquals;
import metier.Offre;
import metier.InvalidArgumentException;
import metier.Utilisateur;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class OffreTest {

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test(expected = InvalidArgumentException.class)
	public final void prixNegatif() throws InvalidArgumentException {
		@SuppressWarnings("unused")
		Offre offre = new Offre(-1, new Utilisateur("pseudo", "nom", "prenom"));
	}

	@Test(expected = InvalidArgumentException.class)
	public final void utilisateurInvalide() throws InvalidArgumentException {
		@SuppressWarnings("unused")
		Offre offre = new Offre(15, null);
	}

	public final void offreCorrecteUtilisateur() throws InvalidArgumentException {
		Utilisateur lambda = new Utilisateur("pseudo", "nom", "prenom");

		Offre offre = new Offre(15, lambda);
		assertEquals(offre.getPersonne(), lambda);
	}

	@SuppressWarnings("deprecation")
	public final void offreCorrectePrix() throws InvalidArgumentException {
		int prix = 15;

		Offre offre = new Offre(prix,
				new Utilisateur("pseudo", "nom", "prenom"));
		assertEquals(offre.getPrix(), prix);
	}
}
