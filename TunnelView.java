package csc281.project.helicoptergame;

import java.util.ArrayList;
import java.util.Random;

import android.R.color;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class TunnelView extends View {

	private MainActivity tunnel;
	//Wall wall;
	Bitmap helicopter;
	public int w, h, heloHeight, heloWidth, heloDelta, heloStart;
	public float topWall, bottomWall, wallLength;
	public ArrayList<Integer> upperLim, lowerLim, wallTop;
	public boolean notCrash=true;
	
	
	
	public TunnelView(Context context) {
		super(context);
		tunnel=(MainActivity)context;
		tunnel.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		upperLim=new ArrayList<Integer>();
		lowerLim=new ArrayList<Integer>();
		wallTop=new ArrayList<Integer>();
		
	}

	
	protected void onDraw(Canvas g)
	{
		w=getWidth();
		h=getHeight();
		heloHeight = (int) (h/10.0);
		heloWidth = (int) (w/10.0);
		wallLength = (int) (h/4.0);
		//heloDelta = (int) (h/15.0);
		heloDelta = (int) (h/150.0);
		
		heloStart = (int) (h/2.0);
		Paint paint=new Paint();
		paint.setColor(Color.WHITE);
		g.drawRect(0, 0, w, h, paint);
		float wallFraction=(float) (h/6.0);
		bottomWall=h-wallFraction;
		topWall=wallFraction+0;
		paint.setColor(Color.BLUE);
		if (notCrash) //put these in constructor
		{
			helicopter= Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.helicopter_icon1),heloWidth,heloHeight,false);
		}
		else if (!notCrash)
		{
			helicopter= Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.helo_crash),heloWidth,heloHeight,false);
		}
		g.drawBitmap(helicopter, 50, tunnel.heloH, paint);
		tunnel.wall.brickWidth=(float) (w/100.0);
		tunnel.wall.wallDifConstant=(float) ((2/3.0)*h);
		for (int i=0; i<99; i++)
		{
			Rect walls;
			Rect topWallRect=new Rect((int) (tunnel.wall.brickWidth*i), 0, (int)(tunnel.wall.brickWidth*(i+1)),(int) (topWall*tunnel.wall.wallPoints.get(i)));
			Rect bottomWallRect=new Rect((int) (tunnel.wall.brickWidth*i), (int) (h-(wallFraction)*(1-tunnel.wall.wallPoints.get(i))), (int)(tunnel.wall.brickWidth*(i+1)),h);
			//if (tunnel.wall.walls.get(i)==0)
			//{
				//walls = new Rect(w,h,w,h);
				//wallTop.add(h);
			//}
			//else
			//{
				//walls = new Rect((int) (tunnel.wall.brickWidth*(i)),(int)(h*tunnel.wall.walls.get(i)),(int) (tunnel.wall.brickWidth*(i+1)),(int)((h*tunnel.wall.walls.get(i))+wallLength));
				//wallTop.add((int) (h*tunnel.wall.walls.get(i)));
			//}
			//tunnel.wall.wallTop.add(h*tunnel.wall.walls.get(0));
			
			walls = new Rect((int) (tunnel.wall.whereIsTheWall),(int)(tunnel.wall.topOfTheWall),(int) (tunnel.wall.brickWidth+tunnel.wall.whereIsTheWall),(int)((tunnel.wall.topOfTheWall+wallLength)));
			wallTop.add((int) (h*tunnel.wall.walls.get(i)));
			
			upperLim.add((int) (topWall*tunnel.wall.wallPoints.get(i)));
			lowerLim.add((int) (h-(wallFraction)*(1-tunnel.wall.wallPoints.get(i))));
			paint.setColor(Color.BLUE);
			g.drawRect(topWallRect, paint);
			g.drawRect(bottomWallRect, paint);
			paint.setColor(Color.RED);
			
			g.drawRect(walls, paint);
		}
	}
	
}
