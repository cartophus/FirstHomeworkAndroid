package com.example.homeworkthree;

public class Address {
    String street;
    String suite;
    String city;
    String zipcode;
    Geo geo;

    Address(){}

    Address(String street, String suite, String city, String zipcode, Geo geo)
    {
        this.street = street;
        this.suite = suite;
        this.city = city;
        this.zipcode = zipcode;
        this.geo = geo;
    }
}
