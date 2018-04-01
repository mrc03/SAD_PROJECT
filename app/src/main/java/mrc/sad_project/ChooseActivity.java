package mrc.sad_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ChooseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.choose_donate:
                Intent donateIntent = new Intent(this, DonateActivity.class);
                startActivity(donateIntent);
                break;
            case R.id.choose_search:
                Intent searchIntent = new Intent(this, SearchActivity.class);
                startActivity(searchIntent);
                break;
            default:
                Intent defaultIntent = new Intent(this, ChooseActivity.class);
                startActivity(defaultIntent);
                break;
        }
    }
}
