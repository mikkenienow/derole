package semestral_derole.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import semestral_derole.POJO.UsuariosEventosItensPOJO;

public class UsuariosEventosItensDAO {
    public static ConPooling cn = ConPooling.getInstance();
        public int inserirUsuarioEventoItem(UsuariosEventosItensPOJO usuario) {
        int op=0;
        Connection con = cn.getConnection();
        String sql="insert into UsuarioEventoItem(IdUsuario,IdEvento,IdItem) values(?,?,?);";
        try {
            PreparedStatement p = con.prepareStatement(sql);
            p.setInt(1, usuario.getId_Usuario());
            p.setInt(2, usuario.getId_Evento());
            p.setInt(3, usuario.getId_Item());
            op=p.executeUpdate();
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro 01 -Cadastro Usuario Eventos Itens - "+e);
        }
        return op;
}  
        
        public List BuscarUsuariosEventosItens(String evento, String item){
            List lista = new ArrayList();
            String sql="select*from usuarioseventositens where id_evento like '%"+evento+"%' and id_item like '%"+item+"%';";
            Connection con = cn.getConnection();
            try {
                PreparedStatement p = con.prepareStatement(sql);
                ResultSet rs=p.executeQuery();
                while(rs.next()){
                    UsuariosEventosItensPOJO usuarioseventositenspojo = new UsuariosEventosItensPOJO();
                    usuarioseventositenspojo.setId_UsuarioEventoItem(rs.getInt("id_usuarioeventoitem"));
                    usuarioseventositenspojo.setId_Evento(rs.getInt("id_evento"));
                    usuarioseventositenspojo.setId_Usuario(rs.getInt("id_usuario"));
                    usuarioseventositenspojo.setId_Item(rs.getInt("id_item"));
                    lista.add(usuarioseventositenspojo);
                }  
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
            cn.retConnection(con);
            return lista;
            }    
        
        public UsuariosEventosItensPOJO BuscarUsuarioEventoItemUnico(int codigo){
            UsuariosEventosItensPOJO usuarioseventositenspojo = new UsuariosEventosItensPOJO();
            String sql="select*from eventos where id_usuarioeventoitem="+codigo;
            Connection con = cn.getConnection();
            try {
                PreparedStatement p = con.prepareStatement(sql);
                ResultSet rs=p.executeQuery();
                while(rs.next()){
                    //UsuarioPOJO usuariopojo = new UsuarioPOJO();
                    usuarioseventositenspojo.setId_UsuarioEventoItem(rs.getInt("id_usuarioeventoitem"));
                    usuarioseventositenspojo.setId_Evento(rs.getInt("id_evento"));
                    usuarioseventositenspojo.setId_Usuario(rs.getInt("id_usuario"));
                    usuarioseventositenspojo.setId_Item(rs.getInt("id_item"));
                    //lista.add(usuariopojo);
                }

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
            cn.retConnection(con);
            return usuarioseventositenspojo;
        }
        
        public int Deletar(int codigo){
            int confirma=0;
            String sql="delete from usarioseventositens where id_usuarioeventoitem="+codigo;
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
        
        public int AlterarUsuarioEventoItem(UsuariosEventosItensPOJO usuarioseventositenspojo) {
            int op=0;
            Connection con = cn.getConnection();
            String sql="update Evento set id_usuario=?,id_evento=?,id_item=? where id_usuarioevento=?;";
            try {
                PreparedStatement p = con.prepareStatement(sql); 
                p.setInt(1, usuarioseventositenspojo.getId_Usuario());
                p.setInt(2, usuarioseventositenspojo.getId_Evento());
                p.setInt(3, usuarioseventositenspojo.getId_Item());
                p.setInt(4, usuarioseventositenspojo.getId_UsuarioEventoItem());
                op=p.executeUpdate();
            }catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro 01 - Atualizar Usuario Evento Item - "+e);
            }
            return op;
        }
}
