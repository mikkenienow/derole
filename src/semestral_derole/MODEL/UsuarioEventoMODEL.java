package semestral_derole.MODEL;

import semestral_derole.POJO.UsuariosEventosPOJO;

public class UsuarioEventoMODEL {
    public void validar_UsuarioEvento (UsuariosEventosPOJO usuarioevento) throws IllegalArgumentException{
        if(usuarioevento.getId_Evento()==0){
            throw new IllegalArgumentException("ID do evento");
        }
        if(usuarioevento.getId_Usuario()==0){
            throw new IllegalArgumentException("ID do usuário");
        }
    }
}
