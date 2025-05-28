package coriani.lorenzo.cookhub;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment {

    private RicettaAdapter adapter;
    private List<Ricetta> listaRicette;

    public Home() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        listaRicette = new ArrayList<>();
        adapter = new RicettaAdapter(getContext(), listaRicette);
        recyclerView.setAdapter(adapter);

        caricaRicette();

        return view;
    }

    private void caricaRicette() {
        Activity activity = getActivity();
        if (activity == null || !isAdded()) {
            Toast.makeText(getContext(), "Errore: Fragment non attaccato", Toast.LENGTH_SHORT).show();
            return;
        }
        //uso requireActivity() al posto di requireContext() perchè potrebbe essere null
        SharedPreferences sharedPreferences = activity.getSharedPreferences("user", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("user_id", -1);

        if (userId == -1) {
            Toast.makeText(getContext(), "Utente non loggato", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "https://thecookshub.org/pages/index.php?user_id=" + userId;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                this::parseRicette,
                error -> {
                    error.printStackTrace();
                    Toast.makeText(getContext(), "Errore nel caricamento: " + error.getMessage(), Toast.LENGTH_LONG).show();
                });

        Volley.newRequestQueue(activity).add(request);
    }



    @SuppressLint("NotifyDataSetChanged")
    private void parseRicette(JSONArray response) {
        listaRicette.clear(); // Importante: svuota prima la lista
        try {
            for (int i = 0; i < response.length(); i++){
                JSONObject obj = response.getJSONObject(i);
                String titolo = obj.getString("titolo");
                String descrizione = obj.getString("descrizione");
                String immagineUrl = obj.getString("immagineUrl");

                Ricetta r = new Ricetta(titolo, descrizione, immagineUrl);
                listaRicette.add(r);
            }
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
