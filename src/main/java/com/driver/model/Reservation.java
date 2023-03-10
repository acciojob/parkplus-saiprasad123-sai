package com.driver.model;

import javax.persistence.*;

@Entity
@Table
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int NumberOfHours;

    @ManyToOne
    @JoinColumn
    private User user;

    @ManyToOne
    @JoinColumn
    private Spot spot;

    @OneToOne(mappedBy = "reservation",cascade = CascadeType.ALL)
    private Payment payment;

    public Reservation(int id, int NumberOfHours, User user, Spot spot, Payment payment) {
        this.id = id;
        this.NumberOfHours = NumberOfHours;
        this.user = user;
        this.spot = spot;
        this.payment = payment;
    }

    public Reservation(int noOfHours) {
        this.NumberOfHours = noOfHours;
    }

    public Reservation() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumberOfHours() {
        return NumberOfHours;
    }

    public void setNumberOfHours(int noOfHours) {
        this.NumberOfHours = noOfHours;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Spot getSpot() {
        return spot;
    }

    public void setSpot(Spot spot) {
        this.spot = spot;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
