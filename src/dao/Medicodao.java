package dao;

import model.Medico;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Classe DAO para operações com Médicos no banco de dados
 */
public class Medicodao {
    
    // Inserir novo médico
    public boolean inserir(Medico medico) {
        Connection conn = Conexao.conectar();
        String sql = "INSERT INTO medicos (nome, crm, especialidade, telefone, email, endereco) VALUES (?, ?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, medico.getNome());
            stmt.setString(2, medico.getCrm());
            stmt.setString(3, medico.getEspecialidade());
            stmt.setString(4, medico.getTelefone());
            stmt.setString(5, medico.getEmail());
            stmt.setString(6, medico.getEndereco());
            
            stmt.executeUpdate();
            stmt.close();
            Conexao.desconectar(conn);
            
            return true;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Erro ao inserir médico!\n" + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    // Atualizar médico existente
    public boolean atualizar(Medico medico) {
        Connection conn = Conexao.conectar();
        String sql = "UPDATE medicos SET nome=?, crm=?, especialidade=?, telefone=?, email=?, endereco=? WHERE id=?";
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, medico.getNome());
            stmt.setString(2, medico.getCrm());
            stmt.setString(3, medico.getEspecialidade());
            stmt.setString(4, medico.getTelefone());
            stmt.setString(5, medico.getEmail());
            stmt.setString(6, medico.getEndereco());
            stmt.setInt(7, medico.getId());
            
            stmt.executeUpdate();
            stmt.close();
            Conexao.desconectar(conn);
            
            return true;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Erro ao atualizar médico!\n" + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    // Excluir médico
    public boolean excluir(int id) {
        Connection conn = Conexao.conectar();
        String sql = "DELETE FROM medicos WHERE id=?";
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            
            stmt.executeUpdate();
            stmt.close();
            Conexao.desconectar(conn);
            
            return true;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Erro ao excluir médico!\n" + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    // Listar todos os médicos
    public ArrayList<Medico> listar() {
        ArrayList<Medico> lista = new ArrayList<>();
        Connection conn = Conexao.conectar();
        String sql = "SELECT * FROM medicos ORDER BY nome";
        
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                Medico medico = new Medico();
                medico.setId(rs.getInt("id"));
                medico.setNome(rs.getString("nome"));
                medico.setCrm(rs.getString("crm"));
                medico.setEspecialidade(rs.getString("especialidade"));
                medico.setTelefone(rs.getString("telefone"));
                medico.setEmail(rs.getString("email"));
                medico.setEndereco(rs.getString("endereco"));
                
                lista.add(medico);
            }
            
            rs.close();
            stmt.close();
            Conexao.desconectar(conn);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Erro ao listar médicos!\n" + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
        
        return lista;
    }
    
    // Buscar médico por ID
    public Medico buscarPorId(int id) {
        Connection conn = Conexao.conectar();
        String sql = "SELECT * FROM medicos WHERE id=?";
        Medico medico = null;
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                medico = new Medico();
                medico.setId(rs.getInt("id"));
                medico.setNome(rs.getString("nome"));
                medico.setCrm(rs.getString("crm"));
                medico.setEspecialidade(rs.getString("especialidade"));
                medico.setTelefone(rs.getString("telefone"));
                medico.setEmail(rs.getString("email"));
                medico.setEndereco(rs.getString("endereco"));
            }
            
            rs.close();
            stmt.close();
            Conexao.desconectar(conn);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Erro ao buscar médico!\n" + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
        
        return medico;
    }
    
    // Buscar médicos por nome
    public ArrayList<Medico> buscarPorNome(String nome) {
        ArrayList<Medico> lista = new ArrayList<>();
        Connection conn = Conexao.conectar();
        String sql = "SELECT * FROM medicos WHERE nome LIKE ? ORDER BY nome";
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + nome + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Medico medico = new Medico();
                medico.setId(rs.getInt("id"));
                medico.setNome(rs.getString("nome"));
                medico.setCrm(rs.getString("crm"));
                medico.setEspecialidade(rs.getString("especialidade"));
                medico.setTelefone(rs.getString("telefone"));
                medico.setEmail(rs.getString("email"));
                medico.setEndereco(rs.getString("endereco"));
                
                lista.add(medico);
            }
            
            rs.close();
            stmt.close();
            Conexao.desconectar(conn);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Erro ao buscar médicos!\n" + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
        
        return lista;
    }
}
