package vn.edu.tdc.fit.myapplication;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.SimpleFormatter;

import vn.edu.tdc.fit.myapplication.R;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    ImageButton imvPlay, imvPause, imvStop, imvNext, imvPrev;
    TextView txttenbaihat, txttimedau, txttimecuoi;
    SeekBar seekBar;
    int position = 0;

    ArrayList<Song> arrayList;

    //ID của theme mà Activity sử dụng
    int themeIdcurrent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Đọc ID theme đã lưu, nếu chưa lưu thì dùng R.style.MyAppTheme
        SharedPreferences locationpref = getApplicationContext()
                .getSharedPreferences("MainActivity", MODE_PRIVATE);
        themeIdcurrent = locationpref.getInt("themeid",R.style.MyAppTheme);

        //Thiết lập theme cho Activity
        setTheme(themeIdcurrent);
        setContentView(R.layout.activity_main);

        setControl();
        arrayList = new ArrayList<>();
        arrayList.add(new Song("Anh nhớ em", R.raw.b1));
        arrayList.add(new Song("Anh nhớ nhé", R.raw.b2));
        arrayList.add(new Song("Đời là thế thôi", R.raw.b3));
        arrayList.add(new Song("Anh có tài mà", R.raw.b4));
        arrayList.add(new Song("Ta đi tìm em", R.raw.b5));

        mediaPlayer = MediaPlayer.create(MainActivity.this, arrayList.get(position).getFile());
        txttenbaihat.setText(arrayList.get(position).getTenbaihat());
        imvPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    imvPlay.setImageResource(R.drawable.play);
                }else{
                    mediaPlayer.start();
                    imvPlay.setImageResource(R.drawable.pause);
                }
                setTimeTotal();
                updatetime();
            }
        });

        imvStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();// dừng chuyển đổi hình play
                mediaPlayer.release();
                imvPlay.setImageResource(R.drawable.play);
                //Khởi tại lại bài hát
                mediaPlayer = MediaPlayer.create(MainActivity.this, arrayList.get(position).getFile());
                txttenbaihat.setText(arrayList.get(position).getTenbaihat());
                setTimeTotal();
                updatetime();
            }
        });

        imvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position++;
                if (position > arrayList.size() - 1){
                    position = 0;
                }
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                //Khởi tại lại bài hát
                mediaPlayer = MediaPlayer.create(MainActivity.this, arrayList.get(position).getFile());
                txttenbaihat.setText(arrayList.get(position).getTenbaihat());
                mediaPlayer.start();
                imvPlay.setImageResource(R.drawable.pause);
                setTimeTotal();
                updatetime();
            }
        });

        imvPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position--;
                if (position < arrayList.size() - 1){
                    position = 0;
                }
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                //Khởi tại lại bài hát
                mediaPlayer = MediaPlayer.create(MainActivity.this, arrayList.get(position).getFile());
                txttenbaihat.setText(arrayList.get(position).getTenbaihat());
                mediaPlayer.start();
                imvPlay.setImageResource(R.drawable.pause);
                setTimeTotal();
                updatetime();
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());// khi người dùng kéo thả bài hát dừng đến điểm nào thì tự động phát


            }
        });

    }
    private  void updatetime(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat dinhDangGio = new SimpleDateFormat("mm:ss");
                txttimedau.setText(dinhDangGio.format(mediaPlayer.getCurrentPosition()) + "");// getCurrentPosition vị trí hiện tại của time
                // Cập nhật propreess sk;
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                handler.postDelayed(this, 500);
            }
        }, 100);
    }
    private void setTimeTotal(){
        SimpleDateFormat dinhDangGio = new SimpleDateFormat("mm:ss");
        txttimecuoi.setText(dinhDangGio.format(mediaPlayer.getDuration()) + "");// lấy tổng time của 1 bài hát
        // gán max sksong = mediaPlayer.getDuration();
        seekBar.setMax(mediaPlayer.getDuration());
    }
    private void setControl() {
        imvNext = (ImageButton) findViewById(R.id.imvNext);
        imvPrev = (ImageButton) findViewById(R.id.imvprev);
        imvStop = (ImageButton) findViewById(R.id.imvstop);
        imvPlay = (ImageButton) findViewById(R.id.imvplay);
        seekBar = (SeekBar) findViewById(R.id.seekbar);
        txttenbaihat = (TextView) findViewById(R.id.tvbaihat);
        txttimedau = (TextView) findViewById(R.id.tvdau);
        txttimecuoi = (TextView) findViewById(R.id.tvcuoi);
    }

    // Tao menu action
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menubar, menu);
        return true;
    }

    // Sử lý menu action
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.chuyen:
                //Chuyển đổi theme
                themeIdcurrent = themeIdcurrent == R.style.MyAppTheme ? R.style.AppTheme : R.style.MyAppTheme;

                //Lưu lại theme ID
                SharedPreferences locationpref = getApplicationContext()
                        .getSharedPreferences("MainActivity", MODE_PRIVATE);
                SharedPreferences.Editor spedit = locationpref.edit();
                spedit.putInt("themeid", themeIdcurrent);
                spedit.apply();

                //Tạo lại Activity để áp dụng theme mởi đổi
                recreate();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

