package semestral_derole.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import semestral_derole.POJO.UsuariosBuscaPOJO;
import semestral_derole.POJO.UsuariosEventosPOJO;
import semestral_derole.POJO.UsuariosPOJO;

public class UsuariosBuscaDAO {
    public static ConPooling cn = ConPooling.getInstance();
    
    public int inserirUsuarios(UsuariosBuscaPOJO usuariopojo) {
        int op=0;
        Connection con = cn.getConnection();
        String sql="insert into usuarios (email,nome,CPF,celular,datanascimento,genero,senha,senha2,datadecadastro,nivel) values(?,?,?,?,?,?,?,aes_encrypt(?,'derole123'),?,?);";
        try {
            PreparedStatement p = con.prepareStatement(sql);
            p.setString(1, usuariopojo.getEmail());
            p.setString(2, usuariopojo.getNome());
            p.setString(3, usuariopojo.getCPF());
            p.setString(4, usuariopojo.getCelular());
            p.setString(5, usuariopojo.getDatadenascimento());            
            p.setString(6, usuariopojo.getGenero());
            p.setString(7, usuariopojo.getSenhavelha());
            p.setString(8, usuariopojo.getSenha());
            p.setString(9, usuariopojo.getDatadecadastro());
            p.setInt(10, usuariopojo.getNivel());
            op=p.executeUpdate();
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro 01 -Cadastro Usuario - "+e);
        }
        return op;
    }
    
    public List BuscarUsuariosEventos(int busca){
    UsuariosEventosPOJO usuarioseventospojo = new UsuariosEventosPOJO();
    System.out.println("Buscando");
    String sql= "select*from usuarioseventos where id_evento="+busca+" and status_convite<=2;";

    List lista = new ArrayList();
    Connection con = cn.getConnection();
    try {
        PreparedStatement p = con.prepareStatement(sql);
        ResultSet rs=p.executeQuery();
        while(rs.next()){
            usuarioseventospojo.setId_Usuario(rs.getInt("id_usuario"));
            usuarioseventospojo.setId_Evento(rs.getInt("id_evento"));
            usuarioseventospojo.setId_UsuarioEvento(rs.getInt("id_usuarioevento"));
            usuarioseventospojo.setStatus_convite(rs.getInt("status_convite"));
            usuarioseventospojo.setPago(rs.getBoolean("pago"));
            int usuario = usuarioseventospojo.getId_Usuario();
            int status_convite = usuarioseventospojo.getStatus_convite();
            String sql2= "select*from usuarios where id_usuario="+usuario+";";
            try {
                PreparedStatement p2 = con.prepareStatement(sql2);
                ResultSet rs2=p2.executeQuery();
                while(rs2.next()){
                    UsuariosBuscaPOJO usuariosbuscapojo = new UsuariosBuscaPOJO();
                    usuariosbuscapojo.setId_Usuario(rs2.getInt("id_usuario"));
                    usuariosbuscapojo.setNome(rs2.getString("nome"));
                    usuariosbuscapojo.setStatus_convite(status_convite);
                    lista.add(usuariosbuscapojo);
                }  
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, e.getMessage());
    }
     cn.retConnection(con);
     return lista;
}
    
    public int inserirUsuariosConvidados(UsuariosBuscaPOJO usuariopojo) {
        int op=0;
        Connection con = cn.getConnection();
        String sql="insert into usuarios (email,nome,senha,senha2,datadecadastro,nivel) values(?,?,?,?,?,?);";
        try {
            PreparedStatement p = con.prepareStatement(sql);
            p.setString(1, usuariopojo.getEmail());
            p.setString(2, usuariopojo.getNome());
            p.setString(3, usuariopojo.getSenha());
            p.setString(4, usuariopojo.getSenhavelha());
            p.setString(5, usuariopojo.getDatadecadastro());
            p.setInt(6, usuariopojo.getNivel());
            op=p.executeUpdate();
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro 01 -Covnidar Usuario - "+e);
        }
        return op;
    }
    
    public List BuscarUsuarios(String termo){
        List lista = new ArrayList();
        String sql="select*from usuarios where nome like '%"+termo+"%';";
        Connection con = cn.getConnection();
        try {
            PreparedStatement p = con.prepareStatement(sql);
            ResultSet rs=p.executeQuery();
            while(rs.next()){
                UsuariosPOJO usuariospojo = new UsuariosPOJO();
                usuariospojo.setId_Usuario(rs.getInt("id_usuario"));
                usuariospojo.setNome(rs.getString("nome"));
                usuariospojo.setDatadenascimento(rs.getString("nascimento"));
                usuariospojo.setEmail(rs.getString("email"));
                usuariospojo.setCelular(rs.getString("celular"));
                usuariospojo.setSenha(rs.getString("senha"));
                usuariospojo.setCPF(rs.getString("cpf"));
                usuariospojo.setGenero(rs.getString("genero"));
                usuariospojo.setDatadecadastro(rs.getString("datadecadastro"));
                lista.add(usuariospojo);
             }  
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
         cn.retConnection(con);
         return lista;
        }
    
    public UsuariosBuscaPOJO BuscarUsuarioUnico(int codigo){
        UsuariosBuscaPOJO usuariosbuscapojo = new UsuariosBuscaPOJO();
        String sql="select*from usuarios where id_usuario="+codigo;
        Connection con = cn.getConnection();
        try {
            PreparedStatement p = con.prepareStatement(sql);
            ResultSet rs=p.executeQuery();
            while(rs.next()){
                //UsuarioPOJO usuariopojo = new UsuarioPOJO();
                usuariosbuscapojo.setId_Usuario(rs.getInt("id_usuario"));
                usuariosbuscapojo.setNome(rs.getString("nome"));
                usuariosbuscapojo.setDatadenascimento(rs.getString("datanascimento"));
                usuariosbuscapojo.setEmail(rs.getString("email"));
                usuariosbuscapojo.setCelular(rs.getString("celular"));
                usuariosbuscapojo.setSenha(rs.getString("senha"));
                usuariosbuscapojo.setCPF(rs.getString("cpf"));
                usuariosbuscapojo.setGenero(rs.getString("genero"));
                usuariosbuscapojo.setDatadecadastro(rs.getString("datadecadastro"));
                //lista.add(usuariopojo);
            }
       
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        cn.retConnection(con);
        return usuariosbuscapojo;
        }    
    
    public UsuariosBuscaPOJO VerificarDuplicidade(String campo, String valor){
        UsuariosBuscaPOJO usuariosbuscapojo = new UsuariosBuscaPOJO();
        String sql="";
        if(campo.equals("email")){
            sql="select id_usuario, nivel, senha, nome from usuarios where email='"+valor+"';";
        }
        if(campo.equals("celular")){
            sql="select id_usuario, nivel, senha, nome from usuarios where celular='"+valor+"';";
        }
        Connection con = cn.getConnection();
        try {
            PreparedStatement p = con.prepareStatement(sql);
            ResultSet rs=p.executeQuery();
            while(rs.next()){
                usuariosbuscapojo.setId_Usuario(rs.getInt("id_usuario"));
                usuariosbuscapojo.setNivel(rs.getInt("nivel"));
                usuariosbuscapojo.setSenha(rs.getString("senha"));
                usuariosbuscapojo.setNome(rs.getString("nome"));
            }
       
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        cn.retConnection(con);
        return usuariosbuscapojo;
        }    
    
    public UsuariosPOJO VerificarSenha(String usuario, String senha){
        UsuariosPOJO usuariospojo = new UsuariosPOJO();
                  //select id_usuario, nivel, senha from usuarios where (email='rodrigo@derole.com.br' or celular='') and senha='12355';
        String sql="select id_usuario, nivel from usuarios where (email='"+usuario+"' or celular='"+usuario+"') and senha2=aes_encrypt('"+senha+"','derole123');";

        Connection con = cn.getConnection();
        try {
            PreparedStatement p = con.prepareStatement(sql);
            ResultSet rs=p.executeQuery();
            while(rs.next()){
                usuariospojo.setId_Usuario(rs.getInt("id_usuario"));
                usuariospojo.setNivel(rs.getInt("nivel"));
            }
       
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        cn.retConnection(con);
        return usuariospojo;
        }    
    
    public int Deletar(int codigo){
            int confirma=0;
            String sql="delete from usuarios where id_usuario="+codigo;
            Connection con = cn.getConnection();
            try {
                PreparedStatement p = con. prepareStatement(sql);
                confirma=p.executeUpdate();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
            cn.retConnection(con);
            return confirma;
        }
    
    public int AlterarUsuario(UsuariosPOJO usuariospojo) {
            int op=0;
            Connection con = cn.getConnection();
            String sql="update Evento set nome=?,datanascimento=?,email=?,celular=?,senha=?,cpf=?,genero=?,datadecadastro=? where id_usuario=?;";
            try {
                PreparedStatement p = con.prepareStatement(sql); 
                p.setString(1, usuariospojo.getNome());
                p.setString(2, usuariospojo.getDatadenascimento());
                p.setString(3, usuariospojo.getEmail());
                p.setString(4, usuariospojo.getCelular());
                p.setString(5, usuariospojo.getSenha());
                p.setString(6, usuariospojo.getCPF());
                p.setString(7, usuariospojo.getGenero());
                p.setString(8, usuariospojo.getDatadecadastro());
                p.setInt(9, usuariospojo.getId_Usuario());
                op=p.executeUpdate();
            }catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro 01 -Atualizar Usuario- "+e);
            }
            return op;
        }
    
}
    