package hw;

public class Getxy_20190594 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int x,y;
		for (x=1;x<10;x++) {
			for (y=1;y<10;y++) {
				int xxy = 100*x+10*x+y;
				int yy = 10*y+y;
				int yxx = 100*y+10*x+x;
				if (xxy + yy == yxx) {
					System.out.print("x: "+x+", y: "+y);
				}
			}
		}
	}
}
