package semestral_derole.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import semestral_derole.POJO.EventosItensPOJO;

/**
 *
 * @author CEDUP11
 */
public class EventosItemsDAO {
        public static ConPooling cn = ConPooling.getInstance();
        public int inserirEventoItem(EventosItensPOJO usuario) { //Id_EventoItem,IdEvento=0, IdItem=0;
            int op=0;
            Connection con = cn.getConnection();
            String sql="insert into eventositens(Id_Evento,Id_Item) values(?,?);";
            try {
                PreparedStatement p = con.prepareStatement(sql);
                p.setInt(1, usuario.getId_Evento());
                p.setInt(2, usuario.getId_Item());
                op=p.executeUpdate();
            }catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro 01 -Cadastro Evento Item - "+e);
            }
        return op;
}
            
        public List BuscarEventosItens(String termo){
         List lista = new ArrayList();
         String sql="select*from eventositens where id_evento like '%"+termo+"%';";
         Connection con = cn.getConnection();
         try {
            PreparedStatement p = con.prepareStatement(sql);
            ResultSet rs=p.executeQuery();
            while(rs.next()){
                EventosItensPOJO eventoitenspojo = new EventosItensPOJO();
                eventoitenspojo.setId_EventoItem(rs.getInt("id_eventoitem"));
                eventoitenspojo.setId_Evento(rs.getInt("id_evento"));
                eventoitenspojo.setId_Item(rs.getInt("id_item"));
                lista.add(eventoitenspojo);
             }  
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
         cn.retConnection(con);
         return lista;
        }
        
        public EventosItensPOJO BuscarEventoItemUnico(int codigo){
            EventosItensPOJO eventositenspojo = new EventosItensPOJO();
            String sql="select*from eventositens where id_eventoitem="+codigo;
            Connection con = cn.getConnection();
            try {
                PreparedStatement p = con.prepareStatement(sql);
                ResultSet rs=p.executeQuery();
                while(rs.next()){
                    //UsuarioPOJO usuariopojo = new UsuarioPOJO();
                    eventositenspojo.setId_EventoItem(rs.getInt("id_eventoitem"));
                    eventositenspojo.setId_Evento(rs.getInt("id_evento"));
                    eventositenspojo.setId_Item(rs.getInt("id_item"));
                    //lista.add(usuariopojo);
                }

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
            cn.retConnection(con);
            return eventositenspojo;
            }           

        public int Deletar(int codigo){
            int confirma=0;
            String sql="delete from eventositens where id_eventoitem="+codigo;
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

        public int AlterarEventoItem(EventosItensPOJO eventoitempojo) { //String titulo,descricao,local,data,horario,adulto,host;
            int op=0;
            Connection con = cn.getConnection();
            String sql="update Evento set titulo=?,descricao=?,local=?,data=?,horario=?,adulto=?,host=? where id_evento=?;";
            try {//Id_EventoItem,Id_Evento=0, Id_Item=0;
                PreparedStatement p = con.prepareStatement(sql); 
                p.setInt(1, eventoitempojo.getId_Item());
                p.setInt(2, eventoitempojo.getId_Evento());
                p.setInt(3, eventoitempojo.getId_EventoItem());
                p.setInt(4, eventoitempojo.getId_Evento());
                op=p.executeUpdate();
            }catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro 01 -Atualizar Evento Item- "+e);
            }
            return op;
        }
}
