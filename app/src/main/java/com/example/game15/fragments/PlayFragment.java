package com.example.game15.fragments;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.game15.GameActivity;
import com.example.game15.MainActivity;
import com.example.game15.MusicAndRecord;
import com.example.game15.R;

public class PlayFragment extends Fragment {
    private ImageButton musicOnImage;
    private MediaPlayer player;

    public PlayFragment() {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        player = MediaPlayer.create(requireContext(), R.raw.faster);
        player.start();
        player.setLooping(true);
        musicOnImage = view.findViewById(R.id.imageView2);


        ImageButton resultButton = view.findViewById(R.id.result);


        view.findViewById(R.id.button_easy).setOnClickListener(v -> {
            showIntent(3);
        });
        view.findViewById(R.id.button_normal).setOnClickListener(v -> {
            showIntent(4);
        });
        view.findViewById(R.id.button_medium).setOnClickListener(v -> {
            showIntent(5);
        });
        view.findViewById(R.id.button_hard).setOnClickListener(v -> {
            showIntent(6);
        });
        view.findViewById(R.id.button_expert).setOnClickListener(v -> {
            showIntent(7);
        });

        setMusicImage();
        musicOnImage.setOnClickListener(v -> {
            boolean t = (!MusicAndRecord.getMusicState());
            MusicAndRecord.saveMusicState(t);
            setMusicImage();
        });


        resultButton.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_playFragment_to_mainFragment);
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_play, container, false);
    }

    public void showIntent(int size) {
        Intent intent = new Intent(requireContext(), GameActivity.class);
        intent.putExtra("SIZE", size);
        player.pause();
        startActivity(intent);
    }

    void setMusicImage() {
        if (MusicAndRecord.getMusicState()) {
            musicOnImage.setImageResource(R.drawable.ic_baseline_music_note_24);
            player.start();
        } else {
            musicOnImage.setImageResource(R.drawable.ic_baseline_music_off_24);
            player.pause();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
//        player.pause();
    }

    @Override
    public void onStop() {
        super.onStop();
        player.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        setMusicImage();
    }
}