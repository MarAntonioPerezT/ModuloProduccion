package com.springApplication.moduloProduccion.util.exceptions;

public class ProveedoresNotFoundException extends Exception{

    public ProveedoresNotFoundException(){

    }

    public ProveedoresNotFoundException(String mensaje){
        super(mensaje);
    }

}
