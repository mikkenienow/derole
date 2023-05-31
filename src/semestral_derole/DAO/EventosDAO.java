package semestral_derole.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import semestral_derole.POJO.EventosPOJO;

public class EventosDAO {
    public static ConPooling cn = ConPooling.getInstance();
        public int inserirEvento(EventosPOJO eventopojo) { //String titulo,descricao,local,data,horario,adulto,host;
            int op=0;
            Connection con = cn.getConnection();
            String sql="insert into eventos(titulo,descricao,local,data,horario,adulto,host,visibilidade,criacao) values(?,?,?,?,?,?,?,?,?);";
            try {
                PreparedStatement p = con.prepareStatement(sql);
                p.setString(1, eventopojo.getTitulo());
                p.setString(2, eventopojo.getDescricao());
                p.setString(3, eventopojo.getLocal());
                p.setString(4, eventopojo.getData());
                p.setString(5, eventopojo.getHorario());            
                p.setInt(6, eventopojo.getAdulto());
                p.setInt(7, eventopojo.getHost());            
                p.setInt(8, eventopojo.getVisibilidade());            
                p.setString(9, eventopojo.getDatacriacao());            
                op=p.executeUpdate();
            }catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro 01 -Cadastro Evento - "+e);
            }
            return op;
        }
        
        public List BuscarEventos(int busca, String modo, String data){
            System.out.println("Buscando");
            String sql="";
            switch (modo){//SELECT * FROM eventos WHERE data < "+data+" and host="+busca+";";
                case "Meus Eventos" : sql="select*from eventos where host="+busca+" and data > '"+data+"';";break;
                case "Comunidade" : sql="select*from eventos where not host="+busca+" and not visibilidade=0 and data > '"+data+"';;";break;
                case "Finalizados" : sql="SELECT * FROM eventos WHERE data < '"+data+"' and host="+busca+";";break;
            }
            List lista = new ArrayList();
            Connection con = cn.getConnection();
            try {
                PreparedStatement p = con.prepareStatement(sql);
                ResultSet rs=p.executeQuery();
                while(rs.next()){
                    EventosPOJO eventospojo = new EventosPOJO();
                    eventospojo.setId_Evento(rs.getInt("id_evento"));
                    eventospojo.setTitulo(rs.getString("titulo"));
                    eventospojo.setDescricao(rs.getString("descricao"));
                    eventospojo.setLocal(rs.getString("local"));
                    eventospojo.setData(rs.getString("data"));
                    eventospojo.setHorario(rs.getString("horario"));
                    eventospojo.setAdulto(rs.getInt("adulto"));
                    eventospojo.setHost(rs.getInt("host"));
                    eventospojo.setHost(rs.getInt("visibilidade"));
                    lista.add(eventospojo);
                }  
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
             cn.retConnection(con);
             return lista;
        }
        public int BuscarUltimoEvento(int usuario){
            int id_evento=0;
            Connection con = cn.getConnection();
            String sql="select max(id_evento) from eventos where host="+usuario+";";
            try {
                PreparedStatement p = con.prepareStatement(sql);
                ResultSet rs=p.executeQuery();
                rs.next();
                id_evento=rs.getInt("max(id_evento)");
            } catch (SQLException e) {
            }
        return id_evento;}
        
        public EventosPOJO BuscarEventoUnico(int codigo){
            EventosPOJO eventospojo = new EventosPOJO();
            String sql="select*from eventos where id_evento="+codigo;
            Connection con = cn.getConnection();
            try {
                PreparedStatement p = con.prepareStatement(sql);
                ResultSet rs=p.executeQuery();
                while(rs.next()){
                    //UsuarioPOJO usuariopojo = new UsuarioPOJO();
                    eventospojo.setId_Evento(rs.getInt("id_evento"));
                    eventospojo.setTitulo(rs.getString("titulo"));
                    eventospojo.setDescricao(rs.getString("descricao"));
                    eventospojo.setLocal(rs.getString("local"));
                    eventospojo.setData(rs.getString("data"));
                    eventospojo.setHorario(rs.getString("horario"));
                    eventospojo.setAdulto(rs.getInt("adulto"));
                    eventospojo.setHost(rs.getInt("host"));
                    eventospojo.setVisibilidade(rs.getInt("visibilidade"));
                    eventospojo.setDatacriacao(rs.getString("criacao"));
                    //lista.add(usuariopojo);
                }

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
            cn.retConnection(con);
            return eventospojo;
            }   

        public int Deletar(int codigo){
            int confirma=0;
            String sql="delete from eventos where id_evento="+codigo;
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
    
        public int AlterarEvento(EventosPOJO eventopojo) { //String titulo,descricao,local,data,horario,adulto,host;
            int op=0;
            Connection con = cn.getConnection();
            String sql="update eventos set titulo=?,descricao=?,local=?,data=?,horario=?,adulto=?,host=?,visibilidade=? where id_evento=?;";
            try {
                PreparedStatement p = con.prepareStatement(sql); 
                p.setString(1, eventopojo.getTitulo());
                p.setString(2, eventopojo.getDescricao());
                p.setString(3, eventopojo.getLocal());
                p.setString(4, eventopojo.getData());
                p.setString(8, eventopojo.getHorario());            
                p.setInt(6, eventopojo.getAdulto());
                p.setInt(7, eventopojo.getHost());
                p.setInt(8, eventopojo.getVisibilidade());
                p.setInt(9, eventopojo.getId_Evento());
                op=p.executeUpdate();
            }catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro 01 -Atualizar Evento - "+e);
            }
            return op;
        }
}