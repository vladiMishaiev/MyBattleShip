package com.example.vladi.mybattleship;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.vladi.mybattleship.Logic.Board;


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
        return board.getCols() * board.getRows();
    }

    @Override
    public Object getItem(int position) {
        return board.getTile(position / board.getCols(), position % board.getCols());
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView tileImage;


        if (convertView == null) {
            // Image Setting
            tileImage = new ImageView(context);
            tileImage.setBackgroundResource(R.drawable.border);
            tileImage.setLayoutParams(new GridView.LayoutParams(85, 85));
            tileImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            tileImage.setPadding(8, 8, 8, 8);
            tileImage.setEnabled(true);
        } else {
            tileImage = (ImageView) convertView;
        }
        int row = position / board.getCols();
        int col = position % board.getCols();

        if (board.getTile(row, col).isHit() && !board.getTile(row, col).isEmptySlot()) {
            tileImage.setImageResource(R.drawable.red);
        } else if (board.isPlayerBoard() && !board.getTile(row,col).isHit() && !board.getTile(row,col).isEmptySlot()){
            tileImage.setImageResource(R.drawable.black);
            //show missed shots on computer board (hit on empty slots)
        }else if (/*!mBoard.isPlayerBoard() &&*/ board.getTile(row,col).isEmptySlot() && board.getTile(row,col).isHit()){
            tileImage.setImageResource(R.drawable.miss);
        }else{
            tileImage.setImageResource(R.drawable.border);
        }

        return tileImage;
    }
}
