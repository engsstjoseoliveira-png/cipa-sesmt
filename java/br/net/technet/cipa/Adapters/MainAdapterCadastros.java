package br.net.technet.cipa.Adapters;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import br.net.technet.cipa.R;
import br.net.technet.cipa.Uteis.UserModel;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapterCadastros extends FirebaseRecyclerAdapter<UserModel, MainAdapterCadastros.cadastroViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MainAdapterCadastros(@NonNull FirebaseRecyclerOptions<UserModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MainAdapterCadastros.cadastroViewHolder holder,
                                    final int position, @NonNull UserModel model) {

        holder.tv_cadastros_nome.setText(model.getNome());
        holder.tv_cadastros_presidencia.setText(model.getPresidencia());
        holder.tv_cadastros_ocupacao.setText(model.getOcupacao());
        holder.tv_cadastros_posicao.setText(model.getPosicao());
        holder.tv_cadastros_email.setText(model.getEmail());
        holder.tv_cadastros_gestao.setText(model.getGestao());

        Glide.with(holder.civ_foto_cadastros.getContext())
                .load(model.getFoto())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.civ_foto_cadastros);

        //Atualização dos membros da CIPA e do Cadastro Geral
        holder.btn_cvc_editar_cipeiro.setOnClickListener(v -> {

            final DialogPlus dialogPlus = DialogPlus.newDialog(holder.civ_foto_cadastros.getContext())
                    .setContentHolder(new ViewHolder(R.layout.update_popup))
                    .setExpanded(true, 1700)
                    .create();

            View view = dialogPlus.getHolderView();
            EditText nome = view.findViewById(R.id.et_update_popup_nome);
            EditText presidencia = view.findViewById(R.id.et_update_popup_presidencia);
            EditText ocupacao = view.findViewById(R.id.et_update_popup_ocupacao);
            EditText posicao = view.findViewById(R.id.et_update_popup_posicao);
            EditText email = view.findViewById(R.id.et_update_popup_email);
            EditText gestao = view.findViewById(R.id.et_update_popup_gestao);
            EditText administrador = view.findViewById(R.id.et_update_popup_administrador);
            EditText foto = view.findViewById(R.id.et_update_popup_foto);

            Button btn_update_popup_atualizar = view.findViewById(R.id.btn_update_popup_cipeiros_atualizar);

            nome.setText(model.getNome());
            presidencia.setText(model.getPresidencia());
            ocupacao.setText(model.getOcupacao());
            posicao.setText(model.getPosicao());
            email.setText(model.getEmail());
            gestao.setText(model.getGestao());
            administrador.setText(model.getAdministrador());
            foto.setText(model.getFoto());

            dialogPlus.show();

            btn_update_popup_atualizar.setOnClickListener(v1 -> {

                Map<String, Object> map = new HashMap<>();
                map.put("nome", nome.getText().toString());
                map.put("presidencia", presidencia.getText().toString());
                map.put("ocupacao", ocupacao.getText().toString());
                map.put("posicao", posicao.getText().toString());
                map.put("email", email.getText().toString());
                map.put("gestao", gestao.getText().toString());
                map.put("administrador", administrador.getText().toString());
                map.put("foto", foto.getText().toString());

                FirebaseDatabase.getInstance().getReference().child("usuarios").child("cipeiros")
                        .child(Objects.requireNonNull(getRef(position).getKey())).updateChildren(map)
                        .addOnSuccessListener(unused -> {

                            Toast.makeText(holder.tv_cadastros_nome.getContext(),
                                    "Cadastro da CIPA atualizado com sucesso",
                                    Toast.LENGTH_LONG).show();
                            dialogPlus.dismiss();
                        }).addOnFailureListener(e -> {

                            Toast.makeText(holder.tv_cadastros_nome.getContext(),
                                    "Não foi possível efetuar a atualização no cadastro da CIPA",
                                    Toast.LENGTH_LONG).show();
                            dialogPlus.dismiss();
                        });

                FirebaseDatabase.getInstance().getReference().child("usuarios").child("confirmar_cadastro")
                        .child(Objects.requireNonNull(getRef(position).getKey())).removeValue();

                FirebaseDatabase.getInstance().getReference().child("usuarios").child("confirmar_cadastro")
                        .child(Objects.requireNonNull(getRef(position).getKey())).updateChildren(map)
                        .addOnSuccessListener(unused -> {

                            Toast.makeText(holder.tv_cadastros_nome.getContext(),
                                    "Cadastro de Usuários atualizado com sucesso", Toast.LENGTH_LONG).show();

                            dialogPlus.dismiss();
                        }).addOnFailureListener(e -> {

                            Toast.makeText(holder.tv_cadastros_nome.getContext(),
                                    "Não foi possível efetuar a atualização do cadastro dos Usuários",
                                    Toast.LENGTH_LONG).show();

                            dialogPlus.dismiss();
                        });
            });
        });

        //Atualização dos membros do SESMT e do Cadastro Geral
        holder.btn_cvc_editar_sesmt.setOnClickListener(v -> {

            final DialogPlus dialogPlus = DialogPlus.newDialog(holder.civ_foto_cadastros.getContext())
                    .setContentHolder(new ViewHolder(R.layout.update_popup))
                    .setExpanded(true, 1700)
                    .create();

            View view = dialogPlus.getHolderView();
            EditText nome = view.findViewById(R.id.et_update_popup_nome);
            EditText presidencia = view.findViewById(R.id.et_update_popup_presidencia);
            EditText ocupacao = view.findViewById(R.id.et_update_popup_ocupacao);
            EditText posicao = view.findViewById(R.id.et_update_popup_posicao);
            EditText email = view.findViewById(R.id.et_update_popup_email);
            EditText gestao = view.findViewById(R.id.et_update_popup_gestao);
            EditText administrador = view.findViewById(R.id.et_update_popup_administrador);
            EditText foto = view.findViewById(R.id.et_update_popup_foto);

            Button btn_update_popup_atualizar = view.findViewById(R.id.btn_update_popup_cipeiros_atualizar);

            nome.setText(model.getNome());
            presidencia.setText(model.getPresidencia());
            ocupacao.setText(model.getOcupacao());
            posicao.setText(model.getPosicao());
            email.setText(model.getEmail());
            gestao.setText(model.getGestao());
            administrador.setText(model.getAdministrador());
            foto.setText(model.getFoto());

            dialogPlus.show();

            btn_update_popup_atualizar.setOnClickListener(v1 -> {

                Map<String, Object> map = new HashMap<>();
                map.put("nome", nome.getText().toString());
                map.put("presidencia", presidencia.getText().toString());
                map.put("ocupacao", ocupacao.getText().toString());
                map.put("posicao", posicao.getText().toString());
                map.put("email", email.getText().toString());
                map.put("gestao", gestao.getText().toString());
                map.put("administrador", administrador.getText().toString());
                map.put("foto", foto.getText().toString());

                FirebaseDatabase.getInstance().getReference().child("usuarios").child("sesmt")
                        .child(Objects.requireNonNull(getRef(position).getKey())).updateChildren(map)
                        .addOnSuccessListener(unused -> {

                            Toast.makeText(holder.tv_cadastros_nome.getContext(), "Atualização do SESMT realizada com sucesso",
                                    Toast.LENGTH_LONG).show();
                            dialogPlus.dismiss();
                        }).addOnFailureListener(e -> {

                            Toast.makeText(holder.tv_cadastros_nome.getContext(), "Não foi possível efetuar a atualização",
                                    Toast.LENGTH_LONG).show();
                            dialogPlus.dismiss();
                        });

                FirebaseDatabase.getInstance().getReference().child("usuarios").child("confirmar_cadastro")
                        .child(Objects.requireNonNull(getRef(position).getKey())).removeValue();

                FirebaseDatabase.getInstance().getReference().child("usuarios").child("confirmar_cadastro")
                        .child(Objects.requireNonNull(getRef(position).getKey())).updateChildren(map)
                        .addOnSuccessListener(unused -> {

                            Toast.makeText(holder.tv_cadastros_nome.getContext(),
                                    "Cadastro de Usuários atualizado com sucesso", Toast.LENGTH_LONG).show();

                            dialogPlus.dismiss();
                        }).addOnFailureListener(e -> {

                            Toast.makeText(holder.tv_cadastros_nome.getContext(),
                                    "Não foi possível efetuar a atualização do cadastro dos Usuários",
                                    Toast.LENGTH_LONG).show();

                            dialogPlus.dismiss();
                        });
            });
        });

        //Atualização dos colaboradores e do Cadastro Geral
        holder.btn_cvc_editar_colaborador.setOnClickListener(v -> {

            final DialogPlus dialogPlus = DialogPlus.newDialog(holder.civ_foto_cadastros.getContext())
                    .setContentHolder(new ViewHolder(R.layout.update_popup))
                    .setExpanded(true, 1700)
                    .create();

            View view = dialogPlus.getHolderView();
            EditText nome = view.findViewById(R.id.et_update_popup_nome);
            EditText ocupacao = view.findViewById(R.id.et_update_popup_ocupacao);
            EditText posicao = view.findViewById(R.id.et_update_popup_posicao);
            EditText email = view.findViewById(R.id.et_update_popup_email);
            EditText gestao = view.findViewById(R.id.et_update_popup_gestao);
            EditText administrador = view.findViewById(R.id.et_update_popup_administrador);
            EditText foto = view.findViewById(R.id.et_update_popup_foto);
            EditText presidencia = view.findViewById(R.id.et_update_popup_presidencia);

            Button btn_update_popup_atualizar = view.findViewById(R.id.btn_update_popup_cipeiros_atualizar);

            nome.setText(model.getNome());
            ocupacao.setText(model.getOcupacao());
            posicao.setText(model.getPosicao());
            email.setText(model.getEmail());
            gestao.setText(model.getGestao());
            administrador.setText(model.getAdministrador());
            foto.setText(model.getFoto());
            presidencia.setText(model.getPresidencia());

            dialogPlus.show();

            btn_update_popup_atualizar.setOnClickListener(v1 -> {

                Map<String, Object> map = new HashMap<>();
                map.put("nome", nome.getText().toString());
                map.put("presidencia", presidencia.getText().toString());
                map.put("ocupacao", ocupacao.getText().toString());
                map.put("posicao", posicao.getText().toString());
                map.put("email", email.getText().toString());
                map.put("gestao", gestao.getText().toString());
                map.put("administrador", administrador.getText().toString());
                map.put("foto", foto.getText().toString());

                FirebaseDatabase.getInstance().getReference().child("usuarios").child("colaboradores")
                        .child(Objects.requireNonNull(getRef(position).getKey())).updateChildren(map)
                        .addOnSuccessListener(unused -> {

                            Toast.makeText(holder.tv_cadastros_nome.getContext(), "Atualização realizada com sucesso",
                                    Toast.LENGTH_LONG).show();
                            dialogPlus.dismiss();
                        }).addOnFailureListener(e -> {

                            Toast.makeText(holder.tv_cadastros_nome.getContext(), "Não foi possível efetuar a atualização",
                                    Toast.LENGTH_LONG).show();
                            dialogPlus.dismiss();
                        });

                FirebaseDatabase.getInstance().getReference().child("usuarios").child("confirmar_cadastro")
                        .child(Objects.requireNonNull(getRef(position).getKey())).removeValue();

                FirebaseDatabase.getInstance().getReference().child("usuarios").child("confirmar_cadastro")
                        .child(Objects.requireNonNull(getRef(position).getKey())).updateChildren(map)
                        .addOnSuccessListener(unused -> {

                            Toast.makeText(holder.tv_cadastros_nome.getContext(),
                                    "Cadastro de Usuários atualizado com sucesso", Toast.LENGTH_LONG).show();

                            dialogPlus.dismiss();
                        }).addOnFailureListener(e -> {

                            Toast.makeText(holder.tv_cadastros_nome.getContext(),
                                    "Não foi possível efetuar a atualização do cadastro dos Usuários",
                                    Toast.LENGTH_LONG).show();

                            dialogPlus.dismiss();
                        });
            });
        });

        holder.btn_cvc_excluir.setOnClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(holder.tv_cadastros_nome.getContext());
            builder.setTitle("Confirma a exclusão do cadastro?");
            builder.setMessage("O cadastro excluído não poderá ser recuperado");

            builder.setPositiveButton("Excluir", (dialog, which) -> {

                FirebaseDatabase.getInstance().getReference()
                        .child("usuarios")
                        .child("confirmar_cadastro")
                        .child(Objects.requireNonNull(getRef(position).getKey())).removeValue();

                FirebaseDatabase.getInstance().getReference()
                        .child("usuarios")
                        .child("cipeiros")
                        .child(Objects.requireNonNull(getRef(position).getKey())).removeValue();

                FirebaseDatabase.getInstance().getReference()
                        .child("usuarios")
                        .child("sesmt")
                        .child(Objects.requireNonNull(getRef(position).getKey())).removeValue();

                FirebaseDatabase.getInstance().getReference()
                        .child("usuarios")
                        .child("colaboradores")
                        .child(Objects.requireNonNull(getRef(position).getKey())).removeValue();
            });

            builder.setNegativeButton("Cancelar", (dialog, which) -> Toast.makeText(holder.civ_foto_cadastros.getContext(), "Cancelado",
                    Toast.LENGTH_LONG).show());

            builder.show();

        });
    }

    @NonNull
    @Override
    public MainAdapterCadastros.cadastroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_cadastros, parent, false);
        return new cadastroViewHolder(view);
    }

    public class cadastroViewHolder extends RecyclerView.ViewHolder {

        CircleImageView civ_foto_cadastros;
        TextView tv_cadastros_nome, tv_cadastros_ocupacao, tv_cadastros_posicao, tv_cadastros_email,
                tv_cadastros_gestao, tv_cadastros_presidencia;
        Button btn_cvc_editar_sesmt, btn_cvc_editar_cipeiro, btn_cvc_editar_colaborador, btn_cvc_excluir;

        public cadastroViewHolder(@NonNull View itemView) {
            super(itemView);

            civ_foto_cadastros = (CircleImageView) itemView.findViewById(R.id.civ_foto_cadastros);
            tv_cadastros_nome = (TextView) itemView.findViewById(R.id.tv_cadastros_nome);
            tv_cadastros_presidencia = (TextView) itemView.findViewById(R.id.tv_cadastros_presidencia);
            tv_cadastros_ocupacao = (TextView) itemView.findViewById(R.id.tv_cadastros_ocupacao);
            tv_cadastros_posicao = (TextView) itemView.findViewById(R.id.tv_cadastros_posicao);
            tv_cadastros_email = (TextView) itemView.findViewById(R.id.tv_cadastros_email);
            tv_cadastros_gestao = (TextView) itemView.findViewById(R.id.tv_cadastros_gestao);

            btn_cvc_editar_sesmt = (Button) itemView.findViewById(R.id.btn_cvc_editar_sesmt);
            btn_cvc_editar_cipeiro = (Button) itemView.findViewById(R.id.btn_cvc_editar_cipeiro);
            btn_cvc_editar_colaborador = (Button) itemView.findViewById(R.id.btn_cvc_editar_colaborador);
            btn_cvc_excluir = (Button) itemView.findViewById(R.id.btn_cvc_excluir);
        }
    }
}
