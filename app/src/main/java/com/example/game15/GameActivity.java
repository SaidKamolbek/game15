package com.example.game15;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.game15.model.Model;
import com.example.game15.database.ModelDao;
import com.example.game15.database.MyDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    private Button[] btns;
    private float[] originalX;
    private float[] originalY;
    private final List<Integer> numbers = new ArrayList<>();
    //Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 15, 13, 14, 11)
    private ImageView restart;
    private ImageView finish;
    private int empty;
    private int size;
    boolean isClickable = true;
    private int count;
    private ObjectAnimator animator;
    private GridLayout allButtons;
    private Chronometer chronometer;
    private long prevTime;
    private MediaPlayer background;
    private MediaPlayer click;
    private MediaPlayer wind;
    private ImageView musicOn;
    private ImageView volumeOn;
    private MediaPlayer applause;
    private TextView attempt;
    private MyDatabase database;
    private ModelDao dao;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Intent intent = getIntent();
        size = intent.getIntExtra("SIZE", 4);
        btns = new Button[size * size - 1];
        originalX = new float[size * size - 1];
        originalY = new float[size * size - 1];
        attempt = findViewById(R.id.attempt);
        chronometer = findViewById(R.id.chron);
        allButtons = findViewById(R.id.buttons);
        finish = findViewById(R.id.finish);
        restart = findViewById(R.id.restart);

        background = MediaPlayer.create(this, R.raw.slower);
        background.start();
        background.setLooping(true);
        applause = MediaPlayer.create(this, R.raw.applause);
        click = MediaPlayer.create(this, R.raw.click_2);
        wind = MediaPlayer.create(this, R.raw.quick);

        allButtons.removeAllViews();
        allButtons.setColumnCount(size);
        allButtons.setRowCount(size);
        database = MyDatabase.getInstance(this);
        dao = database.modelDao();
        for (int i = 1; i < size * size; i++) {
            numbers.add(i);
        }
        for (int i = 0; i < size * size - 1; i++) {
            Button button = new Button(this);
            button.setBackgroundResource(R.drawable.orqa_fon);
            button.setOnClickListener(this);

            GridLayout.LayoutParams param = new GridLayout.LayoutParams(GridLayout.spec(
                    GridLayout.UNDEFINED, GridLayout.FILL, 1f),
                    GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f));

            param.height = 0;
            param.width = 0;
            btns[i] = button;
            if (size == 3) {
                button.setTextAppearance(this, R.style.size3);
            } else if (size == 4) {
                button.setTextAppearance(this, R.style.size4);
            } else if (size == 5) {
                button.setTextAppearance(this, R.style.size5);
            } else if (size == 6) {
                button.setTextAppearance(this, R.style.size6);
            } else {
                button.setTextAppearance(this, R.style.size7);
            }
            button.setLayoutParams(param);
            allButtons.addView(button);

        }
        for (int i = 0; i < allButtons.getChildCount(); i++) {
            btns[i] = (Button) allButtons.getChildAt(i);
            btns[i].setOnClickListener(this);
        }
        toStart();

        finish.setOnClickListener(v -> {
            chronometer.stop();
            finish();
        });

        restart.setOnClickListener(v -> {
            if (t) {
                for (int i = 0; i < allButtons.getChildCount(); i++) {
                    originalX[i] = btns[i].getX();
                    originalY[i] = btns[i].getY();
                }
                restart.setVisibility(View.VISIBLE);
                t = false;
            }
            clickRestart();
        });

        musicOn = findViewById(R.id.musicImg);
        volumeOn = findViewById(R.id.musicVolume);

        setMusic();
        setVolume();
        volumeOn.setOnClickListener(v -> {
            boolean t = !MusicAndRecord.getVolumeState();
            MusicAndRecord.saveVolumeState(t);
            setVolume();
        });
        musicOn.setOnClickListener(v ->
        {
            boolean t = !MusicAndRecord.getMusicState();
            MusicAndRecord.saveMusicState(t);
            setMusic();
        });
    }

    private void clickRestart() {
        count = 0;
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
        empty = size * size - 1;
        attempt.setText("" + 0);
        musicOn.setVisibility(View.VISIBLE);
//        toStart();
        Collections.shuffle(numbers);

        while (!isSolvable(numbers)) {
            Collections.shuffle(numbers);
        }

        for (Button btn : btns) {
            btn.setEnabled(true);
            for (int i = 0; i < numbers.size(); i++) {
                if (btn.getText().equals(numbers.get(i).toString())) {
//                    startAnim(btn, "x", originalX[i]);
                    ObjectAnimator.ofFloat(btn, "x", originalX[i]).setDuration(200).start();
                    btn.setTag(i);
//                    startAnim(btn, "y", originalY[i]);
                    ObjectAnimator.ofFloat(btn, "y", originalY[i]).setDuration(200).start();
                }
            }
        }
        empty = size * size - 1;
        wind.start();
        if (MusicAndRecord.getMusicState()) background.start();
    }

    private void setVolume() {
        if (MusicAndRecord.getVolumeState()) {
            volumeOn.setImageResource(R.drawable.ic_baseline_volume_up_24);
        } else {
            volumeOn.setImageResource(R.drawable.ic_baseline_volume_off_24);
        }

    }

    private void setMusic() {
        if (MusicAndRecord.getMusicState()) {
            musicOn.setImageResource(R.drawable.ic_baseline_music_note_24);
            background.start();
        } else {
            musicOn.setImageResource(R.drawable.ic_baseline_music_off_24);
            background.pause();
        }

    }

    private boolean isSolvable(List<Integer> tiles) {
        int countInversions = 0;
        for (int i = 0; i < tiles.size(); i++) {
            for (int j = 0; j < i; j++) {
                if (tiles.get(j) > tiles.get(i)) {
                    countInversions++;
                }
            }
        }
        return countInversions % 2 == 0;
    }

    private void toStart() {
        count = 0;
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
        empty = size * size - 1;
        Collections.shuffle(numbers);
        while (!isSolvable(numbers)) {
            Collections.shuffle(numbers);
        }

        for (int i = 0; i < size * size - 1; i++) {
            btns[i].setText(String.valueOf(numbers.get(i)));
            btns[i].setTag(i);
//                btns[i].setY(originalY[i]);
//                btns[i].setX(originalX[i]);
        }

    }

    private boolean t = true;

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        if (t) {
            for (int i = 0; i < allButtons.getChildCount(); i++) {
                originalX[i] = btns[i].getX();
                originalY[i] = btns[i].getY();
            }
            restart.setVisibility(View.VISIBLE);
            t = false;
        }
        if (isClickable) {
            int pos = (int) v.getTag();
            if (empty - pos < size && empty - pos > 0 && checkPositionHorizontal(pos, empty)) {

                while (empty > pos) {
                    for (Button btn : btns) {
                        if ((int) btn.getTag() == empty - 1) {
                            swipeRight(btn);
                            break;
                        }
                    }
                }
                if (MusicAndRecord.getVolumeState()) {
                    click.start();
                }
                attempt.setText("" + ++count);

            } else if (pos - empty < size && pos - empty > 0 && checkPositionHorizontal(pos, empty)) {
                while (empty < pos) {
                    for (Button btn : btns) {
                        if ((int) btn.getTag() == empty + 1) {
                            swipeLeft(btn);
                            break;
                        }
                    }
                }
                if (MusicAndRecord.getVolumeState()) {
                    click.start();
                }
                attempt.setText("" + ++count);
            } else if (checkPositionDown(pos, empty)) {
                while (empty > pos) {
                    for (Button btn : btns) {
                        if ((int) btn.getTag() == empty - size) {
                            swipeDown(btn);
                            break;
                        }
                    }
                }
                attempt.setText("" + ++count);
                if (MusicAndRecord.getVolumeState()) {
                    click.start();
                }
            } else if (checkPositionUp(pos, empty)) {
                while (empty < pos) {
                    for (Button btn : btns) {
                        if ((int) btn.getTag() == empty + size) {
                            swipeUp(btn);
                            break;
                        }
                    }
                }
                attempt.setText("" + ++count);
                if (MusicAndRecord.getVolumeState()) {
                    click.start();
                }
            }

        }
        checkIfWin();
    }

    private boolean checkPositionHorizontal(int position, int blank) {
        return position / size == blank / size;
    }

    private boolean checkPositionDown(int pos, int blank) {
        while (pos < blank) {
            pos += size;
        }
        return pos == blank;
    }

    private boolean checkPositionUp(int pos, int blank) {
        while (pos > blank) {
            pos -= size;
        }
        return pos == blank;
    }

    private void swipeDown(View view) {
        int pos = (int) view.getTag();
        view.setTag(empty);
        empty = pos;
        startAnim(view, "y", view.getY() + view.getHeight());
    }

    private void swipeUp(View view) {
        int pos = (int) view.getTag();
        view.setTag(empty);
        empty = pos;
        startAnim(view, "y", view.getY() - view.getHeight());
    }

    private void swipeLeft(View view) {
        int pos = (int) view.getTag();
        view.setTag(empty);
        empty = pos;
        startAnim(view, "x", view.getX() - view.getWidth());
    }

    private void swipeRight(View view) {
        int pos = (int) view.getTag();
        view.setTag(empty);
        empty = pos;
        startAnim(view, "x", view.getX() + view.getWidth());
    }

    private double result;

    private void checkIfWin() {
        for (Button btn : btns) {
            if (Integer.parseInt(btn.getTag().toString()) + 1 != Integer.parseInt(btn.getText().toString()))
                return;
        }
        Toast.makeText(this, "you won", Toast.LENGTH_SHORT).show();
        for (Button btn : btns) {
            btn.setEnabled(false);
        }

        chronometer.stop();
        result = (SystemClock.elapsedRealtime() - chronometer.getBase()) * 0.001 + count;
        showWinAlert(count, chronometer.getText() + "");
        background.pause();
        MusicAndRecord.saveResults(count, (int) ((SystemClock.elapsedRealtime() - chronometer.getBase()) * 0.001 + count), chronometer.getText() + "");  //SystemClock.elapsedRealtime() - chronometer.getBase()  count, chronometer.getText() + ""

        if (MusicAndRecord.getVolumeState() || MusicAndRecord.getMusicState()) applause.start();
    }

    void showWinAlert(int count, String str) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.custom_dialog, null);
        EditText name = view.findViewById(R.id.saveName);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(false)
                .create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button yoxshi_button = view.findViewById(R.id.yoxshi_button);
        Button play_again = view.findViewById(R.id.play_again);
        TextView attempts = view.findViewById(R.id.score_moves);
        TextView time = view.findViewById(R.id.score_time);
        attempts.setText("" + count);
        time.setText("" + str);
        yoxshi_button.setOnClickListener(v -> {
            if (name.getText().toString().trim().length() < 5) {
                Toast.makeText(this, "min length of name must be 5", Toast.LENGTH_SHORT).show();
            } else {
                dao.insertResult(new Model(0,
                        name.getText().toString(),
                        chronometer.getText() + "",
                        (int) result,
                        count,
                        size));
                dialog.cancel();
            }
        });
        play_again.setOnClickListener(v -> {
            if (MusicAndRecord.getMusicState()) background.start();
            clickRestart();
            dialog.dismiss();
        });
        dialog.show();
    }

    private void startAnim(View v, String prop, float value) {
        animator = ObjectAnimator.ofFloat(v, prop, value);
        animator.setDuration(100);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isClickable = false;
                if (MusicAndRecord.getVolumeState()) click.start();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isClickable = true;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }


    @Override
    protected void onPause() {
        background.pause();
        prevTime = chronometer.getBase() - SystemClock.elapsedRealtime();
        super.onPause();
    }

    @Override
    protected void onRestart() {
        restart.setVisibility(View.VISIBLE);
        t = false;
        if (MusicAndRecord.getMusicState()) background.start();
        super.onRestart();
        chronometer.setBase(SystemClock.elapsedRealtime() + prevTime);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

//    @Override
//    protected void onResume() {
//        background.start();
//        super.onResume();
//    }


}
