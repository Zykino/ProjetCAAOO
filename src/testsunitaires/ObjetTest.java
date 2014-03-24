package testsunitaires;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import javax.activity.InvalidActivityException;

import metier.InvalidArgumentException;
import metier.Objet;


import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class ObjetTest {
	Objet table;
	Objet chaise;
	Objet iphone;
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		table = new Objet("Table", "Petite table basse de salon.");
		iphone = new Objet("iphone");
		chaise = new Objet("Chaise","Chaise époque Louis XV. Très bon état.");
	}


	// Test d'un nom vide et sans description doit générer une exeption
	@Test(expected = InvalidArgumentException.class)
	public final void testObjetNomVide() throws InvalidArgumentException {
		table = new Objet("");
	}

	// Test d'un nom vide avec une description doit générer une exeption
	@Test(expected = InvalidArgumentException.class)
	public final void testObjetNomVideDescription() throws InvalidArgumentException{	
			table = new Objet("", "Petite table basse de salon.");
	
	}

	@Test
	public final void testObjetSansDescription() {
		assertEquals("", iphone.getDescription());
	}

	@Test
	public final void testGetNom() {
		assertEquals("Table", table.getNom());
	}

	@Test
	public final void testGetDescription() {
		assertEquals("Petite table basse de salon.", table.getDescription());
	}

}
