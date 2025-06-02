package coriani.lorenzo.cookhub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);

        // Carica il fragment iniziale
        loadFragment(new Home());

        // Gestione click bottom navigation
        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int id = item.getItemId();

            if (id == R.id.homeFragment) {
                selectedFragment = new Home();
            } else if (id == R.id.profileFragment) {
                selectedFragment = new profilo();
            } else if (id == R.id.settingsFragment) {
                selectedFragment = new impostazioni();
            } else if (id == R.id.easterEggFragment) {
                selectedFragment = new easteregg();
            }

            if (selectedFragment != null) {
                loadFragment(selectedFragment);
                return true;
            }
            return false;
        });

        // Gestione Click immagine profilo
        ImageView imageProfilo = findViewById(R.id.image_profilo);
        imageProfilo.setOnClickListener(v -> showDialog());
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    private void showDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog, null);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setTitle("Benvenuto")
                .create();

        Button btnLogin = dialogView.findViewById(R.id.btnLogin);
        Button btnRegister = dialogView.findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(v -> {
            dialog.dismiss();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        });

        btnRegister.setOnClickListener(v -> {
            dialog.dismiss();
            startActivity(new Intent(MainActivity.this, RegisterActivity.class));
        });

        dialog.show();
    }
}
