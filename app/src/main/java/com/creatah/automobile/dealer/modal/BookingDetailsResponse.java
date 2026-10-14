package com.creatah.automobile.dealer.modal;

import com.google.gson.annotations.SerializedName;

/**
 * Created by creatah1 on 03-05-2017.
 */

public class BookingDetailsResponse extends BaseResponse {

    @SerializedName("booking_details")
    private DealerBooking booking;

    public DealerBooking getBooking() {
        return booking;
    }

    public void setBooking(DealerBooking booking) {
        this.booking = booking;
    }
}
