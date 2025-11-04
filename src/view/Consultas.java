package view;

import dao.Consultadao;
import dao.Medicodao;
import dao.Pacientesdao;
import model.Consulta;
import model.Medico;
import model.Paciente;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Tela de gerenciamento de Consultas
 */
public class Consultas extends JFrame {
    
    private JComboBox<String> cmbPaciente, cmbMedico;
    private JTextField txtData, txtHora, txtBuscar;
    private JTextArea txtObservacoes;
    private JButton btnNovo, btnSalvar, btnEditar, btnExcluir, btnBuscar;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private Consultadao consultaDao;
    private Pacientesdao pacienteDao;
    private Medicodao medicoDao;
    private int idSelecionado = 0;
    
    private Map<String, Integer> mapPacientes;
    private Map<String, Integer> mapMedicos;
    
    public Consultas() {
        consultaDao = new Consultadao();
        pacienteDao = new Pacientesdao();
        medicoDao = new Medicodao();
        mapPacientes = new HashMap<>();
        mapMedicos = new HashMap<>();
        
        inicializarComponentes();
        carregarComboBoxes();
        carregarTabela();
    }
    
    private void inicializarComponentes() {
        setTitle("Gerenciamento de Consultas");
        setSize(1200, 700);
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
        JPanel painel = new JPanel(new GridLayout(3, 4, 10, 10));
        painel.setBorder(BorderFactory.createTitledBorder("Dados da Consulta"));
        
        painel.add(new JLabel("Paciente:"));
        cmbPaciente = new JComboBox<>();
        painel.add(cmbPaciente);
        
        painel.add(new JLabel("Médico:"));
        cmbMedico = new JComboBox<>();
        painel.add(cmbMedico);
        
        painel.add(new JLabel("Data (DD/MM/AAAA):"));
        txtData = new JTextField();
        painel.add(txtData);
        
        painel.add(new JLabel("Hora (HH:MM):"));
        txtHora = new JTextField();
        painel.add(txtHora);
        
        painel.add(new JLabel("Observações:"));
        txtObservacoes = new JTextArea(3, 20);
        JScrollPane scrollObs = new JScrollPane(txtObservacoes);
        painel.add(scrollObs);
        
        return painel;
    }
    
    private JPanel criarPainelTabela() {
        JPanel painel = new JPanel(new BorderLayout(5, 5));
        
        // Painel de busca
        JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelBusca.add(new JLabel("Buscar por paciente:"));
        txtBuscar = new JTextField(20);
        painelBusca.add(txtBuscar);
        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> buscarConsultas());
        painelBusca.add(btnBuscar);
        
        painel.add(painelBusca, BorderLayout.NORTH);
        
        // Tabela
        String[] colunas = {"ID", "Paciente", "Médico", "Data", "Hora", "Observações", "ID Paciente", "ID Médico"};
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
        
        // Esconder colunas de ID
        tabela.getColumnModel().getColumn(6).setMinWidth(0);
        tabela.getColumnModel().getColumn(6).setMaxWidth(0);
        tabela.getColumnModel().getColumn(6).setWidth(0);
        tabela.getColumnModel().getColumn(7).setMinWidth(0);
        tabela.getColumnModel().getColumn(7).setMaxWidth(0);
        tabela.getColumnModel().getColumn(7).setWidth(0);
        
        JScrollPane scrollPane = new JScrollPane(tabela);
        painel.add(scrollPane, BorderLayout.CENTER);
        
        return painel;
    }
    
    private JPanel criarPainelBotoes() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        btnNovo = new JButton("Novo");
        btnNovo.addActionListener(e -> limparCampos());
        
        btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvarConsulta());
        
        btnEditar = new JButton("Editar");
        btnEditar.addActionListener(e -> editarConsulta());
        
        btnExcluir = new JButton("Excluir");
        btnExcluir.addActionListener(e -> excluirConsulta());
        
        painel.add(btnNovo);
        painel.add(btnSalvar);
        painel.add(btnEditar);
        painel.add(btnExcluir);
        
        return painel;
    }
    
    private void carregarComboBoxes() {
        cmbPaciente.removeAllItems();
        cmbMedico.removeAllItems();
        mapPacientes.clear();
        mapMedicos.clear();
        
        // Carregar Pacientes
        ArrayList<Paciente> pacientes = pacienteDao.listar();
        for (Paciente p : pacientes) {
            String nome = p.getNome() + " (CPF: " + p.getCpf() + ")";
            cmbPaciente.addItem(nome);
            mapPacientes.put(nome, p.getId());
        }
        
        // Carregar Médicos
        ArrayList<Medico> medicos = medicoDao.listar();
        for (Medico m : medicos) {
            String nome = m.getNome() + " (CRM: " + m.getCrm() + ")";
            cmbMedico.addItem(nome);
            mapMedicos.put(nome, m.getId());
        }
    }
    
    private void carregarTabela() {
        modeloTabela.setRowCount(0);
        ArrayList<Consulta> lista = consultaDao.listar();
        
        for (Consulta c : lista) {
            // Busca os nomes para exibição
            String nomePaciente = pacienteDao.buscarPorId(c.getIdPaciente()).getNome();
            String nomeMedico = medicoDao.buscarPorId(c.getIdMedico()).getNome();
            
            Object[] linha = {
                c.getId(),
                nomePaciente,
                nomeMedico,
                c.getDataConsulta(),
                c.getHoraConsulta(),
                c.getObservacoes(),
                c.getIdPaciente(), // Coluna escondida
                c.getIdMedico()    // Coluna escondida
            };
            modeloTabela.addRow(linha);
        }
    }
    
    private void salvarConsulta() {
        if (!validarCampos()) {
            return;
        }
        
        Consulta consulta = new Consulta();
        
        String pacienteSelecionado = (String) cmbPaciente.getSelectedItem();
        String medicoSelecionado = (String) cmbMedico.getSelectedItem();
        
        consulta.setIdPaciente(mapPacientes.get(pacienteSelecionado));
        consulta.setIdMedico(mapMedicos.get(medicoSelecionado));
        consulta.setDataConsulta(txtData.getText().trim());
        consulta.setHoraConsulta(txtHora.getText().trim());
        consulta.setObservacoes(txtObservacoes.getText().trim());
        
        if (consultaDao.inserir(consulta)) {
            JOptionPane.showMessageDialog(this, "Consulta salva com sucesso!");
            carregarTabela();
            limparCampos();
        }
    }
    
    private void editarConsulta() {
        if (idSelecionado == 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma consulta na tabela!");
            return;
        }
        
        if (!validarCampos()) {
            return;
        }
        
        Consulta consulta = new Consulta();
        consulta.setId(idSelecionado);
        
        String pacienteSelecionado = (String) cmbPaciente.getSelectedItem();
        String medicoSelecionado = (String) cmbMedico.getSelectedItem();
        
        consulta.setIdPaciente(mapPacientes.get(pacienteSelecionado));
        consulta.setIdMedico(mapMedicos.get(medicoSelecionado));
        consulta.setDataConsulta(txtData.getText().trim());
        consulta.setHoraConsulta(txtHora.getText().trim());
        consulta.setObservacoes(txtObservacoes.getText().trim());
        
        if (consultaDao.atualizar(consulta)) {
            JOptionPane.showMessageDialog(this, "Consulta atualizada com sucesso!");
            carregarTabela();
            limparCampos();
        }
    }
    
    private void excluirConsulta() {
        if (idSelecionado == 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma consulta na tabela!");
            return;
        }
        
        int resposta = JOptionPane.showConfirmDialog(this, 
            "Deseja realmente excluir esta consulta?", 
            "Confirmação", 
            JOptionPane.YES_NO_OPTION);
        
        if (resposta == JOptionPane.YES_OPTION) {
            if (consultaDao.excluir(idSelecionado)) {
                JOptionPane.showMessageDialog(this, "Consulta excluída com sucesso!");
                carregarTabela();
                limparCampos();
            }
        }
    }
    
    private void buscarConsultas() {
        // Implementação de busca mais complexa (por nome de paciente)
        String termo = txtBuscar.getText().trim().toLowerCase();
        
        if (termo.isEmpty()) {
            carregarTabela();
            return;
        }
        
        modeloTabela.setRowCount(0);
        ArrayList<Consulta> lista = consultaDao.listar();
        
        for (Consulta c : lista) {
            String nomePaciente = pacienteDao.buscarPorId(c.getIdPaciente()).getNome().toLowerCase();
            
            if (nomePaciente.contains(termo)) {
                String nomeMedico = medicoDao.buscarPorId(c.getIdMedico()).getNome();
                
                Object[] linha = {
                    c.getId(),
                    nomePaciente,
                    nomeMedico,
                    c.getDataConsulta(),
                    c.getHoraConsulta(),
                    c.getObservacoes(),
                    c.getIdPaciente(),
                    c.getIdMedico()
                };
                modeloTabela.addRow(linha);
            }
        }
    }
    
    private void preencherFormulario() {
        int linha = tabela.getSelectedRow();
        if (linha != -1) {
            idSelecionado = (int) modeloTabela.getValueAt(linha, 0);
            
            // Busca os IDs das colunas escondidas
            int idPaciente = (int) modeloTabela.getValueAt(linha, 6);
            int idMedico = (int) modeloTabela.getValueAt(linha, 7);
            
            // Seleciona o item correto no ComboBox
            for (Map.Entry<String, Integer> entry : mapPacientes.entrySet()) {
                if (entry.getValue().equals(idPaciente)) {
                    cmbPaciente.setSelectedItem(entry.getKey());
                    break;
                }
            }
            
            for (Map.Entry<String, Integer> entry : mapMedicos.entrySet()) {
                if (entry.getValue().equals(idMedico)) {
                    cmbMedico.setSelectedItem(entry.getKey());
                    break;
                }
            }
            
            txtData.setText((String) modeloTabela.getValueAt(linha, 3));
            txtHora.setText((String) modeloTabela.getValueAt(linha, 4));
            txtObservacoes.setText((String) modeloTabela.getValueAt(linha, 5));
        }
    }
    
    private void limparCampos() {
        idSelecionado = 0;
        cmbPaciente.setSelectedIndex(-1);
        cmbMedico.setSelectedIndex(-1);
        txtData.setText("");
        txtHora.setText("");
        txtObservacoes.setText("");
        txtBuscar.setText("");
        tabela.clearSelection();
    }
    
    private boolean validarCampos() {
        if (cmbPaciente.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um Paciente!");
            return false;
        }
        if (cmbMedico.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um Médico!");
            return false;
        }
        if (txtData.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "O campo Data é obrigatório!");
            txtData.requestFocus();
            return false;
        }
        if (txtHora.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "O campo Hora é obrigatório!");
            txtHora.requestFocus();
            return false;
        }
        
        return true;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Consultas().setVisible(true);
        });
    }
}
