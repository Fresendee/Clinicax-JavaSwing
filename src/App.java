import view.Pacientes;
import dao.DatabaseInitializer;
import dao.Conexao;
import javax.swing.*;
import java.awt.*;

/**
 * Classe principal do sistema - Menu inicial
 */
public class App extends JFrame {
    
    public App() {
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        setTitle("Sistema Clinicax - Menu Principal");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        // Painel do título
        JPanel painelTitulo = new JPanel();
        painelTitulo.setBackground(new Color(41, 128, 185));
        JLabel lblTitulo = new JLabel("SISTEMA CLINICAX");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(Color.WHITE);
        painelTitulo.add(lblTitulo);
        add(painelTitulo, BorderLayout.NORTH);
        
        // Painel central com botões
        JPanel painelCentral = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Botão Pacientes
        JButton btnPacientes = criarBotao("Gerenciar Pacientes", new Color(46, 204, 113));
        btnPacientes.addActionListener(e -> abrirTelaPacientes());
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        painelCentral.add(btnPacientes, gbc);
        
        // Botão Médicos
        JButton btnMedicos = criarBotao("Gerenciar Médicos", new Color(52, 152, 219));
        btnMedicos.addActionListener(e -> JOptionPane.showMessageDialog(this, 
            "Tela de Médicos em desenvolvimento!", 
            "Em breve", 
            JOptionPane.INFORMATION_MESSAGE));
        gbc.gridy = 1;
        painelCentral.add(btnMedicos, gbc);
        
        // Botão Consultas
        JButton btnConsultas = criarBotao("Gerenciar Consultas", new Color(155, 89, 182));
        btnConsultas.addActionListener(e -> JOptionPane.showMessageDialog(this, 
            "Tela de Consultas em desenvolvimento!", 
            "Em breve", 
            JOptionPane.INFORMATION_MESSAGE));
        gbc.gridy = 2;
        painelCentral.add(btnConsultas, gbc);
        
        // Botão Testar Conexão
        JButton btnTestarConexao = criarBotao("Testar Conexão BD", new Color(230, 126, 34));
        btnTestarConexao.addActionListener(e -> Conexao.testarConexao());
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        painelCentral.add(btnTestarConexao, gbc);
        
        // Botão Sair
        JButton btnSair = criarBotao("Sair", new Color(231, 76, 60));
        btnSair.addActionListener(e -> System.exit(0));
        gbc.gridx = 1;
        painelCentral.add(btnSair, gbc);
        
        add(painelCentral, BorderLayout.CENTER);
        
        // Painel rodapé
        JPanel painelRodape = new JPanel();
        painelRodape.setBackground(new Color(44, 62, 80));
        JLabel lblRodape = new JLabel("© 2025 Clinicax - Sistema de Gerenciamento Médico");
        lblRodape.setForeground(Color.WHITE);
        painelRodape.add(lblRodape);
        add(painelRodape, BorderLayout.SOUTH);
    }
    
    private JButton criarBotao(String texto, Color cor) {
        JButton botao = new JButton(texto);
        botao.setPreferredSize(new Dimension(250, 50));
        botao.setFont(new Font("Arial", Font.BOLD, 14));
        botao.setBackground(cor);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setBorderPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Efeito hover
        botao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botao.setBackground(cor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                botao.setBackground(cor);
            }
        });
        
        return botao;
    }
    
    private void abrirTelaPacientes() {
        Pacientes telaPacientes = new Pacientes();
        telaPacientes.setVisible(true);
    }
    
    public static void main(String[] args) {
        // Define o Look and Feel do sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Inicializa o banco de dados (cria o arquivo e as tabelas se não existirem)
        DatabaseInitializer.initialize();

        SwingUtilities.invokeLater(() -> {
            App app = new App();
            app.setVisible(true);
        });
    }
}