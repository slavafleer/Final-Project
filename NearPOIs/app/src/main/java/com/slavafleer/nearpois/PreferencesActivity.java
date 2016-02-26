package com.slavafleer.nearpois;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

import com.slavafleer.nearpois.db.FavoritesLogic;

// Preferences Activity for Action Bar Settings
public class PreferencesActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new AppPreferenceFragment())
                .commit();


    }

    public static class AppPreferenceFragment extends PreferenceFragment
            implements DialogInterface.OnClickListener {

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

            Preference deletingFavoritesButton = findPreference("deletingFavorites");
            if (deletingFavoritesButton != null) {
                deletingFavoritesButton.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                    @Override
                    public boolean onPreferenceClick(Preference arg0) {

                        Activity activity = getActivity();

                        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
                        alertDialog.setTitle(activity.getString(R.string.deleting_all_favorites));
                        alertDialog.setMessage(activity.getString(R.string.are_you_sure));
                        alertDialog.setButton(Dialog.BUTTON_POSITIVE, activity.getString(R.string.yes), AppPreferenceFragment.this);
                        alertDialog.setButton(Dialog.BUTTON_NEGATIVE, activity.getString(R.string.no), AppPreferenceFragment.this);
                        alertDialog.setCancelable(false);
                        alertDialog.show();

                        return true;
                    }
                });
            }
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {

            switch (which) {
                case Dialog.BUTTON_POSITIVE:
                    // Delete all favorites from DB
                    FavoritesLogic favoritesLogic = new FavoritesLogic(getActivity());
                    favoritesLogic.open();
                    favoritesLogic.deleteAllPois();
                    favoritesLogic.close();
                    break;
            }
        }
    }
}
