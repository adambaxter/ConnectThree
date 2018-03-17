package com.spryfieldsoftwaresolutions.android.connectthree;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Adam Baxter on 09/03/18.
 *
 * Class that defines a player in the game.
 */

public class Player implements Parcelable{
    private String mName;
    private String mColor;
    private int mScore;

    public Player(String name) {
        mName = name;
        mScore = 0;
    }

    public String getName() {
        return mName;
    }

    public void setColor(PlayerList list) {
        final String PLAYER_COLOR_RED = "red";
        final String PLAYER_COLOR_YELLOW = "yellow";

        if (list.getSize() == 0) {
            mColor = PLAYER_COLOR_RED;
        }else{
            mColor = PLAYER_COLOR_YELLOW;
        }
    }

    public String getColor() {
        return  mColor;
    }

    public void addWin() {
        mScore++;
    }

    public int getScore() {
        return mScore;
    }

    public Player(Parcel in) {
        mName = in.readString();
        mColor = in.readString();
        mScore = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mColor);
        dest.writeInt(mScore);
    }

    public static final Parcelable.Creator<Player> CREATOR = new Parcelable.Creator<Player>() {

        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        public Player[] newArray(int size) {
            return new Player[size];
        }
    };


}
