package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HotelManagementRepository {
    Map<String,Hotel> hotelDB=new HashMap<>();
    Map<Integer,User> userDB=new HashMap<>();
    Map<String,Booking> bookinDB=new HashMap<>();
    public String addHotel(Hotel hotel) {
        if(hotelDB.size()==0){
            return "FAILURE";
        }
        for(String name:hotelDB.keySet()){
            if(name.equals(hotel.getHotelName())){
                return "FAILURE";
            }
        }
        hotelDB.put(hotel.getHotelName(),hotel);
        return "successfully adding the hotel to the hotelDb";
    }

    public Integer addUser(User user) {
        userDB.put(user.getaadharCardNo(),user);
        return user.getaadharCardNo();
    }

    public String getHotelWithMostFacilities() {
        int maxcount=0;
        String ans="";
        for (Map.Entry<String, Hotel> entry : hotelDB.entrySet()) {
            String key = entry.getKey();
            Hotel value = entry.getValue();
            int facitilycount=value.getFacilities().size();
            if(maxcount<facitilycount){
                maxcount=facitilycount;
                ans=key;
            }
        }
        if(maxcount==0){
            return ans;
        }
        return ans;
    }

    public int bookARoom(Booking booking) {
        String hotelName=booking.getHotelName();
        for (Map.Entry<String, Hotel> entry : hotelDB.entrySet()) {
            String key = entry.getKey();
            Hotel value = entry.getValue();
            if(hotelName.equals(key)){
                int availableRooms=value.getAvailableRooms();
                int hotelPerRoomPrice=value.getPricePerNight();
                int personWantBookedRoomNo=booking.getNoOfRooms();
                if(personWantBookedRoomNo<=availableRooms){
                    availableRooms-=personWantBookedRoomNo;
                    value.setAvailableRooms(availableRooms);
                    int totalamoutatlast=personWantBookedRoomNo*hotelPerRoomPrice;
                    booking.setAmountToBePaid(totalamoutatlast);
                    bookinDB.put(booking.getBookingId(),booking);
                    return totalamoutatlast;
                }
                else{
                    break;
                }
            }
        }
        return -1;
    }

    public int getBookings(Integer aadharCard) {
        int count=0;
        for (Map.Entry<String, Booking> entry :bookinDB.entrySet()) {
            Booking booking= entry.getValue();
            int bookingadhaarCard=booking.getBookingAadharCard();
            if(bookingadhaarCard==aadharCard){
                count++;
            }
        }
        return count;
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        Hotel hotel=hotelDB.get(hotelName);
        hotel.setFacilities(newFacilities);
        return hotel;
    }
}
