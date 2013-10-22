package csc281.project.helicoptergame;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Wall extends TunnelView {

	public ArrayList<Float> wallPoints, walls, wallTop;
	public float brickWidth, wallWidth;
	public float whereIsTheWall, topOfTheWall;
	
	public float randomFloat, wallFloat;
	Random r;
	MainActivity tunnel;
	public float wallDifConstant;
	public Wall(Context context)
	{
		super(context);
		tunnel = (MainActivity)(context);
		wallPoints =new ArrayList<Float>();
		walls = new ArrayList<Float>();
		//wallTop = new ArrayList<Float>();
		r=new Random();
		//whereIsTheWall=(float)(tunnel.tunnel.w*.80);
		whereIsTheWall=0;
		//topOfTheWall=(float)(r.nextFloat()*tunnel.tunnel.h);
		topOfTheWall=20;
		
		for (int i=0; i<tunnel.blacksNum; i++)
		{
			if (i==80)
			{
				wallFloat=r.nextFloat();
			}
			else
			{
				wallFloat=(float)(0.0);
			}
			
			randomFloat=r.nextFloat();
			wallPoints.add(randomFloat);
			walls.add(wallFloat);
		}
	}
	

}
