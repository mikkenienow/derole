package semestral_derole.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import semestral_derole.POJO.UsuariosPOJO;

public class UsuariosDAO {
    public static ConPooling cn = ConPooling.getInstance();
    
    public int inserirUsuarios(UsuariosPOJO usuariopojo) {
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
    
    public UsuariosPOJO BuscarUsuarioUnico(int codigo){
        UsuariosPOJO usuariospojo = new UsuariosPOJO();
        String sql="select*from usuarios where id_usuario="+codigo;
        Connection con = cn.getConnection();
        try {
            PreparedStatement p = con.prepareStatement(sql);
            ResultSet rs=p.executeQuery();
            while(rs.next()){
                //UsuarioPOJO usuariopojo = new UsuarioPOJO();
                usuariospojo.setId_Usuario(rs.getInt("id_usuario"));
                usuariospojo.setNome(rs.getString("nome"));
                usuariospojo.setDatadenascimento(rs.getString("datanascimento"));
                usuariospojo.setEmail(rs.getString("email"));
                usuariospojo.setCelular(rs.getString("celular"));
                usuariospojo.setSenha(rs.getString("senha"));
                usuariospojo.setCPF(rs.getString("cpf"));
                usuariospojo.setGenero(rs.getString("genero"));
                usuariospojo.setDatadecadastro(rs.getString("datadecadastro"));
                //lista.add(usuariopojo);
            }
       
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        cn.retConnection(con);
        return usuariospojo;
        }    
    
    public UsuariosPOJO VerificarDuplicidade(String campo, String valor){
        UsuariosPOJO usuariospojo = new UsuariosPOJO();
        String sql="";
        if(campo.equals("email")){
            sql="select id_usuario, nivel, senha from usuarios where email='"+valor+"';";
        }
        if(campo.equals("celular")){
            sql="select id_usuario, nivel, senha from usuarios where celular='"+valor+"';";
        }
        Connection con = cn.getConnection();
        try {
            PreparedStatement p = con.prepareStatement(sql);
            ResultSet rs=p.executeQuery();
            while(rs.next()){
                usuariospojo.setId_Usuario(rs.getInt("id_usuario"));
                usuariospojo.setNivel(rs.getInt("nivel"));
                usuariospojo.setSenha(rs.getString("senha"));
            }
       
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        cn.retConnection(con);
        return usuariospojo;
        }    
    
    public UsuariosPOJO VerificarSenha(String usuario, String senha){
        UsuariosPOJO usuariospojo = new UsuariosPOJO();
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
    