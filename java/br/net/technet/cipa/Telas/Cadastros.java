package br.net.technet.cipa.Telas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import br.net.technet.cipa.Adapters.MainAdapterCadastros;
import br.net.technet.cipa.Home.Splash_Sair;
import br.net.technet.cipa.R;
import br.net.technet.cipa.Users.Usuario;
import br.net.technet.cipa.Uteis.UserModel;

public class Cadastros extends AppCompatActivity {

    Button btn_cadastros_voltar, btn_cadastros_sair;

    TextView tv_cadastros_vazio;

    RecyclerView recyclerView;
    MainAdapterCadastros mainAdapterCadastros;
    SharedPreferences sharedPreferences = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastros);
        Objects.requireNonNull(getSupportActionBar()).hide();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_cadastros);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btn_cadastros_voltar = findViewById(R.id.btn_cadastros_voltar);
        btn_cadastros_sair = findViewById(R.id.btn_cadastros_sair);

        tv_cadastros_vazio = findViewById(R.id.tv_cadastros_vazio);

        Intent intent = getIntent();
        String administrador = intent.getStringExtra("administrador");
        String nomeAdm = intent.getStringExtra("nomeAdm");
        String emailAdm = intent.getStringExtra("emailAdm");
        String posicaoAdm = intent.getStringExtra("posicaoAdm");

        FirebaseRecyclerOptions<UserModel> options = new FirebaseRecyclerOptions.Builder<UserModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference()
                        .child("usuarios").child("confirmar_cadastro"), UserModel.class).build();

        mainAdapterCadastros = new MainAdapterCadastros(options);
        recyclerView.setAdapter(mainAdapterCadastros);

        sharedPreferences = getSharedPreferences("night", 0);

        btn_cadastros_voltar.setOnClickListener(v -> {

            Intent i = new Intent(Cadastros.this, Usuario.class);
            i.putExtra("administrador", administrador);
            i.putExtra("nomeAdm", nomeAdm);
            i.putExtra("emailAdm", emailAdm);
            i.putExtra("posicaoAdm", posicaoAdm);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
            finish();

        });

        btn_cadastros_sair.setOnClickListener(v -> {

            FirebaseAuth.getInstance().signOut();

            Intent i = new Intent(Cadastros.this, Splash_Sair.class);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();

        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainAdapterCadastros.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainAdapterCadastros.stopListening();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainAdapterCadastros.stopListening();
    }
}