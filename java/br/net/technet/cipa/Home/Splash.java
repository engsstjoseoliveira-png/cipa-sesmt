package br.net.technet.cipa.Home;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.transition.Fade;

import java.util.Objects;

import br.net.technet.cipa.R;

public class Splash extends AppCompatActivity {

    ImageView iv_cipa;
    TextView tv_cipa_mais;
    CharSequence charSequence;
    int index;
    long delay = 200;
    Handler handler = new Handler();
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Objects.requireNonNull(getSupportActionBar()).hide();

        iv_cipa = findViewById(R.id.iv_cipa);
        tv_cipa_mais = findViewById(R.id.tv_cipa_mais);

        sharedPreferences = getSharedPreferences("night", 0);
        boolean booleanValue = sharedPreferences.getBoolean("night_mode", true);

        if (booleanValue) {

            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        //Set full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Initialize object animator
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(
                iv_cipa,
                PropertyValuesHolder.ofFloat("scaleX", 1.2f),
                PropertyValuesHolder.ofFloat("scaleY", 1.2f)
        );
        //Set duration
        objectAnimator.setDuration(500);
        //Set repeat count
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        //Set repeat mode
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        //Start animation
        objectAnimator.start();

        //Set animate text
        animatText("CIPA + A");

        new Handler().postDelayed(() -> {

            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            Intent i = new Intent(Splash.this, Home.class);

            startActivity(i);
            finish();

        }, 4000);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //When runnable is run
            //Set text
            tv_cipa_mais.setText(charSequence.subSequence(0, index++));
            //Check condition
            if (index <= charSequence.length()) {
                //When index is equal to text length
                //Run handler
                handler.postDelayed(runnable, delay);
            }
        }
    };

    //Create animated text method
    public void animatText(CharSequence cs) {
        //Set text
        charSequence = cs;
        //Clear index
        index = 0;
        //Clear text
        tv_cipa_mais.setText("");
        //Remove call back
        handler.removeCallbacks(runnable);
        //Run handler
        handler.postDelayed(runnable, delay);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}