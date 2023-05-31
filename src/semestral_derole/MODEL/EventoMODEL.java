package semestral_derole.MODEL;

import semestral_derole.POJO.EventosPOJO;

public class EventoMODEL {
    public void validar_evento (EventosPOJO eventospojo) throws IllegalArgumentException{
        if(eventospojo.getTitulo().equals("")||eventospojo.getTitulo().equals(null)){
            throw new IllegalArgumentException("Título");
        }
        if(eventospojo.getDescricao().equals("")||eventospojo.getDescricao().equals(null)){
            throw new IllegalArgumentException("Descrição");
        }
        if(eventospojo.getData().equals("")||eventospojo.getData().equals(null)){
            throw new IllegalArgumentException("Data");
        }
        if(eventospojo.getAdulto()==0){
            throw new IllegalArgumentException("Restrição de idade");
        }
    } 
}
