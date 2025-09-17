package br.net.technet.cipa.Uteis;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserModel {

    public UserModel(String administrador, String email, String foto, String gestao, String nome,
                     String ocupacao, String password, String posicao, String presidencia) {
        this.administrador = administrador;
        this.email = email;
        this.foto = foto;
        this.gestao = gestao;
        this.nome = nome;
        this.ocupacao = ocupacao;
        this.password = password;
        this.posicao = posicao;
        this.presidencia = presidencia;
    }

    public UserModel() {}

    private String nome;
    private String email;
    private String password;
    private String posicao;
    private String foto;
    private String ocupacao;
    private String gestao;
    private String presidencia;
    private String administrador;

    public String getAdministrador() {
        return administrador;
    }

    public void setAdministrador(String administrador) {
        this.administrador = administrador;
    }

    public String getPresidencia() {
        return presidencia;
    }

    public void setPresidencia(String presidencia) {
        this.presidencia = presidencia;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGestao() {
        return gestao;
    }

    public void setGestao(String gestao) {
        this.gestao = gestao;
    }

    public String getOcupacao() {
        return ocupacao;
    }

    public void setOcupacao(String ocupacao) {
        this.ocupacao = ocupacao;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getFoto() {
        return foto;
    }

    public UserModel(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;

    public String getPosicao() {
        return posicao;
    }

    public void setPosicao(String posicao) {
        this.posicao = posicao;
    }

    public void salvar() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("usuarios").child(getId()).setValue(this);
    }
}
