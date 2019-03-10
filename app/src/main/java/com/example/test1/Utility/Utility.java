package com.example.test1.Utility;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import com.example.test1.R;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class Utility {


public static boolean CheckInterConnection(Context context)
{
    boolean isConnected = false;
    ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(CONNECTIVITY_SERVICE);
    NetworkInfo[]  networkInfos = connectivityManager.getAllNetworkInfo();
    for(NetworkInfo networkInfo : networkInfos) {
        if(networkInfo.getState()== NetworkInfo.State.CONNECTED) {
            isConnected = true;
            break;
        }
    }

       return isConnected ;
}



public static void NetworkDialod(Context context)
{


    AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
    builder1.setMessage("Please Check Your Netwok ");
    builder1.setCancelable(true);

    builder1.setPositiveButton(
            "OK",
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });



    AlertDialog alert11 = builder1.create();
    alert11.show();

}
    public static void ChangeStatusBarColor(Context context)
    {

        Window window =((Activity) context).getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(context.getResources().getColor(R.color.button_color));
        }


    }

}
