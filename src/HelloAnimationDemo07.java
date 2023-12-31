import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.Timer;

public class HelloAnimationDemo07 
extends JFrame implements ActionListener
{
	private static final long serialVersionUID = -2245613981181293044L;
	private SimplePanel07 panel;
	private Timer timer;
//	private int timer_i = 0;

	public HelloAnimationDemo07() {
		super("Simple Animation Demo Section 07");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 600);
		panel = new SimplePanel07(this.getWidth(), this.getHeight());
		setLayout(new BorderLayout());
		this.add(this.panel, BorderLayout.CENTER);
		timer = new Timer(10, this);
		timer.setInitialDelay(2000);
		
		timer.start();
	}

	public static void main(String [] args) {
		new HelloAnimationDemo07().setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
//		System.out.println("Hello Timer " + timer_i);
//		timer_i++;
		panel.tickTime();
		panel.repaint();
	}
	
}
