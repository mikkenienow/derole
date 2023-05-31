package semestral_derole.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import semestral_derole.POJO.UsuariosEventosPOJO;

public class UsuariosEventosDAO {
        public static ConPooling cn = ConPooling.getInstance();
            public int inserirUsuarioEvento(UsuariosEventosPOJO usuario) { 
            int op=0;
            Connection con = cn.getConnection();
            String sql="insert into usuarioseventos(id_usuario,id_evento,pago,status_convite) values(?,?,?,?);";
            try {
                PreparedStatement p = con.prepareStatement(sql);
                p.setInt(1, usuario.getId_Usuario());
                p.setInt(2, usuario.getId_Evento());
                p.setBoolean(3, usuario.isPago());
                p.setInt(4, usuario.getStatus_convite());
                op=p.executeUpdate();
            }catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro 01 -Cadastro Usuario Evento - "+e);
            }
            return op;
}
            
            public List BuscarUsuariosEventos(String termo){
            List lista = new ArrayList();
            String sql="select*from usuarioseventos where id_usuario like '%"+termo+"%';";
            Connection con = cn.getConnection();
            try {
                PreparedStatement p = con.prepareStatement(sql);
                ResultSet rs=p.executeQuery();
                while(rs.next()){
                    UsuariosEventosPOJO usuarioseventospojo = new UsuariosEventosPOJO();
                    usuarioseventospojo.setId_UsuarioEvento(rs.getInt("id_usuarioevento"));
                    usuarioseventospojo.setId_Evento(rs.getInt("id_evento"));
                    usuarioseventospojo.setId_Usuario(rs.getInt("id_usuario"));
                    lista.add(usuarioseventospojo);
                }  
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
            cn.retConnection(con);
            return lista;
            }
            
            public UsuariosEventosPOJO BuscarUsuarioEventoUnico(int codigo){
                UsuariosEventosPOJO usuarioseventospojo = new UsuariosEventosPOJO();
                String sql="select*from usuarioseventos where id_usuarioevento="+codigo;
                Connection con = cn.getConnection();
                try {
                    PreparedStatement p = con.prepareStatement(sql);
                    ResultSet rs=p.executeQuery();
                    while(rs.next()){
                        usuarioseventospojo.setId_UsuarioEvento(rs.getInt("id_usuarioevento"));
                        usuarioseventospojo.setId_Evento(rs.getInt("id_evento"));
                        usuarioseventospojo.setId_Usuario(rs.getInt("id_usuario"));
                    }

                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
                cn.retConnection(con);
                return usuarioseventospojo;
            }
            
            public int Deletar(int codigo){
                int confirma=0;
                String sql="delete from usuarioseventos where id_usuarioevento="+codigo;
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
            
            public int AlterarUsuarioEvento(UsuariosEventosPOJO usuarioseventospojo) {
            int op=0;
            Connection con = cn.getConnection();
            String sql="update Evento set id_usuario=?,id_evento=? where id_usuarioevento=?;";
            try {
                PreparedStatement p = con.prepareStatement(sql); 
                p.setInt(1, usuarioseventospojo.getId_Usuario());
                p.setInt(2, usuarioseventospojo.getId_Evento());
                p.setInt(3, usuarioseventospojo.getId_UsuarioEvento());
                op=p.executeUpdate();
            }catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro 01 -Atualizar Usuario Evento- "+e);
            }
            return op;
        }
}
