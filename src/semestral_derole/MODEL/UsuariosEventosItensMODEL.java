package semestral_derole.MODEL;

import semestral_derole.POJO.UsuariosEventosItensPOJO;

public class UsuariosEventosItensMODEL {
    public void validar_EventoItem (UsuariosEventosItensPOJO usuarioeventoitem) throws IllegalArgumentException{
        if(usuarioeventoitem.getId_Evento()==0){
            throw new IllegalArgumentException("ID do evento");
        }
        if(usuarioeventoitem.getId_Item()==0){
            throw new IllegalArgumentException("ID do item");
        }
        if(usuarioeventoitem.getId_Usuario()==0){
            throw new IllegalArgumentException("ID do usuário");
        }        
    }
}
