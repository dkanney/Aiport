# Aiport

## Objective
The objective of this project is to simulate an airport. The airport includes numbered flights, named passengers, and destinations. 

Each flight (of type `Flight`) has a destination and a list of its passengers. A newly instantiated `Airport` has a `Map` that associates a flight number with a flight. Additionally, each flight will have passengers onboard.

The methods in this class are pretty straightforward, including a `readFlightData()` method that utilizes Java threads to read data from a webpage (`.html`). The data is then added to an `Airport` object.
