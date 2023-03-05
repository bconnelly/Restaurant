package com.fullstack.restaurantservice.Utilities;

public class EntityNotFoundException extends Exception{
    public EntityNotFoundException(String message){
        super(message);
    }

    public EntityNotFoundException(){}
}
