package coriani.lorenzo.cookhub;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
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
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        NavController navController = Navigation.findNavController(this, R.id.navigation_bar);
        NavigationUI.setupWithNavController(bottomNav, navController);


        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.home2:
                    selectedFragment = new Home();
                    break;
                case R.id.profilo:
                    selectedFragment = new ProfileFragment(); // esempio
                    break;
                case R.id.impostazioni:
                    selectedFragment = new SettingsFragment(); // esempio
                    break;
            }

            if (selectedFragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
            }

            return true;
        });

    }
}