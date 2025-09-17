package br.net.technet.cipa.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.net.technet.cipa.R;

public class MainAdapterViewPager extends RecyclerView.Adapter<MainAdapterViewPager.ViewHolder> {


    private int[] images = {R.drawable.cipa_mais, R.drawable.sesmt,
            R.drawable.sipat, R.drawable.sua_empresa, R.drawable.saiba_mais, R.drawable.seja_um_cipeiro,
            R.drawable.dds, R.drawable.sugestoes};

    private String cipa = "Conheça os membros da CIPA";
    private String sesmt = "Conheça o SESMT";
    private String sipat = "Fique por dentro da SIPAT";
    private String sua_empresa = "Sua empresa e a SST";
    private String saiba_mais = "O que é SST?";
    private String seja_um_cipeiro = "Quer ser um cipeiro?";
    private String dds = "Dica Diária de Segurança";
    private String sugestoes = "Dê sua opinião!";
    private String[] temas = {cipa, sesmt, sipat, sua_empresa, saiba_mais, seja_um_cipeiro, dds, sugestoes};
    private Context ctx;

    private final RecyclerViewInterface recyclerViewInterface;

    // Constructor of our ViewPager2Adapter class
    public MainAdapterViewPager(Context ctx, RecyclerViewInterface recyclerViewInterface) {

        this.ctx = ctx;
        this.recyclerViewInterface = recyclerViewInterface;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(ctx).inflate(R.layout.item, parent, false);

        return new ViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.images.setImageResource(images[position]);
        holder.temas.setText(temas[position]);

    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView images;
        TextView temas;

        public ViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            images = itemView.findViewById(R.id.ivImage);
            temas = itemView.findViewById(R.id.tv_usuario_temas);
            itemView.setOnClickListener(v -> {

                if (recyclerViewInterface != null) {

                    int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION) {

                        recyclerViewInterface.onItemClick(pos);

                    }
                }
            });
        }
    }
}
