package br.net.technet.cipa.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import br.net.technet.cipa.R;
import br.net.technet.cipa.Uteis.UserModel;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapterCipa extends FirebaseRecyclerAdapter<UserModel, MainAdapterCipa.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MainAdapterCipa(@NonNull FirebaseRecyclerOptions<UserModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MainAdapterCipa.myViewHolder holder, final int position, @NonNull UserModel model) {

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
    }

    @NonNull
    @Override
    public MainAdapterCipa.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_cipeiros,
                parent, false);
        return new myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        CircleImageView civ_foto_cipeiros;
        TextView tv_cipeiros_nome, tv_cipeiros_posicao, tv_cipeiros_ocupacao, tv_cipeiros_gestao,
        tv_cipeiros_presidencia;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            civ_foto_cipeiros = (CircleImageView) itemView.findViewById(R.id.civ_foto_cipeiro);
            tv_cipeiros_nome = (TextView) itemView.findViewById(R.id.tv_cipeiros_nome);
            tv_cipeiros_presidencia = (TextView) itemView.findViewById(R.id.tv_cipeiros_presidencia);
            tv_cipeiros_posicao = (TextView) itemView.findViewById(R.id.tv_cipeiros_posicao);
            tv_cipeiros_ocupacao = (TextView) itemView.findViewById(R.id.tv_cipeiros_ocupacao);
            tv_cipeiros_gestao = (TextView) itemView.findViewById(R.id.tv_cipeiros_gestao);
        }
    }
}
