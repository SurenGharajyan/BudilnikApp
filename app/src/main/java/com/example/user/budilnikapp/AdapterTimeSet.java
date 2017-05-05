package com.example.user.budilnikapp;

import android.content.ClipData;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by USER on 03.05.2017.
 */

public class AdapterTimeSet extends RecyclerView.Adapter<AdapterTimeSet.viewhold> {
    List<Resource_Time_Set_Text> time_set_texts;
    Context context;

    public AdapterTimeSet(List<Resource_Time_Set_Text> time_set_texts, Context context) {
        this.time_set_texts = time_set_texts;
        this.context = context;
    }

    @Override
    public AdapterTimeSet.viewhold onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_obyekts,parent,false);
        viewhold viewhold=new viewhold(view);
        return viewhold;
    }
    @Override
    public void onBindViewHolder(final AdapterTimeSet.viewhold holder, int position) {
        holder.textView.setText(time_set_texts.get(position).getTextview_Text());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!holder.checker.isChecked()) {
                    holder.checker.setChecked(true);

                }else {
                    holder.checker.setChecked(false);
                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return time_set_texts.size();
    }
    class viewhold extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;
        CheckBox checker;
        ItemClickererListenter itemClickererListener;
        public viewhold(View itemView) {
            super(itemView);
            textView=(TextView) itemView.findViewById(R.id.text_id);
            checker=(CheckBox) itemView.findViewById(R.id.checkbox);
            itemView.setOnClickListener(this);
        }
        void setOnItemClickListener(ItemClickererListenter itemClickListener) {
            this.itemClickererListener=itemClickListener;
        }
        @Override
        public void onClick(View v) {
            itemClickererListener.OnClickerer(v,getPosition());
        }
    }
}
