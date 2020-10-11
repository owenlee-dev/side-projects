package com.example.soundhoard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;

public class SoundboardActivity extends AppCompatActivity implements SoundboardDialog.SoundboardDialogListener, SoundDialog.SoundDialogListener {
    private static final int REQUEST_PERMISSION_CODE = 3000;

    private AppDatabase database;
    private Soundboard soundboard;
    private FloatingActionButton fab;
    private FloatingActionButton pauseButton;
    private ArrayList<Sound> sounds;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private SoundboardActivity.MyAdapter mAdapter;
    private MediaPlayer mediaPlayer;
    private Sound soundWorker;
    private int soundWorkerPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soundboard);

        Intent intent = getIntent();
        soundboard = (Soundboard)intent.getSerializableExtra("soundboard");

        getSupportActionBar().setTitle(soundboard.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pauseButton = findViewById(R.id.pauseFab);
        pauseButton.setEnabled(false);
        pauseButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimaryDark)));
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();
                pauseButton.setEnabled(false);
                pauseButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimaryDark)));
            }
        });

        fab = findViewById(R.id.soundFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSoundDialog(SoundDialog.CREATE_DIALOG);
            }
        });

        recyclerView = findViewById(R.id.soundRecyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        database = AppDatabase.getInstance(this);
        sounds = getSounds();

        mAdapter = new SoundboardActivity.MyAdapter(sounds);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_soundboard_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home: // setDisplayHomeAsUpEnabled action
                if(mediaPlayer != null) {
                    mediaPlayer.pause();
                    pauseButton.setEnabled(false);
                    pauseButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimaryDark)));
                }
                pauseButton.setEnabled(false);
                pauseButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimaryDark)));
                onBackPressed();
                return true;
            case R.id.editSoundboardName:
                openSoundboardDialog(SoundboardDialog.UPDATE_DIALOG);
                return true;
            case R.id.deleteSoundboard:
                if(mediaPlayer != null) {
                    mediaPlayer.pause();
                    pauseButton.setEnabled(false);
                    pauseButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimaryDark)));
                }
                openSoundboardDialog(SoundboardDialog.DELETE_DIALOG);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void applyText(String name) {
        int id = soundboard.getId();
        database = database.getInstance(this);

        database.soundboardDao().updateNameById(name, id);
        soundboard = database.soundboardDao().loadById(id);
        getSupportActionBar().setTitle(soundboard.getName());
        Toast.makeText(SoundboardActivity.this, R.string.soundboard_activity_name_edited, Toast.LENGTH_LONG).show();
    }

    @Override
    public void applyDelete() {
        database.soundboardDao().deleteById(soundboard.getId());
        database.soundboardHasSoundDao().deleteBySoundboardId(soundboard.getId());
        for(Sound item : sounds) {
            database.soundDao().delete(item);
        }

        Toast.makeText(SoundboardActivity.this, R.string.soundboard_activity_soundboard_deleted, Toast.LENGTH_LONG).show();
        onBackPressed();
    }
    
    @Override
    public void applySoundText(String name, Uri uri) {
        Sound newSound;
        if(name.isEmpty()) {
            newSound = new Sound("", uri.toString());
        } else {
            newSound = new Sound(name, uri.toString());
        }
        long soundId = database.soundDao().insert(newSound);
        database.soundboardHasSoundDao().insertAll(new SoundboardHasSound(soundboard.soundboard_id, (int)soundId));

        mAdapter.mDataset = getSounds();
        mAdapter.notifyDataSetChanged();
        Toast.makeText(SoundboardActivity.this, R.string.soundboard_activity_sound_created_toast, Toast.LENGTH_LONG).show();
    }

    @Override
    public void applySoundEdit(String name, Uri uri) {
        database.soundDao().updateNameById(name, soundWorker.getId());

        if(uri != null) {
            database.soundDao().updateUriById(uri.toString(), soundWorker.getId());
        }

        mAdapter.mDataset = getSounds();
        mAdapter.notifyDataSetChanged();
        Toast.makeText(SoundboardActivity.this, soundWorker.getName() + " has been edited!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void applySoundDelete() {
        mAdapter.mDataset.remove(soundWorkerPosition);
        mAdapter.notifyDataSetChanged();

        database.soundDao().delete(soundWorker);
        SoundboardHasSound temp = database.soundboardHasSoundDao().loadBySoundId(soundWorker.getId());
        database.soundboardHasSoundDao().delete(temp);

        Toast.makeText(SoundboardActivity.this, soundWorker.getName() + " has been deleted!", Toast.LENGTH_LONG).show();
    }

    public void openSoundDialog(String action) {
        SoundDialog soundDialog = new SoundDialog(action);
        soundDialog.show(getSupportFragmentManager(), "dialog");
    }

    public void openSoundboardDialog(String action) {
        SoundboardDialog soundboardDialog = new SoundboardDialog(action);
        soundboardDialog.show(getSupportFragmentManager(), "dialog");
    }

    public ArrayList<Sound> getSounds() {
        database = AppDatabase.getInstance(this);
        ArrayList<SoundboardHasSound> temp = (ArrayList<SoundboardHasSound>)database.soundboardHasSoundDao().loadAllBySoundboardId(soundboard.soundboard_id);

        int[] soundIds = new int[temp.size()];
        for(int i = 0; i < temp.size(); i++) {
            soundIds[i] = temp.get(i).soundId;
        }

        return (ArrayList<Sound>)database.soundDao().loadAllByIds(soundIds);
    }

    public void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[] {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO
        }, REQUEST_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case REQUEST_PERMISSION_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Granted!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Permission Denied...", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    public boolean checkPermissions() {
        int readExternalStorageResult = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int writeExternalStorageResult = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int recordAudioResult = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        return readExternalStorageResult == PackageManager.PERMISSION_GRANTED && writeExternalStorageResult == PackageManager.PERMISSION_GRANTED && recordAudioResult == PackageManager.PERMISSION_GRANTED;
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private ArrayList<Sound> mDataset;

        public MyAdapter(ArrayList<Sound> myDataset) {
            mDataset = myDataset;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView mTextView;
            public CardView mCardView;
            public ImageButton mSoundOptionsButton;

            public ViewHolder(View v) {
                super(v);
                mTextView = v.findViewById(R.id.soundTextViewName);
                mCardView = v.findViewById(R.id.soundCardView);
                mSoundOptionsButton = v.findViewById(R.id.soundOptionsButton);
            }
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = (View)LayoutInflater.from(parent.getContext()).inflate(R.layout.sound_item, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(SoundboardActivity.MyAdapter.ViewHolder holder, int position) {
            final int soundPosition = position;
            final SoundboardActivity.MyAdapter.ViewHolder soundHolder = holder;
            final Sound data = mDataset.get(position);
            holder.mTextView.setText(data.getName());
            holder.mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!checkPermissions()) {
                        requestPermissions();
                    } else {
                        try {
                            if(mediaPlayer != null) {
                                mediaPlayer.pause();
                                pauseButton.setEnabled(false);
                                pauseButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimaryDark)));
                            }
                            mediaPlayer = new MediaPlayer();
                            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mediaPlayer) {
                                    pauseButton.setEnabled(false);
                                    pauseButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimaryDark)));
                                }
                            });
                            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                            mediaPlayer.setDataSource(SoundboardActivity.this, Uri.parse(data.soundUri));
                            mediaPlayer.prepare();
                            mediaPlayer.start();
                            pauseButton.setEnabled(true);
                            pauseButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.googleBlue)));
                        } catch(IOException e) {
                            Toast.makeText(SoundboardActivity.this, "Could not play sound...", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
            holder.mSoundOptionsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popup = new PopupMenu(SoundboardActivity.this, soundHolder.mSoundOptionsButton);
                    popup.getMenuInflater().inflate(R.menu.activity_soundboard_sound_options, popup.getMenu());

                    if(data.getFavorite()) {
                        popup.getMenu().getItem(0).setTitle(R.string.soundboard_activity_unfavorite_sound_option);
                    } else {
                        popup.getMenu().getItem(0).setTitle(R.string.soundboard_activity_favorite_sound_option);
                    }

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
                            soundWorker = data;
                            soundWorkerPosition = soundPosition;

                            switch(item.getItemId()) {
                                case R.id.favoriteSoundOption:
                                    SharedPreferences sharedPref = SoundboardActivity.this.getPreferences(Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPref.edit();

                                    int numOfFavoriteSounds = sharedPref.getInt(getString(R.string.favoriteCounter), -1);

                                    if(item.getTitle().equals("Unfavorite sound")) {
                                        editor.putInt(getString(R.string.favoriteCounter), numOfFavoriteSounds-1);
                                        editor.commit();

                                        database.soundDao().updateFavoriteStatusById(0, data.getId());

                                        Toast.makeText(SoundboardActivity.this, data.getName() + " has been unfavorited!", Toast.LENGTH_LONG).show();
                                    } else if(item.getTitle().equals("Favorite sound")) {
                                        if(numOfFavoriteSounds == -1) {
                                            editor.putInt(getString(R.string.favoriteCounter), 1);
                                            editor.commit();

                                            database.soundDao().updateFavoriteStatusById(1, data.getId());

                                            Toast.makeText(SoundboardActivity.this, data.getName() + " has been favorited!", Toast.LENGTH_LONG).show();
                                        } else if(numOfFavoriteSounds < 5) {
                                            editor.putInt(getString(R.string.favoriteCounter), numOfFavoriteSounds+1);
                                            editor.commit();

                                            database.soundDao().updateFavoriteStatusById(1, data.getId());

                                            Toast.makeText(SoundboardActivity.this, data.getName() + " has been favorited!", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(SoundboardActivity.this, "You cannot favorite more than 5 sounds...", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                    break;
                                case R.id.editSoundOption:
                                    openSoundDialog(SoundDialog.UPDATE_DIALOG);
                                    break;
                                case R.id.deleteSoundOption:
                                    openSoundDialog(SoundDialog.DELETE_DIALOG);
                                    break;
                            }
                            return true;
                        }
                    });

                    popup.show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }
}