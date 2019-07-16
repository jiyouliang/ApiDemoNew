package com.example.android.apis.mydemo.vector;

import android.app.Activity;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

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

    public void anim(View v) {
        ImageView iv = (ImageView) v;
        Drawable drawable = iv.getBackground();
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }
    }

    /**
     * 路径变换
     *
     * @param v
     */
    public void anim2(View v) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            ImageView iv = (ImageView) v;
            //通过代码动态设置vector drawable
            Drawable drawable = (AnimatedVectorDrawable) getDrawable(R.drawable.star_animated_vector);
            iv.setImageDrawable(drawable);
            if (drawable != null) {
                ((AnimatedVectorDrawable) drawable).start();
            }
        }else{
            Toast.makeText(this, "小于5.0无法执行路径变换动画", Toast.LENGTH_SHORT).show();
        }

    }
}
