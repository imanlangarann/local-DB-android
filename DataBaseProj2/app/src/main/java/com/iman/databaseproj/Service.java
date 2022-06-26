package com.iman.databaseproj;

class Service {
    private String title;
    private double wage;
    private int id;

    public Service(int id , String title, double wage) {
        this.title = title;
        this.id = id;
        this.wage = wage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getWage() {
        return wage;
    }

    public void setWage(double wage) {
        this.wage = wage;
    }
}
