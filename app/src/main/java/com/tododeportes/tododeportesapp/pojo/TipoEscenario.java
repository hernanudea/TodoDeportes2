package com.tododeportes.tododeportesapp.pojo;

/**
 * Created by Giovani Cardona on 31/05/16.
 */
public class TipoEscenario {
    private int id;
    private String descripcion;

    public TipoEscenario(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
