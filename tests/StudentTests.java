package tests;

import org.junit.*;

import airport.Airport;
import static org.junit.Assert.*;

public class StudentTests {

	// write your student tests in this class
	@Test
	public void testNumFlights() {
		Airport airport = new Airport();
		// Checking to see that a new airport should have no flights.
		assertEquals(0, airport.numFlights());

		airport.addFlight(1337, "NY").addFlight(7331, "FL");
		assertEquals(2, airport.numFlights());

		// Testing the chained calls of the Airport class.
		assertEquals(3, airport.addFlight(9, "Canada").numFlights());
	}

	// This method tests that the addFlight() method throws an
	// IllegalArgumentException.
	@Test(expected = IllegalArgumentException.class)
	public void testIllegalArgumentException() {
		Airport airport = new Airport();

		airport.addFlight(1337, "");
	}

	@Test
	// This method tests an attempt at trying to add flights with the same
	// flight number (only one should be added).
	public void testAddFlightConstraint() {
		Airport airport = new Airport();

		airport.addFlight(2077, "Washington");
		airport.addFlight(2077, "Baltimore");

		assertEquals(1, airport.numFlights());
	}

	@Test
	public void testNumPassengersGoingToCity() {
		Airport airport = airport4();

		assertEquals(5, airport.numPassengersGoingToCity("Baltimore"));
	}

	// tests canceling the only flight at an airport; the airport should have
	// no flights after that
	@Test
	public void testCancelFlight() {
		Airport airport = airport1();

		airport.cancelFlight(8209);

		assertEquals(0, airport.numFlights());
	}

	// This method tests the cancelPassengerReservation() on an invalid
	// passenger name.
	@Test(expected = IllegalArgumentException.class)
	public void testCancelPassengerReservation1() {
		Airport airport = airport1();

		airport.cancelPassengerReservation(8209, "");
	}

	// This method tests calling cancelPassengerReservation() on the only
	// passenger reservation on a flight the flight should have no passengers
	// after that.
	@Test
	public void testCancelPassengerReservation2() {
		Airport airport = airport1();

		airport.cancelPassengerReservation(8209, "Sally");

		assertEquals(0, airport.numPassengersOnFlight(8209));
	}

	// tests canceling one flight from an airport that has several flights
	@Test
	public void testNumFlightsGoingToCity1() {
		Airport airport = airport2();

		assertEquals(4, airport.numFlights());
		assertEquals(1, airport.numFlightsGoingToCity("New York"));

		airport.cancelFlight(2917);

		assertEquals(3, airport.numFlights());
		assertEquals(0, airport.numFlightsGoingToCity("New York"));
	}

	// tests calling numFlightsGoingToCity() on an airport that has more than
	// one flight going to the same destination
	@Test
	public void testNumFlightsGoingToCity2() {
		Airport airport = airport4();

		assertEquals(2, airport.numFlightsGoingToCity("Baltimore"));
		assertEquals(1, airport.numFlightsGoingToCity("Minneapolis"));
		assertEquals(0, airport.numFlightsGoingToCity("Miami"));
	}

	// tests calling numPassengersOnFlight() on an invalid flight number (a
	// number that does not correspond to any flight that was added)
	@Test
	public void testNumPassengersOnFlight1() {
		Airport airport = airport1();

		assertEquals(-1, airport.numPassengersOnFlight(31415));
	}

	// This method tests the addFlight() method, and then tests the
	// numPassengersOnFlight() method to ensure that a newly added flight
	// has zero passengers.
	@Test
	public void testNumPassengersOnFlight2() {
		Airport airport = new Airport();

		airport.addFlight(2543, "BWI");
		assertEquals(0, airport.numPassengersOnFlight(2543));

		airport.addPassengerReservation(2543, "Obama");
		assertEquals(1, airport.numPassengersOnFlight(2543));

		airport.addPassengerReservation(2543, "Larry H. Platypus");
		airport.addPassengerReservation(2543, "Konstantinos");
		airport.addPassengerReservation(2543, "President Loh");
		assertEquals(4, airport.numPassengersOnFlight(2543));
	}

	/* **************************
	 * 
	 * Private Utility Methods
	 * 
	 * **************************
	 */

	// returns an airport that has one flight with one passenger
	private Airport airport1() {
		Airport airport = new Airport();

		airport.addFlight(8209, "Seattle");

		airport.addPassengerReservation(8209, "Sally");

		return airport;
	}

	// returns an airport that has several flights, with different numbers of
	// passengers (all with different names)
	private Airport airport2() {
		Airport airport = new Airport();

		airport.addFlight(1752, "Asheville");
		airport.addFlight(834, "Phoenix");
		airport.addFlight(2917, "New York");
		airport.addFlight(8128, "Bangor");

		airport.addPassengerReservation(834, "Ben");
		airport.addPassengerReservation(2917, "Denise");
		airport.addPassengerReservation(8128, "Celia");
		airport.addPassengerReservation(834, "Chana");
		airport.addPassengerReservation(2917, "Marcus");
		airport.addPassengerReservation(8128, "Heather");
		airport.addPassengerReservation(834, "Carla");
		airport.addPassengerReservation(834, "Allison");
		airport.addPassengerReservation(2917, "Miguel");
		airport.addPassengerReservation(834, "Jayson");

		return airport;
	}

	// returns an airport that has several flights, with different numbers of
	// passengers (some with the same names)
	private Airport airport4() {
		Airport airport = new Airport();

		airport.addFlight(1234, "Baltimore");
		airport.addFlight(4567, "San Francisco");
		airport.addFlight(9876, "Minneapolis");
		airport.addFlight(132, "Boston");
		airport.addFlight(321, "Baltimore");
		airport.addFlight(4789, "Chicago");
		airport.addFlight(71675, "Austin");

		airport.addPassengerReservation(132, "Sally");
		airport.addPassengerReservation(132, "Jonas");
		airport.addPassengerReservation(9876, "John");
		airport.addPassengerReservation(4567, "Paul");
		airport.addPassengerReservation(321, "George");
		airport.addPassengerReservation(1234, "Ringo");
		airport.addPassengerReservation(132, "Sally");
		airport.addPassengerReservation(9876, "Taylor");
		airport.addPassengerReservation(4789, "Miley");
		airport.addPassengerReservation(4789, "Larry");
		airport.addPassengerReservation(71675, "Moe");
		airport.addPassengerReservation(1234, "Curly");
		airport.addPassengerReservation(132, "Sally");
		airport.addPassengerReservation(71675, "Shemp");
		airport.addPassengerReservation(4567, "Moe");
		airport.addPassengerReservation(321, "Ringo");
		airport.addPassengerReservation(1234, "Katy");

		return airport;
	}
}
