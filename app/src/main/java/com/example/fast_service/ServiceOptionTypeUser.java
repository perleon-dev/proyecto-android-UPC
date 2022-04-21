package com.example.fast_service;

public class ServiceOptionTypeUser {

    public Integer Id;
    public String Descripcion;

    public ServiceOptionTypeUser(Integer id, String descripcion){
        Id = id;
        Descripcion = descripcion;
    }

    @Override
    public String toString(){
        return Descripcion;
    }

}
