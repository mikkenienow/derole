package semestral_derole.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnectionMySQL {

        public static String status = "Não conectou...";


    public MyConnectionMySQL() {
    }

    public static java.sql.Connection getConexaoMySQL() {
        Connection connection = null;          //atributo do tipo Connection
        try {
        String driverName = "com.mysql.jdbc.Driver";
        Class.forName(driverName);
                String serverName = "187.85.164.154:3306";    //caminho do servidor do BD
                String mydatabase ="gestaopetnet_semestral_derole";        //nome do seu banco de dados
                String url = "jdbc:mysql://" + serverName + "/" + mydatabase;
                String username = "gestaopetnet_derole";        //nome de um usuário de seu BD
                String password = "Derole@123";      //sua senha de acesso
                connection = DriverManager.getConnection(url, username, password);
                //Testa sua conexão//
                if (connection != null) {
                    status = ("STATUS--->Conectado com sucesso!");
                } else {
                    status = ("STATUS--->Não foi possivel realizar conexão");
                }
                return connection;
            } catch (ClassNotFoundException e) {  //Driver não encontrado
                    System.out.println("O driver expecificado nao foi encontrado.");
                    return null;
            } catch (SQLException e) {
                //Não conseguindo se conectar ao banco
                System.out.println(e +" Nao foi possivel conectar ao Banco de Dados.");
                return null;
            }
        }

    //Método que retorna o status da sua conexão//
    public static String statusConection() {
        return status;
    }

   //Método que fecha sua conexão//
    public static boolean FecharConexao() {
        try {
            MyConnectionMySQL.getConexaoMySQL().close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

   //Método que reinicia sua conexão//
    public static java.sql.Connection ReiniciarConexao() {
        FecharConexao();
        return MyConnectionMySQL.getConexaoMySQL();
    }
}