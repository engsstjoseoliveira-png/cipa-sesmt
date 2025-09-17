package br.net.technet.cipa.Home;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import br.net.technet.cipa.R;

public class Splash_Sair extends AppCompatActivity {

    ImageView iv_splash_sair_cipa;
    TextView tv_splash_sair_cipa_mais;
    CharSequence charSequence;
    int index;
    long delay = 200;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_sair);
        Objects.requireNonNull(getSupportActionBar()).hide();

        iv_splash_sair_cipa = findViewById(R.id.iv_splash_sair_cipa);
        tv_splash_sair_cipa_mais = findViewById(R.id.tv_splash_sair_cipa_mais);

        //Set full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Initialize object animator
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(
                iv_splash_sair_cipa,
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

        //Initialize handler
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Redirect to main activity
                //Finish activity
                finish();
            }
        }, 4000);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //When runnable is run
            //Set text
            tv_splash_sair_cipa_mais.setText(charSequence.subSequence(0, index++));
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
        tv_splash_sair_cipa_mais.setText("");
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