package br.net.technet.cipa.Telas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ShareCompat;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import br.net.technet.cipa.Home.Splash_Sair;
import br.net.technet.cipa.R;
import br.net.technet.cipa.Users.Usuario;

public class Sugestoes extends AppCompatActivity {

    Button btn_sugestoes_voltar, btn_sugestoes_sair, btn_sugestoes_enviar;
    EditText et_sugestao_assunto, et_sugestao_mensagem;

    SharedPreferences sharedPreferences = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sugestoes);
        Objects.requireNonNull(getSupportActionBar()).hide();

        btn_sugestoes_voltar = findViewById(R.id.btn_sugestoes_voltar);
        btn_sugestoes_sair = findViewById(R.id.btn_sugestoes_sair);
        btn_sugestoes_enviar = findViewById(R.id.btn_sugestoes_enviar);

        et_sugestao_assunto = findViewById(R.id.et_sugestao_assunto);
        et_sugestao_mensagem = findViewById(R.id.et_sugestao_mensagem);

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
        String posicaoVisitante = intent.getStringExtra("posicaoVisitante");

        sharedPreferences = getSharedPreferences("night", 0);

        btn_sugestoes_enviar.setOnClickListener(v -> enviarMensagem());

        btn_sugestoes_voltar.setOnClickListener(v -> {

            if (administrador != null) {

                Intent i = new Intent(Sugestoes.this, Usuario.class);
                i.putExtra("administrador", administrador);
                i.putExtra("nomeAdm", nomeAdm);
                i.putExtra("emailAdm", emailAdm);
                i.putExtra("posicaoAdm", posicaoAdm);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
                finish();

            } else if (emailSesmt != null) {

                Intent i = new Intent(Sugestoes.this, Usuario.class);
                i.putExtra("nomeSesmt", nomeSesmt);
                i.putExtra("emailSesmt", emailSesmt);
                i.putExtra("posicaoSesmt", posicaoSesmt);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
                finish();

            } else if (emailCipeiro != null) {

                Intent i = new Intent(Sugestoes.this, Usuario.class);
                i.putExtra("nomeCipeiro", nomeCipeiro);
                i.putExtra("emailCipeiro", emailCipeiro);
                i.putExtra("posicaoCipeiro", posicaoCipeiro);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
                finish();

            } else if (emailColaborador != null) {

                Intent i = new Intent(Sugestoes.this, Usuario.class);
                i.putExtra("nomeColaborador", nomeColaborador);
                i.putExtra("emailColaborador", emailColaborador);
                i.putExtra("posicaoColaborador", posicaoColaborador);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
                finish();

            } else {

                Intent i = new Intent(Sugestoes.this, Usuario.class);
                i.putExtra("posicaoVisitante", posicaoVisitante);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
                finish();
            }

        });
        btn_sugestoes_sair.setOnClickListener(v -> {

            FirebaseAuth.getInstance().signOut();

            Intent i = new Intent(Sugestoes.this, Splash_Sair.class);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();

        });
    }

    private void enviarMensagem() {

        String emailDestinatario = "seu_email@aqui";
        String emailAssunto = et_sugestao_assunto.getText().toString();
        String emailTexto = et_sugestao_mensagem.getText().toString();

        ShareCompat.IntentBuilder.from(Sugestoes.this)
                .setType("message/rfc822")
                .addEmailTo(emailDestinatario)
                .setSubject(emailAssunto)
                .setText(emailTexto)
                .setChooserTitle("Escolha seu provedor")
                .startChooser();

        String numeroTelefone = "seu_telefone_aqui";
        String mensagem = "Envie uma mensagem";

        Uri uri = Uri.parse("sms:" + numeroTelefone);
        Intent i = new Intent(Intent.ACTION_VIEW, uri);
        i.putExtra("sms_body", mensagem);
        startActivity(i);
    }
}