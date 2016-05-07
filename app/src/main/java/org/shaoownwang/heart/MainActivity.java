package org.shaoownwang.heart;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.SeekBar;
import android.widget.Toast;

import com.github.danielnilsson9.colorpickerview.dialog.ColorPickerDialogFragment;

public class MainActivity extends AppCompatActivity implements ColorPickerDialogFragment.ColorPickerDialogListener{
    private static final int DIALOG_HEART_ID = 0;
    private static final int DIALOG_RIPPLE1_ID = 1;
    private static final int DIALOG_RIPPLE2_ID = 2;

    private HeartView heartView;
    private RippleView rippleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        heartView = (HeartView) findViewById(R.id.heart_view);
        rippleView = (RippleView) findViewById(R.id.ripple_view);
        initSeekBar();
        final ValueAnimator animator = ValueAnimator.ofFloat(75, 100, 75);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                heartView.setA(value);
                heartView.setoffset((int) (-20 + (value - 75) / 3));
                rippleView.setProgress(value);
            }
        });
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animator.start();
            }
        });


    }

    private void initSeekBar() {
        AppCompatSeekBar seekbar1 = (AppCompatSeekBar) findViewById(R.id.seek_bar1);
        final AppCompatSeekBar seekbar2 = (AppCompatSeekBar) findViewById(R.id.seek_bar2);
        seekbar2.setProgress(50);

        seekbar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                heartView.setoffset(500 - 10 * seekBar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        seekbar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                heartView.setA(seekBar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_heart_color:
                onClickColorPickerDialog(item, DIALOG_HEART_ID);
                break;
            case R.id.action_ripple_color1:
                onClickColorPickerDialog(item, DIALOG_RIPPLE1_ID);
                break;
            case R.id.action_ripple_color2:
                onClickColorPickerDialog(item, DIALOG_RIPPLE2_ID);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickColorPickerDialog(MenuItem item, int dialotId) {

        // The color picker menu item has been clicked. Show
        // a dialog using the custom ColorPickerDialogFragment class.

        ColorPickerDialogFragment f = ColorPickerDialogFragment
                .newInstance(dialotId, null, null, Color.BLACK, true);

        f.setStyle(DialogFragment.STYLE_NORMAL, R.style.LightPickerDialogTheme);
        f.show(getFragmentManager(), "d");

    }

    @Override
    public void onColorSelected(int dialogId, int color) {
        switch(dialogId) {

            case DIALOG_HEART_ID:
                // We got result from the other dialog, the one that is
                // shown when clicking on the icon in the action bar.

                heartView.setHeartColor(color);
                Toast.makeText(MainActivity.this, "Selected Color: " + colorToHexString(color), Toast.LENGTH_SHORT).show();
                break;
            case DIALOG_RIPPLE1_ID:
                // We got result from the other dialog, the one that is
                // shown when clicking on the icon in the action bar.
                rippleView.setStartColor(color);
                Toast.makeText(MainActivity.this, "Selected Color: " + colorToHexString(color), Toast.LENGTH_SHORT).show();
                break;

            case DIALOG_RIPPLE2_ID:
                // We got result from the other dialog, the one that is
                // shown when clicking on the icon in the action bar.
                rippleView.setEndColor(color);
                Toast.makeText(MainActivity.this, "Selected Color: " + colorToHexString(color), Toast.LENGTH_SHORT).show();
                break;

        }


    }

    @Override
    public void onDialogDismissed(int dialogId) {

    }

    private static String colorToHexString(int color) {
        return String.format("#%06X", 0xFFFFFFFF & color);
    }
}
