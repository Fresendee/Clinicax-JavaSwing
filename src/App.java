import view.Pacientes;
import view.Medicos;
import view.Consultas;
import view.Especialidades;
import dao.DatabaseInitializer;
import dao.Conexao;
import javax.swing.*;
import java.awt.*;


public class App extends JFrame {
    
    public App() {
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        setTitle("Clinicax - Menu Principal");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        
        JPanel painelTitulo = new JPanel();
        painelTitulo.setBackground(new Color(41, 128, 185));
        JLabel lblTitulo = new JLabel("CLINICAX");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(Color.WHITE);
        painelTitulo.add(lblTitulo);
        add(painelTitulo, BorderLayout.NORTH);
        
        
        JPanel painelCentral = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
       
        JButton btnPacientes = criarBotao("Gerenciar Pacientes", new Color(46, 204, 113));
        btnPacientes.addActionListener(e -> abrirTelaPacientes());
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        painelCentral.add(btnPacientes, gbc);
        
       
        JButton btnMedicos = criarBotao("Gerenciar Médicos", new Color(52, 152, 219));
        btnMedicos.addActionListener(e -> abrirTelaMedicos());
        gbc.gridy = 1;
        painelCentral.add(btnMedicos, gbc);
        
      
        JButton btnConsultas = criarBotao("Gerenciar Consultas", new Color(155, 89, 182));
        btnConsultas.addActionListener(e -> abrirTelaConsultas());
        gbc.gridy = 2;
        painelCentral.add(btnConsultas, gbc);
        
      
        JButton btnEspecialidades = criarBotao("Gerenciar Especialidades", new Color(241, 196, 15));
        btnEspecialidades.addActionListener(e -> abrirTelaEspecialidades());
        gbc.gridy = 3;
        painelCentral.add(btnEspecialidades, gbc);
        
     
        JButton btnTestarConexao = criarBotao("Testar Conexão BD", new Color(230, 126, 34));
        btnTestarConexao.addActionListener(e -> Conexao.testarConexao());
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        painelCentral.add(btnTestarConexao, gbc);
        
       
        JButton btnSair = criarBotao("Sair", new Color(231, 76, 60));
        btnSair.addActionListener(e -> System.exit(0));
        gbc.gridx = 1;
        painelCentral.add(btnSair, gbc);
        
        add(painelCentral, BorderLayout.CENTER);
        
     
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
    
    private void abrirTelaMedicos() {
        Medicos telaMedicos = new Medicos();
        telaMedicos.setVisible(true);
    }
    
    private void abrirTelaConsultas() {
        Consultas telaConsultas = new Consultas();
        telaConsultas.setVisible(true);
    }
    
    private void abrirTelaEspecialidades() {
        Especialidades telaEspecialidades = new Especialidades();
        telaEspecialidades.setVisible(true);
    }
    
    public static void main(String[] args) {
       
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        DatabaseInitializer.initialize();

        SwingUtilities.invokeLater(() -> {
            App app = new App();
            app.setVisible(true);
        });
    }
}