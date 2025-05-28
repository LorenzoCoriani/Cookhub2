package coriani.lorenzo.cookhub;

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

public class RegisterActivity extends AppCompatActivity {
    EditText email, password;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        registerButton = findViewById(R.id.btnRegister);

        registerButton.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String emailText = email.getText().toString().trim();
        String passwordText = password.getText().toString().trim();

        String url = "https://thecookshub.org/pages/index.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, response -> {
            Toast.makeText(this, "Registrazione completata", Toast.LENGTH_SHORT).show();
            finish();
        }, error -> Toast.makeText(this, "Errore registrazione", Toast.LENGTH_SHORT).show()) {
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
