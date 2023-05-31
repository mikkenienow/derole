package semestral_derole.MODEL;

import semestral_derole.POJO.EventosItensPOJO;

public class EventosItensMODEL {
    public void validar_EventoItem (EventosItensPOJO eventoitem) throws IllegalArgumentException{
        if(eventoitem.getId_Evento()==0){
            throw new IllegalArgumentException("ID do evento");
        }
        if(eventoitem.getId_Item()==0){
            throw new IllegalArgumentException("ID do item");
        }
    }
}
