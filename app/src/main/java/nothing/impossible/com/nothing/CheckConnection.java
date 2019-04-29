package nothing.impossible.com.nothing;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

/**
 * Created by User on 1/8/18.
 */
public class CheckConnection {
    public static void CheckConnection(Context context){
        if (isOnline(context)) {
            //do whatever you want to do
        }
        else
        {
            try {
                final AlertDialog alertDialog = new AlertDialog.Builder(context).create();

                alertDialog.setTitle("No Internet");
                alertDialog.setMessage("No Internet Check your internet and try again");
                alertDialog.show();
            }
            catch(Exception e)
            {
//                Log.d(SyncStateContract.Constants.TAG, "Show Dialog: "+e.getMessage());
            }
        }
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            Toast.makeText(context, "No Internet connection!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
