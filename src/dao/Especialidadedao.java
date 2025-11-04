package dao;

import model.Especialidade;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Classe DAO para operações com Especialidades no banco de dados
 */
public class Especialidadedao {
    
    // Inserir nova especialidade
    public boolean inserir(Especialidade especialidade) {
        Connection conn = Conexao.conectar();
        String sql = "INSERT INTO especialidades (nome) VALUES (?)";
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, especialidade.getNome());
            
            stmt.executeUpdate();
            stmt.close();
            Conexao.desconectar(conn);
            
            return true;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Erro ao inserir especialidade!\n" + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    // Atualizar especialidade existente
    public boolean atualizar(Especialidade especialidade) {
        Connection conn = Conexao.conectar();
        String sql = "UPDATE especialidades SET nome=? WHERE id=?";
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, especialidade.getNome());
            stmt.setInt(2, especialidade.getId());
            
            stmt.executeUpdate();
            stmt.close();
            Conexao.desconectar(conn);
            
            return true;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Erro ao atualizar especialidade!\n" + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    // Excluir especialidade
    public boolean excluir(int id) {
        Connection conn = Conexao.conectar();
        String sql = "DELETE FROM especialidades WHERE id=?";
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            
            stmt.executeUpdate();
            stmt.close();
            Conexao.desconectar(conn);
            
            return true;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Erro ao excluir especialidade!\n" + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    // Listar todas as especialidades
    public ArrayList<Especialidade> listar() {
        ArrayList<Especialidade> lista = new ArrayList<>();
        Connection conn = Conexao.conectar();
        String sql = "SELECT * FROM especialidades ORDER BY nome";
        
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                Especialidade especialidade = new Especialidade();
                especialidade.setId(rs.getInt("id"));
                especialidade.setNome(rs.getString("nome"));
                
                lista.add(especialidade);
            }
            
            rs.close();
            stmt.close();
            Conexao.desconectar(conn);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Erro ao listar especialidades!\n" + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
        
        return lista;
    }
    
    // Buscar especialidade por ID
    public Especialidade buscarPorId(int id) {
        Connection conn = Conexao.conectar();
        String sql = "SELECT * FROM especialidades WHERE id=?";
        Especialidade especialidade = null;
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                especialidade = new Especialidade();
                especialidade.setId(rs.getInt("id"));
                especialidade.setNome(rs.getString("nome"));
            }
            
            rs.close();
            stmt.close();
            Conexao.desconectar(conn);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Erro ao buscar especialidade!\n" + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
        
        return especialidade;
    }
}
