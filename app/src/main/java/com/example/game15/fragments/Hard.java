package com.example.game15.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.game15.R;
import com.example.game15.adapter.RAdapter;
import com.example.game15.database.MyDatabase;
import com.example.game15.model.Model;

import java.util.Collections;
import java.util.List;

public class Hard extends Fragment {
    private RAdapter adapter = new RAdapter();

    public Hard() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recycler = view.findViewById(R.id.recycler_hard);
        MyDatabase database = MyDatabase.getInstance(requireContext());
        List<Model> list = database.modelDao().getResultsByLevel(6);
        Collections.sort(list, (o1, o2) -> Long.compare(o1.result, o2.result));
        if (list.size() > 20) {
            for (int i = 20; i < list.size(); i++) {
                database.modelDao().deleteResult(list.get(i));
            }
            list = database.modelDao().getResultsByLevel(6);
            Collections.sort(list, (o1, o2) -> Long.compare(o1.result, o2.result));
        }
        if (list.size() == 0) {
            view.findViewById(R.id.no_records).setVisibility(View.VISIBLE);
        }

        recycler.setAdapter(adapter);
        adapter.submitItems(list);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hard, container, false);
    }
}