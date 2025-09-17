package br.net.technet.cipa.Telas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import br.net.technet.cipa.Home.Cadastrar;
import br.net.technet.cipa.Home.Splash_Sair;
import br.net.technet.cipa.R;
import br.net.technet.cipa.Users.Usuario;

public class Indice_de_acidentes extends AppCompatActivity {

    Button btn_indice_de_acidentes_cadastrar, btn_indice_de_acidentes_voltar, btn_indice_de_acidentes_sair;
    TextView tv_indice_de_acidentes_dias, tv_indice_de_acidentes_recorde;

    SharedPreferences sharedPreferences = null;

    String indice_dias, indice_recorde, indice_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indice_de_acidentes);
        Objects.requireNonNull(getSupportActionBar()).hide();

        btn_indice_de_acidentes_cadastrar = findViewById(R.id.btn_indice_de_acidentes_cadastrar);
        btn_indice_de_acidentes_voltar = findViewById(R.id.btn_indice_de_acidentes_voltar);
        btn_indice_de_acidentes_sair = findViewById(R.id.btn_indice_de_acidentes_sair);

        tv_indice_de_acidentes_dias = findViewById(R.id.tv_indice_de_acidentes_dias);
        tv_indice_de_acidentes_recorde = findViewById(R.id.tv_indice_de_acidentes_recorde);

        btn_indice_de_acidentes_cadastrar.setVisibility(View.GONE);

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
            btn_indice_de_acidentes_cadastrar.setVisibility(View.VISIBLE);
        }

        btn_indice_de_acidentes_cadastrar.setOnClickListener(v -> {

            Intent i = new Intent(Indice_de_acidentes.this, Cadastrar.class);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        });

        btn_indice_de_acidentes_voltar.setOnClickListener(v -> {

            if (administrador != null) {

                Intent i = new Intent(Indice_de_acidentes.this, Usuario.class);
                i.putExtra("administrador", administrador);
                i.putExtra("nomeAdm", nomeAdm);
                i.putExtra("emailAdm", emailAdm);
                i.putExtra("posicaoAdm", posicaoAdm);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
                finish();

            } else if (emailSesmt != null) {

                Intent i = new Intent(Indice_de_acidentes.this, Usuario.class);
                i.putExtra("nomeSesmt", nomeSesmt);
                i.putExtra("emailSesmt", emailSesmt);
                i.putExtra("posicaoSesmt", posicaoSesmt);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
                finish();

            } else if (emailCipeiro != null) {

                Intent i = new Intent(Indice_de_acidentes.this, Usuario.class);
                i.putExtra("nomeCipeiro", nomeCipeiro);
                i.putExtra("emailCipeiro", emailCipeiro);
                i.putExtra("posicaoCipeiro", posicaoCipeiro);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
                finish();

            } else if (emailColaborador != null) {

                Intent i = new Intent(Indice_de_acidentes.this, Usuario.class);
                i.putExtra("nomeColaborador", nomeColaborador);
                i.putExtra("emailColaborador", emailColaborador);
                i.putExtra("posicaoColaborador", posicaoColaborador);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
                finish();

            } else if (emailConfirmar != null) {

                Intent i = new Intent(Indice_de_acidentes.this, Usuario.class);
                i.putExtra("nomeConfirmar", nomeConfirmar);
                i.putExtra("emailConfirmar", emailConfirmar);
                i.putExtra("posicaoConfirmar", posicaoConfirmar);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
                finish();

            } else {

                Intent i = new Intent(Indice_de_acidentes.this, Usuario.class);
                i.putExtra("posicaoVisitante", posicaoVisitante);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
                finish();
            }
        });
        btn_indice_de_acidentes_sair.setOnClickListener(v -> {

            FirebaseAuth.getInstance().signOut();

            Intent i = new Intent(Indice_de_acidentes.this, Splash_Sair.class);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        });

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        indice_data = dateFormat.format(calendar.getTime());
        Date data_inicio = new Date("10/01/2024");
        Date data_fim = new Date(indice_data);
        long diff =  data_fim.getTime() - data_inicio.getTime();
        long seconds = diff / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = (hours / 24);
        indice_dias = String.valueOf(days);
        indice_recorde = "647";
        String frase_dias = "há " + indice_dias + " dias sem acidentes";
        String frase_recorde = "nosso recorde é de " + indice_recorde + " dias";

        String segundos = String.valueOf(indice_dias);

        tv_indice_de_acidentes_dias.setText(frase_dias);
        tv_indice_de_acidentes_recorde.setText(frase_recorde);

    }
}