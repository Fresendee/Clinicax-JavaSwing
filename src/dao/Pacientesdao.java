package dao;

import model.Paciente;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Classe DAO para operações com Pacientes no banco de dados
 */
public class Pacientesdao {
    
    // Inserir novo paciente
    public boolean inserir(Paciente paciente) {
        Connection conn = Conexao.conectar();
        String sql = "INSERT INTO pacientes (nome, cpf, dataNascimento, telefone, endereco, email, convenio) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, paciente.getNome());
            stmt.setString(2, paciente.getCpf());
            stmt.setString(3, paciente.getDataNascimento());
            stmt.setString(4, paciente.getTelefone());
            stmt.setString(5, paciente.getEndereco());
            stmt.setString(6, paciente.getEmail());
            stmt.setString(7, paciente.getConvenio());
            
            stmt.executeUpdate();
            stmt.close();
            Conexao.desconectar(conn);
            
            return true;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Erro ao inserir paciente!\n" + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    // Atualizar paciente existente
    public boolean atualizar(Paciente paciente) {
        Connection conn = Conexao.conectar();
        String sql = "UPDATE pacientes SET nome=?, cpf=?, dataNascimento=?, telefone=?, endereco=?, email=?, convenio=? WHERE id=?";
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, paciente.getNome());
            stmt.setString(2, paciente.getCpf());
            stmt.setString(3, paciente.getDataNascimento());
            stmt.setString(4, paciente.getTelefone());
            stmt.setString(5, paciente.getEndereco());
            stmt.setString(6, paciente.getEmail());
            stmt.setString(7, paciente.getConvenio());
            stmt.setInt(8, paciente.getId());
            
            stmt.executeUpdate();
            stmt.close();
            Conexao.desconectar(conn);
            
            return true;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Erro ao atualizar paciente!\n" + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    // Excluir paciente
    public boolean excluir(int id) {
        Connection conn = Conexao.conectar();
        String sql = "DELETE FROM pacientes WHERE id=?";
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            
            stmt.executeUpdate();
            stmt.close();
            Conexao.desconectar(conn);
            
            return true;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Erro ao excluir paciente!\n" + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    // Listar todos os pacientes
    public ArrayList<Paciente> listar() {
        ArrayList<Paciente> lista = new ArrayList<>();
        Connection conn = Conexao.conectar();
        String sql = "SELECT * FROM pacientes ORDER BY nome";
        
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                Paciente paciente = new Paciente();
                paciente.setId(rs.getInt("id"));
                paciente.setNome(rs.getString("nome"));
                paciente.setCpf(rs.getString("cpf"));
                paciente.setDataNascimento(rs.getString("dataNascimento"));
                paciente.setTelefone(rs.getString("telefone"));
                paciente.setEndereco(rs.getString("endereco"));
                paciente.setEmail(rs.getString("email"));
                paciente.setConvenio(rs.getString("convenio"));
                
                lista.add(paciente);
            }
            
            rs.close();
            stmt.close();
            Conexao.desconectar(conn);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Erro ao listar pacientes!\n" + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
        
        return lista;
    }
    
    // Buscar paciente por ID
    public Paciente buscarPorId(int id) {
        Connection conn = Conexao.conectar();
        String sql = "SELECT * FROM pacientes WHERE id=?";
        Paciente paciente = null;
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                paciente = new Paciente();
                paciente.setId(rs.getInt("id"));
                paciente.setNome(rs.getString("nome"));
                paciente.setCpf(rs.getString("cpf"));
                paciente.setDataNascimento(rs.getString("dataNascimento"));
                paciente.setTelefone(rs.getString("telefone"));
                paciente.setEndereco(rs.getString("endereco"));
                paciente.setEmail(rs.getString("email"));
                paciente.setConvenio(rs.getString("convenio"));
            }
            
            rs.close();
            stmt.close();
            Conexao.desconectar(conn);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Erro ao buscar paciente!\n" + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
        
        return paciente;
    }
    
    // Buscar pacientes por nome
    public ArrayList<Paciente> buscarPorNome(String nome) {
        ArrayList<Paciente> lista = new ArrayList<>();
        Connection conn = Conexao.conectar();
        String sql = "SELECT * FROM pacientes WHERE nome LIKE ? ORDER BY nome";
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + nome + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Paciente paciente = new Paciente();
                paciente.setId(rs.getInt("id"));
                paciente.setNome(rs.getString("nome"));
                paciente.setCpf(rs.getString("cpf"));
                paciente.setDataNascimento(rs.getString("dataNascimento"));
                paciente.setTelefone(rs.getString("telefone"));
                paciente.setEndereco(rs.getString("endereco"));
                paciente.setEmail(rs.getString("email"));
                paciente.setConvenio(rs.getString("convenio"));
                
                lista.add(paciente);
            }
            
            rs.close();
            stmt.close();
            Conexao.desconectar(conn);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Erro ao buscar pacientes!\n" + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
        
        return lista;
    }
    
    // Buscar paciente por CPF
    public Paciente buscarPorCpf(String cpf) {
        Connection conn = Conexao.conectar();
        String sql = "SELECT * FROM pacientes WHERE cpf=?";
        Paciente paciente = null;
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                paciente = new Paciente();
                paciente.setId(rs.getInt("id"));
                paciente.setNome(rs.getString("nome"));
                paciente.setCpf(rs.getString("cpf"));
                paciente.setDataNascimento(rs.getString("dataNascimento"));
                paciente.setTelefone(rs.getString("telefone"));
                paciente.setEndereco(rs.getString("endereco"));
                paciente.setEmail(rs.getString("email"));
                paciente.setConvenio(rs.getString("convenio"));
            }
            
            rs.close();
            stmt.close();
            Conexao.desconectar(conn);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Erro ao buscar paciente por CPF!\n" + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
        
        return paciente;
    }
}