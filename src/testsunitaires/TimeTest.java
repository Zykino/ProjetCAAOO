package testsunitaires;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.Date;

import metier.Time;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class TimeTest {

	Time localTime;

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		localTime = new Time();
	}

	@Test
	public final void testGetTime() {
		assertEquals(localTime.getTime(),new Date());
	}


	@Test
	public final void testAddThirtyDays() {
	
		int initialMonth = localTime.getTime().getMonth();
		
		localTime.ajouterMois();
	
		assertEquals(localTime.getTime().getMonth(),initialMonth+1);
		//fail("Not yet implemented");
	}

}
