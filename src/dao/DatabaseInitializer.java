package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 * Classe responsável por inicializar o banco de dados SQLite,
 * criando as tabelas necessárias se elas não existirem.
 */
public class DatabaseInitializer {

    public static void initialize() {
        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement()) {

            if (conn != null) {
                // SQL para criar a tabela de Pacientes
                String sqlPacientes = "CREATE TABLE IF NOT EXISTS pacientes (" +
                                      "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                      "nome TEXT NOT NULL," +
                                      "cpf TEXT UNIQUE NOT NULL," +
                                      "dataNascimento TEXT," +
                                      "telefone TEXT," +
                                      "endereco TEXT," +
                                      "email TEXT," +
                                      "convenio TEXT" +
                                      ");";
                
                // SQL para criar a tabela de Médicos
                String sqlMedicos = "CREATE TABLE IF NOT EXISTS medicos (" +
                                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                    "nome TEXT NOT NULL," +
                                    "crm TEXT UNIQUE NOT NULL," +
                                    "especialidade TEXT," +
                                    "telefone TEXT," +
                                    "email TEXT," +
                                    "endereco TEXT" +
                                    ");";

                // Executa as instruções SQL
                stmt.execute(sqlPacientes);
                stmt.execute(sqlMedicos);
                
                System.out.println("Tabelas criadas ou já existentes.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Erro ao inicializar o banco de dados (SQLite)!\n" + e.getMessage(), 
                "Erro de Inicialização", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
