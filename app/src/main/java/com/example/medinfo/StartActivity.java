package com.example.medinfo;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class StartActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        ImageView startImage = findViewById(R.id.start_image);

        // Load the pop-up animation
        Animation popUpAnimation = AnimationUtils.loadAnimation(this, R.anim.pop_up);

        // Make the image visible and start animation
        startImage.setVisibility(View.VISIBLE);
        startImage.startAnimation(popUpAnimation);

        // Delay before moving to SignInActivity
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(StartActivity.this, SignInActivity.class);
            startActivity(intent);
            finish(); // Close splash activity
        }, 3000); // Show splash screen for 3 seconds
    }
}
