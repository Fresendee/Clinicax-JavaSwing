package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * Classe responsável pela conexão com o banco de dados
 */
public class Conexao {
    
    // Configurações do banco de dados SQLite
    private static final String URL = "jdbc:sqlite:clinicax.db";
    
    // Método para estabelecer conexão
    public static Connection conectar() {
        Connection conexao = null;
        
        try {
            // Carrega o driver JDBC do SQLite
            Class.forName("org.sqlite.JDBC");
            
            // Estabelece a conexão
            conexao = DriverManager.getConnection(URL);
            
            System.out.println("Conexão estabelecida com sucesso!");
            
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, 
                "Driver JDBC não encontrado!\n" + e.getMessage(), 
                "Erro de Driver", 
                JOptionPane.ERROR_MESSAGE);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Erro ao conectar com o banco de dados!\n" + e.getMessage(), 
                "Erro de Conexão", 
                JOptionPane.ERROR_MESSAGE);
        }
        
        return conexao;
    }
    
    // Método para fechar conexão
    public static void desconectar(Connection conexao) {
        if (conexao != null) {
            try {
                conexao.close();
                System.out.println("Conexão encerrada com sucesso!");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, 
                    "Erro ao fechar conexão!\n" + e.getMessage(), 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // Método para testar a conexão
    public static void testarConexao() {
        Connection conn = conectar();
        if (conn != null) {
            JOptionPane.showMessageDialog(null, 
                "Conexão testada com sucesso!", 
                "Teste de Conexão", 
                JOptionPane.INFORMATION_MESSAGE);
            desconectar(conn);
        }
    }
}
