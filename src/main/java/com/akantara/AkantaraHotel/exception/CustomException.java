package com.akantara.AkantaraHotel.exception;



// Custom exception class that extends RuntimeException to handle application-specific errors
public class CustomException extends RuntimeException{

    // Constructor that accepts a custom error message and passes it to the superclass
    public CustomException(String message){
        super(message);
    }
}
