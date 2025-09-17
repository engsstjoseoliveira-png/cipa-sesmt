package br.net.technet.cipa.Uteis;

public class SesmtModel {

    public SesmtModel(String texto_sesmt_descricao, String colaborador_1, String colaborador_2, String colaborador_3, String horario,
                      String telefone, String email) {
        this.texto_sesmt_descricao = texto_sesmt_descricao;
        this.colaborador_1 = colaborador_1;
        this.colaborador_2 = colaborador_2;
        this.colaborador_3 = colaborador_3;
        this.horario = horario;
        this.telefone = telefone;
        this.email = email;
    }

    public String getColaborador_1() {
        return colaborador_1;
    }

    public String getColaborador_2() {
        return colaborador_2;
    }

    public String getColaborador_3() {
        return colaborador_3;
    }

    public String getHorario() {
        return horario;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public String getTexto_sesmt_descricao() {
        return texto_sesmt_descricao;
    }

    private String colaborador_1;
    private String colaborador_2;
    private String colaborador_3;
    private String horario;
    private String telefone;
    private String email;
    private String texto_sesmt_descricao;

    public SesmtModel() {
    }
}
