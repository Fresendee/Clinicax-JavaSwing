package dao;

import model.Consulta;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class Consultadao {
    
    
    public boolean inserir(Consulta consulta) {
        Connection conn = Conexao.conectar();
        String sql = "INSERT INTO consultas (idPaciente, idMedico, dataConsulta, horaConsulta, observacoes) VALUES (?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, consulta.getIdPaciente());
            stmt.setInt(2, consulta.getIdMedico());
            stmt.setString(3, consulta.getDataConsulta());
            stmt.setString(4, consulta.getHoraConsulta());
            stmt.setString(5, consulta.getObservacoes());
            
            stmt.executeUpdate();
            stmt.close();
            Conexao.desconectar(conn);
            
            return true;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Erro ao inserir consulta!\n" + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
  
    public boolean atualizar(Consulta consulta) {
        Connection conn = Conexao.conectar();
        String sql = "UPDATE consultas SET idPaciente=?, idMedico=?, dataConsulta=?, horaConsulta=?, observacoes=? WHERE id=?";
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, consulta.getIdPaciente());
            stmt.setInt(2, consulta.getIdMedico());
            stmt.setString(3, consulta.getDataConsulta());
            stmt.setString(4, consulta.getHoraConsulta());
            stmt.setString(5, consulta.getObservacoes());
            stmt.setInt(6, consulta.getId());
            
            stmt.executeUpdate();
            stmt.close();
            Conexao.desconectar(conn);
            
            return true;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Erro ao atualizar consulta!\n" + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    
    public boolean excluir(int id) {
        Connection conn = Conexao.conectar();
        String sql = "DELETE FROM consultas WHERE id=?";
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            
            stmt.executeUpdate();
            stmt.close();
            Conexao.desconectar(conn);
            
            return true;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Erro ao excluir consulta!\n" + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
   
    public ArrayList<Consulta> listar() {
        ArrayList<Consulta> lista = new ArrayList<>();
        Connection conn = Conexao.conectar();
        String sql = "SELECT * FROM consultas ORDER BY dataConsulta DESC, horaConsulta DESC";
        
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                Consulta consulta = new Consulta();
                consulta.setId(rs.getInt("id"));
                consulta.setIdPaciente(rs.getInt("idPaciente"));
                consulta.setIdMedico(rs.getInt("idMedico"));
                consulta.setDataConsulta(rs.getString("dataConsulta"));
                consulta.setHoraConsulta(rs.getString("horaConsulta"));
                consulta.setObservacoes(rs.getString("observacoes"));
                
                lista.add(consulta);
            }
            
            rs.close();
            stmt.close();
            Conexao.desconectar(conn);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Erro ao listar consultas!\n" + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
        
        return lista;
    }
    
    
    public Consulta buscarPorId(int id) {
        Connection conn = Conexao.conectar();
        String sql = "SELECT * FROM consultas WHERE id=?";
        Consulta consulta = null;
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                consulta = new Consulta();
                consulta.setId(rs.getInt("id"));
                consulta.setIdPaciente(rs.getInt("idPaciente"));
                consulta.setIdMedico(rs.getInt("idMedico"));
                consulta.setDataConsulta(rs.getString("dataConsulta"));
                consulta.setHoraConsulta(rs.getString("horaConsulta"));
                consulta.setObservacoes(rs.getString("observacoes"));
            }
            
            rs.close();
            stmt.close();
            Conexao.desconectar(conn);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Erro ao buscar consulta!\n" + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
        
        return consulta;
    }
}
