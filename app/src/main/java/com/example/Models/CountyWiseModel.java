package com.example.Models;

public class CountyWiseModel extends Country {

    private String country;
    private String confirmed;
    private String newConfirmed;
    private String active;
    private String deceased;
    private String newDeceased;
    private String recovered;
    private String newRecovered;
    private String tests;
    private String population;
    private String continent;
    private String flag;

    public CountyWiseModel(String country, String confirmed, String newConfirmed, String active, String deceased, String newDeceased, String recovered, String newRecovered, String tests, String population, String continent, String flag) {
        this.country = country;
        this.confirmed = confirmed;
        this.newConfirmed = newConfirmed;
        this.active = active;
        this.deceased = deceased;
        this.newDeceased = newDeceased;
        this.recovered = recovered;
        this.newRecovered = newRecovered;
        this.tests = tests;
        this.population = population;
        this.continent = continent;
        this.flag = flag;
    }

    public String getCountry() {
        return country;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public String getNewConfirmed() {
        return newConfirmed;
    }

    public String getActive() {
        return active;
    }

    public String getDeceased() {
        return deceased;
    }

    public String getNewDeceased() {
        return newDeceased;
    }

    public String getRecovered() {
        return recovered;
    }

    public String getNewRecovered() {
        return newRecovered;
    }

    public String getTests() {
        return tests;
    }

    public String getPopulation() {
        return population;
    }

    public String getContinent() {
        return continent;
    }

    public String getFlag() {
        return flag;
    }
}