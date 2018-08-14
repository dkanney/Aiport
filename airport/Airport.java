/***********************************************************
 * Name: David Kanney
 * Directory ID: dkanney
 * UID: 113039065
 * Section: 0101
 * *********************************************************
 * This class simulates an airport, with numbered flights, named passengers,
 * and destinations. Each flight (of type Flight) has a destination and a
 * list of its passengers. Every newly instantiated airport has a Map that 
 * associates a flight number (key) to a flight (value) with passengers. The
 * methods in this class are pretty straightforward, including a
 * readFlightData() method that utilizes threads to read data from a website
 * and then add this data to the airport object.
 ***********************************************************/
package airport;

import java.io.BufferedReader;
import java.net.URL;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

public class Airport {

	/******************************
	 * 
	 * Private inner class Flight
	 *
	 ******************************/
	private class Flight {

		private String destination;
		private ArrayList<String> passengers = new ArrayList<String>();

		// This constructor initializes the field variables.
		private Flight(String destination) {
			this.destination = destination;
		}

		// This method returns a flight's destination.
		private String getDestination() {
			return destination;
		}

		// This method gets and returns the passengers in
		// the passengers ArrayList.
		private ArrayList<String> getPassengers() {
			return passengers;
		}

		// This method returns the number of passengers in the
		// passengers ArrayList.
		private int getPassengerCount() {
			return passengers.size();
		}

		private void addPassenger(String name) {
			passengers.add(name);
		}

		// This method removes a passenger from the passenger ArrayList.
		private void removePassenger(String passengerName) {
			passengers.remove(passengerName);
		}
	}

	/******************************
	 * 
	 * Private inner class myThread
	 *
	 ******************************/
	private class myThread implements Runnable {
		String url;

		// This constructor stores the url passed into it from the String array
		// of urls from the readFlightData() method.
		public myThread(String url) {
			this.url = url;
		}

		// This method uses a BufferedReader to read the data from a url's
		// webpage line by line. Then, the fragments of HTML code are filtered
		// out and the passenger/flight data is stored as a string. This string
		// is split into three parts and placed into an array, where the flight
		// number is parsed and stored as an integer. Finally, the flight
		// number, passenger name, and flight destination are added to the
		// original airport object.
		public void run() {
			try {
				String line;
				URL url = new URL(this.url);
				BufferedReader in = new BufferedReader(new InputStreamReader(
						url.openStream()));

				while ((line = in.readLine()) != null)
					if ((line.startsWith("<")) || (line.length() == 0)
							|| (line.contains("Passenger reservation data")))
						continue;
					else {
						StringBuilder content = new StringBuilder();
						content.append(line + "\n");
						String[] temp = content.toString().trim()
								.replace("<br>", "").split(" ");
						int flightNum = Integer.parseInt(temp[1]);
						String name = temp[0];
						String destination = temp[2];

						synchronized (airport) {
							if (!(airport.containsKey(flightNum)))
								addFlight(flightNum, destination);
							addPassengerReservation(flightNum, name);
							System.out.println(flightNum + ":" + name + "\n");
						}
					}
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// All flights of type Flight are stored in this map, associating an
	// Integer to the Flight, which represents a flight number.
	private Map<Integer, Flight> airport = new TreeMap<Integer, Flight>();

	// This method takes in an array of urls as strings, and creates a thread
	// for each url. Then, the run() method is called on each of these threads.
	public void readFlightData(String[] urls) {
		for (String item : urls)
			(new myThread(item)).run();
	}

	// This method adds a flight to an airport.
	public Airport addFlight(int flightNumber, String destination)
			throws IllegalArgumentException {
		if (destination.compareTo("") == 0)
			throw new IllegalArgumentException("Invalid destination.");

		airport.put(flightNumber, new Flight(destination));

		return this;
	}

	// This method takes each flight number being stored in the object and
	// returns them in a list (in numerical order).
	public List<Integer> flightList() {
		List<Integer> flights = new LinkedList<Integer>();

		for (int flightNum : airport.keySet())
			flights.add(flightNum);
		Collections.sort(flights);
		return flights;
	}

	// This method returns the number of flights in the airport ArrayList.
	public int numFlights() {
		return airport.size();
	}

	// This method adds a passenger to a flight by the flight number,
	// returning true if it is successful and false if it is unsuccessful.
	public boolean addPassengerReservation(int flightNumber,
			String passengerName) throws IllegalArgumentException {
		boolean passengerAdded = false;
		Flight plane = airport.get(flightNumber);

		if (this.airport.get(flightNumber) == plane) {
			passengerAdded = true;
			plane.addPassenger(passengerName);
		}

		return passengerAdded;
	}

	// This method takes each passenger stored in a flight (specified by
	// flight number) and returns their names in a list (in alphabetical order).
	// If the specified flight does not exist, an empty list is returned.
	public List<String> passengers(int flightNumber) {
		ArrayList<String> names = new ArrayList<String>();

		if (!(this.flightList().contains(flightNumber)))
			return names;

		for (String passneger : this.airport.get(flightNumber).passengers)
			names.add(passneger);
		Collections.sort(names);
		return names;
	}

	// This method returns the number of passengers on a flight, specified.
	// by its flight number. This method will return a -1 if the flight with
	// the specified flight number cannot be found.
	public int numPassengersOnFlight(int flightNumber) {
		int passengerCount = 0;
		Flight plane = airport.get(flightNumber);

		try {
			if (this.airport.get(flightNumber) == plane)
				passengerCount = plane.getPassengerCount();
		} catch (NullPointerException e) {
			passengerCount = -1;
		}
		return passengerCount;
	}

	// This method returns the number of passengers who are on a flight
	// with the same name. (The flight is specified by its flight number).
	// This method will return a -1 if the flight with the specified
	// flight number cannot be found.
	public int numPassengersOnFlight(int flightNumber, String name)
			throws IllegalArgumentException {
		if (name.compareTo("") == 0)
			throw new IllegalArgumentException("Invalid name.");

		int passengerCount = 0;
		boolean passengersWereFound = false;
		Flight plane = airport.get(flightNumber);

		if (this.airport.get(flightNumber) == plane) {
			passengersWereFound = true;

			for (String person : plane.getPassengers())
				if (person.equals(name))
					passengerCount += 1;
		}

		if (passengersWereFound)
			return passengerCount;

		return -1;
	}

	// This method removes a flight with the specified flight number
	// from the airport it is assigned to, returning true or false
	// if the flight is there or not.
	public boolean cancelFlight(int flightNumber) {
		boolean flightCancelled = false;
		Flight plane = airport.get(flightNumber);

		if (this.airport.get(flightNumber) == plane) {
			airport.remove(flightNumber);

			flightCancelled = true;
		}

		return flightCancelled;
	}

	// This method cancels a passenger's reservation on a specified
	// flight (flight number), and returns true or false if it
	// successfully found the passenger and cancelled the reservation.
	public boolean cancelPassengerReservation(int flightNumber,
			String passengerName) throws IllegalArgumentException {
		if (passengerName.equals(""))
			throw new IllegalArgumentException("Invalid name.");

		boolean reservationCancelled = false;
		Flight plane = airport.get(flightNumber);

		if (this.airport.get(flightNumber) == plane)
			if (plane.getPassengers().contains(passengerName)) {
				plane.removePassenger(passengerName);
				reservationCancelled = true;
			}

		return reservationCancelled;
	}

	// This method returns the number of flights at an airport
	// with the same destinations.
	public int numFlightsGoingToCity(String city)
			throws IllegalArgumentException {
		int numFlights = 0;

		for (Entry<Integer, Flight> plane : airport.entrySet()) {
			if (plane.getValue().getDestination().equals(city))
				numFlights += 1;
		}

		return numFlights;
	}

	// This method returns the number of passengers at an airport who
	// are all heading to the same destination.
	public int numPassengersGoingToCity(String city)
			throws IllegalArgumentException {
		if (city.equals(""))
			throw new IllegalArgumentException("Invalid city.");

		int passengerCount = 0;

		for (Entry<Integer, Flight> plane : airport.entrySet())
			if (airport.get(plane.getKey()).getDestination().equals(city))
				passengerCount += plane.getValue().getPassengerCount();

		return passengerCount;
	}

}
