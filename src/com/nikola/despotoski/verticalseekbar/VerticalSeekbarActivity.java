package com.nikola.despotoski.verticalseekbar;

import java.util.Vector;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class VerticalSeekbarActivity extends Activity implements OnSeekBarChangeListener {
    /** Called when the activity is first created. */
	public Vector<VerticalSeekbar> vBars = new Vector<VerticalSeekbar>();
	public LinearLayout.LayoutParams params=  new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT);
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        LayoutInflater inflater = getInflater();
        View tMain =inflater.inflate(R.layout.main, null);
        LinearLayout main = (LinearLayout)tMain;        
        LinearLayout tempLayout = new LinearLayout(this);
        tempLayout.setOrientation(LinearLayout.HORIZONTAL); 
        
        for(int i = 1; i <=5; i++)
        {   RelativeLayout vSeekbarLayout = (RelativeLayout)inflater.inflate(R.layout.vertical_seekbar_layout, null);
        	VerticalSeekbar vBar = (VerticalSeekbar)vSeekbarLayout.findViewById(R.id.bar);
        	vBar.setMaximum(i*1000);
        	Log.i("MAX", ""+vBar.getMaximum());
        	vBar.setProgressAndThumb((int)vBar.getMaximum()/2);
        	vBar.setOnSeekBarChangeListener(this);
        	tempLayout.setLayoutParams(params);
        	tempLayout.addView(vSeekbarLayout, params);
        	vBars.add(vBar);
        }
        
        main.addView(tempLayout);
        setContentView(main);
        final Button b = (Button)main.findViewById(R.id.btn);
        b.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				b.setEnabled(false);
				new Thread(){

					@Override
					public void run() {
						
						int index = 0;
					    while(index != vBars.size())
					    {
					    	final VerticalSeekbar bar = vBars.get(index);
					    	while(bar.getProgress()!=bar.getMaximum())
					    	{
					    	    try {
									sleep(10);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
					    		VerticalSeekbarActivity.this.runOnUiThread(new Runnable(){

									@Override
									public void run() {
										bar.setProgressAndThumb(bar.getProgress()+1);
										
									}});
					    		
					    	}
					    	index++;
					    	
					    }
					    VerticalSeekbarActivity.this.runOnUiThread(new Runnable(){

							@Override
							public void run() {
								b.setEnabled(true);
								
							}});
						super.run();
					}}.start();
			}});
        
        
    }
    
    public LayoutInflater getInflater()
    {
    	LayoutInflater inflater = (LayoutInflater)VerticalSeekbarActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		return inflater;
    	
    }

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}
}