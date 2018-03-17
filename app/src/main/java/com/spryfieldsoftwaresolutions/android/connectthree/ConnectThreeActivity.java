package com.spryfieldsoftwaresolutions.android.connectthree;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ConnectThreeActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        return GameFragment.newInstance();
    }

}
