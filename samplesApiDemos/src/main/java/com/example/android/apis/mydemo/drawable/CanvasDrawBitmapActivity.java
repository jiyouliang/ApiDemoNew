package com.example.android.apis.mydemo.drawable;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.android.apis.R;

public class CanvasDrawBitmapActivity extends Activity {

    private ImageView mImg1;
    private ImageView mImg2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas_draw_bitmap);
        initView();


        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.balloon);

        mImg1.setImageBitmap(bitmap);


        Bitmap destBitmap = Bitmap.createBitmap(bitmap);
        Matrix matrix = new Matrix();
        matrix.postTranslate(0, -20);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        Canvas canvas = new Canvas();
//        canvas.drawBitmap(destBitmap, matrix, paint);
        Rect srcRect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        Rect destRect = new Rect(0, bitmap.getHeight(), bitmap.getWidth(), bitmap.getHeight() + 50);

        canvas.drawBitmap(destBitmap, srcRect, destRect, paint);

        mImg2.setImageBitmap(destBitmap);

    }

    private void initView() {
        mImg1 = (ImageView) findViewById(R.id.img1);
        mImg2 = (ImageView) findViewById(R.id.img2);
    }
}
