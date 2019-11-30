package com.tpj.teamproject.ui.mypage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

import com.tpj.teamproject.R;

public class AlarmReceiver extends BroadcastReceiver {
    public MediaPlayer mp;

    @Override
    public void onReceive(Context context, Intent intent) {
        mp = MediaPlayer.create(context, R.raw.music);
        context.startActivity(new Intent(context, AlarmActivity.class));

        mp.start();
        Toast.makeText(context, "Recieved", Toast.LENGTH_SHORT).show();
    }
}
