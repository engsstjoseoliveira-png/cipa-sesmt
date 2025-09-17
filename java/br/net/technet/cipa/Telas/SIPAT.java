package br.net.technet.cipa.Telas;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

import br.net.technet.cipa.Home.Cadastrar;
import br.net.technet.cipa.Home.Splash_Sair;
import br.net.technet.cipa.R;
import br.net.technet.cipa.Users.Usuario;
import br.net.technet.cipa.Uteis.DialogProgress;
import br.net.technet.cipa.Uteis.SipatModel;

public class SIPAT extends AppCompatActivity {

    Button btn_sipat_obter_imagem, btn_sipat_limpar_temas, btn_sipat_atualizar_tema, btn_sipat_cadastrar,
            btn_sipat_voltar, btn_sipat_sair;

    TextView tv_sipat_tema_i, tv_sipat_tema_ii, tv_sipat_tema_iii, tv_sipat_tema_iv, tv_sipat_tema_v,
    tv_sipat_dia_ii, tv_sipat_dia_iii, tv_sipat_dia_iv, tv_sipat_dia_i, tv_sipat_dia_v, tv_sipat_local_i,
            tv_sipat_local_ii, tv_sipat_local_iii, tv_sipat_local_iv, tv_sipat_local_v;

    EditText et_sipat_tema_i, et_sipat_tema_ii, et_sipat_tema_iii, et_sipat_tema_iv, et_sipat_tema_v,
            et_sipat_dia_i, et_sipat_dia_ii, et_sipat_dia_iii, et_sipat_dia_iv, et_sipat_dia_v,
            et_sipat_local_i, et_sipat_local_ii, et_sipat_local_iii, et_sipat_local_iv, et_sipat_local_v;
    ImageView iv_sipat_cabecalho;

    EditText[] editTexts;
    TextView[] textViews;

    WebView wv_sipat_descricao;

    SharedPreferences sharedPreferences = null;
    FirebaseStorage firebaseStorage;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ValueEventListener valueEventListener;
    LinearLayout linearLayout6;
    Uri uri_for_camera;

    SipatModel sipatModel;

    String imagem, tema_i, tema_ii, tema_iii, tema_iv, tema_v, local_i, local_ii, local_iii, local_iv,
            local_v, dia_i, dia_ii, dia_iii, dia_iv, dia_v, imagem_sipat;
    String TAG = "Permission";

    String[] required_permissions = new String[] {

            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.CAMERA
    };

    boolean is_storage_image_permitted = false;
    boolean is_camera_access_permitted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sipat);
        Objects.requireNonNull(getSupportActionBar()).hide();

        btn_sipat_atualizar_tema = findViewById(R.id.btn_sipat_atualizar_tema);
        btn_sipat_obter_imagem = findViewById(R.id.btn_sipat_obter_imagem);
        btn_sipat_limpar_temas = findViewById(R.id.btn_sipat_limpar_temas);
        btn_sipat_cadastrar = findViewById(R.id.btn_sipat_cadastrar);
        btn_sipat_voltar = findViewById(R.id.btn_sipat_voltar);
        btn_sipat_sair = findViewById(R.id.btn_sipat_sair);

        tv_sipat_tema_i = findViewById(R.id.tv_sipat_tema_i);
        tv_sipat_tema_ii = findViewById(R.id.tv_sipat_tema_ii);
        tv_sipat_tema_iii = findViewById(R.id.tv_sipat_tema_iii);
        tv_sipat_tema_iv = findViewById(R.id.tv_sipat_tema_iv);
        tv_sipat_tema_v = findViewById(R.id.tv_sipat_tema_v);
        tv_sipat_local_i = findViewById(R.id.tv_sipat_local_i);
        tv_sipat_local_ii = findViewById(R.id.tv_sipat_local_ii);
        tv_sipat_local_iii = findViewById(R.id.tv_sipat_local_iii);
        tv_sipat_local_iv = findViewById(R.id.tv_sipat_local_iv);
        tv_sipat_local_v = findViewById(R.id.tv_sipat_local_v);
        tv_sipat_dia_i = findViewById(R.id.tv_sipat_dia_i);
        tv_sipat_dia_ii = findViewById(R.id.tv_sipat_dia_ii);
        tv_sipat_dia_iii = findViewById(R.id.tv_sipat_dia_iii);
        tv_sipat_dia_iv = findViewById(R.id.tv_sipat_dia_iv);
        tv_sipat_dia_v = findViewById(R.id.tv_sipat_dia_v);

        et_sipat_tema_i = findViewById(R.id.et_sipat_tema_i);
        et_sipat_tema_ii = findViewById(R.id.et_sipat_tema_ii);
        et_sipat_tema_iii = findViewById(R.id.et_sipat_tema_iii);
        et_sipat_tema_iv = findViewById(R.id.et_sipat_tema_iv);
        et_sipat_tema_v = findViewById(R.id.et_sipat_tema_v);
        et_sipat_dia_i = findViewById(R.id.et_sipat_dia_i);
        et_sipat_dia_ii = findViewById(R.id.et_sipat_dia_ii);
        et_sipat_dia_iii = findViewById(R.id.et_sipat_dia_iii);
        et_sipat_dia_iv = findViewById(R.id.et_sipat_dia_iv);
        et_sipat_dia_v = findViewById(R.id.et_sipat_dia_v);
        et_sipat_local_i = findViewById(R.id.et_sipat_local_i);
        et_sipat_local_ii = findViewById(R.id.et_sipat_local_ii);
        et_sipat_local_iii = findViewById(R.id.et_sipat_local_iii);
        et_sipat_local_iv = findViewById(R.id.et_sipat_local_iv);
        et_sipat_local_v = findViewById(R.id.et_sipat_local_v);

        iv_sipat_cabecalho = findViewById(R.id.iv_sipat_cabecalho);

        linearLayout6 = findViewById(R.id.linearLayout6);
        linearLayout6.setVisibility(View.GONE);

        editTexts = new EditText[] {et_sipat_tema_i, et_sipat_tema_ii, et_sipat_tema_iii,
                et_sipat_tema_iv, et_sipat_tema_v,et_sipat_dia_i, et_sipat_dia_ii, et_sipat_dia_iii,
                et_sipat_dia_iv, et_sipat_dia_v, et_sipat_local_i, et_sipat_local_ii, et_sipat_local_iii,
                et_sipat_local_iv, et_sipat_local_v};

        textViews = new TextView[] {tv_sipat_tema_i, tv_sipat_tema_ii, tv_sipat_tema_iii,
                tv_sipat_tema_iv, tv_sipat_tema_v, tv_sipat_dia_ii, tv_sipat_dia_iii,
                tv_sipat_dia_iv, tv_sipat_dia_i, tv_sipat_dia_v};

        wv_sipat_descricao = findViewById(R.id.wv_sipat_descricao);

        btn_sipat_cadastrar.setVisibility(View.GONE);

        Intent intent = getIntent();
        String administrador = intent.getStringExtra("administrador");
        String nomeAdm = intent.getStringExtra("nomeAdm");
        String nomeCipeiro = intent.getStringExtra("nomeCipeiro");
        String nomeColaborador = intent.getStringExtra("nomeColaborador");
        String nomeSesmt = intent.getStringExtra("nomeSesmt");
        String emailSesmt = intent.getStringExtra("emailSesmt");
        String posicaoSesmt = intent.getStringExtra("posicaoSesmt");
        String nomeConfirmar = intent.getStringExtra("nomeConfirmar");
        String emailAdm = intent.getStringExtra("emailAdm");
        String emailCipeiro = intent.getStringExtra("emailCipeiro");
        String emailColaborador = intent.getStringExtra("emailColaborador");
        String emailConfirmar = intent.getStringExtra("emailConfirmar");
        String posicaoAdm = intent.getStringExtra("posicaoAdm");
        String posicaoCipeiro = intent.getStringExtra("posicaoCipeiro");
        String posicaoColaborador = intent.getStringExtra("posicaoColaborador");
        String posicaoConfirmar = intent.getStringExtra("posicaoConfirmar");
        String posicaoVisitante = intent.getStringExtra("posicaoVisitante");

        String wv_sipat_descricao_texto = "<html><body>" + "<p align=\"justify\">"
                + "<font color = \"green\" size =\"4\" face =\"Verdana\">"
                + getString(R.string.texto_sipat)
                + "</font>" + "</p>" + "</body></html>";

        wv_sipat_descricao.setBackgroundColor(0);
        wv_sipat_descricao.loadData(wv_sipat_descricao_texto, "text/html", "utf-8");

        sharedPreferences = getSharedPreferences("night", 0);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("sipat");
        firebaseStorage = FirebaseStorage.getInstance();
        sipatModel = new SipatModel(imagem, tema_i, tema_ii, tema_iii, tema_iv, tema_v, local_i,
                local_ii, local_iii, local_iv, local_v, dia_i, dia_ii, dia_iii, dia_iv, dia_v);

        if (!is_storage_image_permitted) {
            requestPermissonStorageImages();
        }

        if (posicaoVisitante != null) {
            btn_sipat_cadastrar.setVisibility(View.VISIBLE);
        }

        if (administrador != null) {

            linearLayout6.setVisibility(View.VISIBLE);
        }

        temasSipat();

        btn_sipat_limpar_temas.setOnClickListener(v -> {

            limparTemasSipat();

        });

        btn_sipat_obter_imagem.setOnClickListener(v -> {

            obterImagem_Galeria();
        });

        btn_sipat_atualizar_tema.setOnClickListener(v -> {

            atualizarSipat();
            uploadImagemPerfil();
        });

        btn_sipat_cadastrar.setOnClickListener(v -> {
            Intent i = new Intent(SIPAT.this, Cadastrar.class);
            startActivity(i);
            overridePendingTransition(R.anim.mover_esquerda, R.anim.fade_out);
            finish();
        });

        btn_sipat_voltar.setOnClickListener(v -> {

            if (administrador != null) {

                Intent i = new Intent(SIPAT.this, Usuario.class);
                i.putExtra("administrador", administrador);
                i.putExtra("nomeAdm", nomeAdm);
                i.putExtra("emailAdm", emailAdm);
                i.putExtra("posicaoAdm", posicaoAdm);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
                finish();

            } else if (emailSesmt != null) {

                Intent i = new Intent(SIPAT.this, Usuario.class);
                i.putExtra("nomeSesmt", nomeSesmt);
                i.putExtra("emailSesmt", emailSesmt);
                i.putExtra("posicaoSesmt", posicaoSesmt);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
                finish();

            } else if (emailCipeiro != null) {

                Intent i = new Intent(SIPAT.this, Usuario.class);
                i.putExtra("nomeCipeiro", nomeCipeiro);
                i.putExtra("emailCipeiro", emailCipeiro);
                i.putExtra("posicaoCipeiro", posicaoCipeiro);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
                finish();

            } else if (emailColaborador != null) {

                Intent i = new Intent(SIPAT.this, Usuario.class);
                i.putExtra("nomeColaborador", nomeColaborador);
                i.putExtra("emailColaborador", emailColaborador);
                i.putExtra("posicaoColaborador", posicaoColaborador);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
                finish();

            } else if (emailConfirmar != null) {

                Intent i = new Intent(SIPAT.this, Usuario.class);
                i.putExtra("nomeConfirmar", nomeConfirmar);
                i.putExtra("emailConfirmar", emailConfirmar);
                i.putExtra("posicaoConfirmar", posicaoConfirmar);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
                finish();

            } else {

                Intent i = new Intent(SIPAT.this, Usuario.class);
                i.putExtra("posicaoVisitante", posicaoVisitante);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
                finish();
            }
        });

        btn_sipat_sair.setOnClickListener(v -> {

            FirebaseAuth.getInstance().signOut();

            Intent i = new Intent(SIPAT.this, Splash_Sair.class);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        });
    }

    private void limparTemasSipat() {

        AlertDialog.Builder builder = new AlertDialog.Builder(SIPAT.this);
        builder.setTitle("Confirme a exclusão dos Temas");
        builder.setMessage("Todos os dados serão excluídos.");

        builder.setPositiveButton("Excluir", (dialog, which) -> {

            FirebaseDatabase.getInstance().getReference().child("sipat").removeValue();

            Toast.makeText(SIPAT.this, "Dados excluídos", Toast.LENGTH_LONG).show();

        });

        builder.setNegativeButton("Cancelar", (dialog, which) -> {

            Toast.makeText(SIPAT.this, "Cancelado", Toast.LENGTH_LONG).show();

        });

        builder.show();
    }

    private void atualizarSipat() {

        String etVazio = "";

        tema_i = et_sipat_tema_i.getText().toString();
        tema_ii = et_sipat_tema_ii.getText().toString();
        tema_iii = et_sipat_tema_iii.getText().toString();
        tema_iv = et_sipat_tema_iv.getText().toString();
        tema_v = et_sipat_tema_v.getText().toString();
        local_i = et_sipat_local_i.getText().toString();
        local_ii = et_sipat_local_ii.getText().toString();
        local_iii = et_sipat_local_iii.getText().toString();
        local_iv = et_sipat_local_iv.getText().toString();
        local_v = et_sipat_local_v.getText().toString();
        dia_i = et_sipat_dia_i.getText().toString();
        dia_ii = et_sipat_dia_ii.getText().toString();
        dia_iii = et_sipat_dia_iii.getText().toString();
        dia_iv = et_sipat_dia_iv.getText().toString();
        dia_v = et_sipat_dia_v.getText().toString();

        String sipatId = databaseReference.push().getKey();
        sipatModel = new SipatModel(imagem, tema_i, tema_ii, tema_iii, tema_iv, tema_v, local_i,
                local_ii, local_iii, local_iv, local_v, dia_i, dia_ii, dia_iii, dia_iv, dia_v);

        tema_i = sipatModel.getTema_i();
        tema_ii = sipatModel.getTema_ii();
        tema_iii = sipatModel.getTema_iii();
        tema_iv = sipatModel.getTema_iv();
        tema_v = sipatModel.getTema_v();
        local_i = sipatModel.getLocal_i();
        local_ii = sipatModel.getLocal_ii();
        local_iii = sipatModel.getLocal_iii();
        local_iv = sipatModel.getLocal_iv();
        local_v = sipatModel.getLocal_v();
        dia_i = sipatModel.getDia_i();
        dia_ii = sipatModel.getDia_ii();
        dia_iii = sipatModel.getDia_iii();
        dia_iv = sipatModel.getDia_iv();
        dia_v = sipatModel.getDia_v();

        databaseReference.child(sipatId).setValue(sipatModel);

        for (EditText text : editTexts) {

            text.setText(etVazio);
        }

        temasSipat();
    }

    private void temasSipat() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        sipatModel = dataSnapshot.getValue(SipatModel.class);

                        tv_sipat_tema_i.setText(sipatModel.getTema_i());
                        tv_sipat_tema_ii.setText(sipatModel.getTema_ii());
                        tv_sipat_tema_iii.setText(sipatModel.getTema_iii());
                        tv_sipat_tema_iv.setText(sipatModel.getTema_iv());
                        tv_sipat_tema_v.setText(sipatModel.getTema_v());
                        tv_sipat_local_i.setText(sipatModel.getLocal_i());
                        tv_sipat_local_ii.setText(sipatModel.getLocal_ii());
                        tv_sipat_local_iii.setText(sipatModel.getLocal_iii());
                        tv_sipat_local_iv.setText(sipatModel.getLocal_iv());
                        tv_sipat_local_v.setText(sipatModel.getLocal_v());
                        tv_sipat_dia_i.setText(sipatModel.getDia_i());
                        tv_sipat_dia_ii.setText(sipatModel.getDia_ii());
                        tv_sipat_dia_iii.setText(sipatModel.getDia_iii());
                        tv_sipat_dia_iv.setText(sipatModel.getDia_iv());
                        tv_sipat_dia_v.setText(sipatModel.getDia_v());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(SIPAT.this, "Erro " + error, Toast.LENGTH_LONG).show();
            }
        });

        //databaseReference.addValueEventListener(valueEventListener);
    }

    public void requestPermissonStorageImages() {

        if (ContextCompat.checkSelfPermission(SIPAT.this, required_permissions[0]) ==
                PackageManager.PERMISSION_GRANTED) {

            Log.d(TAG, required_permissions[0] + " Granted");
            is_storage_image_permitted = true;
            requestPermissionCameraAccess();

        } else {

            request_permission_launcher_storage_images.launch(required_permissions[0]);
        }
    }

    private void obterImagem_Galeria() {

        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(i,"Escolha uma imagem"), 0);
    }

    private void uploadImagemPerfil() {

        final DialogProgress dialogProgress = new DialogProgress();
        dialogProgress.show(getSupportFragmentManager(), "");
        StorageReference storageReference = firebaseStorage.getReference().child("imagem_sipat");
        StorageReference nome_imagem = storageReference.child("sipat"
                + System.currentTimeMillis() + ".jpg");

        BitmapDrawable drawable = (BitmapDrawable) iv_sipat_cabecalho.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        UploadTask uploadTask = nome_imagem.putBytes(bytes.toByteArray());

        uploadTask.continueWithTask(task -> nome_imagem.getDownloadUrl()).addOnCompleteListener(task -> {

            if (task.isSuccessful()) {
                Uri uri = task.getResult();

                imagem_sipat = uri.toString();

                dialogProgress.dismiss();

            } else {

                Toast.makeText(getBaseContext(), "Erro ao realizar upload da imagem", Toast.LENGTH_LONG).show();
                dialogProgress.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == 0) {

                if (data != null) {

                    uri_for_camera = data.getData();
                    Glide.with(getBaseContext()).asBitmap().load(uri_for_camera).listener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, @Nullable Object model, @NonNull Target<Bitmap> target, boolean isFirstResource) {
                            Toast.makeText(SIPAT.this, "Falha ao carregar imagem", Toast.LENGTH_LONG).show();
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(@NonNull Bitmap resource, @NonNull Object model, Target<Bitmap> target, @NonNull DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    }).into(iv_sipat_cabecalho);

                } else {

                    Toast.makeText(SIPAT.this, "Falha ao carregar imagem", Toast.LENGTH_LONG).show();

                }
            }
        }
    }

    private ActivityResultLauncher<String> request_permission_launcher_camera_access =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {

                if (isGranted) {

                    Log.d(TAG, required_permissions[1] + " Granted");
                    is_storage_image_permitted = true;

                } else {

                    Log.d(TAG, required_permissions[1] + " Not Granted");
                    is_storage_image_permitted = false;
                }
                requestPermissionCameraAccess();
            });

    private ActivityResultLauncher<String> request_permission_launcher_storage_images =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {

                if (isGranted) {

                    Log.d(TAG, required_permissions[0] + " Granted");
                    is_storage_image_permitted = true;

                } else {

                    Log.d(TAG, required_permissions[0] + " Not Granted");
                    is_storage_image_permitted = false;
                }
                requestPermissionCameraAccess();
            });

    private void requestPermissionCameraAccess() {

        if (ContextCompat.checkSelfPermission(SIPAT.this, required_permissions[1]) ==
                PackageManager.PERMISSION_GRANTED) {

            Log.d(TAG, required_permissions[1] + " Granted");
            is_camera_access_permitted = true;

        } else {

            request_permission_launcher_camera_access.launch(required_permissions[1]);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (valueEventListener != null) {
            databaseReference.removeEventListener(valueEventListener);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (valueEventListener != null) {
            databaseReference.removeEventListener(valueEventListener);
        }
    }
}