package com.akantara.AkantaraHotel.service.ServiceInterface;

import com.akantara.AkantaraHotel.dto.Response;
import com.akantara.AkantaraHotel.entity.Booking;

public interface BookingServiceInterface {

    Response saveBooking(Long roomId, Long userId, Booking bookingRequest);

    Response findBookingByConfirmationCode(String confirmationCode);

    Response getAllBookings();

    Response deleteBooking(Long bookingId);
}

