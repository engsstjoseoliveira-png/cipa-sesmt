package br.net.technet.cipa.Adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import br.net.technet.cipa.R;
import br.net.technet.cipa.Uteis.UserModel;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapterAdm extends FirebaseRecyclerAdapter<UserModel, MainAdapterAdm.myViewHolderAdm> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MainAdapterAdm(@NonNull FirebaseRecyclerOptions<UserModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MainAdapterAdm.myViewHolderAdm holder, @SuppressLint("RecyclerView") final int position, @NonNull UserModel model) {

        holder.tv_cipeiros_nome.setText(model.getNome());
        holder.tv_cipeiros_presidencia.setText(model.getPresidencia());
        holder.tv_cipeiros_ocupacao.setText(model.getOcupacao());
        holder.tv_cipeiros_posicao.setText(model.getPosicao());
        holder.tv_cipeiros_gestao.setText(model.getGestao());

        Glide.with(holder.civ_foto_cipeiros.getContext())
                .load(model.getFoto())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.civ_foto_cipeiros);

        holder.btn_cvc_editar.setVisibility(View.VISIBLE);
        holder.btn_cvc_excluir.setVisibility(View.VISIBLE);
        holder.tv_cipeiros_presidencia.setVisibility(View.VISIBLE);

        //Editar Cadastro do membro da CIPA
        holder.btn_cvc_editar.setOnClickListener(v -> {

            final DialogPlus dialogPlus = DialogPlus.newDialog(holder.civ_foto_cipeiros.getContext())
                    .setContentHolder(new ViewHolder(R.layout.update_popup))
                    .setExpanded(true, 1600)
                    .create();

            View view = dialogPlus.getHolderView();
            EditText nome = view.findViewById(R.id.et_update_popup_nome);
            EditText presidencia = view.findViewById(R.id.et_update_popup_presidencia);
            EditText ocupacao = view.findViewById(R.id.et_update_popup_ocupacao);
            EditText posicao = view.findViewById(R.id.et_update_popup_posicao);
            EditText gestao = view.findViewById(R.id.et_update_popup_gestao);
            EditText foto = view.findViewById(R.id.et_update_popup_foto);

            Button btn_update_popup_cipeiros_atualizar = view.findViewById(R.id.btn_update_popup_cipeiros_atualizar);

            nome.setText(model.getNome());
            presidencia.setText(model.getPresidencia());
            ocupacao.setText(model.getOcupacao());
            posicao.setText(model.getPosicao());
            gestao.setText(model.getGestao());
            foto.setText(model.getFoto());

            dialogPlus.show();

            btn_update_popup_cipeiros_atualizar.setOnClickListener(v1 -> {

                Map<String, Object> map = new HashMap<>();
                map.put("nome", nome.getText().toString());
                map.put("presidencia", presidencia.getText().toString());
                map.put("ocupacao", ocupacao.getText().toString());
                map.put("posicao", posicao.getText().toString());
                map.put("gestao", gestao.getText().toString());
                map.put("foto", foto.getText().toString());

                FirebaseDatabase.getInstance().getReference()
                        .child("usuarios")
                        .child("cipeiros")
                        .child(getRef(position).getKey()).updateChildren(map)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                                Toast.makeText(holder.tv_cipeiros_nome.getContext(), "Atualização realizada com sucesso",
                                        Toast.LENGTH_LONG).show();
                                dialogPlus.dismiss();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(holder.tv_cipeiros_nome.getContext(), "Não foi possível realizar a atualização",
                                        Toast.LENGTH_LONG).show();
                                dialogPlus.dismiss();

                            }
                        });
            });
        });

        holder.btn_cvc_excluir.setOnClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(holder.tv_cipeiros_nome.getContext());
            builder.setTitle("Deseja Excluir o Cadastro?");
            builder.setMessage("O cadastro excluído não poderá ser recuperado");

            builder.setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    FirebaseDatabase.getInstance().getReference().child("cipeiros")
                            .child(getRef(position).getKey()).removeValue();

                }
            });

            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {


                    Toast.makeText(holder.civ_foto_cipeiros.getContext(), "Cancelado",
                            Toast.LENGTH_LONG).show();
                }
            });
            builder.show();
        });
    }

    @NonNull
    @Override
    public MainAdapterAdm.myViewHolderAdm onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_cipeiros, parent, false);
        return new myViewHolderAdm(view);
    }

    public class myViewHolderAdm extends RecyclerView.ViewHolder {

        CircleImageView civ_foto_cipeiros;
        TextView tv_cipeiros_nome, tv_cipeiros_ocupacao, tv_cipeiros_posicao, tv_cipeiros_gestao,
        tv_cipeiros_presidencia;
        Button btn_cvc_editar, btn_cvc_excluir;

        public myViewHolderAdm(@NonNull View itemView) {
            super(itemView);

            civ_foto_cipeiros = (CircleImageView) itemView.findViewById(R.id.civ_foto_cipeiro);
            tv_cipeiros_nome = (TextView) itemView.findViewById(R.id.tv_cipeiros_nome);
            tv_cipeiros_presidencia = (TextView) itemView.findViewById(R.id.tv_cipeiros_presidencia);
            tv_cipeiros_posicao = (TextView) itemView.findViewById(R.id.tv_cipeiros_posicao);
            tv_cipeiros_ocupacao = (TextView) itemView.findViewById(R.id.tv_cipeiros_ocupacao);
            tv_cipeiros_gestao = (TextView) itemView.findViewById(R.id.tv_cipeiros_gestao);

            btn_cvc_editar = (Button) itemView.findViewById(R.id.btn_cvc_editar);
            btn_cvc_excluir = (Button) itemView.findViewById(R.id.btn_cvc_excluir);
        }
    }
}
