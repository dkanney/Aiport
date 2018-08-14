package tests;

import airport.Airport;
import org.junit.*;
import static org.junit.Assert.*;

public class PublicTests {

	// Tests that one thread can be created and read one list of reservations,
	// containing one reservation, for one passenger, for one flight.
	@Test
	public void testPublic1() {
		Airport airport = new Airport();

		airport.readFlightData(new String[] { "http://www.cs.umd.edu/class/fall2014/cmsc132/reservations1.html" });

		assertEquals(1, airport.numFlights());
		assertEquals("8209", ListToString.listToString(airport.flightList()));
		assertEquals("Sally",
				ListToString.listToString(airport.passengers(8209)));
	}

	// Tests that one thread can be created and read one list of reservations,
	// containing several reservations, for several passengers, for one flight.
	@Test
	public void testPublic2() {
		Airport airport = new Airport();

		airport.readFlightData(new String[] { "http://www.cs.umd.edu/class/fall2014/cmsc132/reservations2.html" });

		assertEquals(1, airport.numFlights());
		assertEquals("216", ListToString.listToString(airport.flightList()));
		assertEquals("Allison Celia Chana Hao Keisha Marcus Miguel Paula",
				ListToString.listToString(airport.passengers(216)));
	}

	// Tests that one thread can be created and read one list of reservations,
	// containing several reservations, for several passengers, for different
	// flights.
	@Test
	public void testPublic3() {
		Airport airport = new Airport();

		airport.readFlightData(new String[] { "http://www.cs.umd.edu/class/fall2014/cmsc132/reservations3.html" });

		assertEquals(3, airport.numFlights());
		assertEquals("567 1551 7117",
				ListToString.listToString(airport.flightList()));
		assertEquals("Bob Brendan Dave Harold Rhonda",
				ListToString.listToString(airport.passengers(567)));
		assertEquals("Elisa Lois",
				ListToString.listToString(airport.passengers(1551)));
		assertEquals("Mike Mohamed Ravi",
				ListToString.listToString(airport.passengers(7117)));
	}

	// Tests that two threads can be created and read different list of
	// reservations, containing several reservations, for several passengers,
	// for different flights.
	@Test
	public void testPublic4() {
		Airport airport = new Airport();

		airport.readFlightData(new String[] {
				"http://www.cs.umd.edu/class/fall2014/cmsc132/reservations2.html",
		"http://www.cs.umd.edu/class/fall2014/cmsc132/reservations3.html" });

		assertEquals(4, airport.numFlights());

		assertEquals("216 567 1551 7117",
				ListToString.listToString(airport.flightList()));

		assertEquals("Allison Celia Chana Hao Keisha Marcus Miguel Paula",
				ListToString.listToString(airport.passengers(216)));
		assertEquals("Bob Brendan Dave Harold Rhonda",
				ListToString.listToString(airport.passengers(567)));
		assertEquals("Elisa Lois",
				ListToString.listToString(airport.passengers(1551)));
		assertEquals("Mike Mohamed Ravi",
				ListToString.listToString(airport.passengers(7117)));
	}

	// Tests that one thread can be created and read one list of reservations,
	// containing many reservations, for many passengers, for the same flight.
	@Test
	public void testPublic5() {
		Airport airport = new Airport();
		String expectedPassengers = "";
		int i;

		airport.readFlightData(new String[] { "http://www.cs.umd.edu/class/fall2014/cmsc132/reservations4.html" });

		assertEquals(1, airport.numFlights());

		assertEquals("654", ListToString.listToString(airport.flightList()));

		// construct the expected answer
		for (i = 1; i <= 100; i++) {
			// as used below, String.format() returns a string with the value of
			// i, in three digits, padded with zeros on the left; for example,
			// if
			// i is 3 it would return "003", and if i is 12 it would return
			// "012"
			expectedPassengers += ("Person" + String.format("%03d", i));
			if (i < 100) // do not add a blank space after the last element
				expectedPassengers += " ";
		}

		assertEquals(expectedPassengers,
				ListToString.listToString(airport.passengers(654)));
	}

	// Tests that one thread can be created and read one list of reservations,
	// containing many reservations, for many passengers with the same name,
	// for the same flight.
	@Test
	public void testPublic6() {
		Airport airport = new Airport();
		String expectedPassengers = "";
		int i;

		airport.readFlightData(new String[] { "http://www.cs.umd.edu/class/fall2014/cmsc132/reservations5.html" });

		assertEquals(1, airport.numFlights());

		assertEquals("543", ListToString.listToString(airport.flightList()));

		// construct the expected answer
		for (i = 1; i <= 100; i++) {
			expectedPassengers += "Platypus";
			if (i < 100) // do not add a blank space after the last element
				expectedPassengers += " ";
		}

		assertEquals(expectedPassengers,
				ListToString.listToString(airport.passengers(543)));
	}

	// Tests that two threads can be created and read different list of
	// reservations, containing several reservations, for several passengers,
	// for the same flights.
	@Test
	public void testPublic7() {
		Airport airport = new Airport();

		airport.readFlightData(new String[] {
				"http://www.cs.umd.edu/class/fall2014/cmsc132/reservations6.html",
		"http://www.cs.umd.edu/class/fall2014/cmsc132/reservations7.html" });

		assertEquals(7, airport.numFlights());

		assertEquals("132 321 1234 4567 4789 9876 71675",
				ListToString.listToString(airport.flightList()));

		assertEquals("Carlyn Clarence Jonas Sally Sally Sally",
				ListToString.listToString(airport.passengers(132)));
		assertEquals("Bessie George Jose Ringo Zhou",
				ListToString.listToString(airport.passengers(321)));
		assertEquals("Curly Eva Katy Mabel Ringo Syeed",
				ListToString.listToString(airport.passengers(1234)));
		assertEquals("Bonnie Harry Minnie Moe Paul",
				ListToString.listToString(airport.passengers(4567)));
		assertEquals("Bruno Julie Larry Marty Miley Prasad Richard",
				ListToString.listToString(airport.passengers(4789)));
		assertEquals("Allison Cara Darien Eli John Madison Taylor",
				ListToString.listToString(airport.passengers(9876)));
		assertEquals("Armando Brandon Justin Moe Shemp Yang",
				ListToString.listToString(airport.passengers(71675)));
	}

	// Tests that two threads can be created and read different list of
	// reservations, containing several reservations, for several passengers,
	// for the same flights, where the same passenger names appear in the
	// reservation lists processed by different threads.
	@Test
	public void testPublic8() {
		Airport airport = new Airport();

		airport.readFlightData(new String[] {
				"http://www.cs.umd.edu/class/fall2014/cmsc132/reservations7.html",
		"http://www.cs.umd.edu/class/fall2014/cmsc132/reservations8.html" });

		assertEquals(9, airport.numFlights());

		assertEquals("132 321 719 1234 2287 4567 4789 9876 71675",
				ListToString.listToString(airport.flightList()));

		assertEquals(
				"Alyssa Carlyn Carlyn Clarence Clarence Clarence Clarence",
				ListToString.listToString(airport.passengers(132)));
		assertEquals("Bessie Eva Jose Zhou Zhou",
				ListToString.listToString(airport.passengers(321)));
		assertEquals("Cara Dylan",
				ListToString.listToString(airport.passengers(719)));
		assertEquals("Bessie Eva Mabel Syeed Syeed Zhou",
				ListToString.listToString(airport.passengers(1234)));
		assertEquals("Adelmo Armando",
				ListToString.listToString(airport.passengers(2287)));
		assertEquals("Bonnie Harry Harry Minnie Minnie",
				ListToString.listToString(airport.passengers(4567)));
		assertEquals("Bruno Julie Larry Marty Prasad Prasad Richard",
				ListToString.listToString(airport.passengers(4789)));
		assertEquals("Allison Allison Cara Cara Darien Eli Hailey Madison",
				ListToString.listToString(airport.passengers(9876)));
		assertEquals("Armando Armando Brandon Brandon Justin Yang",
				ListToString.listToString(airport.passengers(71675)));
	}

}
