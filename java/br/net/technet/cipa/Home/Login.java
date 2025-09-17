package br.net.technet.cipa.Home;

import static androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG;
import static androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.regex.Pattern;

import br.net.technet.cipa.R;
import br.net.technet.cipa.Users.Usuario;
import br.net.technet.cipa.Uteis.UserModel;
import br.net.technet.cipa.Uteis.Uteis;

public class Login extends AppCompatActivity {

    Button btn_login_sair, btn_login_entrar, btn_login_voltar;
    EditText et_login_email, et_login_senha;
    TextView tv_login_esqueci_senha, tv_login_cipa_mais;
    ImageView imageView;

    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    ValueEventListener valueEventListener;
    SharedPreferences sharedPreferences = null;

    String nome, email, posicao, loginEmail, loginSenha, administrador;

    Executor executor;
    BiometricPrompt biometricPrompt;
    BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Objects.requireNonNull(getSupportActionBar()).hide();

        btn_login_sair = findViewById(R.id.btn_login_sair);
        btn_login_entrar = findViewById(R.id.btn_login_entrar);
        btn_login_voltar = findViewById(R.id.btn_login_voltar);

        et_login_email = findViewById(R.id.et_login_email);
        et_login_senha = findViewById(R.id.et_login_senha);
        tv_login_esqueci_senha = findViewById(R.id.tv_login_esqueci_senha);
        tv_login_cipa_mais = findViewById(R.id.tv_login_cipa_mais);

        imageView = findViewById(R.id.imageView);

        progressBar = findViewById(R.id.pb_login);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference().child("usuarios").child("confirmar_cadastro");
        FirebaseAuth.getInstance().signOut();

        sharedPreferences = getSharedPreferences("night", 0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            verificarSensorBiometria();
        }

        tv_login_esqueci_senha.setOnClickListener(v -> {

            loginEmail = et_login_email.getText().toString();

            if (loginEmail.isEmpty()) {

                Toast.makeText(Login.this, "Insira seu e-mail para receber um link e " +
                        "redefinir a senha", Toast.LENGTH_LONG).show();

            } else if (!verificarEmail(loginEmail)) {

                Toast.makeText(Login.this, "E-mail inválido", Toast.LENGTH_LONG).show();

            } else {

                firebaseAuth.sendPasswordResetEmail(loginEmail).addOnSuccessListener(unused ->

                                Toast.makeText(Login.this, "Enviamos um link para o e-mail informado",
                                        Toast.LENGTH_LONG).show())

                        .addOnFailureListener(e -> {

                            String erro = e.toString();
                            Uteis.opcoesErro(getBaseContext(), erro);
                        });
            }
        });

        btn_login_entrar.setOnClickListener(v -> {

            loginEmail = et_login_email.getText().toString();
            loginSenha = et_login_senha.getText().toString();

            if (loginEmail.isEmpty() || loginSenha.isEmpty()) {

                Toast.makeText(Login.this, "E-mail e senha obrigatórios", Toast.LENGTH_LONG).show();

            } else if (!verificarEmail(loginEmail)) {

                Toast.makeText(Login.this, "E-mail inválido", Toast.LENGTH_LONG).show();

            } else {

                if (Uteis.verificarInternet(this)) {

                    entrar(loginEmail, loginSenha);
                }
            }
        });

        btn_login_sair.setOnClickListener(v -> {

            FirebaseAuth.getInstance().signOut();

            Intent i = new Intent(Login.this, Splash_Sair.class);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        });

        btn_login_voltar.setOnClickListener(v -> {

            FirebaseAuth.getInstance().signOut();

            Intent i = new Intent(Login.this, Home.class);
            startActivity(i);
            overridePendingTransition(R.anim.mover_esquerda, R.anim.fade_out);
            finish();
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    private void verificarSensorBiometria() {

        //Verificar se o dispositivo possui leitor de impressão digital
        BiometricManager sensorImpressao = BiometricManager.from(this);

        //Verificar se estão cadastrados ao menos a impressão digital
        switch (sensorImpressao.canAuthenticate(BIOMETRIC_STRONG | DEVICE_CREDENTIAL)) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                //Caso possua o sensor e há ao menos uma digital cadastrada para uso
                Toast.makeText(Login.this, R.string.sensorEncontrado, Toast.LENGTH_SHORT).show();
                criaPainelAutenticacao();
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                //Caso não possua o sensor
                Toast.makeText(Login.this, R.string.sensorNaoEncontrado, Toast.LENGTH_SHORT).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                //Caso possua o sensor mas não está disponível no momento
                Toast.makeText(Login.this, R.string.sensorIndisponivel, Toast.LENGTH_SHORT).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                //Caso possua o sensor mas não realizou o cadastro de uma digital
                Toast.makeText(Login.this, R.string.sensorEncontradoNaoCadastrado, Toast.LENGTH_SHORT).show();
                //Abre a tela para requisitar que a pessoa cadastre uma digital
                final Intent telaConfiguracaoDigital = new Intent(Settings.ACTION_BIOMETRIC_ENROLL);
                telaConfiguracaoDigital.putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,BIOMETRIC_STRONG);
                startActivityForResult(telaConfiguracaoDigital, 1000);
                break;
    }}

    private void entrar(String loginEmail, String loginSenha) {

        firebaseAuth.signInWithEmailAndPassword(loginEmail, loginSenha)
                .addOnCompleteListener(task -> {
                    progressBar.setVisibility(View.VISIBLE);
                    if (task.isSuccessful()) {

                        entrarDefinirUsuario();

                    } else {
                        progressBar.setVisibility(View.INVISIBLE);
                        String resposta = task.getException().toString();
                        Uteis.opcoesErro(getBaseContext(), resposta);
                    }
                });
    }

    private void entrarDefinirUsuario() {

        loginEmail = et_login_email.getText().toString();
        loginSenha = et_login_senha.getText().toString();

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        UserModel userModel = dataSnapshot.getValue(UserModel.class);
                        nome = userModel.getNome();
                        email = userModel.getEmail();
                        posicao = userModel.getPosicao();
                        administrador = userModel.getAdministrador();


                        if (loginEmail.equals(email)) {

                            if (administrador.equals("Administrador")) {

                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                                Intent i = new Intent(Login.this, Usuario.class);
                                i.putExtra("administrador", administrador);
                                i.putExtra("nomeAdm", nome);
                                i.putExtra("emailAdm", email);
                                i.putExtra("posicaoAdm", posicao);

                                Pair<View, String> pair1 = Pair.create(imageView, "imagemProfile");
                                Pair<View, String> pair2 = Pair.create(tv_login_cipa_mais, "textoProfile");

                                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                                        .makeSceneTransitionAnimation(Login.this, pair1, pair2);

                                startActivity(i, optionsCompat.toBundle());
                                finish();

                            } else {

                                switch (posicao) {

                                    case "Cipeiro": {

                                        Intent i = new Intent(Login.this, Usuario.class);
                                        i.putExtra("nomeCipeiro", nome);
                                        i.putExtra("emailCipeiro", email);
                                        i.putExtra("posicaoCipeiro", posicao);

                                        Pair<View, String> pair1 = Pair.create(imageView, "imagemProfile");
                                        Pair<View, String> pair2 = Pair.create(tv_login_cipa_mais, "textoProfile");

                                        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                                                .makeSceneTransitionAnimation(Login.this, pair1, pair2);

                                        startActivity(i, optionsCompat.toBundle());
                                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                        finish();

                                        break;
                                    }
                                    case "Colaborador": {

                                        Intent i = new Intent(Login.this, Usuario.class);
                                        i.putExtra("nomeColaborador", nome);
                                        i.putExtra("emailColaborador", email);
                                        i.putExtra("posicaoColaborador", posicao);

                                        Pair<View, String> pair1 = Pair.create(imageView, "imagemProfile");
                                        Pair<View, String> pair2 = Pair.create(tv_login_cipa_mais, "textoProfile");

                                        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                                                .makeSceneTransitionAnimation(Login.this, pair1, pair2);

                                        startActivity(i, optionsCompat.toBundle());
                                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                        finish();

                                        break;
                                    }
                                    case "SESMT": {

                                        Intent i = new Intent(Login.this, Usuario.class);
                                        i.putExtra("nomeSesmt", nome);
                                        i.putExtra("emailSesmt", email);
                                        i.putExtra("posicaoSesmt", posicao);

                                        Pair<View, String> pair1 = Pair.create(imageView, "imagemProfile");
                                        Pair<View, String> pair2 = Pair.create(tv_login_cipa_mais, "textoProfile");

                                        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                                                .makeSceneTransitionAnimation(Login.this, pair1, pair2);

                                        startActivity(i, optionsCompat.toBundle());
                                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                        finish();

                                        break;
                                    }
                                    case "Confirmar Cadastro": {

                                        Intent i = new Intent(Login.this, Usuario.class);
                                        i.putExtra("nomeConfirmar", nome);
                                        i.putExtra("emailConfirmar", email);
                                        i.putExtra("posicaoConfirmar", posicao);

                                        Pair<View, String> pair1 = Pair.create(imageView, "imagemProfile");
                                        Pair<View, String> pair2 = Pair.create(tv_login_cipa_mais, "textoProfile");

                                        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                                                .makeSceneTransitionAnimation(Login.this, pair1, pair2);

                                        startActivity(i, optionsCompat.toBundle());
                                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                        finish();
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    }
                } else {

                    Toast.makeText(Login.this, "dados vazios", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        reference.addValueEventListener(valueEventListener);
    }

    private boolean verificarEmail(String loginEmail) {
        Pattern patterns = Patterns.EMAIL_ADDRESS;
        return patterns.matcher(loginEmail).matches();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (valueEventListener != null) {
            reference.removeEventListener(valueEventListener);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (valueEventListener != null) {
            reference.removeEventListener(valueEventListener);
        }
    }

    private void criaPainelAutenticacao() {

        //Objeto da classe Executor para criar e gerenciar Threads paralelas
        Executor executor = ContextCompat.getMainExecutor(this);

        //Configuração da janela popup que irá requisitar a leitura da impressão digital
        BiometricPrompt.PromptInfo telaMensagem = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Validação biométrica") //Título
                .setSubtitle("Entrar utilizando a biometria digital") //Mensagem do corpo
                .setAllowedAuthenticators(BIOMETRIC_STRONG | DEVICE_CREDENTIAL) //Métodos de autenticação permitidos
                .build();

        //Configuração das possiveis saídas da leitura, ou seja, com sucesso, erro ou falha
        BiometricPrompt leituraImpressao = new BiometricPrompt(Login.this, executor, new BiometricPrompt.AuthenticationCallback() {
            //Caso ocorra um erro ao autenticar como, por exemplo, cancelar a tela, tentar várias vezes
            //com uma digital não cadastrada
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                //Exibe mensagem de erro
                Toast.makeText(Login.this, R.string.erroLeitura, Toast.LENGTH_SHORT).show();
                //Encerra (fecha) a tela (activity)
                finish();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                //Exibe mensagem de leitura válida
                //Snackbar.make(findViewById(R.id.tela), R.string.acertoLeitura, Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                //Exibe mensagem de leitura com erro
                Toast.makeText(Login.this, R.string.falhaLeitura, Toast.LENGTH_SHORT).show();

            }
        });

        //Exibe a tela para realizar a leitura da impressão digital
        leituraImpressao.authenticate(telaMensagem);
    }
}

