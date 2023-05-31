package semestral_derole.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import semestral_derole.POJO.EventosItensPOJO;
import semestral_derole.POJO.ItensPOJO;

public class ItensDAO {
    public static ConPooling cn = ConPooling.getInstance();
        public int inserirItem(ItensPOJO itenspojo) { 
        int op=0;
        Connection con = cn.getConnection();
        String sql="insert into itens (tituloitem,descricaoitem,valoritem,pagante,criado) values(?,?,?,?,?);";
        try {
            PreparedStatement p = con.prepareStatement(sql);
            p.setString(1, itenspojo.getTitulo());
            p.setString(2, itenspojo.getDescricao());
            p.setDouble(3, itenspojo.getValoritem());
            p.setString(4, itenspojo.getPagante());     
            p.setInt(5, itenspojo.getCriado());     
            op=p.executeUpdate();
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro 01 -Cadastro Item - "+e);
        }
        return op;
}
        
        public List BuscarItensDeEventos(int busca){
            EventosItensPOJO eventositenspojo = new EventosItensPOJO();
            System.out.println("Buscando");
            String sql= "select*from eventositens where id_evento="+busca+";";

            List lista = new ArrayList();
            Connection con = cn.getConnection();
            try {
                PreparedStatement p = con.prepareStatement(sql);
                ResultSet rs=p.executeQuery();
                while(rs.next()){
                    eventositenspojo.setId_Item(rs.getInt("id_item"));
                    eventositenspojo.setId_Evento(rs.getInt("id_evento"));
                    eventositenspojo.setId_EventoItem(rs.getInt("id_eventoitem"));
                    int item = eventositenspojo.getId_Item();
                    String sql2= "select * from itens where id_item="+item;
                    try {
                        PreparedStatement p2 = con.prepareStatement(sql2);
                        ResultSet rs2=p2.executeQuery();
                        while(rs2.next()){
                            ItensPOJO itenspojo = new ItensPOJO();
                            itenspojo.setId_Item(rs2.getInt("id_item"));
                            itenspojo.setTitulo(rs2.getString("tituloitem"));
                            itenspojo.setDescricao(rs2.getString("descricaoitem"));
                            itenspojo.setPagante(rs2.getString("pagante"));
                            itenspojo.setCriado(rs2.getInt("criado"));
                            itenspojo.setValoritem(rs2.getDouble("valoritem"));
                            lista.add(itenspojo);
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
        
        public List BuscarItens(String termo){
         List lista = new ArrayList();
         String sql="select*from itens where id_item like '%"+termo+"%';";
         Connection con = cn.getConnection();
         try {
            PreparedStatement p = con.prepareStatement(sql);
             ResultSet rs=p.executeQuery();
             while(rs.next()){
                 ItensPOJO itenspojo = new ItensPOJO();
                 itenspojo.setId_Item(rs.getInt("id_item"));
                 itenspojo.setTitulo(rs.getString("tituloitem"));
                 itenspojo.setDescricao(rs.getString("descricaoitem"));
                 itenspojo.setValoritem(rs.getDouble("valoritem"));
                 itenspojo.setPagante(rs.getString("pagante"));
                 lista.add(itenspojo);
             }  
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
         cn.retConnection(con);
         return lista;
        }
        
        public int BuscarUltimoItem(int usuario){
            int id_item=0;
            Connection con = cn.getConnection();
            String sql="select max(id_item) from itens where criado="+usuario+";";
            try {
                PreparedStatement p = con.prepareStatement(sql);
                ResultSet rs=p.executeQuery();
                rs.next();
                id_item=rs.getInt("max(id_item)");
            } catch (SQLException e) {
            }
        return id_item;}
        
        public ItensPOJO BuscarItemUnico(int codigo){
            ItensPOJO itenspojo = new ItensPOJO();
            String sql="select*from itens where id_item="+codigo;
            Connection con = cn.getConnection();
            try {
                PreparedStatement p = con.prepareStatement(sql);
                ResultSet rs=p.executeQuery();
                while(rs.next()){
                    //UsuarioPOJO usuariopojo = new UsuarioPOJO();
                    itenspojo.setId_Item(rs.getInt("id_item"));
                    itenspojo.setTitulo(rs.getString("tituloitem"));
                    itenspojo.setDescricao(rs.getString("descricaoitem"));
                    itenspojo.setValoritem(rs.getDouble("valoritem"));
                    itenspojo.setPagante(rs.getString("pagante"));
                    //lista.add(usuariopojo);
                }

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
            cn.retConnection(con);
            return itenspojo;
            }     
        
        public int Deletar(int codigo){
            int confirma=0;
            String sql="delete from itens where id_item="+codigo;
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
        
        public int AlterarItem(ItensPOJO itempojo) { //String titulo,descricao,local,data,horario,adulto,host;
            int op=0;
            Connection con = cn.getConnection();
            String sql="update evento set titulo=?,descricao=?,valoritem=?,pagante=? where id_item=?;";
            try {
                PreparedStatement p = con.prepareStatement(sql); 
                p.setString(1, itempojo.getTitulo());
                p.setString(2, itempojo.getDescricao());
                p.setDouble(3, itempojo.getValoritem());
                p.setString(4, itempojo.getPagante());
                p.setInt(5, itempojo.getId_Item());
                op=p.executeUpdate();
            }catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro 01 -Atualizar Item- "+e);
            }
            return op;
        }
}
