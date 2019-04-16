package gameFiles;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class NoMoney {
	public NoMoney(int price, int score) {
		JFrame frame = new JFrame();
		frame.setLocationRelativeTo(null);
		frame.setSize(256, 128);
		frame.setLayout(new BorderLayout());
		frame.add(new JTextArea("You need " + (price - score) + " more cookies to afford that."), BorderLayout.NORTH);
		frame.setVisible(true);
	}

}