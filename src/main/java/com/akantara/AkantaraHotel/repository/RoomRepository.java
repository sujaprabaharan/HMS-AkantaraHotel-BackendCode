package com.akantara.AkantaraHotel.repository;

import com.akantara.AkantaraHotel.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

// RoomRepository is an interface that acts as a data access layer for the Room entity
// It doesn’t need an implementation class — Spring Data JPA automatically generates the implementation at runtime.
public interface RoomRepository extends JpaRepository<Room, Long> {

    // For the below provided custom Query methods using JPQL

    // Retrieves a list of all distinct room types from the Room entity
    @Query("SELECT DISTINCT r.roomType FROM Room r")
    List<String> findDistinctRoomTypes();

    // Finds available rooms of a specific type that are not booked within the given date range
    @Query("SELECT r FROM Room r WHERE r.roomType LIKE %:roomType% AND r.id NOT IN (SELECT b.room.id FROM Booking b WHERE" +
            "(b.checkInDate <= :checkOutDate) AND (b.checkOutDate >= :checkInDate))")
    List<Room> findAvailableRoomsByDateAndType(LocalDate checkInDate, LocalDate checkOutDate, String roomType);


    // Retrieves all rooms that currently have no bookings associated with them
    @Query("SELECT r FROM Room r WHERE r.id NOT IN (SELECT b.room.id FROM Booking b)")
    List<Room> getAllAvailableRooms();

}
