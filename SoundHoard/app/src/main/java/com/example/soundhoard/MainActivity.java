package com.example.soundhoard;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private AppDatabase database;
    private ImageView myImage;
    private Button soundboardsButton;
    private Button settingsButton;
    private ConstraintLayout rootLayout;
    private ArrayList<Sound> favoriteSounds;
    private FloatingActionButton favoriteFab1;
    private FloatingActionButton favoriteFab2;
    private FloatingActionButton favoriteFab3;
    private FloatingActionButton favoriteFab4;
    private FloatingActionButton favoriteFab5;
    private MediaPlayer mediaPlayer;
    private FloatingActionButton buttonPlaying;
    private boolean isPlayingSound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        favoriteFab1 = findViewById(R.id.favoriteButton1);
        favoriteFab1.hide();
        favoriteFab2 = findViewById(R.id.favoriteButton2);
        favoriteFab2.hide();
        favoriteFab3 = findViewById(R.id.favoriteButton3);
        favoriteFab3.hide();
        favoriteFab4 = findViewById(R.id.favoriteButton4);
        favoriteFab4.hide();
        favoriteFab5 = findViewById(R.id.favoriteButton5);
        favoriteFab5.hide();

        database = AppDatabase.getInstance(this);
        favoriteSounds = (ArrayList<Sound>)database.soundDao().getAllFavorites();
        if(favoriteSounds != null) {
            switch(favoriteSounds.size()) {
                case 1:
                    favoriteFab1.hide();
                    favoriteFab3.hide();
                    favoriteFab4.hide();
                    favoriteFab5.hide();

                    favoriteFab2.show();

                    favoriteFab2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            setFabAction(favoriteFab2, android.R.drawable.btn_star, 0);
                        }
                    });
                    break;
                case 2:
                    favoriteFab1.hide();
                    favoriteFab2.hide();
                    favoriteFab3.hide();

                    favoriteFab4.show();
                    favoriteFab5.show();

                    favoriteFab4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            setFabAction(favoriteFab4, android.R.drawable.star_big_on, 0);
                        }
                    });
                    favoriteFab5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            setFabAction(favoriteFab5, android.R.drawable.star_big_off, 1);
                        }
                    });
                    break;
                case 3:
                    favoriteFab1.hide();
                    favoriteFab3.hide();

                    favoriteFab2.show();
                    favoriteFab4.show();
                    favoriteFab5.show();

                    favoriteFab2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            setFabAction(favoriteFab2, android.R.drawable.btn_star, 0);
                        }
                    });
                    favoriteFab4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            setFabAction(favoriteFab4, android.R.drawable.star_big_on, 1);
                        }
                    });
                    favoriteFab5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            setFabAction(favoriteFab5, android.R.drawable.star_big_off, 2);
                        }
                    });
                    break;
                case 4:
                    favoriteFab2.hide();

                    favoriteFab1.show();
                    favoriteFab3.show();
                    favoriteFab4.show();
                    favoriteFab5.show();

                    favoriteFab1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            setFabAction(favoriteFab1, android.R.drawable.btn_star_big_on, 0);
                        }
                    });
                    favoriteFab3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            setFabAction(favoriteFab3, android.R.drawable.star_on, 1);
                        }
                    });
                    favoriteFab4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            setFabAction(favoriteFab4, android.R.drawable.star_big_on, 2);
                        }
                    });
                    favoriteFab5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            setFabAction(favoriteFab5, android.R.drawable.star_big_off, 3);
                        }
                    });
                    break;
                case 5:
                    favoriteFab1.show();
                    favoriteFab2.show();
                    favoriteFab3.show();
                    favoriteFab4.show();
                    favoriteFab5.show();

                    favoriteFab1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            setFabAction(favoriteFab1, android.R.drawable.btn_star_big_on, 0);
                        }
                    });
                    favoriteFab2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            setFabAction(favoriteFab2, android.R.drawable.btn_star, 1);
                        }
                    });
                    favoriteFab3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            setFabAction(favoriteFab3, android.R.drawable.star_on, 2);
                        }
                    });
                    favoriteFab4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            setFabAction(favoriteFab4, android.R.drawable.star_big_on, 3);
                        }
                    });
                    favoriteFab5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            setFabAction(favoriteFab5, android.R.drawable.star_big_off, 4);
                        }
                    });
                    break;
            }
        }

        myImage = findViewById(R.id.imageView);
        myImage.setImageResource(R.drawable.soundhoard);
        chooseRandomBackground();

        soundboardsButton = findViewById(R.id.soundboardsButton);
        soundboardsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity(MainActivity.this, SoundboardsActivity.class);
            }
        });

        settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity(MainActivity.this, SettingsActivity.class);
            }
        });
    }

    public void openActivity(Context context, Class<?> thisClass) {
        Intent intent = new Intent(context, thisClass);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    public void chooseRandomBackground() {
        rootLayout = findViewById(R.id.rootLayout);

        // int randomNumber = (int)(Math.floor(Math.random() * largestNumberForBackground) + leastNumberForBackground);
        // upper bound must be updated when additional app backgrounds are added
        int randomNumber = (int)(Math.floor(Math.random() * 5) + 1);
        switch(randomNumber) {
            case 1:
                rootLayout.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background1));
                return;
            case 2:
                rootLayout.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background2));
                return;
            case 3:
                rootLayout.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background3));
                return;
            case 4:
                rootLayout.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background4));
                return;
            case 5:
                rootLayout.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background5));
                return;
        }
    }

    public void playSound(int soundIndex) {
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(getApplicationContext(), Uri.parse(favoriteSounds.get(soundIndex).soundUri));
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch(IOException e) {
            Toast.makeText(getApplicationContext(), "Could not play sound...", Toast.LENGTH_LONG).show();
        }
    }

    public void resetButtonIcon(FloatingActionButton button) {
        if(button == favoriteFab1) {
            favoriteFab1.setImageResource(android.R.drawable.btn_star_big_on);
        } else if(button == favoriteFab2) {
            favoriteFab2.setImageResource(android.R.drawable.btn_star);
        } else if(button == favoriteFab3) {
            favoriteFab3.setImageResource(android.R.drawable.star_on);
        } else if(button == favoriteFab4) {
            favoriteFab4.setImageResource(android.R.drawable.star_big_on);
        } else if(button == favoriteFab5) {
            favoriteFab5.setImageResource(android.R.drawable.star_big_off);
        }
    }

    public void setFabAction(FloatingActionButton button, int resId, int soundIndex) {
        if(isPlayingSound) {
            if(mediaPlayer != null) {
                mediaPlayer.release();
            }

            if(buttonPlaying == button) {
                button.setImageResource(resId);
                isPlayingSound = false;
                buttonPlaying = null;
            } else {
                resetButtonIcon(buttonPlaying);

                playSound(soundIndex);
                button.setImageResource(android.R.drawable.ic_media_pause);
                buttonPlaying = button;
                isPlayingSound = true;
            }
        } else {
            playSound(soundIndex);
            button.setImageResource(android.R.drawable.ic_media_pause);
            buttonPlaying = button;
            isPlayingSound = true;
        }
    }
}