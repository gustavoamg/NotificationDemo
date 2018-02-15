package com.imusicacorp.notificationdemo.ui;

import android.os.Parcel;
import android.widget.RemoteViews;

/**
 * Created by gustavo.guimaraes on 31/01/2018.
 */

public class CustomLayoutNotification extends RemoteViews {


    public CustomLayoutNotification(String packageName, int layoutId) {
        super(packageName, layoutId);
    }

    public CustomLayoutNotification(RemoteViews landscape, RemoteViews portrait) {
        super(landscape, portrait);
    }

    public CustomLayoutNotification(Parcel parcel) {
        super(parcel);
    }


}
