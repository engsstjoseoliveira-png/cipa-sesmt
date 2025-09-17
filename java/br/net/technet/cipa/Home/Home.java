package br.net.technet.cipa.Home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import br.net.technet.cipa.R;
import br.net.technet.cipa.Users.Usuario;
import br.net.technet.cipa.Uteis.Permissao;

public class Home extends AppCompatActivity {

    Button btn_home_cadastrado, btn_home_cadastrar, btn_home_visitante, btn_home_sair;
    ImageView iv_cipa_1;
    TextView tv_home_cipa_mais;
    SharedPreferences sharedPreferences = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Objects.requireNonNull(getSupportActionBar()).hide();

        btn_home_cadastrado = findViewById(R.id.btn_home_cadastrado);
        btn_home_cadastrar = findViewById(R.id.btn_home_cadastrar);
        btn_home_visitante = findViewById(R.id.btn_home_visitante);
        btn_home_sair = findViewById(R.id.btn_home_sair);

        tv_home_cipa_mais = findViewById(R.id.tv_home_cipa_mais);
        iv_cipa_1 = findViewById(R.id.iv_cipa_1);

        String posicaoVisitante = "Visitante";

        // Comentou-se a função "permissão" após erro de instalação nas versões 15 e 16 do android
        //permissao();

        sharedPreferences = getSharedPreferences("night", 0);

        btn_home_cadastrado.setOnClickListener(v -> {

            FirebaseAuth.getInstance().signOut();

            Intent i = new Intent(Home.this, Login.class);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
            finish();
        });

        btn_home_cadastrar.setOnClickListener(v -> {
            Intent i = new Intent(Home.this, Cadastrar.class);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
            finish();

        });

        btn_home_visitante.setOnClickListener(v -> {

            Intent i = new Intent(Home.this, Usuario.class);
            i.putExtra("posicaoVisitante", posicaoVisitante);
            startActivity(i);
            overridePendingTransition(R.anim.mover_esquerda, R.anim.fade_out);
            finish();
        });

        btn_home_sair.setOnClickListener(v -> {

            FirebaseAuth.getInstance().signOut();

            Intent i = new Intent(Home.this, Splash_Sair.class);
            Pair<View, String> pair1 = Pair.create(iv_cipa_1, "imagemProfile");
            Pair<View, String> pair2 = Pair.create(tv_home_cipa_mais, "textoProfile");
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(Home.this, pair1, pair2);
            startActivity(i, optionsCompat.toBundle());
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        });
    }

    private void permissao() {

        String permissoes[] = new String[]{

                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.INTERNET,
                Manifest.permission.CAMERA,
                Manifest.permission.READ_MEDIA_IMAGES,
        };

        Permissao.permissao(this, 0, permissoes);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, int deviceId) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId);

        for (int result : grantResults) {

            if (result == PackageManager.PERMISSION_DENIED) {

                Intent i = new Intent(this, Splash_Sair.class);
                startActivity(i);

                Toast.makeText(this, "Aceite as permissões para o aplicativo funcionar corretamente",
                        Toast.LENGTH_LONG).show();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
                break;
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}