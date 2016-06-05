package com.tododeportes.tododeportesapp.pojo;

/**
 * Created by hernan on 2/06/16.
 */
public class TipoDocumento {
    public int idTipoDocumento;
    public String descripcion;
    public String abreviado;

    public TipoDocumento(int idTipoDocumento, String descripcion, String abreviado) {
        this.idTipoDocumento = idTipoDocumento;
        this.descripcion = descripcion;
        this.abreviado = abreviado;
    }

    public int getIdTipoDocumento() {
        return idTipoDocumento;
    }

    public void setIdTipoDocumento(int idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAbreviado() {
        return abreviado;
    }

    public void setAbreviado(String abreviado) {
        this.abreviado = abreviado;
    }
}
