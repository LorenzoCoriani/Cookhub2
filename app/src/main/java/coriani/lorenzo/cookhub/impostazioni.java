package coriani.lorenzo.cookhub;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class impostazioni extends Fragment {

    private Switch themeSwitch, notificationSwitch;
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "AppSettings";
    private static final String THEME_KEY = "DarkTheme";
    private static final String NOTIFICATIONS_KEY = "NotificationsEnabled";

    public impostazioni() {
        // Costruttore pubblico vuoto richiesto
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Carica il layout del fragment
        View view = inflater.inflate(R.layout.fragment_impostazioni, container, false);

        // Inizializza le SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        // Recupera le view dal layout
        themeSwitch = view.findViewById(R.id.theme_switch);
        notificationSwitch = view.findViewById(R.id.notification_switch);

        // Carica le impostazioni salvate
        loadSettings();

        // Listener per il cambio tema
        themeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            saveThemeSetting(isChecked);
            applyTheme(isChecked);
            Toast.makeText(getContext(), "Tema cambiato. Riavvia l'app per vedere le modifiche", Toast.LENGTH_SHORT).show();
        });

        // Listener per le notifiche
        notificationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            saveNotificationSetting(isChecked);
            Toast.makeText(getContext(), "Impostazioni notifiche aggiornate", Toast.LENGTH_SHORT).show();
        });

        return view;
    }

    private void loadSettings() {
        // Carica lo stato del tema (default: chiaro)
        boolean isDarkTheme = sharedPreferences.getBoolean(THEME_KEY, false);
        themeSwitch.setChecked(isDarkTheme);

        // Carica lo stato delle notifiche (default: attive)
        boolean notificationsEnabled = sharedPreferences.getBoolean(NOTIFICATIONS_KEY, true);
        notificationSwitch.setChecked(notificationsEnabled);
    }

    private void saveThemeSetting(boolean isDarkTheme) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(THEME_KEY, isDarkTheme);
        editor.apply();
    }

    private void saveNotificationSetting(boolean notificationsEnabled) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(NOTIFICATIONS_KEY, notificationsEnabled);
        editor.apply();
    }

    private void applyTheme(boolean isDarkTheme) {
        if (isDarkTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}