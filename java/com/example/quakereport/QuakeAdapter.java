package com.example.quakereport;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SimpleTimeZone;

import static java.security.AccessController.getContext;


public class QuakeAdapter extends RecyclerView.Adapter<QuakeAdapter.ViewHolder> {
    private ArrayList<Quake> quakes;
    private Context context;

    public QuakeAdapter(ArrayList<Quake> quakes) {
        this.quakes = quakes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView magnitudeTextView;
        private TextView locationOffset;
        private TextView primaryLocation;
        private TextView dateTextView;
        private TextView timeTextView;
        private Button buttonView;
        private LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            magnitudeTextView=(TextView) itemView.findViewById(R.id.magnitude);
            locationOffset=(TextView) itemView.findViewById(R.id.location_offset);
            primaryLocation=(TextView) itemView.findViewById(R.id.primary_location);
            timeTextView=(TextView) itemView.findViewById(R.id.time);
            dateTextView=(TextView) itemView.findViewById(R.id.date);
            linearLayout=(LinearLayout) itemView.findViewById(R.id.linear_layout);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Quake currentQuake= quakes.get(position);

        double magnitude=currentQuake.getmMagnitude();
        DecimalFormat formatter= new DecimalFormat("0.0");
        String formattedMagnitude= formatter.format(magnitude);
        holder.magnitudeTextView.setText(formattedMagnitude);


        //Taking the whole location from Quake template
        String string= currentQuake.getmPlace();
        //Search if there is a word "of" in the string. if there is split string into
        //two substrings on "of".
        if(string.contains("of")){
            int index= string.indexOf("of");
            String str1=string.substring(0,index+2);
            String str2= string.substring(index+3,string.length());
            holder.locationOffset.setText(str1);
            holder.primaryLocation.setText(str2);
        }
        //else put near the in the start of the sentence and the rest of the string
        else{
            String str1= "Near the ";
            holder.locationOffset.setText(str1);
            holder.primaryLocation.setText(string);
        }


        // Create a new Date object from the time in milliseconds of the earthquake
        Date dateObject = new Date(currentQuake.getmDate());
        // Find the TextView with view ID time
        // Format the time string (i.e. "4:30PM")
        String formattedTime = formatTime(dateObject);
        // Display the time of the current earthquake in that TextView
        holder.timeTextView.setText(formattedTime);


        // Find the TextView with view ID date
        // Format the date string (i.e. "Mar 3, 1984")
        String formattedDate = formatDate(dateObject);
        // Display the date of the current earthquake in that TextView
        holder.dateTextView.setText(formattedDate);

        //Setting a clicklistener to pass on the url
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                //converting the string into a uri object
                Uri myUri= Uri.parse(currentQuake.getmUrl());
                //passing the intent of myUri with ACTION_VIEW
                Intent intent = new Intent(Intent.ACTION_VIEW, myUri);
                context.startActivity(intent);
            }
        }) ;



        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle= (GradientDrawable) holder.magnitudeTextView.getBackground();
        // Get the appropriate background color based on the current earthquake magnitude
        //setting the color on the magnitude circle
        magnitudeCircle.setColor(getMagnitudeColor(currentQuake.getmMagnitude()));
    }



    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                return Color.parseColor("#4A7BA7");
            case 2:
                return Color.parseColor("#04B4B3");
            case 3:
               return  Color.parseColor("#10CAC9");
            case 4:
               return  Color.parseColor("#F5A623");
            case 5:
                return Color.parseColor("#FF7D50");
            case 6:
                return Color.parseColor("#FC6644");
            case 7:
                return Color.parseColor("#E75F40");
            case 8:
                return Color.parseColor("#E13A20");
            case 9:
                return Color.parseColor("#D93218");
            default:
                return Color.parseColor("#C03823");
        }
    }


    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat= new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat= new SimpleDateFormat("LLL DD, YYYY");
        return dateFormat.format(dateObject);
    }

    @Override
    public int getItemCount() {
        return quakes.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}

