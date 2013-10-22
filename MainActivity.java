package csc281.project.helicoptergame;

import java.util.ArrayList;
import java.util.Random;

//import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;

public class MainActivity extends Activity implements OnTouchListener {


	Handler mainThreadHandler;
	public Wall wall;
	Random r;
	float randomFloat, wallFloat;
	public int heloH = 100;
	GestureDetector gesture;
	public boolean isDown;
	public boolean running=true;
	public int blacksNum=100;
	public TunnelView tunnel;
	public int heloSleep=10;
	//MediaPlayer crash, helicopterSound, background;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tunnel=new TunnelView(this);
		tunnel.setOnTouchListener(this);
		//background=MediaPlayer.create(this, R.raw.background);
		//crash=MediaPlayer.create(this,R.raw.crash);
		//helicopterSound= MediaPlayer.create(this, R.raw.helicopter);
		playGame();
	}
	
	public void playGame()
	{
		wall=new Wall(this);
		heloH=100;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Would you like to play?");
		builder.setCancelable(true);
		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){

			public void onClick(DialogInterface arg0, int arg1) {
				//background.start();
				new Thread(new Runnable(){
					public void run(){
						int sleep=75;
						while (running)
						{
							wall.wallPoints.remove(0);
							wall.walls.remove(0);
							wall.whereIsTheWall-=tunnel.w/blacksNum;
							if (wall.whereIsTheWall<=0)
							{
								wall.whereIsTheWall=tunnel.w;
								wall.topOfTheWall=(float)(Math.random()*tunnel.h);
							}
							tunnel.wallTop.remove(0);
							r=new Random();
							randomFloat=r.nextFloat();
							wallFloat=r.nextFloat();
							wall.wallPoints.add(randomFloat);
							
							if (wall.walls.get(0)==0)
							{
								wall.walls.add((float) 0.0);
							}
							else
							{
								wall.walls.add(wallFloat);
							}
							
							
							if ((heloH)<=tunnel.upperLim.get((int)((blacksNum/6.)))||(heloH+tunnel.heloHeight)>=tunnel.lowerLim.get((int)((blacksNum/6.))))
							{
								tunnel.notCrash=false;
								running=false;
							}
							else if ((heloH+tunnel.heloHeight)>=(wall.topOfTheWall) && (heloH)<=(wall.topOfTheWall+tunnel.wallLength)&& (50+tunnel.heloWidth)>(wall.whereIsTheWall)&&(50)<(wall.whereIsTheWall+wall.brickWidth))
							{
								tunnel.notCrash=false;
								running=false;
							}
							else
							{
								tunnel.upperLim.remove(0);
								tunnel.lowerLim.remove(0);
							}
							if (sleep>50)
								sleep-=5;
							tunnel.postInvalidate();
							try {
								Thread.sleep(sleep);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}}).start();
				new Thread(new Runnable(){
					public void run(){
						while(running)
						{
							if (isDown)
							{
								heloH-=tunnel.heloDelta;
								heloSleep=25;
							}
							else
							{
								heloH+=tunnel.heloDelta;
								heloSleep=10;
							}
							if ((heloH)<=tunnel.upperLim.get((int)((blacksNum/6.)))||(heloH+tunnel.heloHeight)>=tunnel.lowerLim.get((int)((blacksNum/6.))))
							{
								tunnel.notCrash=false;
								running=false;
							}
							else if ((heloH+tunnel.heloHeight)>=(wall.topOfTheWall) && (heloH)<=(wall.topOfTheWall+tunnel.wallLength)&& (50+tunnel.heloWidth)>(wall.whereIsTheWall)&&(50)<(wall.whereIsTheWall+wall.brickWidth))
							{
								tunnel.notCrash=false;
								running=false;
							}
							
							tunnel.postInvalidate();
							try {
								Thread.sleep(heloSleep);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						
					}
				}).start();
				
			}});
		builder.setNegativeButton("No", new DialogInterface.OnClickListener(){

			public void onClick(DialogInterface dialog, int which) {
				System.exit(0);
				
			}});
		
		AlertDialog box = builder.create();
		box.show();	
		
		setContentView(tunnel);
	}

	@Override
	public boolean onTouch(View v, MotionEvent e) {
		
		if (running==false)
		{
			running=true;
			tunnel.notCrash=true;
			playGame();
			return true;
		}
		
		if (e.getActionMasked()==e.ACTION_DOWN)
			isDown=true;
		else if (e.getActionMasked()==e.ACTION_UP)
			isDown=false;
		return true;
	}	
	
}
