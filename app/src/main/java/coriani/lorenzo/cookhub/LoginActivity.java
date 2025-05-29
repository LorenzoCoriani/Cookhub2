package coriani.lorenzo.cookhub;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inizializza le view
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        Button loginButton = findViewById(R.id.btnLogin);
        Button registerButton = findViewById(R.id.btnRegister);

        // Gestione click sul pulsante di login
        loginButton.setOnClickListener(v -> loginUser());

        // Gestione click sul pulsante di registrazione
        registerButton.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));
    }

    private void loginUser() {
        // Validazione input
        String emailText = email.getText().toString().trim();
        String passwordText = password.getText().toString().trim();

        if (emailText.isEmpty() || passwordText.isEmpty()) {
            Toast.makeText(this, "Inserisci email e password", Toast.LENGTH_SHORT).show();
            return;
        }

        StringRequest request = getStringRequest(emailText, passwordText);

        Volley.newRequestQueue(this).add(request);
    }

    @NonNull
    private StringRequest getStringRequest(String emailText, String passwordText) {
        String url = "https://thecookshub.org/pages/index.php";

        return new StringRequest(Request.Method.POST, url,
                LoginActivity.this::handleLoginResponse,
                LoginActivity.this::handleLoginError) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", emailText);
                params.put("password", passwordText);
                return params;
            }
        };
    }

    private void handleLoginResponse(String response) {
        try {
            JSONObject jsonResponse = new JSONObject(response);
            Log.d(TAG, "Response from server: " + jsonResponse);

            if (jsonResponse.has("user_id")) {
                int userId = jsonResponse.getInt("user_id");

                // Salva l'ID utente nelle SharedPreferences
                SharedPreferences sharedPref = getSharedPreferences("user", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("user_id", userId);
                editor.apply();

                // Avvia MainActivity
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else if (jsonResponse.has("error")){
                String errorMessage = jsonResponse.getString("error");
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Errore sconosciuto durante il login", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            Log.e(TAG, "JSON parsing error", e);
            Toast.makeText(this, "Errore nel formato della risposta dal server", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleLoginError(VolleyError error) {
        String errorMessage = "Errore durante il login";

        if (error.networkResponse != null) {
            errorMessage += " - Codice: " + error.networkResponse.statusCode;
            try {
                String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                JSONObject data = new JSONObject(responseBody);
                if (data.has("message")) {
                    errorMessage += ": " + data.getString("message");
                }
            } catch (Exception e) {
                Log.e(TAG, "Error parsing error response", e);
            }
        } else if (error.getMessage() != null) {
            errorMessage += ": " + error.getMessage();
        }
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
    }
}