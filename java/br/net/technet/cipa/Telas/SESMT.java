package br.net.technet.cipa.Telas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
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
import br.net.technet.cipa.Uteis.SesmtModel;
import br.net.technet.cipa.Uteis.UserModel;

public class SESMT extends AppCompatActivity {

    Button btn_sesmt_limpar_descricao, btn_sesmt_atualizar_descricao, btn_sesmt_voltar, btn_sesmt_sair,
            btn_sesmt_limpar_colaboradores, btn_sesmt_atualizar_colaboradores;
    EditText et_sesmt_editar_descricao, et_sesmt_editar_colaborador_1, et_sesmt_editar_colaborador_2,
            et_sesmt_editar_colaborador_3, et_sesmt_editar_horario, et_sesmt_editar_telefone,
            et_sesmt_editar_email;
    WebView wv_sesmt_descricao;
    LinearLayout ll_sesmt_editar_descricao;
    RecyclerView recyclerView;
    MainAdapterCipa mainAdapterCipa;
    MainAdapterAdm mainAdapterAdm;
    SharedPreferences sharedPreferences = null;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ValueEventListener valueEventListener;

    boolean starStopAdapter = false;
    String texto_sesmt_descricao, colaborador_1, colaborador_2, colaborador_3, horario, email, telefone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sesmt);
        Objects.requireNonNull(getSupportActionBar()).hide();

        btn_sesmt_limpar_descricao = findViewById(R.id.btn_sesmt_limpar_descricao);
        btn_sesmt_atualizar_descricao = findViewById(R.id.btn_sesmt_atualizar_descricao);
        btn_sesmt_voltar = findViewById(R.id.btn_sesmt_voltar);
        btn_sesmt_sair = findViewById(R.id.btn_sesmt_sair);
        btn_sesmt_limpar_colaboradores = findViewById(R.id.btn_sesmt_limpar_colaboradores);
        btn_sesmt_atualizar_colaboradores = findViewById(R.id.btn_sesmt_atualizar_colaboradores);
        et_sesmt_editar_descricao = findViewById(R.id.et_sesmt_editar_descricao);
        /*
        et_sesmt_editar_colaborador_1 = findViewById(R.id.et_sesmt_editar_colaborador_1);
        et_sesmt_editar_colaborador_2 = findViewById(R.id.et_sesmt_editar_colaborador_2);
        et_sesmt_editar_colaborador_3 = findViewById(R.id.et_sesmt_editar_colaborador_3);
        et_sesmt_editar_horario = findViewById(R.id.et_sesmt_editar_horario);
        et_sesmt_editar_telefone = findViewById(R.id.et_sesmt_editar_telefone);
        et_sesmt_editar_email = findViewById(R.id.et_sesmt_editar_email);

         */

        wv_sesmt_descricao = (WebView) findViewById(R.id.wv_sesmt_descricao);
        ll_sesmt_editar_descricao = findViewById(R.id.ll_sesmt_editar_descricao);

        //ll_sesmt_editar_divisao = findViewById(R.id.ll_sesmt_editar_divisao);

        Intent intent = getIntent();
        String administrador = intent.getStringExtra("administrador");
        String nomeAdm = intent.getStringExtra("nomeAdm");
        String nomeCipeiro = intent.getStringExtra("nomeCipeiro");
        String nomeColaborador = intent.getStringExtra("nomeColaborador");
        String nomeSesmt = intent.getStringExtra("nomeSesmt");
        String emailSesmt = intent.getStringExtra("emailSesmt");
        String posicaoSesmt = intent.getStringExtra("posicaoSesmt");
        String emailAdm = intent.getStringExtra("emailAdm");
        String emailCipeiro = intent.getStringExtra("emailCipeiro");
        String emailColaborador = intent.getStringExtra("emailColaborador");
        String posicaoAdm = intent.getStringExtra("posicaoAdm");
        String posicaoCipeiro = intent.getStringExtra("posicaoCipeiro");
        String posicaoColaborador = intent.getStringExtra("posicaoColaborador");

        FirebaseRecyclerOptions<UserModel> options = new FirebaseRecyclerOptions.Builder<UserModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference()
                        .child("usuarios").child("sesmt"), UserModel.class).build();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("usuarios");

        sharedPreferences = getSharedPreferences("night", 0);

        ll_sesmt_editar_descricao.setVisibility(View.GONE);

        initializeWebView();

        if (administrador != null) {

            recyclerView = (RecyclerView) findViewById(R.id.rv_sesmt_membros);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            ll_sesmt_editar_descricao.setVisibility(View.VISIBLE);

            mainAdapterAdm = new MainAdapterAdm(options);
            recyclerView.setAdapter(mainAdapterAdm);
            starStopAdapter = true;

        } else {

            recyclerView = (RecyclerView) findViewById(R.id.rv_sesmt_membros);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            mainAdapterCipa = new MainAdapterCipa(options);
            recyclerView.setAdapter(mainAdapterCipa);
            starStopAdapter = false;
        }

        btn_sesmt_limpar_descricao.setOnClickListener(v -> {

            databaseReference = firebaseDatabase.getReference().child("dados")
                    .child("descricao_sesmt");
            FirebaseDatabase.getInstance().getReference().child("dados")
                    .child("descricao_sesmt").removeValue();

        });

        btn_sesmt_atualizar_descricao.setOnClickListener(v -> {

            databaseReference = firebaseDatabase.getReference().child("dados")
                    .child("descricao_sesmt");
            texto_sesmt_descricao = et_sesmt_editar_descricao.getText().toString();
            String id = databaseReference.push().getKey();
            SesmtModel sesmtModel = new SesmtModel(texto_sesmt_descricao, colaborador_1, colaborador_2,
                    colaborador_3, horario, telefone, email);
            databaseReference.child(id).setValue(sesmtModel);

            Toast.makeText(SESMT.this, "Descrição atualizada com sucesso",
                    Toast.LENGTH_LONG).show();

            et_sesmt_editar_descricao.setText("");
            initializeWebView();
        });

        btn_sesmt_limpar_colaboradores.setOnClickListener(v -> {

            databaseReference = firebaseDatabase.getReference().child("dados").child("divisao_sesmt");
            FirebaseDatabase.getInstance().getReference().child("dados")
                    .child("divisao_sesmt").removeValue();
            
        });

        btn_sesmt_atualizar_colaboradores.setOnClickListener(v -> {

            databaseReference = firebaseDatabase.getReference().child("dados").child("divisao_sesmt");
            colaborador_1 = et_sesmt_editar_colaborador_1.getText().toString();
            colaborador_2 = et_sesmt_editar_colaborador_2.getText().toString();
            colaborador_3 = et_sesmt_editar_colaborador_3.getText().toString();
            horario = et_sesmt_editar_horario.getText().toString();
            email = et_sesmt_editar_email.getText().toString();
            telefone = et_sesmt_editar_telefone.getText().toString();

            String id = databaseReference.push().getKey();
            SesmtModel sesmtModel = new SesmtModel(texto_sesmt_descricao, colaborador_1, colaborador_2,
                    colaborador_3, horario, telefone, email);

            databaseReference.child(id).setValue(sesmtModel);

            Toast.makeText(SESMT.this, "Divisão atualizada com sucesso", Toast.LENGTH_LONG).show();
            et_sesmt_editar_colaborador_1.setText("");
            et_sesmt_editar_colaborador_2.setText("");
            et_sesmt_editar_colaborador_3.setText("");
            et_sesmt_editar_horario.setText("");
            et_sesmt_editar_email.setText("");
            et_sesmt_editar_telefone.setText("");
        });

        btn_sesmt_voltar.setOnClickListener(v -> {

            if (administrador != null) {

                Intent i = new Intent(SESMT.this, Usuario.class);
                i.putExtra("administrador", administrador);
                i.putExtra("nomeAdm", nomeAdm);
                i.putExtra("emailAdm", emailAdm);
                i.putExtra("posicaoAdm", posicaoAdm);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
                finish();

            } else if (emailSesmt != null) {

                Intent i = new Intent(SESMT.this, Usuario.class);
                i.putExtra("nomeSesmt", nomeSesmt);
                i.putExtra("emailSesmt", emailSesmt);
                i.putExtra("posicaoSesmt", posicaoSesmt);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
                finish();

            } else if (emailCipeiro != null) {

                Intent i = new Intent(SESMT.this, Usuario.class);
                i.putExtra("nomeCipeiro", nomeCipeiro);
                i.putExtra("emailCipeiro", emailCipeiro);
                i.putExtra("posicaoCipeiro", posicaoCipeiro);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
                finish();

            } else if (emailColaborador != null) {

                Intent i = new Intent(SESMT.this, Usuario.class);
                i.putExtra("nomeColaborador", nomeColaborador);
                i.putExtra("emailColaborador", emailColaborador);
                i.putExtra("posicaoColaborador", posicaoColaborador);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
                finish();

            }

        });

        btn_sesmt_sair.setOnClickListener(v -> {

            FirebaseAuth.getInstance().signOut();

            Intent i = new Intent(SESMT.this, Splash_Sair.class);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();

        });
    }

    private void initializeWebView() {

        databaseReference = firebaseDatabase.getReference().child("dados")
                .child("descricao_sesmt");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        SesmtModel sesmtModel = dataSnapshot.getValue(SesmtModel.class);

                        assert sesmtModel != null;
                        String webV_sesmt_descricao_texto = sesmtModel.getTexto_sesmt_descricao();
                        String webV_sesmt_descricao_texto_format = "<html><body>" + "<p align=\"justify\">"
                                + "<font color = \"green\" size =\"4\" face =\"Verdana\">"
                                + webV_sesmt_descricao_texto
                                + "</font>" + "</p>" + "</body></html>";

                        wv_sesmt_descricao.setBackgroundColor(0);
                        wv_sesmt_descricao.loadData(webV_sesmt_descricao_texto_format, "text/html", "utf-8");
                    }

                } else {

                    Toast.makeText(SESMT.this, "Agora, atualize a descrição do SESMT na organização",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(SESMT.this, "Falha ao carregar WebView", Toast.LENGTH_LONG).show();
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