package hw;

import java.awt.*;     
import java.awt.event.*;
import java.lang.Math;

class WindowDestroyer extends WindowAdapter
{
    public void windowClosing(WindowEvent e) 
    {
        System.exit(0);
    }
}

public class Calculator_20190594 implements ActionListener {
	private Frame f;
	private Button button[] = new Button[25];
	private TextArea show;
	private String answer = "";
	private String cal = "";
	
	public Calculator_20190594() {
		f = new Frame("Calculator");
		f.setLayout(new GridLayout(2, 1));
		Panel keyboard= new Panel();
		Panel showresult = new Panel();
		keyboard.setLayout(new GridLayout(5,5));
		show = new TextArea("",3,55,TextArea.SCROLLBARS_NONE);
		showresult.add(show);
		
		button[0] = new Button("x!");
		button[1] = new Button("(");
		button[2] = new Button(")");
		button[3] = new Button("%");
		button[4] = new Button("AC");
		button[5] = new Button("ln");
		button[6] = new Button("7");
		button[7] = new Button("8");
		button[8] = new Button("9");
		button[9] = new Button("÷");
		button[10] = new Button("log");
		button[11] = new Button("4");
		button[12] = new Button("5");
		button[13] = new Button("6");
		button[14] = new Button("×");
		button[15] = new Button("√");
		button[16] = new Button("1");
		button[17] = new Button("2");
		button[18] = new Button("3");
		button[19] = new Button("-");
		button[20] = new Button("x^y");
		button[21] = new Button("0");
		button[22] = new Button(".");
		button[23] = new Button("=");
		button[24] = new Button("+");
		
		for(int i = 0; i < 25; i++)
			button[i].addActionListener(this);
		
		for(int i = 0; i < 25; i++)
			keyboard.add(button[i]);
		
		f.add(showresult);
		f.add(keyboard);
		f.pack();
		f.setSize(500, 300);
	    f.setVisible(true);  
		
		WindowDestroyer listener = new WindowDestroyer();
	    f.addWindowListener(listener);
	}
	
	public static int factorial(int num){
		int ans = 1;
		
		if (num<=1)
			return 1;
		else {
			for (int i=1;i<=num;i++)
				ans = ans*i;
		}
		return ans;
	}

	public double fdm_op(String s) {
		boolean isMinus = false;
		double ans=0;
		
		if (s.charAt(0)=='-') { //음수인 경우
			s = s.substring(1);
			isMinus = true;
		}
		
		if (s.charAt(0)=='√') {
			System.out.println(s.charAt(0));
			ans = Math.sqrt(Double.parseDouble(s.substring(1)));
			if (isMinus == true)
				ans = -ans;
		}
		else if (s.charAt(s.length()-1)=='!') {
			ans = factorial(Integer.parseInt(s.substring(0,s.length()-1)));
			if (isMinus == true)
				ans = -ans;
		}
		else if (s.charAt(s.length()-1)=='%') {
			ans = (Double.parseDouble(s.substring(0,s.length()-1)))/100;
			if (isMinus == true)
				ans = -ans;
		}
		else if (s.substring(0,2).equals("ln")) {
			ans = Math.log(Double.parseDouble(s.substring(2)));
			if (isMinus == true)
				ans = -ans;
		}
		else if (s.substring(0,3).equals("log")) {
			ans = Math.log10(Double.parseDouble(s.substring(3)));
			System.out.println(ans);
			if (isMinus == true)
				ans = -ans;
		}
		else { ///for문 돌려서 '^' 있으면 n제곱하기
			int did = 0;
			for (int i=0;i<s.length();i++) {
				double d,u;
				if (s.charAt(i)=='^') {
					d = Double.parseDouble(s.substring(0,i));
					if (s.charAt(i+1)=='-')
						u = -Double.parseDouble(s.substring(i+2,s.length()));
					else
						u = Double.parseDouble(s.substring(i+1,s.length()));
					ans = Math.pow(d, u);
					if (isMinus == true)
						ans = -ans;
					did = 1;
					break;
				}
			}
			
			if (did==0) { //사칙연산
				int idx[] = new int[200];
				int k = 0;
				
				int numcnt = 1;
				for (int i = 0; ; i++) {
					if (i == s.length())
						break;
					if (s.charAt(i) == '+' || s.charAt(i) == '-' || s.charAt(i) == '×' || s.charAt(i) == '÷') {
						if (s.charAt(i + 1) == '-') {
							s = s.substring(0, i + 1) + s.substring(i + 2);
							i++;
							idx[k] = numcnt;
							k++;
						}
							numcnt++;
					}
				}
				
				String[] num = s.split("-|\\+|\\×|÷");
				String[] op = s.split("0|1|2|3|4|5|6|7|8|9|\\.");
				
				if (isMinus == true)
					num[0] = "-" + num[0];
				
				ans = Double.parseDouble(num[0]);
				double tmp;
				int j = 1, prev = 0;
				
				for (int i = 0; i < op.length; i++) {
					for (k = 0; k < num.length; k++) {
						if (j == idx[k] && prev != j) {
							num[j] = "-" + num[j];
							break;
						}
					}
					
					prev = j;
					tmp = Double.parseDouble(num[j]);
					
					if (op[i].equals("-")) {
						ans -= tmp;
						j++;
					}
					else if (op[i].equals("+")){
						ans += tmp;
						j++;
					}
					else if (op[i].equals("×")) {
						ans *= tmp;
						j++;
					}
					else if (op[i].equals("÷")) {
						ans /= tmp;
						j++;
					}
				}
			}
		}

		
		return ans;
	}
	
	public void bracket_calculate(String s) {
		int bcnt=0,l=-1,r=-1;
		
		for (int k=0;k<s.length();k++) {
			if (s.charAt(k)=='(')
				bcnt++;
		}
		
		for (int i=0;i<bcnt;i++) {
			String tmp = "";
			for (int j=0;j<s.length();j++) {
				if (s.charAt(j)=='(')
					l = j;
				else if (s.charAt(j)==')') {
					r = j;
					break;
				}
			}
			tmp = s.substring(l+1,r);
			tmp = Double.toString(fdm_op(tmp));
			s = s.substring(0,l)+tmp+s.substring(r+1,s.length());
		}
		
		double ans = fdm_op(s);

		answer = "\n"+ans;
	}
	
	public void actionPerformed(ActionEvent e) {
		Button btn = (Button)e.getSource();
		String str = btn.getLabel();
		char input = str.charAt(0);
		
		if(!answer.isEmpty()) {
			answer = "";
			cal = "";
			show.setText("");
		}
		
		if (input == '+' || input == '-' || input == '×' || input == '÷' || input == '=') {
			if (!cal.isEmpty()) {
				int idx = (int)cal.length() - 1;
				char prev = cal.charAt(idx);
				
				//if (prev == '+' || prev == '-' || prev == '×' || prev == '÷')
					//show.replaceRange(str, idx, idx + 1);
				//else
				//이걸 왜 안 지웠지...
					show.append(str);
			}
			else if (input == '-')
				show.append(str);
		}
		else if (input == '.') {
			if (!cal.isEmpty()) {
				int idx = (int)cal.length() - 1;
				char prev = cal.charAt(idx);
				
				if (prev == '+' || prev == '-' || prev == '×' || prev == '÷')
					show.append("0.");
				else if (prev != '.')
					show.append(str);
			}
			else
				show.append("0.");
		}
		else if (str=="x!")
			show.append(str.substring(1));
		else if (str == "x^y")
			show.append(str.substring(1,2));
		else if (str=="AC")
			show.setText("");
		else
			show.append(str);
		
		cal = show.getText();
		
		if (input == '=')
			if (!cal.isEmpty()) {
				cal = cal.replace("=", "");
				bracket_calculate(cal);
				show.append(answer);
			}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Calculator_20190594 calculator = new Calculator_20190594();
	}
}
