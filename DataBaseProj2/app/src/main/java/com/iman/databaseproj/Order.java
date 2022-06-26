package com.iman.databaseproj;

public class Order {
    private int clientID;
    private int serviceID;
    private int hours;
    private int numberOfCleaners;
    private float cost;
    private String orderDate;
    private String title;


    public Order(int clientID, int serviceID, int hours, int numberOfCleaners, float cost, String orderDate, String title) {
        this.clientID = clientID;
        this.serviceID = serviceID;
        this.hours = hours;
        this.numberOfCleaners = numberOfCleaners;
        this.cost = cost;
        this.orderDate = orderDate;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getNumberOfCleaners() {
        return numberOfCleaners;
    }

    public void setNumberOfCleaners(int numberOfCleaners) {
        this.numberOfCleaners = numberOfCleaners;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}
