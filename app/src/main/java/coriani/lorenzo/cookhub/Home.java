package coriani.lorenzo.cookhub;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment {

    private RecyclerView recyclerView;
    private RicettaAdapter adapter;
    private List<Ricetta> listaRicette;

    public Home() {}

    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        listaRicette = new ArrayList<>();
        adapter = new RicettaAdapter(getContext(), listaRicette);
        recyclerView.setAdapter(adapter);

        caricaRicette();

        return view;
    }

    private void caricaRicette() {
        String url = "https://tuo-dominio.com/api/ricette"; // <-- CAMBIA URL QUI

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    parseRicette(response);
                },
                error -> {
                    Toast.makeText(getContext(), "Errore nel caricamento", Toast.LENGTH_SHORT).show();
                });

        Volley.newRequestQueue(requireContext()).add(request);
    }

    private void parseRicette(Object response) {
        try {
            for (int i = 0; i < response(); i++) {
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
