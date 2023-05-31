package semestral_derole.MODEL;

import semestral_derole.POJO.UsuariosPOJO;

public class UsuariosMODEL {
    public void validar_cadastro(UsuariosPOJO usuariopojo) throws IllegalArgumentException{
        if(usuariopojo.getEmail().equals("")||usuariopojo.getEmail().equals(null)){
            throw new IllegalArgumentException("E-mail");
        }
        if(usuariopojo.getNome().equals("")||usuariopojo.getNome().equals(null)){
            throw new IllegalArgumentException("Nome"); 
        }
        if(usuariopojo.getSenha().equals("")||usuariopojo.getSenha().equals(null)){
            throw new IllegalArgumentException("Senha");
        }
    }
    
    public void validar_cadastro_simples(UsuariosPOJO usuariopojo) throws IllegalArgumentException{
        if(usuariopojo.getEmail().equals("")||usuariopojo.getEmail().equals(null)){
            throw new IllegalArgumentException("E-mail");
        }
        if(usuariopojo.getNome().equals("")||usuariopojo.getNome().equals(null)){
            throw new IllegalArgumentException("Nome"); 
        }
    }
    
}
