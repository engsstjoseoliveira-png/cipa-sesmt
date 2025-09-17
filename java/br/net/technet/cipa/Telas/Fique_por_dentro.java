package br.net.technet.cipa.Telas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import br.net.technet.cipa.Home.Cadastrar;
import br.net.technet.cipa.Home.Splash_Sair;
import br.net.technet.cipa.R;
import br.net.technet.cipa.Users.Usuario;

public class Fique_por_dentro extends AppCompatActivity {

    Button btn_fique_por_dentro_cadastrar, btn_fique_por_dentro_voltar, btn_fique_por_dentro_sair;

    WebView webv_fique_por_dentro_i, webv_fique_por_dentro_ii, webv_fique_por_dentro_iii, webv_fique_por_dentro_iv;

    SharedPreferences sharedPreferences = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fique_por_dentro);
        Objects.requireNonNull(getSupportActionBar()).hide();

        btn_fique_por_dentro_cadastrar = findViewById(R.id.btn_fique_por_dentro_cadastrar);
        btn_fique_por_dentro_voltar = findViewById(R.id.btn_fique_por_dentro_voltar);
        btn_fique_por_dentro_sair = findViewById(R.id.btn_fique_por_dentro_sair);

        webv_fique_por_dentro_i = findViewById(R.id.webv_fique_por_dentro_i);
        webv_fique_por_dentro_ii = findViewById(R.id.webv_fique_por_dentro_ii);
        webv_fique_por_dentro_iii = findViewById(R.id.webv_fique_por_dentro_iii);
        webv_fique_por_dentro_iv = findViewById(R.id.webv_fique_por_dentro_iv);

        btn_fique_por_dentro_cadastrar.setVisibility(View.GONE);

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

        String webv_fique_por_dentro_texto_i = "<html><body>" + "<p align=\"justify\">"
                + "<font color = \"green\" size =\"4\" face =\"Verdana\">"
                + getString(R.string.o_que_e_sst_i)
                + "</font>" + "</p>" + "</body></html>";

        webv_fique_por_dentro_i.setBackgroundColor(0);
        webv_fique_por_dentro_i.loadData(webv_fique_por_dentro_texto_i, "text/html", "utf-8");

        String webv_fique_por_dentro_texto_ii = "<html><body>" + "<p align=\"justify\">"
                + "<font color = \"green\" size =\"4\" face =\"Verdana\">"
                + getString(R.string.o_que_e_sst_iv)
                + "</font>" + "</p>" + "</body></html>";

        webv_fique_por_dentro_ii.setBackgroundColor(0);
        webv_fique_por_dentro_ii.loadData(webv_fique_por_dentro_texto_ii, "text/html", "utf-8");

        String webv_fique_por_dentro_texto_iii = "<html><body>" + "<p align=\"justify\">"
                + "<font color = \"green\" size =\"4\" face =\"Verdana\">"
                + getString(R.string.o_que_e_sst_vi)
                + "</font>" + "</p>" + "</body></html>";

        webv_fique_por_dentro_iii.setBackgroundColor(0);
        webv_fique_por_dentro_iii.loadData(webv_fique_por_dentro_texto_iii, "text/html", "utf-8");

        String webv_fique_por_dentro_texto_iv = "<html><body>" + "<p align=\"justify\">"
                + "<font color = \"green\" size =\"4\" face =\"Verdana\">"
                + getString(R.string.o_que_e_sst_vii)
                + "</font>" + "</p>" + "</body></html>";

        webv_fique_por_dentro_iv.setBackgroundColor(0);
        webv_fique_por_dentro_iv.loadData(webv_fique_por_dentro_texto_iv, "text/html", "utf-8");

        if (posicaoVisitante != null) {
            btn_fique_por_dentro_cadastrar.setVisibility(View.VISIBLE);
        }

        btn_fique_por_dentro_cadastrar.setOnClickListener(v -> {

            Intent i = new Intent(Fique_por_dentro.this, Cadastrar.class);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();

        });
        btn_fique_por_dentro_voltar.setOnClickListener(v -> {

            if (administrador != null) {

                Intent i = new Intent(Fique_por_dentro.this, Usuario.class);
                i.putExtra("administrador", administrador);
                i.putExtra("nomeAdm", nomeAdm);
                i.putExtra("emailAdm", emailAdm);
                i.putExtra("posicaoAdm", posicaoAdm);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
                finish();

            } else if (emailSesmt != null) {

                Intent i = new Intent(Fique_por_dentro.this, Usuario.class);
                i.putExtra("nomeSesmt", nomeSesmt);
                i.putExtra("emailSesmt", emailSesmt);
                i.putExtra("posicaoSesmt", posicaoSesmt);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
                finish();

            } else if (emailCipeiro != null) {

                Intent i = new Intent(Fique_por_dentro.this, Usuario.class);
                i.putExtra("nomeCipeiro", nomeCipeiro);
                i.putExtra("emailCipeiro", emailCipeiro);
                i.putExtra("posicaoCipeiro", posicaoCipeiro);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
                finish();

            } else if (emailColaborador != null) {

                Intent i = new Intent(Fique_por_dentro.this, Usuario.class);
                i.putExtra("nomeColaborador", nomeColaborador);
                i.putExtra("emailColaborador", emailColaborador);
                i.putExtra("posicaoColaborador", posicaoColaborador);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
                finish();

            } else if (emailConfirmar != null) {

                Intent i = new Intent(Fique_por_dentro.this, Usuario.class);
                i.putExtra("nomeConfirmar", nomeConfirmar);
                i.putExtra("emailConfirmar", emailConfirmar);
                i.putExtra("posicaoConfirmar", posicaoConfirmar);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
                finish();

            } else {

                Intent i = new Intent(Fique_por_dentro.this, Usuario.class);
                i.putExtra("posicaoVisitante", posicaoVisitante);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
                finish();
            }

        });
        btn_fique_por_dentro_sair.setOnClickListener(v -> {

            FirebaseAuth.getInstance().signOut();

            Intent i = new Intent(Fique_por_dentro.this, Splash_Sair.class);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();

        });
    }
}