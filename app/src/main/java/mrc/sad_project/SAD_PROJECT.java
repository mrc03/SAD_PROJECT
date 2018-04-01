package mrc.sad_project;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by HP on 31-03-2018.
 */

public class SAD_PROJECT extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
