package br.net.technet.cipa.Home;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Objects;
import java.util.regex.Pattern;

import br.net.technet.cipa.R;
import br.net.technet.cipa.Telas.Termo_de_aceite;
import br.net.technet.cipa.Uteis.DialogProgress;
import br.net.technet.cipa.Uteis.UserModel;
import br.net.technet.cipa.Uteis.Uteis;
import de.hdodenhof.circleimageview.CircleImageView;

@RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
public class Cadastrar extends AppCompatActivity {

    Button btn_cadastrar_cadastrar, btn_cadastrar_voltar, btn_cadastrar_sair,
            btn_cadastrar_foto_galeria, btn_cadastrar_foto_camera, btn_cadastrar_termos;
    EditText et_cadastrar_nome, et_cadastrar_email, et_cadastrar_senha, et_cadastrar_ocupacao;

    CheckBox cb_cadastrar_termo_de_aceite;
    CircleImageView civ_cadastrar_foto;
    WebView webv_cadastrar_termo_de_aceite;

    private FirebaseAuth firebaseAuth;
    FirebaseStorage storage;
    DatabaseReference reference;
    SharedPreferences sharedPreferences = null;

    String cadastroPosicao, cadastroNome, cadastroEmail, cadastroSenha, cadastroFoto, cadastroOcupacao,
            cadastroGestao, cadastroPresidencia, cadastroAdministrador, teste;
    UserModel userModel;

    String[] required_permissions = new String[] {

            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.CAMERA
    };

    boolean is_storage_image_permitted = false;
    boolean is_camera_access_permitted = false;
    boolean imagem_perfil = false;
    boolean termoAceite;

    String TAG = "Permission";
    Uri uri_for_camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);
        Objects.requireNonNull(getSupportActionBar()).hide();

        cadastroPosicao = "Confirmar Cadastro";

        btn_cadastrar_cadastrar = findViewById(R.id.btn_cadastrar_cadastrar);
        btn_cadastrar_voltar = findViewById(R.id.btn_cadastrar_voltar);
        btn_cadastrar_sair = findViewById(R.id.btn_cadastrar_sair);
        btn_cadastrar_foto_galeria = findViewById(R.id.btn_cadastrar_foto_galeria);
        btn_cadastrar_foto_camera = findViewById(R.id.btn_cadastrar_foto_camera);
        btn_cadastrar_termos = findViewById(R.id.btn_cadastrar_termos);

        et_cadastrar_nome = findViewById(R.id.et_cadastrar_nome);
        et_cadastrar_email = findViewById(R.id.et_cadastrar_email);
        et_cadastrar_senha = findViewById(R.id.et_cadastrar_senha);
        et_cadastrar_ocupacao = findViewById(R.id.et_cadastrar_ocupacao);

        civ_cadastrar_foto = findViewById(R.id.civ_cadastrar_foto);

        webv_cadastrar_termo_de_aceite = findViewById(R.id.webv_cadastrar_termo_de_aceite);
        cb_cadastrar_termo_de_aceite = findViewById(R.id.cb_cadastrar_termo_de_aceite);

        firebaseAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("usuarios").child("confirmar_cadastro");
        storage = FirebaseStorage.getInstance();
        userModel = new UserModel(cadastroAdministrador, cadastroEmail, cadastroFoto, cadastroGestao,
                cadastroNome, cadastroOcupacao, cadastroSenha, cadastroPosicao, cadastroPresidencia);

        sharedPreferences = getSharedPreferences("night", 0);

        if (!is_storage_image_permitted) {
            requestPermissonStorageImages();
        }

        String termo = "<html><body>" + "<p align=\"justify\">"
                + "<font color = \"green\" size =\"4\" face =\"Verdana\">"
                + getString(R.string.termo_de_aceite)
                + "</font>" + "</p>" + "</body></html>";

        webv_cadastrar_termo_de_aceite.setBackgroundColor(0);
        webv_cadastrar_termo_de_aceite.loadData(termo, "text/html", "utf-8");

        cb_cadastrar_termo_de_aceite.setOnCheckedChangeListener((buttonView, isChecked) -> {
            termoAceite = isChecked;
            teste = Boolean.toString(isChecked);

            if (teste.equals("true")) {

                checkBoxTrue();
            } else {

                checkBoxFalse();
            }
        });

        chegarPermissoesCheckBox();

        btn_cadastrar_cadastrar.setOnClickListener(v -> {

            final DialogProgress dialogProgress = new DialogProgress();
            dialogProgress.show(getSupportFragmentManager(), "");

            cadastroEmail = et_cadastrar_email.getText().toString();
            cadastroNome = et_cadastrar_nome.getText().toString();
            cadastroSenha = et_cadastrar_senha.getText().toString();

            if (cadastroEmail.isEmpty() || cadastroSenha.isEmpty()) {

                Toast.makeText(Cadastrar.this, "Os campos e-mail e senha são obrigatórios",
                        Toast.LENGTH_LONG).show();
                dialogProgress.dismiss();

            } else {

                if (Uteis.verificarInternet(this)) {

                    if (termoAceite) {

                        if (imagem_perfil) {

                            uploadImagemPerfil();

                        } else {

                            cadastrarUsuario(cadastroEmail, cadastroSenha);
                            dialogProgress.dismiss();
                        }

                    } else {

                        Toast.makeText(Cadastrar.this, "Você precisa ler e aceitar os termos de utilização de dados",
                                Toast.LENGTH_LONG).show();
                        dialogProgress.dismiss();
                    }
                }
            }
        });

        btn_cadastrar_termos.setOnClickListener(v -> {

            if (cb_cadastrar_termo_de_aceite.isChecked()) {

                Toast.makeText(Cadastrar.this, "A opção de aceite do Termo de Uso está selecionada",
                        Toast.LENGTH_LONG).show();

            } else {

                Intent i = new Intent(Cadastrar.this, Termo_de_aceite.class);
                startActivity(i);
                overridePendingTransition(R.anim.mover_esquerda, R.anim.fade_out);
                finish();
            }
        });

        btn_cadastrar_voltar.setOnClickListener(v -> {

            FirebaseAuth.getInstance().signOut();

            Intent i = new Intent(Cadastrar.this, Home.class);
            startActivity(i);
            overridePendingTransition(R.anim.mover_esquerda, R.anim.fade_out);
            finish();
        });

        btn_cadastrar_sair.setOnClickListener(v -> {

            FirebaseAuth.getInstance().signOut();

            Intent i = new Intent(Cadastrar.this, Splash_Sair.class);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        });

        btn_cadastrar_foto_camera.setOnClickListener(v -> {

            imagem_perfil = true;

            if (is_camera_access_permitted) {

                openCamera();

            } else {

                requestPermissionCameraAccess();
            }
        });

        btn_cadastrar_foto_galeria.setOnClickListener(v -> {

            imagem_perfil = true;

            obterImagem_Galeria();

        });

        et_cadastrar_nome.setOnClickListener(v -> {
            checarPermissoesEditText();
        });

        et_cadastrar_ocupacao.setOnClickListener(v -> checarPermissoesEditText());

        et_cadastrar_email.setOnClickListener(v -> checarPermissoesEditText());

        et_cadastrar_senha.setOnClickListener(v -> checarPermissoesEditText());
    }

    private void checarPermissoesEditText() {

        if (cb_cadastrar_termo_de_aceite.isChecked()) {

            teste = Boolean.toString(termoAceite);

            if (teste.equals("true")) {

                checkBoxTrue();

            } else {

                checkBoxFalse();

                Toast.makeText(Cadastrar.this, "Aceite os Termos de Uso para habilitar essa função",
                        Toast.LENGTH_LONG).show();
            }

        } else if (!Objects.equals(teste, "true")){

            teste = "false";

            checkBoxFalse();

            Toast.makeText(Cadastrar.this, "Aceite os Termos de Uso para habilitar essa função",
                    Toast.LENGTH_SHORT).show();

        } else {

            checkBoxTrue();
        }
    }

    private void chegarPermissoesCheckBox() {

        if (termoAceite) {

            if (teste.equals("true")) {

                checkBoxTrue();
            }

            checkBoxFalse();
        }

        checkBoxFalse();
    }

    private void checkBoxFalse() {

        et_cadastrar_nome.setFocusable(false);
        et_cadastrar_ocupacao.setFocusable(false);
        et_cadastrar_email.setFocusable(false);
        et_cadastrar_senha.setFocusable(false);
    }

    private void checkBoxTrue() {

        et_cadastrar_nome.setFocusableInTouchMode(true);
        et_cadastrar_ocupacao.setFocusableInTouchMode(true);
        et_cadastrar_email.setFocusableInTouchMode(true);
        et_cadastrar_senha.setFocusableInTouchMode(true);
    }

    private void uploadImagemPerfil() {

        final DialogProgress dialogProgress = new DialogProgress();
        dialogProgress.show(getSupportFragmentManager(), "");
        StorageReference storageReference = storage.getReference().child("foto");
        StorageReference nome_imagem = storageReference.child(cadastroNome
                + System.currentTimeMillis() + ".jpg");

        BitmapDrawable drawable = (BitmapDrawable) civ_cadastrar_foto.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        UploadTask uploadTask = nome_imagem.putBytes(bytes.toByteArray());

        uploadTask.continueWithTask(task -> nome_imagem.getDownloadUrl()).addOnCompleteListener(task -> {

            if (task.isSuccessful()) {
                Uri uri = task.getResult();

                cadastroFoto = uri.toString();

                cadastrarUsuario(cadastroEmail, cadastroSenha);

                dialogProgress.dismiss();

            } else {

                Toast.makeText(getBaseContext(), "Erro ao realizar upload da imagem", Toast.LENGTH_LONG).show();
                dialogProgress.dismiss();
            }
        });
    }

    private void cadastrarUsuario(String cadastroEmail, String cadastroSenha) {

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(cadastroEmail, cadastroSenha)
                .addOnCompleteListener(task -> {

            if (task.isSuccessful()) {

                addUsuarios();

                Toast.makeText(Cadastrar.this, "Usuário cadastrado com sucesso",
                        Toast.LENGTH_LONG).show();

                Intent i = new Intent(Cadastrar.this, Login.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();

            } else if (verificarEmail(cadastroEmail)){

                Toast.makeText(Cadastrar.this, "Existe um cadastro com esse e-mail",
                        Toast.LENGTH_LONG).show();
            } else {

                Toast.makeText(Cadastrar.this, "Falha ao realizar cadastro",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void addUsuarios() {

        cadastroNome = et_cadastrar_nome.getText().toString().trim();
        cadastroEmail = et_cadastrar_email.getText().toString().trim();
        cadastroSenha = et_cadastrar_senha.getText().toString().trim();
        cadastroOcupacao = et_cadastrar_ocupacao.getText().toString().trim();
        cadastroPosicao = "Confirmar Cadastro";
        cadastroAdministrador = "";
        cadastroGestao = "";

        String id = reference.push().getKey();
        UserModel userModel1 = new UserModel(cadastroAdministrador, cadastroEmail, cadastroFoto, cadastroGestao,
                cadastroNome, cadastroOcupacao, cadastroSenha, cadastroPosicao, cadastroPresidencia);

        reference.child(id).setValue(userModel1);
    }

    public void requestPermissonStorageImages() {

        if (ContextCompat.checkSelfPermission(Cadastrar.this, required_permissions[0]) ==
                PackageManager.PERMISSION_GRANTED) {

            Log.d(TAG, required_permissions[0] + " Granted");
            is_storage_image_permitted = true;
            requestPermissionCameraAccess();

        } else {

            request_permission_launcher_storage_images.launch(required_permissions[0]);
        }
    }

    private void requestPermissionCameraAccess() {

        if (ContextCompat.checkSelfPermission(Cadastrar.this, required_permissions[1]) ==
        PackageManager.PERMISSION_GRANTED) {

            Log.d(TAG, required_permissions[1] + " Granted");
            is_camera_access_permitted = true;

        } else {

            request_permission_launcher_camera_access.launch(required_permissions[1]);
        }
    }

    public void openCamera() {

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "CIPA + A");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Capturado por CIPA+A");
        uri_for_camera = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri_for_camera);
        launcher_for_camera.launch(cameraIntent);
    }

    private void obterImagem_Galeria() {

        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(i,"Escolha uma imagem"), 0);
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
                            Toast.makeText(Cadastrar.this, "Falha ao carregar imagem", Toast.LENGTH_LONG).show();
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(@NonNull Bitmap resource, @NonNull Object model, Target<Bitmap> target, @NonNull DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    }).into(civ_cadastrar_foto);

                } else {

                    Toast.makeText(Cadastrar.this, "Falha ao carregar imagem", Toast.LENGTH_LONG).show();

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

    private ActivityResultLauncher<Intent> launcher_for_camera = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    if (result.getResultCode() == RESULT_OK) {

                        civ_cadastrar_foto.setImageURI(uri_for_camera);

                    }
                }
            });

    private boolean verificarEmail(String loginEmail) {
        Pattern patterns = Patterns.EMAIL_ADDRESS;
        return patterns.matcher(loginEmail).matches();
    }
}