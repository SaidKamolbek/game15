package com.example.game15.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.game15.R;
import com.example.game15.model.Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class RAdapter extends RecyclerView.Adapter<RAdapter.MyHolder> {

    private final List<Model> list = new ArrayList<>();

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView name = itemView.findViewById(R.id.result_name);
        TextView place = itemView.findViewById(R.id.result_place);
        TextView time = itemView.findViewById(R.id.time_value);
        TextView moves = itemView.findViewById(R.id.moves_value);
        FrameLayout background = itemView.findViewById(R.id.result_background);

        public MyHolder(@NonNull View itemView) {
            super(itemView);
        }

        void binding() {
            Model item = list.get(getAdapterPosition());
            name.setText(item.name.trim());
            int pos = getAdapterPosition();
            place.setText(String.valueOf(pos + 1));
            time.setText(item.time);
            moves.setText(String.valueOf(item.count));
            if (pos == 0)
                background.setBackgroundResource(R.color.red);
            else if (pos == 1)
                background.setBackgroundResource(R.color.orange);
            else if (pos == 2)
                background.setBackgroundResource(R.color.yellow);
            else
                background.setBackgroundResource(R.color.grey);
        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.result_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.binding();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void submitItems(List<Model> items) {
        list.clear();
        list.addAll(items);
        notifyDataSetChanged();
    }


}
