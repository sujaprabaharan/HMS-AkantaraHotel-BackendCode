package com.akantara.AkantaraHotel.repository;

import com.akantara.AkantaraHotel.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// BookingRepository is an interface that acts as a data access layer for the Booking entity
// It doesn’t need an implementation class — Spring Data JPA automatically generates the implementation at runtime.
public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Finds a booking by the booking confirmation code and returns it as an Optional
    Optional<Booking> findByBookingConfirmationCode(String confirmationCode);
}
