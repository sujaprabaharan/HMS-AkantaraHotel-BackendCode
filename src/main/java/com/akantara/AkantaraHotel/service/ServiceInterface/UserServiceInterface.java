package com.akantara.AkantaraHotel.service.ServiceInterface;

import com.akantara.AkantaraHotel.dto.LoginRequest;
import com.akantara.AkantaraHotel.dto.Response;
import com.akantara.AkantaraHotel.entity.User;

public interface UserServiceInterface {

    Response register(User user);

    Response login(LoginRequest loginRequest);

    Response getUserBookingHistory(String userId);

    Response getMyInfo(String email);
}
