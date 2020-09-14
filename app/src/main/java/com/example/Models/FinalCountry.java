package com.example.Models;

public class FinalCountry {

    private long active;
    private long recovered;
    private long deaths;

    private double deathsPerOneMillion;
    private double criticalPerOneMillion;
    private double activePerOneMillion;
    private double recoveredPerOneMillion;
    private double casesPerOneMillion;
    private double testsPerOneMillion;

    private Integer affectedCountries;
    private String tests;
    private String population;


    public long getActive() {
        return active;
    }

    public void setActive(long active) {
        this.active = active;
    }

    public long getRecovered() {
        return recovered;
    }

    public void setRecovered(long recovered) {
        this.recovered = recovered;
    }

    public long getDeaths() {
        return deaths;
    }

    public void setDeaths(long deaths) {
        this.deaths = deaths;
    }

    public double getDeathsPerOneMillion() {
        return deathsPerOneMillion;
    }

    public void setDeathsPerOneMillion(double deathsPerOneMillion) {
        this.deathsPerOneMillion = deathsPerOneMillion;
    }

    public double getCriticalPerOneMillion() {
        return criticalPerOneMillion;
    }

    public void setCriticalPerOneMillion(double criticalPerOneMillion) {
        this.criticalPerOneMillion = criticalPerOneMillion;
    }

    public double getActivePerOneMillion() {
        return activePerOneMillion;
    }

    public void setActivePerOneMillion(double activePerOneMillion) {
        this.activePerOneMillion = activePerOneMillion;
    }

    public double getRecoveredPerOneMillion() {
        return recoveredPerOneMillion;
    }

    public void setRecoveredPerOneMillion(double recoveredPerOneMillion) {
        this.recoveredPerOneMillion = recoveredPerOneMillion;
    }

    public double getCasesPerOneMillion() {
        return casesPerOneMillion;
    }

    public void setCasesPerOneMillion(double casesPerOneMillion) {
        this.casesPerOneMillion = casesPerOneMillion;
    }

    public double getTestsPerOneMillion() {
        return testsPerOneMillion;
    }

    public void setTestsPerOneMillion(double testsPerOneMillion) {
        this.testsPerOneMillion = testsPerOneMillion;
    }

    public Integer getAffectedCountries() {
        return affectedCountries;
    }

    public void setAffectedCountries(Integer affectedCountries) {
        this.affectedCountries = affectedCountries;
    }

    public String getTests() {
        return tests;
    }

    public void setTests(String tests) {
        this.tests = tests;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

}