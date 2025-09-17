package br.net.technet.cipa.Uteis;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SipatModel {

    public SipatModel(String imagem, String tema_i, String tema_ii, String tema_iii, String tema_iv,
                      String tema_v, String local_i, String local_ii, String local_iii, String local_iv,
                      String local_v, String dia_i, String dia_ii, String dia_iii, String dia_iv, String dia_v) {
        this.imagem = imagem;
        this.tema_i = tema_i;
        this.tema_ii = tema_ii;
        this.tema_iii = tema_iii;
        this.tema_iv = tema_iv;
        this.tema_v = tema_v;
        this.local_i = local_i;
        this.local_ii = local_ii;
        this.local_iii = local_iii;
        this.local_iv = local_iv;
        this.local_v = local_v;
        this.dia_i = dia_i;
        this.dia_ii = dia_ii;
        this.dia_iii = dia_iii;
        this.dia_iv = dia_iv;
        this.dia_v = dia_v;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getTema_i() {
        return tema_i;
    }

    public void setTema_i(String tema_i) {
        this.tema_i = tema_i;
    }

    public String getTema_ii() {
        return tema_ii;
    }

    public void setTema_ii(String tema_ii) {
        this.tema_ii = tema_ii;
    }

    public String getTema_iii() {
        return tema_iii;
    }

    public void setTema_iii(String tema_iii) {
        this.tema_iii = tema_iii;
    }

    public String getTema_iv() {
        return tema_iv;
    }

    public void setTema_iv(String tema_iv) {
        this.tema_iv = tema_iv;
    }

    public String getTema_v() {
        return tema_v;
    }

    public void setTema_v(String tema_v) {
        this.tema_v = tema_v;
    }

    public String getLocal_i() {
        return local_i;
    }

    public void setLocal_i(String local_i) {
        this.local_i = local_i;
    }

    public String getLocal_ii() {
        return local_ii;
    }

    public void setLocal_ii(String local_ii) {
        this.local_ii = local_ii;
    }

    public String getLocal_iii() {
        return local_iii;
    }

    public void setLocal_iii(String local_iii) {
        this.local_iii = local_iii;
    }

    public String getLocal_iv() {
        return local_iv;
    }

    public void setLocal_iv(String local_iv) {
        this.local_iv = local_iv;
    }

    public String getLocal_v() {
        return local_v;
    }

    public void setLocal_v(String local_v) {
        this.local_v = local_v;
    }

    public String getDia_i() {
        return dia_i;
    }

    public void setDia_i(String dia_i) {
        this.dia_i = dia_i;
    }

    public String getDia_ii() {
        return dia_ii;
    }

    public void setDia_ii(String dia_ii) {
        this.dia_ii = dia_ii;
    }

    public String getDia_iii() {
        return dia_iii;
    }

    public void setDia_iii(String dia_iii) {
        this.dia_iii = dia_iii;
    }

    public String getDia_iv() {
        return dia_iv;
    }

    public void setDia_iv(String dia_iv) {
        this.dia_iv = dia_iv;
    }

    public String getDia_v() {
        return dia_v;
    }

    public void setDia_v(String dia_v) {
        this.dia_v = dia_v;
    }

    private String imagem;
    private String tema_i;
    private String tema_ii;
    private String tema_iii;
    private String tema_iv;
    private String tema_v;
    private String local_i;
    private String local_ii;
    private String local_iii;
    private String local_iv;
    private String local_v;
    private String dia_i;
    private String dia_ii;
    private String dia_iii;
    private String dia_iv;
    private String dia_v;

    public SipatModel() {}

    public void salvar() {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("sipat").child(getTema_i()).setValue(this);
    }
}
