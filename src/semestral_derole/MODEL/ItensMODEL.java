package semestral_derole.MODEL;

import semestral_derole.POJO.ItensPOJO;

public class ItensMODEL {
        public void validar_itens (ItensPOJO itenspojo) throws IllegalArgumentException{
        if(itenspojo.getTitulo().equals("")||itenspojo.getTitulo().equals(null)){
            throw new IllegalArgumentException("Título do item");
        }
        if(itenspojo.getValoritem()==0){
            throw new IllegalArgumentException("Valor");
        }
                
    }
}
