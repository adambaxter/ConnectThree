package com.spryfieldsoftwaresolutions.android.connectthree;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Adam Baxter on 12/03/18.
 *
 * A Parcelable object to save game state
 */

public class GameState implements Parcelable{
    private Player mPlayerOne, mPlayerTwo;
    private int[] mGameState;
    private boolean mIsRedsturn;
    private int mMoveCount;
    private int mPlayerCount;

    GameState(Player p1, Player p2, int[] gameState, boolean isRedsTurn, int moveCount, int playerCount){
        mPlayerOne = p1;
        mPlayerTwo = p2;
        mGameState = gameState;
        mIsRedsturn = isRedsTurn;
        mMoveCount = moveCount;
        mPlayerCount = playerCount;

    }

    Player getPlayerOne() {
        return mPlayerOne;
    }

    Player getPlayerTwo() {
        return mPlayerTwo;
    }

    int[] getGameState(){
        return mGameState;
    }

    boolean getIsRedsTurn() {
        return mIsRedsturn;
    }

    int getMoveCount(){
        return mMoveCount;
    }

    int getPlayerCount(){
        return mPlayerCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    private GameState(Parcel in) {
        mPlayerOne = in.readParcelable(Player.class.getClassLoader());
        mPlayerTwo = in.readParcelable(Player.class.getClassLoader());
       mGameState = in.createIntArray();
       mIsRedsturn = in.readByte() != 0;
       mMoveCount = in.readInt();
       mPlayerCount = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mPlayerOne, flags);
        dest.writeParcelable(mPlayerTwo, flags);
        dest.writeIntArray(mGameState);
        dest.writeByte((byte) (mIsRedsturn ? 1 : 0));
        dest.writeInt(mMoveCount);
        dest.writeInt(mPlayerCount);
    }

    public static final Parcelable.Creator<GameState> CREATOR = new Parcelable.Creator<GameState>() {

        public GameState createFromParcel(Parcel in) {
            return new GameState(in);
        }

        public GameState[] newArray(int size) {
            return new GameState[size];
        }
    };

}
