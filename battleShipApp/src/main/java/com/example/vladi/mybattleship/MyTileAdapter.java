package com.example.vladi.mybattleship;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.GradientDrawable;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.vladi.mybattleship.Logic.Board;

import java.util.jar.Attributes;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by Max on 21/01/2018.
 */

public class MyTileAdapter extends BaseAdapter {
    private Context context;
    private Board board;

    public MyTileAdapter(Context context, Board board) {
        this.context = context;
        this.board = board;
    }

    @Override
    public int getCount() {
        return board.getCols()*board.getRows();
    }

    @Override
    public Object getItem(int position) {
        return board.getTile(position/board.getCols(),position%board.getCols());
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layout;

        ImageView blackShip;
        ImageView redShip;
        ImageView explosion;
        ImageView ancor;

        if (convertView == null) {
            layout = new LinearLayout(context);
            layout.setLayoutParams(new LinearLayout.LayoutParams(85, 85));

            layout.setBackgroundResource(R.drawable.border);

            blackShip = new ImageView(context);
            blackShip.setImageResource(R.drawable.black);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, MATCH_PARENT);
            params.setMargins(0,0,0,0);
            blackShip.setLayoutParams(params);
            blackShip.setAdjustViewBounds(true);
            blackShip.setScaleType(ImageView.ScaleType.FIT_CENTER);
            blackShip.setPadding(0, 0, 0, 0);
            blackShip.setEnabled(true);

            redShip = new ImageView(context);
            redShip.setLayoutParams(new GridView.LayoutParams(MATCH_PARENT, MATCH_PARENT));
            redShip.setScaleType(ImageView.ScaleType.CENTER_CROP);
            redShip.setPadding(16, 16, 16, 16);
            redShip.setEnabled(false);

            explosion = new ImageView(context);
            explosion.setLayoutParams(new GridView.LayoutParams(MATCH_PARENT, MATCH_PARENT));
            explosion.setScaleType(ImageView.ScaleType.CENTER_CROP);
            explosion.setPadding(16, 16, 16, 16);
            explosion.setEnabled(false);

            ancor = new ImageView(context);
            ancor.setLayoutParams(new GridView.LayoutParams(MATCH_PARENT, MATCH_PARENT));
            ancor.setScaleType(ImageView.ScaleType.CENTER_CROP);
            ancor.setPadding(16, 16, 16, 16);
            ancor.setEnabled(false);

            layout.addView(blackShip);
            layout.addView(redShip);
            layout.addView(explosion);
            layout.addView(ancor);


        } else {
            layout = (LinearLayout) convertView;
        }
//        blackShip.setImageResource(R.drawable.black);
        return layout;
    }
}
