package com.akantara.AkantaraHotel.service.ServiceInterface;

import com.akantara.AkantaraHotel.dto.Response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface RoomServiceInterface {

    Response addNewRoom(String roomType, BigDecimal roomPrice, String description);

    List<String> getAllRoomTypes();

    Response getAllRooms();

    Response deleteRoom(Long roomId);

    Response updateRoom(Long roomId, String description, String roomType, BigDecimal roomPrice);

    Response getRoomById(Long roomId);

    Response getAvailableRoomsByDataAndType(LocalDate checkInDate, LocalDate checkOutDate, String roomType);

}
