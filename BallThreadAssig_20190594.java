package hw;

import java.awt.*;
import java.awt.event.*;

public class BallThreadAssig_20190594 extends Frame implements ActionListener
{  	 
	private Canvas canvas; 
	
	public BallThreadAssig_20190594()
   	{  	
		canvas = new Canvas();
      	add("Center", canvas);
      	Panel p = new Panel();
		Button s = new Button("Start");
		Button c = new Button("Close");
		p.add(s);
		p.add(c);
      	s.addActionListener(this);
		c.addActionListener(this);
      	add("South", p);
   	}
	
	public void actionPerformed(ActionEvent evt)
   	{  	
		if (evt.getActionCommand() == "Start")
      	{  	
   			Ball b = new Ball(canvas);
   			b.start();
      	}
      	else if (evt.getActionCommand() == "Close")
         	System.exit(0);
   	}
	
	public static void main(String[] args)
   	{  	
		Frame f = new BallThreadAssig_20190594();
      	f.setSize(400, 300);
      	
      	WindowDestroyer listener = new WindowDestroyer();  
      	f.addWindowListener(listener);
      	f.setVisible(true);  
   	}
}
		
class Ball extends Thread
{  	
	private Canvas box;
	private int[] XSIZE = new int[40];
	private int[] YSIZE = new int[40];
	private int[] x = new int[40];
	private int[] y = new int[40];
	private int[] dx = new int[40];
	private int[] dy = new int[40];
	private boolean[] coll = new boolean[40];
	private int cnt = 5;
	private int cnttmp = 5;
	private boolean ch = false;
		
	public Ball(Canvas c)
	{
		box = c;
	}
		
	public void draw()
	{  	
		int i;
		for (i=0;i<40;i++) {
			coll[i] = false;
		}
		for (i=0;i<cnt;i++) {
			XSIZE[i] = 16;
			YSIZE[i] = 16;
		}
		
		Dimension d = box.getSize();
		
		for (i=0;i<cnt;i++) {
			x[i] = d.width/2;
			y[i] = d.height/2;
		}
		dx[0] = 2; dx[1] = 2; dx[2] = -1; dx[3] = -1; dx[4] = 2;
		dy[0] = 0; dy[1] = -2; dy[2] = 2; dy[3] = -1; dy[4] = 2;
		
		for (i=0;i<cnt;i++) {
			Graphics g = box.getGraphics();
		  	g.fillOval(x[i], y[i], XSIZE[i], YSIZE[i]);
		  	g.dispose();
		}
	}
	
	public void move()
	{  	
	  	for (int i=0;i<cnt;i++) {
	  		if (XSIZE[i] == 0) //ball이 사라진 경우
	  			continue;
	  		
	  		Graphics g = box.getGraphics();
		  	g.setXORMode(box.getBackground());
		  	g.fillOval(x[i], y[i], XSIZE[i], YSIZE[i]);
	
		  	x[i] += dx[i];	
		  	y[i] += dy[i];
		  	Dimension d = box.getSize();
		  		
		  	if (x[i] < 0) {
		  		x[i] = 0;
		  		dx[i] = -dx[i];
		  		ch = true;
		  	}
		  	if (x[i] + XSIZE[i] >= d.width) {
		  		x[i] = d.width - XSIZE[i];
		  		dx[i] = -dx[i];
		  		ch = true;
		  	}
		  	if (y[i] < 0) {
		  		y[i] = 0;
		  		dy[i] = -dy[i];
		  		ch = true;
		  	}
		  	if (y[i] + YSIZE[i] >= d.height) {
		  		y[i] = d.height - YSIZE[i];
		  		dy[i] = -dy[i];
		  		ch = true;
		  	}
		  	
		  	g.fillOval(x[i], y[i], XSIZE[i], YSIZE[i]);
		  	g.dispose();
	  	}
	  	
	  	for (int i=0;i<cnt;i++) {
	  		if (ch==false) //맨 처음에 공들이 다 모여있을 때
					break;
	  		if (XSIZE[i]==0)
	  			continue;
	  		if (coll[i] == true) //이미 본 케이스
	  			continue;
		  	for (int j=i+1;j<cnt;j++) {
		  		if (XSIZE[j]==0)
		  			continue;
		  		if (coll[j]==true) //이미 본 케이스
		  			continue;
		  		else {
		  			if ((int)Math.sqrt(Math.pow(x[i]- x[j],2)+Math.pow(y[i]-y[j], 2)) <= (XSIZE[i]+XSIZE[j])/2) {
		  				coll[i]=true;
		  				coll[j]=true;
		  			}
		  		}
		  	}
	  	}
	  	
	  	
	  	for (int i=0;i<cnt;i++) {
	  		if (coll[i]==true) {
	  			Graphics g = box.getGraphics();
	  			g.setXORMode(box.getBackground());
	  			g.fillOval(x[i], y[i], XSIZE[i], YSIZE[i]);
			  	
	  			XSIZE[i] /= 2;
	  			YSIZE[i] /= 2;
	  			coll[i] = false;
	 
	  			if (XSIZE[i]==1) { //ball 없애기 위해, 어차피 새로 생성된 공 size가 1이므로 더 할 필요 없음
	  				XSIZE[i] = 0;
	  				continue;
	  			}
	  			
	  			XSIZE[cnttmp] = XSIZE[i];
	  			YSIZE[cnttmp] = YSIZE[i];
	  			dx[i] = -dx[i];
	  			dx[cnttmp] = dx[i];
	  			dy[i] = (int)(2*Math.random())+1;
	  			dy[cnttmp] = (int)(-2*Math.random());
	  			x[cnttmp] = x[i];
	  			y[cnttmp] = y[i];
	  			
	  			while((int)Math.sqrt(Math.pow(x[i]- x[cnttmp],2)+Math.pow(y[i]-y[cnttmp], 2)) <= (XSIZE[i]+XSIZE[cnttmp])/2) {
	  				x[cnttmp] += dx[cnttmp];
		  			y[cnttmp] += dy[cnttmp];
		  			x[i] += dx[i];
		  			y[i] += dy[i];
	  			} //분리되자마자 충돌하지 않도록

	  			g.fillOval(x[i], y[i], XSIZE[i], YSIZE[i]);
	  			g.fillOval(x[cnttmp], y[cnttmp], XSIZE[cnttmp], YSIZE[cnttmp]);
			  	g.dispose();
			  	
	  			cnttmp++;
	  		}
	  	}
	  	cnt = cnttmp;
	  	
	 }
		
	public void run()
	{  	
		draw();
	  	for (;;)
	  	{  	
	  		move();
	     	try {
	     		Thread.sleep(10);
	     	} catch(InterruptedException e) {
	     	}
	    }
	}
}
