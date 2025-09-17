package br.net.technet.cipa.Uteis;

public class SejaUmCipeiroModel {

    public SejaUmCipeiroModel() {
    }

    public SejaUmCipeiroModel(String mandato, String gestao, String eleicao, String eleicao_iii, int ativar_eleicao) {
        this.mandato = mandato;
        this.gestao = gestao;
        this.eleicao = eleicao;
        this.eleicao_iii = eleicao_iii;
        this.ativar_eleicao = ativar_eleicao;
    }

    public String getMandato() {
        return mandato;
    }

    public String getGestao() {
        return gestao;
    }

    public String getEleicao() {
        return eleicao;
    }

    public String getEleicao_iii() {
        return eleicao_iii;
    }

    public int getAtivar_eleicao() {
        return ativar_eleicao;
    }

    private String mandato;
    private String gestao;
    private String eleicao;
    private String eleicao_iii;
    private int ativar_eleicao;
}
