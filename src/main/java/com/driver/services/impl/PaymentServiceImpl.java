package com.driver.services.impl;

import com.driver.model.Payment;
import com.driver.model.PaymentMode;
import com.driver.model.Reservation;
import com.driver.model.Spot;
import com.driver.repository.PaymentRepository;
import com.driver.repository.ReservationRepository;
import com.driver.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    ReservationRepository reservationRepository2;
    @Autowired
    PaymentRepository paymentRepository2;

    @Override
    public Payment pay(Integer reservationId, int amountSent, String mode) throws Exception {

        Reservation reservation = reservationRepository2.getOne(reservationId);

        Spot spot = reservation.getSpot();

        int bill = spot.getPricePerHour()*reservation.getNumberOfHours();

        if(bill>amountSent){
            throw  new Exception("Insufficient Amount");
        }
        mode = mode.toLowerCase();
        if(!(mode.equals("cash")||mode.equals("card")||mode.equals("upi"))){
            throw new Exception("Payment mode not detected");
        }
        PaymentMode paymentMode = null;
        if(mode.equals("cash"))
            paymentMode=PaymentMode.CASH;
        else if (mode.equals("card"))
            paymentMode=PaymentMode.CARD;
        else paymentMode=PaymentMode.UPI;


        Payment payment = new Payment(paymentMode);
        payment.setPaymentCompleted(true);
        reservation.setPayment(payment);
        payment.setReservation(reservation);
        paymentRepository2.save(payment);
        reservationRepository2.save(reservation);


        return payment;
    }
}
