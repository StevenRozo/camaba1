package com.example.camaba1.camaba1.entidades;

public class Notificaciones {

    private String idMensajeNotif,Mens_id_usu_envia,Mens_bandera_nueva_notificacion;

    public String getMens_bandera_nueva_notificacion() {
        return Mens_bandera_nueva_notificacion;
    }

    public void setMens_bandera_nueva_notificacion(String mens_bandera_nueva_notificacion) {
        Mens_bandera_nueva_notificacion = mens_bandera_nueva_notificacion;
    }

    public String getIdMensajeNotif() {
        return idMensajeNotif;
    }

    public void setIdMensajeNotif(String idMensajeNotif) {
        this.idMensajeNotif = idMensajeNotif;
    }

    public String getMens_id_usu_envia() {
        return Mens_id_usu_envia;
    }

    public void setMens_id_usu_envia(String mens_id_usu_envia) {
        Mens_id_usu_envia = mens_id_usu_envia;
    }
}
