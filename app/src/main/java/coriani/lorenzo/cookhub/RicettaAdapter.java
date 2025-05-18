package coriani.lorenzo.cookhub;

import android.content.Context;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RicettaAdapter extends RecyclerView.Adapter<RicettaAdapter.ViewHolder> {

    private List<Ricetta> ricettaList;
    private Context context;

    // Costruttore
    public RicettaAdapter(Context context, List<Ricetta> ricettaList) {
        this.context = context;
        this.ricettaList = ricettaList;
    }

    // ViewHolder: rappresenta il singolo elemento (card) della RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titoloTextView;
        public TextView descrizioneTextView;
        public ImageView immagineImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            titoloTextView = itemView.findViewById(R.id.text_titolo);
            descrizioneTextView = itemView.findViewById(R.id.text_descrizione);
            immagineImageView = itemView.findViewById(R.id.image_ricetta);
        }
    }

    @Override
    public RicettaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ricetta, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RicettaAdapter.ViewHolder holder, int position) {
        Ricetta ricetta = ricettaList.get(position);

        holder.titoloTextView.setText(ricetta.getTitolo());
        holder.descrizioneTextView.setText(ricetta.getDescrizione());
        holder.immagineImageView.setImageResource(ricetta.getIdImmagine());
    }

    @Override
    public int getItemCount() {
        return ricettaList.size();
    }
}
