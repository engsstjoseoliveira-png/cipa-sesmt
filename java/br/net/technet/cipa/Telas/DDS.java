package br.net.technet.cipa.Telas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;
import java.util.Random;

import br.net.technet.cipa.Home.Cadastrar;
import br.net.technet.cipa.Home.Splash_Sair;
import br.net.technet.cipa.R;
import br.net.technet.cipa.Users.Usuario;
import de.hdodenhof.circleimageview.CircleImageView;

public class DDS extends AppCompatActivity {

    Button btn_dds_cadastrar, btn_dds_voltar, btn_dds_sair, btn_dds_dica;

    TextView tv_dds_dica;

    CircleImageView civ_foto_dds;
    WebView webv_dds_dica;

    SharedPreferences sharedPreferences =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dds);
        Objects.requireNonNull(getSupportActionBar()).hide();

        btn_dds_cadastrar = findViewById(R.id.btn_dds_cadastrar);
        btn_dds_voltar = findViewById(R.id.btn_dds_voltar);
        btn_dds_sair = findViewById(R.id.btn_dds_sair);
        btn_dds_dica = findViewById(R.id.btn_dds_dica);
        tv_dds_dica = findViewById(R.id.tv_dds_dica);

        webv_dds_dica = findViewById(R.id.webv_dds_dica);
        civ_foto_dds = findViewById(R.id.civ_foto_dds);
        btn_dds_cadastrar.setVisibility(View.GONE);

        Intent intent = getIntent();
        String administrador = intent.getStringExtra("administrador");
        String nomeAdm = intent.getStringExtra("nomeAdm");
        String emailAdm = intent.getStringExtra("emailAdm");
        String posicaoAdm = intent.getStringExtra("posicaoAdm");
        String nomeSesmt = intent.getStringExtra("nomeSesmt");
        String emailSesmt = intent.getStringExtra("emailSesmt");
        String posicaoSesmt = intent.getStringExtra("posicaoSesmt");
        String nomeCipeiro = intent.getStringExtra("nomeCipeiro");
        String emailCipeiro = intent.getStringExtra("emailCipeiro");
        String posicaoCipeiro = intent.getStringExtra("posicaoCipeiro");
        String nomeColaborador = intent.getStringExtra("nomeColaborador");
        String emailColaborador = intent.getStringExtra("emailColaborador");
        String posicaoColaborador = intent.getStringExtra("posicaoColaborador");
        String nomeConfirmar = intent.getStringExtra("nomeConfirmar");
        String emailConfirmar = intent.getStringExtra("emailConfirmar");
        String posicaoConfirmar = intent.getStringExtra("posicaoConfirmar");
        String posicaoVisitante = intent.getStringExtra("posicaoVisitante");

        sharedPreferences = getSharedPreferences("night", 0);


        if (posicaoVisitante != null) {

            btn_dds_cadastrar.setVisibility(View.VISIBLE);
        }

        webviewDicas();

        btn_dds_dica.setOnClickListener(v -> {

            webviewDicas();

        });

        btn_dds_cadastrar.setOnClickListener(v -> {

            Intent i = new Intent(DDS.this, Cadastrar.class);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();

        });

        btn_dds_voltar.setOnClickListener(v -> {

            if (administrador != null) {

                Intent i = new Intent(DDS.this, Usuario.class);
                i.putExtra("administrador", administrador);
                i.putExtra("nomeAdm", nomeAdm);
                i.putExtra("emailAdm", emailAdm);
                i.putExtra("posicaoAdm", posicaoAdm);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
                finish();

            } else if (emailSesmt != null) {

                Intent i = new Intent(DDS.this, Usuario.class);
                i.putExtra("nomeSesmt", nomeSesmt);
                i.putExtra("emailSesmt", emailSesmt);
                i.putExtra("posicaoSesmt", posicaoSesmt);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
                finish();

            } else if (emailCipeiro != null) {

                Intent i = new Intent(DDS.this, Usuario.class);
                i.putExtra("nomeCipeiro", nomeCipeiro);
                i.putExtra("emailCipeiro", emailCipeiro);
                i.putExtra("posicaoCipeiro", posicaoCipeiro);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
                finish();

            } else if (emailColaborador != null) {

                Intent i = new Intent(DDS.this, Usuario.class);
                i.putExtra("nomeColaborador", nomeColaborador);
                i.putExtra("emailColaborador", emailColaborador);
                i.putExtra("posicaoColaborador", posicaoColaborador);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
                finish();

            } else if (emailConfirmar != null) {

                Intent i = new Intent(DDS.this, Usuario.class);
                i.putExtra("nomeConfirmar", nomeConfirmar);
                i.putExtra("emailConfirmar", emailConfirmar);
                i.putExtra("posicaoConfirmar", posicaoConfirmar);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
                finish();

            } else {

                Intent i = new Intent(DDS.this, Usuario.class);
                i.putExtra("posicaoVisitante", posicaoVisitante);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
                finish();
            }

        });
        btn_dds_sair.setOnClickListener(v -> {

            FirebaseAuth.getInstance().signOut();

            Intent i = new Intent(DDS.this, Splash_Sair.class);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();

        });
    }

    private void webviewDicas() {

        String dicaUm = "<html><body>" + "<p align=\"justify\">"
                + "<font color = \"green\" size =\"4\" face =\"Verdana\">"
                + getString(R.string.dica_diaria_de_seguranca_um)
                + "</font>" + "</p>" + "</body></html>";

        String dica_dois = "<html><body>" + "<p align=\"justify\">"
                + "<font color = \"green\" size = \"4\" face =\"verdana\">"
                + getString(R.string.dica_diaria_de_seguranca_doisdois)
                + "</font>" + "</p>" + "</body></html>";

        String dicaTres = "<html><body>" + "<p align=\"justify\">"
                + "<font color = \"green\" size =\"4\" face =\"Verdana\">"
                + getString(R.string.dica_diaria_de_seguranca_tres)
                + "</font>" + "</p>" + "</body></html>";

        String dicaQuatro = "<html><body>" + "<p align=\"justify\">"
                + "<font color = \"green\" size =\"4\" face =\"Verdana\">"
                + getString(R.string.dica_diaria_de_seguranca_quatro)
                + "</font>" + "</p>" + "</body></html>";

        String dicaCinco = "<html><body>" + "<p align=\"justify\">"
                + "<font color = \"green\" size =\"4\" face =\"Verdana\">"
                + getString(R.string.dica_diaria_de_seguranca_cinco)
                + "</font>" + "</p>" + "</body></html>";

        String dicaSeis = "<html><body>" + "<p align=\"justify\">"
                + "<font color = \"green\" size =\"4\" face =\"Verdana\">"
                + getString(R.string.dica_diaria_de_seguranca_seis)
                + "</font>" + "</p>" + "</body></html>";

        String dica_sete = "<html><body>" + "<p align=\"justify\">"
                + "<font color=\"green\" size=\"4\" face=\"vernada\">"
                + getString(R.string.dica_sete)
                + "</font>" + "</p>" + "</body></html>";

        String dicaOito = "<html><body>" + "<p align=\"justify\">"
                + "<font color = \"green\" size =\"4\" face =\"Verdana\">"
                + getString(R.string.dica_diaria_de_seguranca_oito)
                + "</font>" + "</p>" + "</body></html>";

        String dicaNove = "<html><body>" + "<p align=\"justify\">"
                + "<font color = \"green\" size =\"4\" face =\"Verdana\">"
                + getString(R.string.dica_diaria_de_seguranca_nove)
                + "</font>" + "</p>" + "</body></html>";

        String dicaDez = "<html><body>" + "<p align=\"justify\">"
                + "<font color = \"green\" size =\"4\" face =\"Verdana\">"
                + getString(R.string.dica_diaria_de_seguranca_dez)
                + "</font>" + "</p>" + "</body></html>";

        String dicaOnze = "<html><body>" + "<p align=\"justify\">"
                + "<font color = \"green\" size =\"4\" face =\"Verdana\">"
                + getString(R.string.dica_diaria_de_seguranca_onze)
                + "</font>" + "</p>" + "</body></html>";

        String dicaDoze = "<html><body>" + "<p align=\"justify\">"
                + "<font color = \"green\" size =\"4\" face =\"Verdana\">"
                + getString(R.string.dica_diaria_de_seguranca_doze)
                + "</font>" + "</p>" + "</body></html>";

        Random r = new Random();
        int dicaSST;

        dicaSST = r.nextInt(12);

        if (dicaSST == 1) {

            String treinamento = "Campanhas e Treinamentos";
            tv_dds_dica.setText(treinamento);

            webv_dds_dica.setBackgroundColor(0);
            webv_dds_dica.loadData(dicaUm, "text/html", "utf-8");
            Glide.with(DDS.this)
                    .load(R.drawable.treinamento_dds)
                    .into(civ_foto_dds);

        } else if (dicaSST == 2) {

            String maquinas = "Você está no controle!";
            tv_dds_dica.setText(maquinas);

            webv_dds_dica.setBackgroundColor(0);
            webv_dds_dica.loadData(dica_dois, "text/html", "utf-8");
            Glide.with(DDS.this)
                    .load(R.drawable.voce_manda_nas_maquinas_dds)
                    .into(civ_foto_dds);

        } else if (dicaSST == 3) {

            String incendio = "E se tiver um incêndio?";
            tv_dds_dica.setText(incendio);

            webv_dds_dica.setBackgroundColor(0);
            webv_dds_dica.loadData(dicaTres, "text/html", "utf-8");
            Glide.with(DDS.this)
                    .load(R.drawable.risco_de_incendio_dds)
                    .into(civ_foto_dds);

        } else if (dicaSST == 4) {

            String seja_um_cipeiro = "Quer ser um cipeiro?";
            tv_dds_dica.setText(seja_um_cipeiro);

            webv_dds_dica.setBackgroundColor(0);
            webv_dds_dica.loadData(dicaQuatro, "text/html", "utf-8");
            Glide.with(DDS.this)
                    .load(R.drawable.cipa_dds)
                    .into(civ_foto_dds);

        } else if (dicaSST == 5) {

            String piso_molhado = "Cuidado! Piso molhado";
            tv_dds_dica.setText(piso_molhado);

            webv_dds_dica.setBackgroundColor(0);
            webv_dds_dica.loadData(dicaCinco, "text/html", "utf-8");
            Glide.with(DDS.this)
                    .load(R.drawable.piso_molhado_dds)
                    .into(civ_foto_dds);

        } else if (dicaSST == 6) {

            String ergonomia = "Sabe o que é Ergonomia?";
            tv_dds_dica.setText(ergonomia);

            webv_dds_dica.setBackgroundColor(0);
            webv_dds_dica.loadData(dicaSeis, "text/html", "utf-8");
            Glide.with(DDS.this)
                    .load(R.drawable.ergonomia_dds)
                    .into(civ_foto_dds);

        } else if (dicaSST == 7) {

            String nao_brinque = "Aqui não é a 5ª Série...";
            tv_dds_dica.setText(nao_brinque);

            webv_dds_dica.setBackgroundColor(0);
            webv_dds_dica.loadData(dica_sete, "text/html", "utf-8");
            Glide.with(DDS.this)
                    .load(R.drawable.nao_brinque_em_servico_dds)
                    .into(civ_foto_dds);

        } else if (dicaSST == 8) {

            String emergencia = "Qual é o plano?";
            tv_dds_dica.setText(emergencia);

            webv_dds_dica.setBackgroundColor(0);
            webv_dds_dica.loadData(dicaOito, "text/html", "utf-8");
            Glide.with(DDS.this)
                    .load(R.drawable.plano_de_emergencia_dds)
                    .into(civ_foto_dds);

        } else if (dicaSST == 9) {

            String conforto = "Conforto e Segurança";
            tv_dds_dica.setText(conforto);

            webv_dds_dica.setBackgroundColor(0);
            webv_dds_dica.loadData(dicaNove, "text/html", "utf-8");
            Glide.with(DDS.this)
                    .load(R.drawable.condicoes_ambientais_trabalho_dds)
                    .into(civ_foto_dds);

        } else if (dicaSST == 10) {

            String nao_tenha_pressa = "Não tenha pressa!";
            tv_dds_dica.setText(nao_tenha_pressa);

            webv_dds_dica.setBackgroundColor(0);
            webv_dds_dica.loadData(dicaDez, "text/html", "utf-8");
            Glide.with(DDS.this)
                    .load(R.drawable.nao_tenha_pressa_dds)
                    .into(civ_foto_dds);

        } else if (dicaSST == 11) {

            String gambiarra = "Gambiarra, aqui não!";
            tv_dds_dica.setText(gambiarra);

            webv_dds_dica.setBackgroundColor(0);
            webv_dds_dica.loadData(dicaOnze, "text/html", "utf-8");
            Glide.with(DDS.this)
                    .load(R.drawable.gambiarra_dds)
                    .into(civ_foto_dds);

        } else {

            String regras_de_ouro = "Respeite as Regras de Ouro!";
            tv_dds_dica.setText(regras_de_ouro);

            webv_dds_dica.setBackgroundColor(0);
            webv_dds_dica.loadData(dicaDoze, "text/html", "utf-8");
            Glide.with(DDS.this)
                    .load(R.drawable.regras_de_ouro_dds)
                    .into(civ_foto_dds);

        }
    }
}