package mrc.sad_project;


/**
 * Created by HP on 30-03-2018.
 */


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;


    private ArrayList<Donor> donors;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        CircleImageView call, gmap;
        TextView name, address, bgp;

        public MyViewHolder(View itemView) {
            super(itemView);
            bgp = (TextView) itemView.findViewById(R.id.receive_single_bloodgp);
            call = (CircleImageView) itemView.findViewById(R.id.receive_single_call);
            gmap = (CircleImageView) itemView.findViewById(R.id.receive_single_gmap);
            name = (TextView) itemView.findViewById(R.id.receive_single_name);
            address = (TextView) itemView.findViewById(R.id.receive_single_address);
        }
    }

    public CustomAdapter(Context context, ArrayList<Donor> donors) {
        this.donors = donors;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.receive_single_item, parent, false);

        //     view.setOnClickListener(MainActivity.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, final int listPosition) {

        TextView bloodgpView = myViewHolder.bgp;
        TextView nameView = myViewHolder.name;
        TextView addressView = myViewHolder.address;
        CircleImageView callImageView = myViewHolder.call;
        CircleImageView gmapImageView = myViewHolder.gmap;


        String nameofdonor = donors.get(listPosition).getName();
        final String phoneofdonor = donors.get(listPosition).getPhone();
        String blood = donors.get(listPosition).getBlood_group();


        bloodgpView.setText(blood);
        nameView.setText(nameofdonor);
        addressView.setText(phoneofdonor);
        callImageView.setImageResource(R.drawable.call);
        gmapImageView.setImageResource(R.drawable.google_maps);

        callImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneofdonor));
                context.startActivity(intent);
            }
        });
        gmapImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lat=donors.get(listPosition).getLatitude();
                String lungi=donors.get(listPosition).getLongitude();
                Intent mapsIntent = new Intent(context,MapsActivity.class);
                mapsIntent.putExtra("lat",lat);
                mapsIntent.putExtra("lungi",lungi);
                context.startActivity(mapsIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return donors.size();
    }
}
