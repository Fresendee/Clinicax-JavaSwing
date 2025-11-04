package view;

import dao.Especialidadedao;
import model.Especialidade;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

/**
 * Tela de gerenciamento de Especialidades
 */
public class Especialidades extends JFrame {
    
    private JTextField txtNome, txtBuscar;
    private JButton btnNovo, btnSalvar, btnEditar, btnExcluir, btnBuscar;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private Especialidadedao dao;
    private int idSelecionado = 0;
    
    public Especialidades() {
        dao = new Especialidadedao();
        inicializarComponentes();
        carregarTabela();
    }
    
    private void inicializarComponentes() {
        setTitle("Gerenciamento de Especialidades");
        setSize(600, 400);
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
        JPanel painel = new JPanel(new GridLayout(1, 2, 10, 10));
        painel.setBorder(BorderFactory.createTitledBorder("Dados da Especialidade"));
        
        painel.add(new JLabel("Nome da Especialidade:"));
        txtNome = new JTextField();
        painel.add(txtNome);
        
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
        btnBuscar.addActionListener(e -> buscarEspecialidades());
        painelBusca.add(btnBuscar);
        
        painel.add(painelBusca, BorderLayout.NORTH);
        
        // Tabela
        String[] colunas = {"ID", "Nome da Especialidade"};
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
        btnSalvar.addActionListener(e -> salvarEspecialidade());
        
        btnEditar = new JButton("Editar");
        btnEditar.addActionListener(e -> editarEspecialidade());
        
        btnExcluir = new JButton("Excluir");
        btnExcluir.addActionListener(e -> excluirEspecialidade());
        
        painel.add(btnNovo);
        painel.add(btnSalvar);
        painel.add(btnEditar);
        painel.add(btnExcluir);
        
        return painel;
    }
    
    private void carregarTabela() {
        modeloTabela.setRowCount(0);
        ArrayList<Especialidade> lista = dao.listar();
        
        for (Especialidade e : lista) {
            Object[] linha = {
                e.getId(),
                e.getNome()
            };
            modeloTabela.addRow(linha);
        }
    }
    
    private void salvarEspecialidade() {
        if (!validarCampos()) {
            return;
        }
        
        Especialidade especialidade = new Especialidade();
        especialidade.setNome(txtNome.getText().trim());
        
        if (dao.inserir(especialidade)) {
            JOptionPane.showMessageDialog(this, "Especialidade salva com sucesso!");
            carregarTabela();
            limparCampos();
        }
    }
    
    private void editarEspecialidade() {
        if (idSelecionado == 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma especialidade na tabela!");
            return;
        }
        
        if (!validarCampos()) {
            return;
        }
        
        Especialidade especialidade = new Especialidade();
        especialidade.setId(idSelecionado);
        especialidade.setNome(txtNome.getText().trim());
        
        if (dao.atualizar(especialidade)) {
            JOptionPane.showMessageDialog(this, "Especialidade atualizada com sucesso!");
            carregarTabela();
            limparCampos();
        }
    }
    
    private void excluirEspecialidade() {
        if (idSelecionado == 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma especialidade na tabela!");
            return;
        }
        
        int resposta = JOptionPane.showConfirmDialog(this, 
            "Deseja realmente excluir esta especialidade?", 
            "Confirmação", 
            JOptionPane.YES_NO_OPTION);
        
        if (resposta == JOptionPane.YES_OPTION) {
            if (dao.excluir(idSelecionado)) {
                JOptionPane.showMessageDialog(this, "Especialidade excluída com sucesso!");
                carregarTabela();
                limparCampos();
            }
        }
    }
    
    private void buscarEspecialidades() {
        String termo = txtBuscar.getText().trim();
        
        if (termo.isEmpty()) {
            carregarTabela();
            return;
        }
        
        modeloTabela.setRowCount(0);
        ArrayList<Especialidade> lista = dao.listar();
        
        for (Especialidade e : lista) {
            if (e.getNome().toLowerCase().contains(termo.toLowerCase())) {
                Object[] linha = {
                    e.getId(),
                    e.getNome()
                };
                modeloTabela.addRow(linha);
            }
        }
    }
    
    private void preencherFormulario() {
        int linha = tabela.getSelectedRow();
        if (linha != -1) {
            idSelecionado = (int) modeloTabela.getValueAt(linha, 0);
            txtNome.setText((String) modeloTabela.getValueAt(linha, 1));
        }
    }
    
    private void limparCampos() {
        idSelecionado = 0;
        txtNome.setText("");
        txtBuscar.setText("");
        tabela.clearSelection();
        txtNome.requestFocus();
    }
    
    private boolean validarCampos() {
        if (txtNome.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "O campo Nome da Especialidade é obrigatório!");
            txtNome.requestFocus();
            return false;
        }
        
        return true;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Especialidades().setVisible(true);
        });
    }
}
