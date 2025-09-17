package br.net.technet.cipa.Uteis;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import br.net.technet.cipa.Home.Splash_Sair;
import br.net.technet.cipa.R;

public class Pesquisa extends AppCompatActivity {

    Button btn_pesquisa_sair;
    WebView wv_googleforms;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_pesquisa);

        btn_pesquisa_sair = findViewById(R.id.btn_pesquisa_sair);
        wv_googleforms = findViewById(R.id.wv_pesquisa_googleforms);

        wv_googleforms.getSettings().setJavaScriptEnabled(true);
        wv_googleforms.setWebViewClient(new WebViewClient());

        //wv_googleforms.loadUrl("https://forms.gle/LhxubPtG3EVpVFWBA");
        wv_googleforms.loadUrl("https://forms.gle/48jBG13PcrucH69h6");

        btn_pesquisa_sair.setOnClickListener(v -> {
            Intent i = new Intent(Pesquisa.this, Splash_Sair.class);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
            finish();
        });
    }
}