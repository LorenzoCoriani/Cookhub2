package coriani.lorenzo.cookhub;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

public class profilo extends Fragment {

    private ImageView profileImage;
    private TextView usernameText;
    private EditText bioEditText;
    private int userId;

    public profilo() {
        // Costruttore vuoto richiesto
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profilo, container, false);

        // Inizializza le view
        profileImage = view.findViewById(R.id.profile_image);
        usernameText = view.findViewById(R.id.username_text);
        bioEditText = view.findViewById(R.id.bio_edit_text);
        Button saveButton = view.findViewById(R.id.save_button);

        // Ottieni l'ID utente dalle SharedPreferences
        SharedPreferences sharedPref = requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        userId = sharedPref.getInt("user_id", -1);

        if (userId == -1) {
            Toast.makeText(getContext(), "Utente non loggato", Toast.LENGTH_SHORT).show();
            return view;
        }

        // Carica i dati del profilo
        loadProfileData();

        // Listener per il pulsante salva
        saveButton.setOnClickListener(v -> saveProfile());

        return view;
    }

    private void loadProfileData() {
        String url = "https://thecookshub.org/pages/get_profile.php?user_id=" + userId;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        // Estrai i dati dalla risposta JSON
                        String username = response.getString("username");
                        String bio = response.getString("bio");
                        String imageUrl = response.getString("profile_image");

                        // Aggiorna la UI con i dati del profilo
                        usernameText.setText(username);
                        bioEditText.setText(bio);

                        // Carica l'immagine del profilo con Glide
                        if (getContext() != null) {
                            Glide.with(getContext())
                                    .load(imageUrl)
                                    .placeholder(R.drawable.ic_persona)
                                    .error(R.drawable.ic_persona)
                                    .circleCrop()
                                    .into(profileImage);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "Errore nel parsing dei dati", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(getContext(), "Errore nel caricamento del profilo", Toast.LENGTH_SHORT).show();
                });

        // Aggiungi la richiesta alla coda di Volley
        if (getContext() != null) {
            Volley.newRequestQueue(getContext()).add(request);
        }
    }

    private void saveProfile() {
        String newBio = bioEditText.getText().toString().trim();
        String url = "https://thecookshub.org/pages/update_profile.php";

        try {
            JSONObject params = new JSONObject();
            params.put("user_id", userId);
            params.put("bio", newBio);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, params,
                    response -> {
                        try {
                            if (response.getBoolean("success")) {
                                Toast.makeText(getContext(), "Profilo aggiornato con successo", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Errore nell'aggiornamento", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Errore nella risposta del server", Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        error.printStackTrace();
                        Toast.makeText(getContext(), "Errore di connessione", Toast.LENGTH_SHORT).show();
                    });

            if (getContext() != null) {
                Volley.newRequestQueue(getContext()).add(request);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Errore nella creazione della richiesta", Toast.LENGTH_SHORT).show();
        }
    }
}