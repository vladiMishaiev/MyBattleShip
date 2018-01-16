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
        image = new ImageView(context);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        image.setLayoutParams(layoutParams);
        image.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        setBackgroundResource(R.drawable.border);
        this.addView(image);

    }
}
