package coriani.lorenzo.cookhub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;


import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        //Collega la bottom navigation al sistema di navigazione definito con navigation_bar
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        NavController navController = Navigation.findNavController(this, R.id.navigation_bar);
        NavigationUI.setupWithNavController(bottomNav, navController);

        //Quando clicchi sull'immagine del profilo, viene eseguito
        ImageView imageProfilo = findViewById(R.id.image_profilo);
        imageProfilo.setOnClickListener(v -> showDialog());

    }

        //converte la pagina xml dialog in oggetti view
            private void showDialog() {
            View dialogView = getLayoutInflater().inflate(R.layout.dialog, null);

            //Crea un dialog con quel layout
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setView(dialogView)
                    .setTitle("Benvenuto")
                    .create();

            Button btnLogin = dialogView.findViewById(R.id.btnLogin);
            Button btnRegister = dialogView.findViewById(R.id.btnRegister);

            //Se clicchi Login, il dialog si chiude e parte LoginActivity
            btnLogin.setOnClickListener(v -> {
                dialog.dismiss();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            });
            //Se clicchi Register, parte RegisterActivity
            btnRegister.setOnClickListener(v -> {
                dialog.dismiss();
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            });

            dialog.show();
    }
}