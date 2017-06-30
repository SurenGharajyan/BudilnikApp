package com.example.user.budilnikapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by USER on 03.05.2017.
 */

public class AdapterTimeSet extends RecyclerView.Adapter<AdapterTimeSet.viewhold> {
    List<ResourceTimeSetText> time_set_texts;
    Context context;
    String[] WeekDay={"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
    String[] WeekDayAbbreviation={"Mo","Tu","We","Th","Fr","Sa","Su"};
    ArrayList<Integer> whichers=new ArrayList<>();
    final ArrayList selectedDays=new ArrayList();


    //Constructor
    public AdapterTimeSet(List<ResourceTimeSetText> time_set_texts, Context context) {
        this.time_set_texts = time_set_texts;
        this.context = context;
    }

    //ViewHolder Creating
    @Override
    public AdapterTimeSet.viewhold onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_obyekts,parent,false);
        viewhold viewhold=new viewhold(view);
        return viewhold;
    }

AlarmRendering alarms=new AlarmRendering();
    int Hour=0;
    int Minute=0;
    @Override
    public void onBindViewHolder(final AdapterTimeSet.viewhold holder, int position) {
        holder.textView.setText(time_set_texts.get(position).getTextview_Text());

        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alert = new AlertDialog.Builder(context).create();
                alert.setTitle("Delete Alarm");
                alert.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        holder.removeAt(holder.getAdapterPosition());
                      //  alarms.CancelAlarmI(holder.getAdapterPosition());
                    }
                });
                alert.show();
            }
        });



        holder.mTextViewDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog alertDays = new AlertDialog.Builder(context).setMultiChoiceItems(WeekDay, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            whichers.add(which);
                        }
                    }
                })
                        .setTitle("Set Days")
                        .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String s="";
                        int[] whichArraySort=new int[whichers.size()];
                        //get items of ArrayList whichers
                        for (int i = 0; i <whichers.size() ; i++) {
                            whichArraySort[i] = whichers.get(i);
                            //put on massiv whichArraySort
                        }
                        //Sorting new massiv whichArraySort
                        Arrays.sort(whichArraySort);
                        for (int j = 0; j <whichArraySort.length ; j++) {
                            //Add Sorted massiv in ArrayList selectedDays
                            selectedDays.add(WeekDayAbbreviation[whichArraySort[j]]);
                        }

                        for (int i=0;i<selectedDays.size();i++) {
                            //get items and put on String s
                            s+=selectedDays.get(i).toString()+ " ";
                        }
                        //Clear both ArrayLists
                        selectedDays.clear();
                        whichers.clear();

                        //setText
                        holder.mTextViewDays.setText(s);

                    }
                })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
                alertDays.show();
            }
        });

        holder.mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    Toast.makeText(context, "Alarm is activeted", Toast.LENGTH_SHORT).show();
                    String HourANDMinute = holder.textView.getText().toString();
                    if (HourANDMinute.indexOf('0') == 0) {
                        Hour = Integer.parseInt(String.valueOf(HourANDMinute.charAt(1)));
                    } else {
                        Hour = 10 * Integer.parseInt(String.valueOf(HourANDMinute.charAt(0))) + Integer.parseInt(String.valueOf(HourANDMinute.charAt(1)));
                    }
                    if (HourANDMinute.charAt(3) == '0') {
                        Minute = Integer.parseInt(String.valueOf(HourANDMinute.charAt(4)));
                    } else {
                        Minute = 10 * Integer.parseInt(String.valueOf(HourANDMinute.charAt(3))) + Integer.parseInt(String.valueOf(HourANDMinute.charAt(4)));
                    }
                    alarms.SetOneAlarm(context, Hour, Minute);
                }
            }
        });


    }


    @Override
    public int getItemCount() {
        return time_set_texts.size();
    }
    class viewhold extends RecyclerView.ViewHolder{
        public TextView textView;
        public Switch mSwitch;
        public ImageView mImageView;
        public TextView mTextViewDays;
        private ItemClickererListenter itemClickererListener;
        public viewhold(View itemView) {
            super(itemView);
            textView=(TextView) itemView.findViewById(R.id.text_id);
            mSwitch=(Switch) itemView.findViewById(R.id.switchbar);
            mImageView=(ImageView) itemView.findViewById(R.id.trash_icon);
            mTextViewDays=(TextView) itemView.findViewById(R.id.weekdays);
        }
        public void setOnItemClickListener(ItemClickererListenter itemClickListener) {
            this.itemClickererListener=itemClickListener;
        }

        public void removeAt(int position) {
            time_set_texts.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, time_set_texts.size());
        }


    }

}
