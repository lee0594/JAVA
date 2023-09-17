package hw;

public class Lotto_20190594 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] w_nums= {7,18,32,37,44};
		int iter = 0;
		int match = 0;
		
		while(true) {
			int[] numbers = new int[5];
		
			for (int idx=0;idx<5;idx++) {
			
			numbers[idx] = (int)(50*Math.random());
			for (int s = 0;s<idx;s++)
				if(numbers[idx]==numbers[s]) {
					idx--;
					break;
				}
			}
			
			for (int i=0;i<5;i++) {
				for (int j=0;j<5;j++)
					if (w_nums[i]==numbers[j])
						match++;
			}
			
			iter++;
			
			if (match<3)
				match=0;
			else {
				System.out.print("Final random numbers : ");
				for (int i=0;i<5;i++)
					System.out.print(numbers[i]+" ");
				System.out.print("\nNumber of iterations : " + iter);
				break;
			}
		}	
	}
}