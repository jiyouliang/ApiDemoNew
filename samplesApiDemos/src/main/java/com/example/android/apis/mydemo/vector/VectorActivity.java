package com.example.android.apis.mydemo.vector;

import android.app.Activity;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.android.apis.R;

/**
 * android矢量图动画：VectorDrawable
 */
public class VectorActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vector);
    }

    public void anim(View v){
        ImageView iv = (ImageView) v;
        Drawable drawable = iv.getBackground();
        if(drawable instanceof Animatable){
            ((Animatable) drawable).start();
        }
    }
}
