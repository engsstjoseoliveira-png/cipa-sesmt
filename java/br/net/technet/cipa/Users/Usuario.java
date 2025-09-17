package br.net.technet.cipa.Users;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.viewpager2.widget.ViewPager2;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.net.technet.cipa.Adapters.MainAdapterViewPager;
import br.net.technet.cipa.Adapters.RecyclerViewInterface;
import br.net.technet.cipa.Home.Login;
import br.net.technet.cipa.Telas.CIPA;
import br.net.technet.cipa.Home.Cadastrar;
import br.net.technet.cipa.Telas.Cadastros;
import br.net.technet.cipa.Telas.DDS;
import br.net.technet.cipa.Telas.Fique_por_dentro;
import br.net.technet.cipa.Home.Home;
import br.net.technet.cipa.Telas.Indice_de_acidentes;
import br.net.technet.cipa.R;
import br.net.technet.cipa.Telas.SESMT;
import br.net.technet.cipa.Telas.SIPAT;
import br.net.technet.cipa.Telas.Seja_um_cipeiro;
import br.net.technet.cipa.Home.Splash_Sair;
import br.net.technet.cipa.Telas.Sugestoes;
import br.net.technet.cipa.Uteis.Pesquisa;

public class Usuario extends AppCompatActivity implements RecyclerViewInterface {

    Button btn_usuario_sair, btn_usuario_voltar, btn_usuario_cadastrar, btn_usuario_pesquisa, btn_usuario_gerenciar;

    TextView tv_usuario_seja_bem_vindo_nome, tv_usuario_seja_bem_vindo_email, tv_usuario_cipa_mais;

    ImageView iv2, iv3, iv4, iv5, iv6, iv7, iv8, iv9, iv_usuario_cipa_mais;
    SwitchCompat switchMode_usuarios;
    SharedPreferences sharedPreferences = null;

    private ViewPager2 viewPager2;

    ArrayList<ImageView> imageViews = new ArrayList<>();

    String administrador, nomeAdm, emailAdm, posicaoAdm, nomeCipeiro, emailCipeiro, posicaoCipeiro,
            nomeColaborador, emailColaborador, posicaoColaborador, nomeSesmt, emailSesmt, posicaoSesmt,
            nomeConfirmar, emailConfirmar, posicaoConfirmar, posicaoVisitante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);
        Objects.requireNonNull(getSupportActionBar()).hide();

        btn_usuario_sair = findViewById(R.id.btn_usuario_sair);
        btn_usuario_voltar = findViewById(R.id.btn_usuario_voltar);
        btn_usuario_cadastrar = findViewById(R.id.btn_usuario_cadastrar);
        btn_usuario_pesquisa = findViewById(R.id.btn_usuario_pesquisa);
        btn_usuario_gerenciar = findViewById(R.id.btn_usuario_gerenciar);

        tv_usuario_cipa_mais = findViewById(R.id.tv_usuario_cipa_mais);
        iv_usuario_cipa_mais = findViewById(R.id.iv_usuario_cipa_mais);

        iv2 = findViewById(R.id.iv2);
        iv3 = findViewById(R.id.iv3);
        iv4 = findViewById(R.id.iv4);
        iv5 = findViewById(R.id.iv5);
        iv6 = findViewById(R.id.iv6);
        iv7 = findViewById(R.id.iv7);
        iv8 = findViewById(R.id.iv8);
        iv9 = findViewById(R.id.iv9);

        imageViews.add(iv2);
        imageViews.add(iv3);
        imageViews.add(iv4);
        imageViews.add(iv5);
        imageViews.add(iv6);
        imageViews.add(iv7);
        imageViews.add(iv8);
        imageViews.add(iv9);

        tv_usuario_seja_bem_vindo_nome = findViewById(R.id.tv_usuario_seja_bem_vindo_nome);
        tv_usuario_seja_bem_vindo_email = findViewById(R.id.tv_usuario_seja_bem_vindo_email);

        switchMode_usuarios = findViewById(R.id.switchMode_usuarios);

        viewPager2 = findViewById(R.id.view_pager2);
        MainAdapterViewPager mainAdapterViewPager = new MainAdapterViewPager(this, this);
        viewPager2.setAdapter(mainAdapterViewPager);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                changeColor();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

        Intent intent = getIntent();
        administrador = intent.getStringExtra("administrador");
        nomeAdm = intent.getStringExtra("nomeAdm");
        emailAdm = intent.getStringExtra("emailAdm");
        posicaoAdm = intent.getStringExtra("posicaoAdm");
        nomeSesmt = intent.getStringExtra("nomeSesmt");
        emailSesmt = intent.getStringExtra("emailSesmt");
        posicaoSesmt = intent.getStringExtra("posicaoSesmt");
        nomeCipeiro = intent.getStringExtra("nomeCipeiro");
        emailCipeiro = intent.getStringExtra("emailCipeiro");
        posicaoCipeiro = intent.getStringExtra("posicaoCipeiro");
        nomeColaborador = intent.getStringExtra("nomeColaborador");
        emailColaborador = intent.getStringExtra("emailColaborador");
        posicaoColaborador = intent.getStringExtra("posicaoColaborador");
        nomeConfirmar = intent.getStringExtra("nomeConfirmar");
        emailConfirmar = intent.getStringExtra("emailConfirmar");
        posicaoConfirmar = intent.getStringExtra("posicaoConfirmar");
        posicaoVisitante = intent.getStringExtra("posicaoVisitante");

        String adm_nome = nomeAdm + "\nSeja bem vindo(a) " + administrador;
        String sesmt_nome = nomeSesmt + "\nSeja bem vindo(a) " + posicaoSesmt;
        String cipeiro_nome = nomeCipeiro + "\nSeja bem vindo(a) " + posicaoCipeiro;
        String colaborador_nome = nomeColaborador + "\nSeja bem vindo(a) " + posicaoColaborador;
        String confirmar_nome = nomeConfirmar + "\nSeu cadastro está em análise";

        String visitante = "Agradecemos a sua visita";

        sharedPreferences = getSharedPreferences("night", 0);
        Boolean booleanValue = sharedPreferences.getBoolean("night_mode", true);

        if (booleanValue) {

            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            switchMode_usuarios.setChecked(true);
        }

        switchMode_usuarios.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked) {

                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                switchMode_usuarios.setChecked(true);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("night_mode", true);
                editor.commit();

            } else {

                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                switchMode_usuarios.setChecked(false);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("night_mode", false);
                editor.commit();

            }
        });

        if (administrador != null) {

            tv_usuario_seja_bem_vindo_nome.setText(adm_nome);
            tv_usuario_seja_bem_vindo_email.setText(emailAdm);
            btn_usuario_gerenciar.setVisibility(View.VISIBLE);

        } else if (nomeCipeiro != null) {

            tv_usuario_seja_bem_vindo_nome.setText(cipeiro_nome);
            tv_usuario_seja_bem_vindo_email.setText(emailCipeiro);

        } else if (nomeSesmt != null) {

            tv_usuario_seja_bem_vindo_nome.setText(sesmt_nome);
            tv_usuario_seja_bem_vindo_email.setText(emailSesmt);

        } else if (nomeColaborador != null) {

            tv_usuario_seja_bem_vindo_nome.setText(colaborador_nome);
            tv_usuario_seja_bem_vindo_email.setText(emailColaborador);

        } else if (nomeConfirmar != null) {

            tv_usuario_seja_bem_vindo_nome.setText(confirmar_nome);
            tv_usuario_seja_bem_vindo_email.setText(emailConfirmar);

        } else {

            tv_usuario_seja_bem_vindo_nome.setText(visitante);
            btn_usuario_cadastrar.setVisibility(View.VISIBLE);

        }

        btn_usuario_pesquisa.setOnClickListener(v -> {

            if (emailAdm != null || emailCipeiro != null || emailSesmt != null || emailColaborador != null) {

                Intent i = new Intent(Usuario.this, Pesquisa.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
                finish();

            } else if (posicaoConfirmar != null) {

                Toast.makeText(Usuario.this, "O seu cadastro está em análise, em breve você terá acesso a essa página",
                        Toast.LENGTH_LONG).show();

            } else if (posicaoVisitante != null) {

                Toast.makeText(Usuario.this, "Cadastre-se para ter acesso a essa página",
                        Toast.LENGTH_LONG).show();
            }
        });

        btn_usuario_voltar.setOnClickListener(v -> {

            FirebaseAuth.getInstance().signOut();

            Intent i = new Intent(Usuario.this, Home.class);
            startActivity(i);
            overridePendingTransition(R.anim.mover_esquerda, R.anim.fade_out);
            finish();
        });

        btn_usuario_cadastrar.setOnClickListener(v -> {

            Intent i = new Intent(Usuario.this, Cadastrar.class);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
            finish();
        });

        btn_usuario_gerenciar.setOnClickListener(v -> {

            Intent i = new Intent(Usuario.this, Cadastros.class);
            i.putExtra("administrador", administrador);
            i.putExtra("nomeAdm", nomeAdm);
            i.putExtra("emailAdm", emailAdm);
            i.putExtra("posicaoAdm", posicaoAdm);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
            finish();
        });

        btn_usuario_sair.setOnClickListener(v -> {

            FirebaseAuth.getInstance().signOut();

            Intent i = new Intent(Usuario.this, Splash_Sair.class);

            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        });
    }

    private void changeColor() {

        int viewpager = viewPager2.getCurrentItem();

        switch (viewpager) {
            case 0:
                iv2.setImageResource(R.drawable.cipa_mais);
                iv3.setImageResource(R.drawable.black_radius);
                iv4.setImageResource(R.drawable.black_radius);
                iv5.setImageResource(R.drawable.black_radius);
                iv6.setImageResource(R.drawable.black_radius);
                iv7.setImageResource(R.drawable.black_radius);
                iv8.setImageResource(R.drawable.black_radius);
                iv9.setImageResource(R.drawable.black_radius);
                break;
            case 1:
                iv2.setImageResource(R.drawable.black_radius);
                iv3.setImageResource(R.drawable.sesmt);
                iv4.setImageResource(R.drawable.black_radius);
                iv5.setImageResource(R.drawable.black_radius);
                iv6.setImageResource(R.drawable.black_radius);
                iv7.setImageResource(R.drawable.black_radius);
                iv8.setImageResource(R.drawable.black_radius);
                iv9.setImageResource(R.drawable.black_radius);
                break;
            case 2:
                iv2.setImageResource(R.drawable.black_radius);
                iv3.setImageResource(R.drawable.black_radius);
                iv4.setImageResource(R.drawable.sipat);
                iv5.setImageResource(R.drawable.black_radius);
                iv6.setImageResource(R.drawable.black_radius);
                iv7.setImageResource(R.drawable.black_radius);
                iv8.setImageResource(R.drawable.black_radius);
                iv9.setImageResource(R.drawable.black_radius);
                break;
            case 3:
                iv2.setImageResource(R.drawable.black_radius);
                iv3.setImageResource(R.drawable.black_radius);
                iv4.setImageResource(R.drawable.black_radius);
                iv5.setImageResource(R.drawable.sua_empresa);
                iv6.setImageResource(R.drawable.black_radius);
                iv7.setImageResource(R.drawable.black_radius);
                iv8.setImageResource(R.drawable.black_radius);
                iv9.setImageResource(R.drawable.black_radius);
                break;
            case 4:
                iv2.setImageResource(R.drawable.black_radius);
                iv3.setImageResource(R.drawable.black_radius);
                iv4.setImageResource(R.drawable.black_radius);
                iv5.setImageResource(R.drawable.black_radius);
                iv6.setImageResource(R.drawable.saiba_mais);
                iv7.setImageResource(R.drawable.black_radius);
                iv8.setImageResource(R.drawable.black_radius);
                iv9.setImageResource(R.drawable.black_radius);
                break;
            case 5:
                iv2.setImageResource(R.drawable.black_radius);
                iv3.setImageResource(R.drawable.black_radius);
                iv4.setImageResource(R.drawable.black_radius);
                iv5.setImageResource(R.drawable.black_radius);
                iv6.setImageResource(R.drawable.black_radius);
                iv7.setImageResource(R.drawable.seja_um_cipeiro);
                iv8.setImageResource(R.drawable.black_radius);
                iv9.setImageResource(R.drawable.black_radius);
                break;
            case 6:
                iv2.setImageResource(R.drawable.black_radius);
                iv3.setImageResource(R.drawable.black_radius);
                iv4.setImageResource(R.drawable.black_radius);
                iv5.setImageResource(R.drawable.black_radius);
                iv6.setImageResource(R.drawable.black_radius);
                iv7.setImageResource(R.drawable.black_radius);
                iv8.setImageResource(R.drawable.dds);
                iv9.setImageResource(R.drawable.black_radius);
                break;
            case 7:
                iv2.setImageResource(R.drawable.black_radius);
                iv3.setImageResource(R.drawable.black_radius);
                iv4.setImageResource(R.drawable.black_radius);
                iv5.setImageResource(R.drawable.black_radius);
                iv6.setImageResource(R.drawable.black_radius);
                iv7.setImageResource(R.drawable.black_radius);
                iv8.setImageResource(R.drawable.black_radius);
                iv9.setImageResource(R.drawable.sugestoes);
                break;
        }
    }

    @Override
    public void onItemClick(int position) {

        switch (position) {

            case 0:
                cipa();
                break;

            case 1:
                sesmt();
                break;

            case 2:
                sipat();
                break;

            case 3:
                indice_de_acidentes();
                break;

            case 4:
                fique_por_dentro();
                break;

            case 5:
                seja_um_cipeiro();
                break;

            case 6:
                dds();
                break;

            case 7:
                sugestoes();
                break;
        }
    }

    private void sugestoes() {

        if (administrador != null) {

            Intent i = new Intent(Usuario.this, Sugestoes.class);
            i.putExtra("nomeAdm", nomeAdm);
            i.putExtra("emailAdm", emailAdm);
            i.putExtra("posicaoAdm", posicaoAdm);
            i.putExtra("administrador", administrador);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
            finish();

        } else if (emailCipeiro != null) {

            Intent i = new Intent(Usuario.this, Sugestoes.class);
            i.putExtra("nomeCipeiro", nomeCipeiro);
            i.putExtra("emailCipeiro", emailCipeiro);
            i.putExtra("posicaoCipeiro", posicaoCipeiro);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
            finish();

        } else if (emailSesmt != null) {

            Intent i = new Intent(Usuario.this, Sugestoes.class);
            i.putExtra("nomeSesmt", nomeSesmt);
            i.putExtra("emailSesmt", emailSesmt);
            i.putExtra("posicaoSesmt", posicaoSesmt);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
            finish();

        } else if (emailColaborador != null) {

            Intent i = new Intent(Usuario.this, Sugestoes.class);
            i.putExtra("nomeColaborador", nomeColaborador);
            i.putExtra("emailColaborador", emailColaborador);
            i.putExtra("posicaoColaborador", posicaoColaborador);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
            finish();

        } else if (posicaoConfirmar != null) {

            Toast.makeText(Usuario.this, "O seu cadastro está em análise, em breve você terá acesso a essa página",
                    Toast.LENGTH_LONG).show();
            btn_usuario_pesquisa.setVisibility(View.INVISIBLE);

        } else if (posicaoVisitante != null) {

            Toast.makeText(Usuario.this, "Cadastre-se para ter acesso a essa página",
                    Toast.LENGTH_LONG).show();
            btn_usuario_pesquisa.setVisibility(View.INVISIBLE);
        }
    }

    private void dds() {

        if (administrador != null) {

            Intent i = new Intent(Usuario.this, DDS.class);
            i.putExtra("nomeAdm", nomeAdm);
            i.putExtra("emailAdm", emailAdm);
            i.putExtra("posicaoAdm", posicaoAdm);
            i.putExtra("administrador", administrador);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
            finish();

        } else if (emailCipeiro != null) {

            Intent i = new Intent(Usuario.this, DDS.class);
            i.putExtra("nomeCipeiro", nomeCipeiro);
            i.putExtra("emailCipeiro", emailCipeiro);
            i.putExtra("posicaoCipeiro", posicaoCipeiro);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
            finish();

        } else if (emailSesmt != null) {

            Intent i = new Intent(Usuario.this, DDS.class);
            i.putExtra("nomeSesmt", nomeSesmt);
            i.putExtra("emailSesmt", emailSesmt);
            i.putExtra("posicaoSesmt", posicaoSesmt);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
            finish();

        } else if (emailColaborador != null) {

            Intent i = new Intent(Usuario.this, DDS.class);
            i.putExtra("nomeColaborador", nomeColaborador);
            i.putExtra("emailColaborador", emailColaborador);
            i.putExtra("posicaoColaborador", posicaoColaborador);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
            finish();

        } else if (emailConfirmar != null) {

            Intent i = new Intent(Usuario.this, DDS.class);
            i.putExtra("nomeConfirmar", nomeConfirmar);
            i.putExtra("emailConfirmar", emailConfirmar);
            i.putExtra("posicaoConfirmar", posicaoConfirmar);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
            finish();

        } else if (posicaoVisitante != null) {

            Intent i = new Intent(Usuario.this, DDS.class);
            i.putExtra("posicaoVisitante", posicaoVisitante);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
            finish();
        }

    }

    private void seja_um_cipeiro() {

        if (administrador != null) {

            Intent i = new Intent(Usuario.this, Seja_um_cipeiro.class);
            i.putExtra("nomeAdm", nomeAdm);
            i.putExtra("emailAdm", emailAdm);
            i.putExtra("posicaoAdm", posicaoAdm);
            i.putExtra("administrador", administrador);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
            finish();

        } else if (emailCipeiro != null) {

            Intent i = new Intent(Usuario.this, Seja_um_cipeiro.class);
            i.putExtra("nomeCipeiro", nomeCipeiro);
            i.putExtra("emailCipeiro", emailCipeiro);
            i.putExtra("posicaoCipeiro", posicaoCipeiro);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
            finish();

        } else if (emailSesmt != null) {

            Intent i = new Intent(Usuario.this, Seja_um_cipeiro.class);
            i.putExtra("nomeSesmt", nomeSesmt);
            i.putExtra("emailSesmt", emailSesmt);
            i.putExtra("posicaoSesmt", posicaoSesmt);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
            finish();

        } else if (emailColaborador != null) {

            Intent i = new Intent(Usuario.this, Seja_um_cipeiro.class);
            i.putExtra("nomeColaborador", nomeColaborador);
            i.putExtra("emailColaborador", emailColaborador);
            i.putExtra("posicaoColaborador", posicaoColaborador);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
            finish();

        } else if (emailConfirmar != null) {

            Intent i = new Intent(Usuario.this, Seja_um_cipeiro.class);
            i.putExtra("nomeConfirmar", nomeConfirmar);
            i.putExtra("emailConfirmar", emailConfirmar);
            i.putExtra("posicaoConfirmar", posicaoConfirmar);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
            finish();

        } else if (posicaoVisitante != null) {

            Intent i = new Intent(Usuario.this, Seja_um_cipeiro.class);
            i.putExtra("posicaoVisitante", posicaoVisitante);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
            finish();
        }
    }

    private void fique_por_dentro() {

        if (administrador != null) {

            Intent i = new Intent(Usuario.this, Fique_por_dentro.class);
            i.putExtra("nomeAdm", nomeAdm);
            i.putExtra("emailAdm", emailAdm);
            i.putExtra("posicaoAdm", posicaoAdm);
            i.putExtra("administrador", administrador);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
            finish();

        } else if (emailCipeiro != null) {

            Intent i = new Intent(Usuario.this, Fique_por_dentro.class);
            i.putExtra("nomeCipeiro", nomeCipeiro);
            i.putExtra("emailCipeiro", emailCipeiro);
            i.putExtra("posicaoCipeiro", posicaoCipeiro);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
            finish();

        } else if (emailSesmt != null) {

            Intent i = new Intent(Usuario.this, Fique_por_dentro.class);
            i.putExtra("nomeSesmt", nomeSesmt);
            i.putExtra("emailSesmt", emailSesmt);
            i.putExtra("posicaoSesmt", posicaoSesmt);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
            finish();

        } else if (emailColaborador != null) {

            Intent i = new Intent(Usuario.this, Fique_por_dentro.class);
            i.putExtra("nomeColaborador", nomeColaborador);
            i.putExtra("emailColaborador", emailColaborador);
            i.putExtra("posicaoColaborador", posicaoColaborador);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
            finish();

        } else if (emailConfirmar != null) {

            Intent i = new Intent(Usuario.this, Fique_por_dentro.class);
            i.putExtra("nomeConfirmar", nomeConfirmar);
            i.putExtra("emailConfirmar", emailConfirmar);
            i.putExtra("posicaoConfirmar", posicaoConfirmar);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
            finish();

        } else if (posicaoVisitante != null) {

            Intent i = new Intent(Usuario.this, Fique_por_dentro.class);
            i.putExtra("posicaoVisitante", posicaoVisitante);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
            finish();
        }
    }

    private void indice_de_acidentes() {

        if (administrador != null) {

            Intent i = new Intent(Usuario.this, Indice_de_acidentes.class);
            i.putExtra("nomeAdm", nomeAdm);
            i.putExtra("emailAdm", emailAdm);
            i.putExtra("posicaoAdm", posicaoAdm);
            i.putExtra("administrador", administrador);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
            finish();

        } else if (emailCipeiro != null) {

            Intent i = new Intent(Usuario.this, Indice_de_acidentes.class);
            i.putExtra("nomeCipeiro", nomeCipeiro);
            i.putExtra("emailCipeiro", emailCipeiro);
            i.putExtra("posicaoCipeiro", posicaoCipeiro);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
            finish();

        } else if (emailSesmt != null) {

            Intent i = new Intent(Usuario.this, Indice_de_acidentes.class);
            i.putExtra("nomeSesmt", nomeSesmt);
            i.putExtra("emailSesmt", emailSesmt);
            i.putExtra("posicaoSesmt", posicaoSesmt);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
            finish();

        } else if (emailColaborador != null) {

            Intent i = new Intent(Usuario.this, Indice_de_acidentes.class);
            i.putExtra("nomeColaborador", nomeColaborador);
            i.putExtra("emailColaborador", emailColaborador);
            i.putExtra("posicaoColaborador", posicaoColaborador);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
            finish();

        } else if (emailConfirmar != null) {

            Intent i = new Intent(Usuario.this, Indice_de_acidentes.class);
            i.putExtra("nomeConfirmar", nomeConfirmar);
            i.putExtra("emailConfirmar", emailConfirmar);
            i.putExtra("posicaoConfirmar", posicaoConfirmar);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
            finish();

        } else if (posicaoVisitante != null) {

            Intent i = new Intent(Usuario.this, Indice_de_acidentes.class);
            i.putExtra("posicaoVisitante", posicaoVisitante);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
            finish();
        }
    }

    private void sipat() {

        if (administrador != null) {

            Intent i = new Intent(Usuario.this, SIPAT.class);
            i.putExtra("nomeAdm", nomeAdm);
            i.putExtra("emailAdm", emailAdm);
            i.putExtra("posicaoAdm", posicaoAdm);
            i.putExtra("administrador", administrador);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
            finish();

        } else if (emailCipeiro != null) {

            Intent i = new Intent(Usuario.this, SIPAT.class);
            i.putExtra("nomeCipeiro", nomeCipeiro);
            i.putExtra("emailCipeiro", emailCipeiro);
            i.putExtra("posicaoCipeiro", posicaoCipeiro);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
            finish();

        } else if (emailSesmt != null) {

            Intent i = new Intent(Usuario.this, SIPAT.class);
            i.putExtra("nomeSesmt", nomeSesmt);
            i.putExtra("emailSesmt", emailSesmt);
            i.putExtra("posicaoSesmt", posicaoSesmt);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
            finish();

        } else if (emailColaborador != null) {

            Intent i = new Intent(Usuario.this, SIPAT.class);
            i.putExtra("nomeColaborador", nomeColaborador);
            i.putExtra("emailColaborador", emailColaborador);
            i.putExtra("posicaoColaborador", posicaoColaborador);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
            finish();

        } else if (emailConfirmar != null) {

            Intent i = new Intent(Usuario.this, SIPAT.class);
            i.putExtra("nomeConfirmar", nomeConfirmar);
            i.putExtra("emailConfirmar", emailConfirmar);
            i.putExtra("posicaoConfirmar", posicaoConfirmar);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
            finish();

        } else if (posicaoVisitante != null) {

            Intent i = new Intent(Usuario.this, SIPAT.class);
            i.putExtra("posicaoVisitante", posicaoVisitante);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
            finish();
        }
    }

    private void sesmt() {

        if (administrador != null) {

            Intent i = new Intent(Usuario.this, SESMT.class);
            i.putExtra("nomeAdm", nomeAdm);
            i.putExtra("emailAdm", emailAdm);
            i.putExtra("posicaoAdm", posicaoAdm);
            i.putExtra("administrador", administrador);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
            finish();

        } else if (emailCipeiro != null) {

            Intent i = new Intent(Usuario.this, SESMT.class);
            i.putExtra("nomeCipeiro", nomeCipeiro);
            i.putExtra("emailCipeiro", emailCipeiro);
            i.putExtra("posicaoCipeiro", posicaoCipeiro);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
            finish();

        } else if (emailSesmt != null) {

            Intent i = new Intent(Usuario.this, SESMT.class);
            i.putExtra("nomeSesmt", nomeSesmt);
            i.putExtra("emailSesmt", emailSesmt);
            i.putExtra("posicaoSesmt", posicaoSesmt);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
            finish();

        } else if (emailColaborador != null) {

            Intent i = new Intent(Usuario.this, SESMT.class);
            i.putExtra("nomeColaborador", nomeColaborador);
            i.putExtra("emailColaborador", emailColaborador);
            i.putExtra("posicaoColaborador", posicaoColaborador);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
            finish();

        } else if (posicaoConfirmar != null) {

            Toast.makeText(Usuario.this, "O seu cadastro está em análise, em breve você terá acesso a essa página",
                    Toast.LENGTH_LONG).show();

        } else if (posicaoVisitante != null) {

            Toast.makeText(Usuario.this, "Cadastre-se para ter acesso a essa página",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void cipa() {

        if (administrador != null) {

            Intent i = new Intent(Usuario.this, CIPA.class);
            i.putExtra("nomeAdm", nomeAdm);
            i.putExtra("emailAdm", emailAdm);
            i.putExtra("posicaoAdm", posicaoAdm);
            i.putExtra("administrador", administrador);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
            finish();

        } else if (emailCipeiro != null) {

            Intent i = new Intent(Usuario.this, CIPA.class);
            i.putExtra("nomeCipeiro", nomeCipeiro);
            i.putExtra("emailCipeiro", emailCipeiro);
            i.putExtra("posicaoCipeiro", posicaoCipeiro);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
            finish();

        } else if (emailSesmt != null) {

            Intent i = new Intent(Usuario.this, CIPA.class);
            i.putExtra("nomeSesmt", nomeSesmt);
            i.putExtra("emailSesmt", emailSesmt);
            i.putExtra("posicaoSesmt", posicaoSesmt);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
            finish();

        } else if (emailColaborador != null) {

            Intent i = new Intent(Usuario.this, CIPA.class);
            i.putExtra("nomeColaborador", nomeColaborador);
            i.putExtra("emailColaborador", emailColaborador);
            i.putExtra("posicaoColaborador", posicaoColaborador);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
            finish();

        } else if (posicaoConfirmar != null) {

            Toast.makeText(Usuario.this, "O seu cadastro está em análise, em breve você terá acesso a essa página",
                    Toast.LENGTH_LONG).show();

        } else if (posicaoVisitante != null) {

            Toast.makeText(Usuario.this, "Cadastre-se para ter acesso a essa página",
                    Toast.LENGTH_LONG).show();
        }
    }
}