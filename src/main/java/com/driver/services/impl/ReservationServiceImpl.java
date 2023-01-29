package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.ReservationRepository;
import com.driver.repository.SpotRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    UserRepository userRepository3;
    @Autowired
    SpotRepository spotRepository3;
    @Autowired
    ReservationRepository reservationRepository3;
    @Autowired
    ParkingLotRepository parkingLotRepository3;
    @Override
    public Reservation reserveSpot(Integer userId, Integer parkingLotId, Integer timeInHours, Integer numberOfWheels) throws Exception {

        try{
        Reservation reservation = new Reservation(timeInHours);
        User user = userRepository3.getOne(userId);
        ParkingLot parkingLot = parkingLotRepository3.getOne(parkingLotId);
        Spot spot = null;
        List<Spot> spotList = parkingLot.getSpotList();
        int min  = Integer.MAX_VALUE;
        for(Spot spot1:spotList){
            if(spot1.getPricePerHour()<min)
            {
                if(spot1.getOccupied()==false) {
                    if (numberOfWheels > 4 && spot.getSpotType().equals(SpotType.OTHERS)) {
                        spot = spot1;
                    } else if (numberOfWheels > 2 && numberOfWheels <= 4 && (spot.getSpotType().equals(SpotType.OTHERS) || spot.getSpotType().equals(SpotType.FOUR_WHEELER))) {
                        spot = spot1;
                    } else if (numberOfWheels <= 2) {
                        spot = spot1;
                    }
                }
            }

        }

        if (spot==null)
            throw new Exception("Cannot make reservation");

        List<Reservation> reservationList = spot.getReservationList();

        reservationList.add(reservation);
        reservation.setSpot(spot);

        reservation.setUser(user);
        List<Reservation> reservationList1 = user.getReservationList();
        reservationList1.add(reservation);
        spot.setOccupied(true);
        spot.setReservationList(reservationList);
        user.setReservationList(reservationList1);
        spotRepository3.save(spot);
        userRepository3.save(user);

        return reservation;
    }catch (Exception e){
        return  null;
    }
    }
}
