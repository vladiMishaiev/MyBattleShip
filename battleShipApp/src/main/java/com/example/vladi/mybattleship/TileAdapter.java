package com.example.vladi.mybattleship;
/**
 * Created by vladi on 12/30/2017.
 */
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.example.vladi.mybattleship.Logic.Board;


public class TileAdapter extends BaseAdapter {
    private static final String TAG = "TileAdapter";
    private Context mContext;
    private Board mBoard;

    public TileAdapter(Context context, Board board) {
        mContext = context;
        mBoard = board;
    }
    @Override
    public int getCount() {
        return (mBoard.getCols()*mBoard.getRows());
    }

    @Override
    public Object getItem(int position) {
        return mBoard.getTile(position/mBoard.getCols(),position%mBoard.getCols());
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        int row = position/mBoard.getCols();
        int col = position%mBoard.getCols();
        TileView tileView;
        tileView = (TileView)view;
        if(tileView == null)
            tileView = new TileView(mContext);
        //hit on a ship (computer/player)
        if (mBoard.getTile(row,col).isHit() && !mBoard.getTile(row,col).isEmptySlot()){
            tileView.setBackgroundResource(R.drawable.red);
        //show unhit ships on player board
        }else if (mBoard.isPlayerBoard() && !mBoard.getTile(row,col).isHit() && !mBoard.getTile(row,col).isEmptySlot()){
            tileView.setBackgroundResource(R.drawable.black);
        //show missed shots on computer board (hit on empty slots)
        }else if (/*!mBoard.isPlayerBoard() &&*/ mBoard.getTile(row,col).isEmptySlot() && mBoard.getTile(row,col).isHit()){
            tileView.setBackgroundResource(R.drawable.miss);
        }else{
            tileView.setBackgroundResource(R.drawable.border);
        }

        //if(mBoard.getTile(row, col).getmIsPressed()) {
            //if (mBoard.getTile(row, col).getmIsMine())
              //  tileView.setBackgroundResource(R.drawable.mine);
            //else {
          //      String text = String.valueOf(mBoard.getTile(row, col).getmNumOfNearByMines());
            //    if(Integer.valueOf(text) == 0)
              //      tileView.text.setText("");
                //else
                  //  tileView.text.setText(text);
                //tileView.setBackgroundColor(Color.parseColor("#e0e0e0"));
            //}
        //}
        //else if(mBoard.getTile(row, col).getmIsFlag())
          //  tileView.setBackgroundResource(R.drawable.background_with_flag);
        //else
          //  tileView.setBackgroundColor(Color.parseColor("#a8a8a8"));
        return tileView;
    }
}
