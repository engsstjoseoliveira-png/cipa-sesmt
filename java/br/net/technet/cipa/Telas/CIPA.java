package br.net.technet.cipa.Telas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import br.net.technet.cipa.Adapters.MainAdapterAdm;
import br.net.technet.cipa.Adapters.MainAdapterCipa;
import br.net.technet.cipa.Home.Splash_Sair;
import br.net.technet.cipa.R;
import br.net.technet.cipa.Users.Usuario;
import br.net.technet.cipa.Uteis.CipaModel;
import br.net.technet.cipa.Uteis.UserModel;

public class CIPA extends AppCompatActivity {

    Button btn_cipa_limpar_descricao, btn_cipa_editar_descricao, btn_porque_cipa_mais_voltar, btn_porque_cipa_mais_sair;
    EditText et_cipa_editar_descricao;
    WebView wv_cipa_descricao;
    LinearLayout ll_cipa_editar_descricao;
    RecyclerView recyclerView;;
    MainAdapterCipa mainAdapterCipa;
    MainAdapterAdm mainAdapterAdm;
    SharedPreferences sharedPreferences = null;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    boolean starStopAdapter = false;
    String texto_cipa_descricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cipa);
        Objects.requireNonNull(getSupportActionBar()).hide();

        btn_cipa_limpar_descricao = findViewById(R.id.btn_cipa_limpar_descricao);
        btn_cipa_editar_descricao = findViewById(R.id.btn_cipa_editar_descricao);
        btn_porque_cipa_mais_voltar = findViewById(R.id.btn_porque_cipa_mais_voltar);
        btn_porque_cipa_mais_sair = findViewById(R.id.btn_porque_cipa_mais_sair);

        et_cipa_editar_descricao = findViewById(R.id.et_cipa_editar_descricao);
        ll_cipa_editar_descricao = findViewById(R.id.ll_cipa_editar_descricao);
        wv_cipa_descricao = (WebView) findViewById(R.id.wv_cipa_descricao);

        ll_cipa_editar_descricao.setVisibility(View.GONE);

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

        FirebaseRecyclerOptions<UserModel> options = new FirebaseRecyclerOptions
                .Builder<UserModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference()
                        .child("usuarios").child("cipeiros"), UserModel.class).build();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("dados")
                .child("descricao_cipa");

        sharedPreferences = getSharedPreferences("night", 0);

        initializeWebView();

        if (administrador != null) {

            recyclerView = (RecyclerView) findViewById(R.id.rv_cipa_cipeiros);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            ll_cipa_editar_descricao.setVisibility(View.VISIBLE);

            mainAdapterAdm = new MainAdapterAdm(options);
            recyclerView.setAdapter(mainAdapterAdm);
            starStopAdapter = true;

        } else {

            recyclerView = (RecyclerView) findViewById(R.id.rv_cipa_cipeiros);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            mainAdapterCipa = new MainAdapterCipa(options);
            recyclerView.setAdapter(mainAdapterCipa);
            starStopAdapter = false;
        }

        btn_cipa_limpar_descricao.setOnClickListener(v -> {

            FirebaseDatabase.getInstance().getReference().child("dados")
                    .child("descricao_cipa").removeValue();
        });

        btn_cipa_editar_descricao.setOnClickListener(v -> {

            texto_cipa_descricao = et_cipa_editar_descricao.getText().toString();
            String id = databaseReference.push().getKey();
            CipaModel cipaModel = new CipaModel(texto_cipa_descricao);

            databaseReference.child(id).setValue(cipaModel);

            Toast.makeText(CIPA.this, "Descrição atualizada com sucesso",
                    Toast.LENGTH_LONG).show();

            et_cipa_editar_descricao.setText("");

            initializeWebView();
        });

        btn_porque_cipa_mais_voltar.setOnClickListener(v -> {

            if (administrador != null) {

                Intent i = new Intent(CIPA.this, Usuario.class);
                i.putExtra("administrador", administrador);
                i.putExtra("nomeAdm", nomeAdm);
                i.putExtra("emailAdm", emailAdm);
                i.putExtra("posicaoAdm", posicaoAdm);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
                finish();

            } else if (emailSesmt != null) {

                Intent i = new Intent(CIPA.this, Usuario.class);
                i.putExtra("nomeSesmt", nomeSesmt);
                i.putExtra("emailSesmt", emailSesmt);
                i.putExtra("posicaoSesmt", posicaoSesmt);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
                finish();

            } else if (emailCipeiro != null) {

                Intent i = new Intent(CIPA.this, Usuario.class);
                i.putExtra("nomeCipeiro", nomeCipeiro);
                i.putExtra("emailCipeiro", emailCipeiro);
                i.putExtra("posicaoCipeiro", posicaoCipeiro);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
                finish();

            } else if (emailColaborador != null) {

                Intent i = new Intent(CIPA.this, Usuario.class);
                i.putExtra("nomeColaborador", nomeColaborador);
                i.putExtra("emailColaborador", emailColaborador);
                i.putExtra("posicaoColaborador", posicaoColaborador);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
                finish();
            }
        });

        btn_porque_cipa_mais_sair.setOnClickListener(v -> {

            FirebaseAuth.getInstance().signOut();

            Intent i = new Intent(CIPA.this, Splash_Sair.class);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();

        });
    }

    private void initializeWebView() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        CipaModel cipaModel = dataSnapshot.getValue(CipaModel.class);

                        assert cipaModel != null;
                        String wv_cipa_descricao_texto = cipaModel.getTexto_cipa_descricao();

                        String wv_cipa_descricao_texto_formatada = "<html><body>" + "<p align=\"justify\">"
                                + "<font color = \"green\" size =\"4\" face =\"Verdana\">"
                                + wv_cipa_descricao_texto
                                + "</font>" + "</p>" + "</body></html>";

                        wv_cipa_descricao.setBackgroundColor(0);
                        wv_cipa_descricao.loadData(wv_cipa_descricao_texto_formatada, "text/html", "utf-8");
                    }

                } else {

                    Toast.makeText(CIPA.this, "Agora, atualize a descrição da CIPA na organização",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(CIPA.this, "Falha ao carregar WebView", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (starStopAdapter) {

            mainAdapterAdm.startListening();
        } else {

            mainAdapterCipa.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (starStopAdapter) {

            mainAdapterAdm.stopListening();
        } else {

            mainAdapterCipa.stopListening();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (starStopAdapter) {

            mainAdapterAdm.stopListening();
        } else {

            mainAdapterCipa.stopListening();
        }
    }
}