package model;

/**
 * Classe modelo que representa uma Consulta
 */
public class Consulta {
    private int id;
    private int idPaciente;
    private int idMedico;
    private String dataConsulta;
    private String horaConsulta;
    private String observacoes;
    
    // Construtor vazio
    public Consulta() {
    }
    
    // Construtor completo
    public Consulta(int id, int idPaciente, int idMedico, String dataConsulta, String horaConsulta, String observacoes) {
        this.id = id;
        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
        this.dataConsulta = dataConsulta;
        this.horaConsulta = horaConsulta;
        this.observacoes = observacoes;
    }
    
    // Getters e Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getIdPaciente() {
        return idPaciente;
    }
    
    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }
    
    public int getIdMedico() {
        return idMedico;
    }
    
    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }
    
    public String getDataConsulta() {
        return dataConsulta;
    }
    
    public void setDataConsulta(String dataConsulta) {
        this.dataConsulta = dataConsulta;
    }
    
    public String getHoraConsulta() {
        return horaConsulta;
    }
    
    public void setHoraConsulta(String horaConsulta) {
        this.horaConsulta = horaConsulta;
    }
    
    public String getObservacoes() {
        return observacoes;
    }
    
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
    
    @Override
    public String toString() {
        return "Consulta{" +
                "id=" + id +
                ", idPaciente=" + idPaciente +
                ", idMedico=" + idMedico +
                ", dataConsulta='" + dataConsulta + '\'' +
                ", horaConsulta='" + horaConsulta + '\'' +
                ", observacoes='" + observacoes + '\'' +
                '}';
    }
}
