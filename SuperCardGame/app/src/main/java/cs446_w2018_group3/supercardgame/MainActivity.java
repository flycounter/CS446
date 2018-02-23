package cs446_w2018_group3.supercardgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public abstract class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //test code
        Intent intent = new Intent(MainActivity.this,SinglePlayActivity.class);
        startActivity(intent);
        finish();
    }
    }

}
