package coriani.lorenzo.cookhub;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText email, password;
    Button loginButton, registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.btnLogin);
        registerButton = findViewById(R.id.btnRegister);

        loginButton.setOnClickListener(v -> loginUser());
        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void loginUser() {
        String emailText = email.getText().toString().trim();
        String passwordText = password.getText().toString().trim();

        String url = "https://tuo-dominio.com/api/login"; //

        StringRequest request = new StringRequest(Request.Method.POST, url, response -> {
            // Gestione login OK
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }, error -> {
            Toast.makeText(this, "Login fallito", Toast.LENGTH_SHORT).show();
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", emailText);
                params.put("password", passwordText);
                return params;
            }
        };

        Volley.newRequestQueue(this).add(request);
    }
}