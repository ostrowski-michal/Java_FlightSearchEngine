package com.company.flightsearchengine;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        FlightDataBase dataBase = new FlightDataBase();
        dataBase.checkIfFlightExists("Paris", "Porto");
        dataBase.displayFlightsFromCity("Paris");
        dataBase.displayFlightsToCity("Warsaw");
        ArrayList<String > cities = dataBase.getCities();
        System.out.println(cities);
        Flight cheapestFlight = dataBase.getCheapestFlight();
        System.out.println("Cheapest flight: " +
                cheapestFlight.getDetails());
        Flight cheapestFlightFromCity =
                dataBase.getCheapestFlightFromCity("Madrid");
        System.out.println("Cheapest flight from city: " +
        cheapestFlightFromCity.getDetails());
        ArrayList<Journey> journeys = dataBase.getFlights("Paris", "Porto");
        System.out.println(journeys);
    }
}
class Flight {              //Nowa klasa
    String departure;       //zmienne typu String
    String arrival;
    int price;              //zmienna typu int

    public Flight (String departure, String arrival,  //konstruktor przyjmujácy trzy argumenty
                   int price) {                       //typu String i int
        this.departure = departure;                   //przypisujemy argumenty do zmiennych
        this.arrival = arrival;
        this.price = price;
    }
    public String getDetails () {                       //metoda zwracajaca informacje o locie
        return "Flight from " +
                this.departure + " to " + this.arrival
                + " costs " + this.price;
    }
}
class FlightDataBase {
    ArrayList<Flight> flights = new ArrayList<Flight>();

    public FlightDataBase() {
        this.flights.add(new Flight("Berlin", "Tokyo",
                1800));
        this.flights.add(new Flight("Paris", "Berlin",
                79));
        this.flights.add(new Flight("Warsaw", "Paris",
                120));
        this.flights.add(new Flight("Madrid", "Berlin",
                200));
        this.flights.add(new Flight("Berlin", "Warsaw",
                77));
        this.flights.add(new Flight("Paris", "Madrid",
                180));
        this.flights.add(new Flight("Porto", "Warsaw",
                412));
        this.flights.add(new Flight("Madrid", "Porto",
                102));
        this.flights.add(new Flight("Warsaw", "Madrid",
                380));

    }

    public void checkIfFlightExists(String start, String end) {
        for (Flight flight : this.flights) {
            if (start.equals(flight.departure) &&
                    end.equals(flight.arrival)) {
                System.out.println("Flight exists!!!");
                return;
            }
        }
        System.out.println("Flight with given params not exists");
    }
    public ArrayList<Flight> getFlightsFromCity(String city) {
        ArrayList<Flight> results = new ArrayList<Flight>();
        for (Flight flight : this.flights) {
            if (city.equals(flight.departure)) {
                results.add(flight);
            }
        }
        return results;
    }
    public ArrayList<Flight> getFlightsToCity(String city) {
        ArrayList<Flight> results = new ArrayList<Flight>();
        for (Flight flight : this.flights) {
            if (city.equals(flight.arrival)) {
                results.add(flight);
            }
        }
        return results;
    }
    public void displayFlightsFromCity(String city) {
        ArrayList<Flight> results = getFlightsFromCity(city);
        displayFlights(results);
    }
    public void displayFlightsToCity(String city) {
        ArrayList<Flight> results = getFlightsToCity(city);
        displayFlights(results);
    }
    public void displayFlights(ArrayList<Flight> results) {
        if (results.isEmpty()) {
            System.out.println("Flight not found");
        }
        for (Flight flight : results) {
            System.out.println(flight.getDetails());
        }
    }
    public ArrayList<String> getCities() {
        ArrayList<String> cities = new ArrayList<>();
        for (Flight flight : this.flights) {
            if (!cities.contains(flight.departure)) {
                cities.add(flight.departure);
            }
            if (!cities.contains(flight.arrival)) {
                cities.add(flight.arrival);
            }
        }
        return cities;
    }

    public Flight getCheapestFlight() {
        Flight cheapestFlight = null;
        for (Flight flight : this.flights) {
            if (cheapestFlight == null || flight.price <
                    cheapestFlight.price) {
                cheapestFlight = flight;
            }
        }
        return cheapestFlight;
    }

    public Flight getCheapestFlightFromCity(String city) {
        ArrayList<Flight> fromCity = getFlightsFromCity(city);
        Flight cheapestFlight = null;
        for (Flight flight : fromCity) {
            if (cheapestFlight == null || flight.price <
                    cheapestFlight.price) {
                cheapestFlight = flight;
            }
        }
        return cheapestFlight;
    }

    public ArrayList<Journey> getFlights(String start, String end) {
        ArrayList<Flight> starting = getFlightsFromCity(start);
        ArrayList<Flight> ending = getFlightsToCity(end);
        ArrayList<Journey> results = new ArrayList<Journey>();
        for (Flight first : starting) {
            for (Flight second : ending) {
                if (first.arrival.equals(second.departure)) {
                    results.add(new Journey(first, second));
                }
            }
        }
        return results;
    }
}
class Journey {
    Flight first;
    Flight second;
    public Journey(Flight first, Flight second) {
        this.first = first;
        this.second = second;
    }
    public String toString() {
        return "Flight from " + first.departure + " to " +
        second.arrival + " with stop at " + first.arrival +
        " costs " + (first.price + second.price);
    }
}
