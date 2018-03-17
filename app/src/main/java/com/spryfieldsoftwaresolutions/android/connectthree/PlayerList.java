package com.spryfieldsoftwaresolutions.android.connectthree;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam Baxter on 09/03/18.
 *
 * Class to keep track of players
 */

public class PlayerList implements Parcelable {

    private static PlayerList sPlayerList;
    private List<Player> mPlayers;

    static PlayerList get() {
        if(sPlayerList == null) {
            sPlayerList = new PlayerList();
        }
        return sPlayerList;
    }

    private PlayerList() {
        mPlayers = new ArrayList<>();
    }

    void addPlayer(Player player) {
        if (mPlayers.size() < 2) {
            mPlayers.add(player);
        }
    }

    Player getPlayer(int position) {
        return mPlayers.get(position);
    }

    /* Return the postion of the player with a given name.
     * Retun -1 if name isnt found
     */
    int getPositionByName(String name) {
        for(Player player : mPlayers) {
            if(name.equals(player.getName())){
                return mPlayers.indexOf(player);
            }
        }
        return -1;
    }

    int getSize() {
        return mPlayers.size();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    private PlayerList(Parcel in) {
        Player player1 = in.readParcelable(Player.class.getClassLoader());
        Player player2 = in.readParcelable(Player.class.getClassLoader());
        mPlayers.add(player1);
        mPlayers.add(player2);

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if(mPlayers.size() > 0) {
            dest.writeParcelable(mPlayers.get(0), 0);
            dest.writeParcelable(mPlayers.get(1), 0);
        }
    }

    public static final Parcelable.Creator<PlayerList> CREATOR = new Parcelable.Creator<PlayerList>() {

        public PlayerList createFromParcel(Parcel in) {

            return new PlayerList(in);
        }

        public PlayerList[] newArray(int size) {
            return new PlayerList[size];
        }
    };


}
