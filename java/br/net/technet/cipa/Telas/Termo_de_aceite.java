package br.net.technet.cipa.Telas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import br.net.technet.cipa.Home.Cadastrar;
import br.net.technet.cipa.Home.Splash_Sair;
import br.net.technet.cipa.R;

public class Termo_de_aceite extends AppCompatActivity {

    Button btn_termo_de_aceite_voltar, btn_termo_de_aceite_sair;

    WebView webv_termo_de_aceite;

    SharedPreferences sharedPreferences = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termo_de_aceite);
        Objects.requireNonNull(getSupportActionBar()).hide();

        btn_termo_de_aceite_voltar = findViewById(R.id.btn_termo_de_aceite_voltar);
        btn_termo_de_aceite_sair = findViewById(R.id.btn_termo_de_aceite_sair);

        webv_termo_de_aceite = findViewById(R.id.webv_termo_de_aceite);

        sharedPreferences = getSharedPreferences("night", 0);
        Boolean booleanValue = sharedPreferences.getBoolean("night_mode", true);

        String termo = "<html><body>" + "<p align=\"justify\">"
                + "<font color = \"green\" size =\"4\" face =\"Verdana\">"
                + getString(R.string.termo_de_aceite_activity)
                + "</font>" + "</p>" + "</body></html>";

        webv_termo_de_aceite.setBackgroundColor(0);
        webv_termo_de_aceite.loadData(termo, "text/html", "utf-8");

        btn_termo_de_aceite_voltar.setOnClickListener(v -> {

            Intent i = new Intent(Termo_de_aceite.this, Cadastrar.class);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
            finish();

        });

        btn_termo_de_aceite_sair.setOnClickListener(v -> {

            Intent i = new Intent(Termo_de_aceite.this, Splash_Sair.class);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();

        });
    }
}