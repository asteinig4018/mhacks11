package com.mhacks11app.mhacks11app;

import com.google.android.gms.maps.GoogleMap;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.maps.model.Marker;

import org.w3c.dom.Text;

public class CustonInfoWindowGoogleMap implements GoogleMap.InfoWindowAdapter {

    private Context context;

    public CustonInfoWindowGoogleMap(Context ctx){
        context=ctx;
    }

    @Override
    public View getInfoWindow(Marker marker){
        return null;
    }
    @Override
    public View getInfoContents(Marker marker){
        View view= ((Activity)context).getLayoutInflater()
                .inflate(R.layout.activity_customwindow,null);

        TextView name_tv = view.findViewById(R.id.name);
        TextView details1_tv=view.findViewById(R.id.details1);
        ImageView img = view.findViewById(R.id.pic);

        TextView details2_tv=view.findViewById(R.id.details2);
        TextView details3_tv = view.findViewById(R.id.details3);

        name_tv.setText(marker.getTitle());
        details1_tv.setText(marker.getSnippet());

        InfoWindowData infoWindowData = (InfoWindowData) marker.getTag();

        int imageId = context.getResources().getIdentifier(infoWindowData.getImage(),"drawable", context.getPackageName());
        img.setImageResource(imageId);

        details2_tv.setText(infoWindowData.getdetails2());
        details3_tv.setText(infoWindowData.getdetails3());

        return view;

    }
}
