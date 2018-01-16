package com.example.vladi.mybattleship;
/**
 * Created by vladi on 12/30/2017.
 */
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class TileView extends LinearLayout {
    public TextView text;
    public ImageView image;

    public TileView(Context context) {
        super(context);

        this.setOrientation(VERTICAL);
        text = new TextView(context);
       // image = new ImageView(context);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        text.setLayoutParams(layoutParams);
        text.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        text.setTextSize(10f);
        setBackgroundResource(R.drawable.border);
        this.addView(text);

    }
}
