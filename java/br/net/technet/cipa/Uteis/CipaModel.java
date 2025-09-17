package br.net.technet.cipa.Uteis;

public class CipaModel {

    public CipaModel(){}

    public String getTexto_cipa_descricao() {
        return texto_cipa_descricao;
    }

    private String texto_cipa_descricao;

    public CipaModel(String textoCipaDescricao) {
        texto_cipa_descricao = textoCipaDescricao;
    }
}
