package com.tododeportes.tododeportesapp.pojo;

/**
 * Created by hernan on 4/06/16.
 */
public class Usuario {
    private String idUsuario;
    private String login;
    private String contraseña;
    private Persona persona;
    private int estado;
    private Rol rol;

    public Usuario() {
    }

    public Usuario(String idUsuario, String login, String contraseña, Persona persona, int estado, Rol rol) {
        this.idUsuario = idUsuario;
        this.login = login;
        this.contraseña = contraseña;
        this.persona = persona;
        this.estado = estado;
        this.rol = rol;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
