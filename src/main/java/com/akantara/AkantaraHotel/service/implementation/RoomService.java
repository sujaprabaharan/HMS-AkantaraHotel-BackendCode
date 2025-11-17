package com.akantara.AkantaraHotel.service.implementation;

import com.akantara.AkantaraHotel.dto.Response;
import com.akantara.AkantaraHotel.dto.RoomDTO;
import com.akantara.AkantaraHotel.entity.Room;
import com.akantara.AkantaraHotel.exception.CustomException;
import com.akantara.AkantaraHotel.repository.BookingRepository;
import com.akantara.AkantaraHotel.repository.RoomRepository;
import com.akantara.AkantaraHotel.service.ServiceInterface.RoomServiceInterface;
import com.akantara.AkantaraHotel.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class RoomService implements RoomServiceInterface {


    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private BookingRepository bookingRepository;


    // Adds a new room to the database with given type, price, and description
    @Override
    public Response addNewRoom(String roomType, BigDecimal roomPrice, String description) {
        Response response = new Response();

        try {
            Room room = new Room();
            room.setRoomType(roomType);
            room.setRoomPrice(roomPrice);
            room.setRoomDescription(description);
            Room savedRoom = roomRepository.save(room);
            // Convert entity to DTO for response
            RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTO(savedRoom);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setRoom(roomDTO);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error Occurred: " + e.getMessage());
        }
        return response;
    }

    // Retrieves all distinct room types from the database
    @Override
    public List<String> getAllRoomTypes() {
        return roomRepository.findDistinctRoomTypes();
    }

    // Fetches all rooms sorted by ID (descending order)
    @Override
    public Response getAllRooms() {
        Response response = new Response();

        try {
            List<Room> roomList = roomRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
            List<RoomDTO> roomDTOList = Utils.mapRoomListEntityToRoomListDTO(roomList);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setRooms(roomDTOList);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error Occurred: " + e.getMessage());
        }
        return response;
    }

    // Deletes a specific room by its ID if it exists
    @Override
    public Response deleteRoom(Long roomId) {
        Response response = new Response();

        try {
            roomRepository.findById(roomId).orElseThrow(() -> new CustomException("Room Not Found"));
            roomRepository.deleteById(roomId);
            response.setStatusCode(200);
            response.setMessage("successful");

        } catch (CustomException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error Occurred: " + e.getMessage());
        }
        return response;
    }

    // Updates room details (type, price, description)
    @Override
    public Response updateRoom(Long roomId, String description, String roomType, BigDecimal roomPrice) {
        Response response = new Response();

        try {
            Room room = roomRepository.findById(roomId).orElseThrow(() -> new CustomException("Room Not Found"));
            if (roomType != null) room.setRoomType(roomType);
            if (roomPrice != null) room.setRoomPrice(roomPrice);
            if (description != null) room.setRoomDescription(description);
            Room updatedRoom = roomRepository.save(room);
            RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTO(updatedRoom);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setRoom(roomDTO);

        } catch (CustomException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error Occurred: " + e.getMessage());
        }
        return response;
    }

    // Retrieves room details by ID including its bookings
    @Override
    public Response getRoomById(Long roomId) {
        Response response = new Response();

        try {
            Room room = roomRepository.findById(roomId).orElseThrow(() -> new CustomException("Room Not Found"));
            RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTOPlusBookings(room);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setRoom(roomDTO);

        } catch (CustomException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error Occurred: " + e.getMessage());
        }
        return response;
    }

    // Fetches available rooms based on check-in, check-out dates, and room type
    @Override
    public Response getAvailableRoomsByDataAndType(LocalDate checkInDate, LocalDate checkOutDate, String roomType) {
        Response response = new Response();

        try {
            List<Room> availableRooms = roomRepository.findAvailableRoomsByDateAndType(checkInDate, checkOutDate, roomType);
            List<RoomDTO> roomDTOList = Utils.mapRoomListEntityToRoomListDTO(availableRooms);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setRooms(roomDTOList);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error Occurred: " + e.getMessage());
        }
        return response;
    }

}
