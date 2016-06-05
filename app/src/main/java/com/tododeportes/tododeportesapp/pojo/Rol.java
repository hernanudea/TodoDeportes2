package com.tododeportes.tododeportesapp.pojo;

/**
 * Created by hernan on 4/06/16.
 */
public class Rol {
    private int idNombreRol;
    private String nombreRol;

    public Rol(int idNombreRol, String nombreRol) {
        this.idNombreRol = idNombreRol;
        this.nombreRol = nombreRol;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public int getIdNombreRol() {
        return idNombreRol;
    }

    public void setIdNombreRol(int idNombreRol) {
        this.idNombreRol = idNombreRol;
    }
}
