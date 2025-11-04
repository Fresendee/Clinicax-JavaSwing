package view;

import dao.Pacientesdao;
import model.Paciente;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import dao.DatabaseInitializer;

/**
 * Tela de gerenciamento de Pacientes
 */
public class Pacientes extends JFrame {
    
    private JTextField txtNome, txtCpf, txtDataNasc, txtTelefone, txtEndereco, txtEmail, txtConvenio, txtBuscar;
    private JButton btnNovo, btnSalvar, btnEditar, btnExcluir, btnBuscar;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private Pacientesdao dao;
    private int idSelecionado = 0;
    
    public Pacientes() {
        // Garante que o banco de dados e as tabelas existam antes de qualquer acesso
        DatabaseInitializer.initialize();
        
        dao = new Pacientesdao();
        inicializarComponentes();
        carregarTabela();
    }
    
    private void inicializarComponentes() {
        setTitle("Gerenciamento de Pacientes");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        // Painel superior - Formulário
        add(criarPainelFormulario(), BorderLayout.NORTH);
        
        // Painel central - Tabela
        add(criarPainelTabela(), BorderLayout.CENTER);
        
        // Painel inferior - Botões
        add(criarPainelBotoes(), BorderLayout.SOUTH);
    }
    
    private JPanel criarPainelFormulario() {
        JPanel painel = new JPanel(new GridLayout(4, 4, 10, 10));
        painel.setBorder(BorderFactory.createTitledBorder("Dados do Paciente"));
        
        painel.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        painel.add(txtNome);
        
        painel.add(new JLabel("CPF:"));
        txtCpf = new JTextField();
        painel.add(txtCpf);
        
        painel.add(new JLabel("Data Nascimento:"));
        txtDataNasc = new JTextField();
        painel.add(txtDataNasc);
        
        painel.add(new JLabel("Telefone:"));
        txtTelefone = new JTextField();
        painel.add(txtTelefone);
        
        painel.add(new JLabel("E-mail:"));
        txtEmail = new JTextField();
        painel.add(txtEmail);
        
        painel.add(new JLabel("Convênio:"));
        txtConvenio = new JTextField();
        painel.add(txtConvenio);
        
        painel.add(new JLabel("Endereço:"));
        txtEndereco = new JTextField();
        painel.add(txtEndereco);
        
        return painel;
    }
    
    private JPanel criarPainelTabela() {
        JPanel painel = new JPanel(new BorderLayout(5, 5));
        
        // Painel de busca
        JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelBusca.add(new JLabel("Buscar por nome:"));
        txtBuscar = new JTextField(20);
        painelBusca.add(txtBuscar);
        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> buscarPacientes());
        painelBusca.add(btnBuscar);
        
        painel.add(painelBusca, BorderLayout.NORTH);
        
        // Tabela
        String[] colunas = {"ID", "Nome", "CPF", "Data Nasc.", "Telefone", "E-mail", "Convênio", "Endereço"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tabela = new JTable(modeloTabela);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                preencherFormulario();
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(tabela);
        painel.add(scrollPane, BorderLayout.CENTER);
        
        return painel;
    }
    
    private JPanel criarPainelBotoes() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        btnNovo = new JButton("Novo");
        btnNovo.addActionListener(e -> limparCampos());
        
        btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvarPaciente());
        
        btnEditar = new JButton("Editar");
        btnEditar.addActionListener(e -> editarPaciente());
        
        btnExcluir = new JButton("Excluir");
        btnExcluir.addActionListener(e -> excluirPaciente());
        
        painel.add(btnNovo);
        painel.add(btnSalvar);
        painel.add(btnEditar);
        painel.add(btnExcluir);
        
        return painel;
    }
    
    private void carregarTabela() {
        modeloTabela.setRowCount(0);
        ArrayList<Paciente> lista = dao.listar();
        
        for (Paciente p : lista) {
            Object[] linha = {
                p.getId(),
                p.getNome(),
                p.getCpf(),
                p.getDataNascimento(),
                p.getTelefone(),
                p.getEmail(),
                p.getConvenio(),
                p.getEndereco()
            };
            modeloTabela.addRow(linha);
        }
    }
    
    private void salvarPaciente() {
        if (!validarCampos()) {
            return;
        }
        
        Paciente paciente = new Paciente();
        paciente.setNome(txtNome.getText().trim());
        paciente.setCpf(txtCpf.getText().trim());
        paciente.setDataNascimento(txtDataNasc.getText().trim());
        paciente.setTelefone(txtTelefone.getText().trim());
        paciente.setEmail(txtEmail.getText().trim());
        paciente.setConvenio(txtConvenio.getText().trim());
        paciente.setEndereco(txtEndereco.getText().trim());
        
        if (dao.inserir(paciente)) {
            JOptionPane.showMessageDialog(this, "Paciente salvo com sucesso!");
            carregarTabela();
            limparCampos();
        }
    }
    
    private void editarPaciente() {
        if (idSelecionado == 0) {
            JOptionPane.showMessageDialog(this, "Selecione um paciente na tabela!");
            return;
        }
        
        if (!validarCampos()) {
            return;
        }
        
        Paciente paciente = new Paciente();
        paciente.setId(idSelecionado);
        paciente.setNome(txtNome.getText().trim());
        paciente.setCpf(txtCpf.getText().trim());
        paciente.setDataNascimento(txtDataNasc.getText().trim());
        paciente.setTelefone(txtTelefone.getText().trim());
        paciente.setEmail(txtEmail.getText().trim());
        paciente.setConvenio(txtConvenio.getText().trim());
        paciente.setEndereco(txtEndereco.getText().trim());
        
        if (dao.atualizar(paciente)) {
            JOptionPane.showMessageDialog(this, "Paciente atualizado com sucesso!");
            carregarTabela();
            limparCampos();
        }
    }
    
    private void excluirPaciente() {
        if (idSelecionado == 0) {
            JOptionPane.showMessageDialog(this, "Selecione um paciente na tabela!");
            return;
        }
        
        int resposta = JOptionPane.showConfirmDialog(this, 
            "Deseja realmente excluir este paciente?", 
            "Confirmação", 
            JOptionPane.YES_NO_OPTION);
        
        if (resposta == JOptionPane.YES_OPTION) {
            if (dao.excluir(idSelecionado)) {
                JOptionPane.showMessageDialog(this, "Paciente excluído com sucesso!");
                carregarTabela();
                limparCampos();
            }
        }
    }
    
    private void buscarPacientes() {
        String termo = txtBuscar.getText().trim();
        
        if (termo.isEmpty()) {
            carregarTabela();
            return;
        }
        
        modeloTabela.setRowCount(0);
        ArrayList<Paciente> lista = dao.buscarPorNome(termo);
        
        for (Paciente p : lista) {
            Object[] linha = {
                p.getId(),
                p.getNome(),
                p.getCpf(),
                p.getDataNascimento(),
                p.getTelefone(),
                p.getEmail(),
                p.getConvenio(),
                p.getEndereco()
            };
            modeloTabela.addRow(linha);
        }
    }
    
    private void preencherFormulario() {
        int linha = tabela.getSelectedRow();
        if (linha != -1) {
            idSelecionado = (int) modeloTabela.getValueAt(linha, 0);
            txtNome.setText((String) modeloTabela.getValueAt(linha, 1));
            txtCpf.setText((String) modeloTabela.getValueAt(linha, 2));
            txtDataNasc.setText((String) modeloTabela.getValueAt(linha, 3));
            txtTelefone.setText((String) modeloTabela.getValueAt(linha, 4));
            txtEmail.setText((String) modeloTabela.getValueAt(linha, 5));
            txtConvenio.setText((String) modeloTabela.getValueAt(linha, 6));
            txtEndereco.setText((String) modeloTabela.getValueAt(linha, 7));
        }
    }
    
    private void limparCampos() {
        idSelecionado = 0;
        txtNome.setText("");
        txtCpf.setText("");
        txtDataNasc.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
        txtConvenio.setText("");
        txtEndereco.setText("");
        txtBuscar.setText("");
        tabela.clearSelection();
        txtNome.requestFocus();
    }
    
    private boolean validarCampos() {
        if (txtNome.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "O campo Nome é obrigatório!");
            txtNome.requestFocus();
            return false;
        }
        
        if (txtCpf.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "O campo CPF é obrigatório!");
            txtCpf.requestFocus();
            return false;
        }
        
        return true;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Pacientes().setVisible(true);
        });
    }
}