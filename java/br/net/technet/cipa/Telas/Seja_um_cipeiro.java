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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import br.net.technet.cipa.Home.Cadastrar;
import br.net.technet.cipa.Home.Splash_Sair;
import br.net.technet.cipa.R;
import br.net.technet.cipa.Users.Usuario;
import br.net.technet.cipa.Uteis.SejaUmCipeiroModel;

public class Seja_um_cipeiro extends AppCompatActivity {

    Button btn_seja_um_cipeiro_cadastrar, btn_seja_um_cipeiro_voltar, btn_seja_um_cipeiro_sair,
            btn_seja_um_cipeiro_limpar_descricao, btn_seja_um_cipeiro_atualizar_descricao;

    WebView webv_seja_um_cipeiro_i, webv_seja_um_cipeiro_ii, webv_seja_um_cipeiro_iii, webv_seja_um_cipeiro_iv,
            webv_seja_um_cipeiro_v, webv_seja_um_cipeiro_vi, webv_seja_um_cipeiro_vii;
    TextView tv_seja_um_cipeiro_mandato, tv_seja_um_cipeiro_gestao, tv_seja_um_cipeiro_eleicao,
            tv_seja_um_cipeiro_eleicao_i, tv_seja_um_cipeiro_eleicao_ii, tv_seja_um_cipeiro_eleicao_iii;
    EditText et_seja_um_cipeiro_mandato, et_seja_um_cipeiro_gestao, et_seja_um_cipeiro_eleicao,
            et_seja_um_cipeiro_eleicao_iii;

    SwitchCompat switchMode_seja_um_cipeiro_eleicoes;
    LinearLayout ll_seja_um_cipeiro_editar_gestao_i;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ValueEventListener valueEventListener;
    SharedPreferences sharedPreferences = null;

    String id, mandato, gestao, eleicao, eleicao_iii;
    int ativar_eleicao;
    boolean booleanValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seja_um_cipeiro);
        Objects.requireNonNull(getSupportActionBar()).hide();

        btn_seja_um_cipeiro_cadastrar = findViewById(R.id.btn_seja_um_cipeiro_cadastrar);
        btn_seja_um_cipeiro_voltar = findViewById(R.id.btn_seja_um_cipeiro_voltar);
        btn_seja_um_cipeiro_sair = findViewById(R.id.btn_seja_um_cipeiro_sair);
        btn_seja_um_cipeiro_limpar_descricao = findViewById(R.id.btn_seja_um_cipeiro_limpar_descricao);
        btn_seja_um_cipeiro_atualizar_descricao = findViewById(R.id.btn_seja_um_cipeiro_atualizar_descricao);

        webv_seja_um_cipeiro_i = findViewById(R.id.webv_seja_um_cipeiro_i);
        webv_seja_um_cipeiro_ii = findViewById(R.id.webv_seja_um_cipeiro_ii);
        webv_seja_um_cipeiro_iii = findViewById(R.id.webv_seja_um_cipeiro_iii);
        webv_seja_um_cipeiro_iv = findViewById(R.id.webv_seja_um_cipeiro_iv);
        webv_seja_um_cipeiro_v = findViewById(R.id.webv_seja_um_cipeiro_v);
        webv_seja_um_cipeiro_vi = findViewById(R.id.webv_seja_um_cipeiro_vi);
        webv_seja_um_cipeiro_vii = findViewById(R.id.webv_seja_um_cipeiro_vii);

        tv_seja_um_cipeiro_mandato = findViewById(R.id.tv_seja_um_cipeiro_mandato);
        tv_seja_um_cipeiro_gestao = findViewById(R.id.tv_seja_um_cipeiro_gestao);
        tv_seja_um_cipeiro_eleicao = findViewById(R.id.tv_seja_um_cipeiro_eleicao);
        tv_seja_um_cipeiro_eleicao_i = findViewById(R.id.tv_seja_um_cipeiro_eleicao_i);
        tv_seja_um_cipeiro_eleicao_ii = findViewById(R.id.tv_seja_um_cipeiro_eleicao_ii);
        tv_seja_um_cipeiro_eleicao_iii = findViewById(R.id.tv_seja_um_cipeiro_eleicao_iii);

        et_seja_um_cipeiro_mandato = findViewById(R.id.et_seja_um_cipeiro_mandato);
        et_seja_um_cipeiro_gestao = findViewById(R.id.et_seja_um_cipeiro_gestao);
        et_seja_um_cipeiro_eleicao = findViewById(R.id.et_seja_um_cipeiro_eleicao);
        et_seja_um_cipeiro_eleicao_iii = findViewById(R.id.et_seja_um_cipeiro_eleicao_iii);

        switchMode_seja_um_cipeiro_eleicoes = findViewById(R.id.switchMode_seja_um_cipeiro_eleicoes);
        ll_seja_um_cipeiro_editar_gestao_i = findViewById(R.id.ll_seja_um_cipeiro_editar_gestao_i);

        btn_seja_um_cipeiro_cadastrar.setVisibility(View.GONE);
        ll_seja_um_cipeiro_editar_gestao_i.setVisibility(View.GONE);

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

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("seja_um_cipeiro");

        String webv_seja_um_cipeiro_texto_i = "<html><body>" + "<p align=\"justify\">"
                + "<font color = \"green\" size =\"4\" face =\"Verdana\">"
                + getString(R.string.vantagem_de_ser_cipeiro_i)
                + "</font>" + "</p>" + "</body></html>";

        webv_seja_um_cipeiro_i.setBackgroundColor(0);
        webv_seja_um_cipeiro_i.loadData(webv_seja_um_cipeiro_texto_i, "text/html", "utf-8");

        String webv_seja_um_cipeiro_texto_ii = "<html><body>" + "<p align=\"justify\">"
                + "<font color = \"green\" size =\"4\" face =\"Verdana\">"
                + getString(R.string.vantagem_de_ser_cipeiro_ii)
                + "</font>" + "</p>" + "</body></html>";

        webv_seja_um_cipeiro_ii.setBackgroundColor(0);
        webv_seja_um_cipeiro_ii.loadData(webv_seja_um_cipeiro_texto_ii, "text/html", "utf-8");

        String webv_seja_um_cipeiro_texto_iii = "<html><body>" + "<p align=\"justify\">"
                + "<font color = \"green\" size =\"4\" face =\"Verdana\">"
                + getString(R.string.vantagem_de_ser_cipeiro_iii)
                + "</font>" + "</p>" + "</body></html>";

        webv_seja_um_cipeiro_iii.setBackgroundColor(0);
        webv_seja_um_cipeiro_iii.loadData(webv_seja_um_cipeiro_texto_iii, "text/html", "utf-8");

        String webv_seja_um_cipeiro_texto_iv = "<html><body>" + "<p align=\"justify\">"
                + "<font color = \"green\" size =\"4\" face =\"Verdana\">"
                + getString(R.string.vantagem_de_ser_cipeiro_iv)
                + "</font>" + "</p>" + "</body></html>";

        webv_seja_um_cipeiro_iv.setBackgroundColor(0);
        webv_seja_um_cipeiro_iv.loadData(webv_seja_um_cipeiro_texto_iv, "text/html", "utf-8");

        String webv_seja_um_cipeiro_texto_v = "<html><body>" + "<p align=\"justify\">"
                + "<font color = \"green\" size =\"4\" face =\"Verdana\">"
                + getString(R.string.vantagem_de_ser_cipeiro_v)
                + "</font>" + "</p>" + "</body></html>";

        webv_seja_um_cipeiro_v.setBackgroundColor(0);
        webv_seja_um_cipeiro_v.loadData(webv_seja_um_cipeiro_texto_v, "text/html", "utf-8");

        String webv_seja_um_cipeiro_texto_vi = "<html><body>" + "<p align=\"justify\">"
                + "<font color = \"green\" size =\"4\" face =\"Verdana\">"
                + getString(R.string.vantagem_de_ser_cipeiro_vi)
                + "</font>" + "</p>" + "</body></html>";

        webv_seja_um_cipeiro_vi.setBackgroundColor(0);
        webv_seja_um_cipeiro_vi.loadData(webv_seja_um_cipeiro_texto_vi, "text/html", "utf-8");

        String webv_seja_um_cipeiro_texto_vii = "<html><body>" + "<p align=\"justify\">"
                + "<font color = \"green\" size =\"4\" face =\"Verdana\">"
                + getString(R.string.vantagem_de_ser_cipeiro_vii)
                + "</font>" + "</p>" + "</body></html>";

        webv_seja_um_cipeiro_vii.setBackgroundColor(0);
        webv_seja_um_cipeiro_vii.loadData(webv_seja_um_cipeiro_texto_vii, "text/html", "utf-8");

        sharedPreferences = getSharedPreferences("night", 0);

        if (administrador != null) {

            administradorAtivarEleicao();
        }

        ativarEleicao();
        inicializeEdicao();

        if (posicaoVisitante != null) {

            btn_seja_um_cipeiro_cadastrar.setVisibility(View.VISIBLE);
        }

        btn_seja_um_cipeiro_limpar_descricao.setOnClickListener(v -> {

            FirebaseDatabase.getInstance().getReference().child("seja_um_cipeiro").removeValue();
        });

        btn_seja_um_cipeiro_atualizar_descricao.setOnClickListener(v -> {

            mandato = et_seja_um_cipeiro_mandato.getText().toString();
            gestao = et_seja_um_cipeiro_gestao.getText().toString();
            eleicao = et_seja_um_cipeiro_eleicao.getText().toString();
            eleicao_iii = et_seja_um_cipeiro_eleicao_iii.getText().toString();

            id = databaseReference.push().getKey();
            SejaUmCipeiroModel sejaUmCipeiroModel = new SejaUmCipeiroModel(mandato, gestao, eleicao, eleicao_iii, ativar_eleicao);

            databaseReference.child(id).setValue(sejaUmCipeiroModel);
            Toast.makeText(Seja_um_cipeiro.this, "Descrição atualizada com sucesso",
                    Toast.LENGTH_LONG).show();

            et_seja_um_cipeiro_mandato.setText("");
            et_seja_um_cipeiro_gestao.setText("");
            et_seja_um_cipeiro_eleicao.setText("");
            et_seja_um_cipeiro_eleicao_iii.setText("");

            inicializeEdicao();
        });

        btn_seja_um_cipeiro_cadastrar.setOnClickListener(v -> {

            Intent i = new Intent(Seja_um_cipeiro.this, Cadastrar.class);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();

        });

        btn_seja_um_cipeiro_voltar.setOnClickListener(v -> {

            if (administrador != null) {

                Intent i = new Intent(Seja_um_cipeiro.this, Usuario.class);
                i.putExtra("administrador", administrador);
                i.putExtra("nomeAdm", nomeAdm);
                i.putExtra("emailAdm", emailAdm);
                i.putExtra("posicaoAdm", posicaoAdm);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
                finish();

            } else if (emailSesmt != null) {

                Intent i = new Intent(Seja_um_cipeiro.this, Usuario.class);
                i.putExtra("nomeSesmt", nomeSesmt);
                i.putExtra("emailSesmt", emailSesmt);
                i.putExtra("posicaoSesmt", posicaoSesmt);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
                finish();

            } else if (emailCipeiro != null) {

                Intent i = new Intent(Seja_um_cipeiro.this, Usuario.class);
                i.putExtra("nomeCipeiro", nomeCipeiro);
                i.putExtra("emailCipeiro", emailCipeiro);
                i.putExtra("posicaoCipeiro", posicaoCipeiro);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
                finish();

            } else if (emailColaborador != null) {

                Intent i = new Intent(Seja_um_cipeiro.this, Usuario.class);
                i.putExtra("nomeColaborador", nomeColaborador);
                i.putExtra("emailColaborador", emailColaborador);
                i.putExtra("posicaoColaborador", posicaoColaborador);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
                finish();

            } else if (emailConfirmar != null) {

                Intent i = new Intent(Seja_um_cipeiro.this, Usuario.class);
                i.putExtra("nomeConfirmar", nomeConfirmar);
                i.putExtra("emailConfirmar", emailConfirmar);
                i.putExtra("posicaoConfirmar", posicaoConfirmar);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
                finish();

            } else {

                Intent i = new Intent(Seja_um_cipeiro.this, Usuario.class);
                i.putExtra("posicaoVisitante", posicaoVisitante);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
                finish();
            }

        });

        btn_seja_um_cipeiro_sair.setOnClickListener(v -> {

            FirebaseAuth.getInstance().signOut();

            Intent i = new Intent(Seja_um_cipeiro.this, Splash_Sair.class);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();

        });
    }

    private void ativarEleicao() {

        databaseReference = firebaseDatabase.getReference().child("seja_um_cipeiro");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        SejaUmCipeiroModel sejaUmCipeiroModel = dataSnapshot.getValue(SejaUmCipeiroModel.class);

                        assert sejaUmCipeiroModel != null;

                        ativar_eleicao = sejaUmCipeiroModel.getAtivar_eleicao();

                        if (ativar_eleicao == 1) {

                            tv_seja_um_cipeiro_eleicao_i.setVisibility(View.VISIBLE);
                            tv_seja_um_cipeiro_eleicao_ii.setVisibility(View.VISIBLE);
                            tv_seja_um_cipeiro_eleicao_iii.setVisibility(View.VISIBLE);
                            tv_seja_um_cipeiro_eleicao_iii.setText(sejaUmCipeiroModel.getEleicao_iii());

                        } else {

                            tv_seja_um_cipeiro_eleicao_i.setVisibility(View.GONE);
                            tv_seja_um_cipeiro_eleicao_ii.setVisibility(View.GONE);
                            tv_seja_um_cipeiro_eleicao_iii.setVisibility(View.GONE);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(Seja_um_cipeiro.this, "Falha ao carregar WebView", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void administradorAtivarEleicao() {

        sharedPreferences = getSharedPreferences("eleicoes_cipa", 0);
        booleanValue = sharedPreferences.getBoolean("eleicoes_cipa_ativa", true);

            ll_seja_um_cipeiro_editar_gestao_i.setVisibility(View.VISIBLE);

            if (booleanValue) {

                switchMode_seja_um_cipeiro_eleicoes.setChecked(true);
            }

            switchMode_seja_um_cipeiro_eleicoes.setOnCheckedChangeListener((buttonView, isChecked) -> {

                if (isChecked) {

                    switchMode_seja_um_cipeiro_eleicoes.setChecked(true);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("eleicoes_cipa_ativa", true);
                    editor.commit();
                    ativar_eleicao = 1;

                } else {

                    switchMode_seja_um_cipeiro_eleicoes.setChecked(false);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("eleicoes_cipa_ativa", false);
                    editor.commit();
                    ativar_eleicao = 0;
                }
            });
    }

    private void inicializeEdicao() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        SejaUmCipeiroModel sejaUmCipeiroModel = dataSnapshot.getValue(SejaUmCipeiroModel.class);

                        assert sejaUmCipeiroModel != null;
                        tv_seja_um_cipeiro_mandato.setText(sejaUmCipeiroModel.getMandato());
                        tv_seja_um_cipeiro_gestao.setText(sejaUmCipeiroModel.getGestao());
                        tv_seja_um_cipeiro_eleicao.setText(sejaUmCipeiroModel.getEleicao());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(Seja_um_cipeiro.this, "Falha ao carregar dados", Toast.LENGTH_LONG).show();
            }
        });
    }
}