package com.example.android.apis.mydemo.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.example.android.apis.R;

public class AudioPlayFloatingView extends RelativeLayout {
    public AudioPlayFloatingView(Context context) {
        this(context, null, 0);
    }

    public AudioPlayFloatingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AudioPlayFloatingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_audio_play_floating, this, true);
    }


}
