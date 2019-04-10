package gameFiles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

	public class MainGame implements MouseListener {

		static final int JFRAME_HEIGHT = 1080;
		static final int JFRAME_LENGTH = 1920;

		static int score = 0;
		private int score_multiplier;

		private static JFrame frame = new JFrame("Cookie Clicker");
		private static JTextArea ccta = new JTextArea("Cookies: " + score);

		public MainGame() {
			score_multiplier = 1;
	}

	public static void main(String [] args) throws IOException {

	/**********************INSTANTIATE********************************/
		frame.setLayout(new BorderLayout());
		JPanel cookiepanel = new JPanel();
		ccta.setFont(new Font("Comic Sans MS", Font.BOLD, 40));
    /***************ADD IMAGE AND CENTER WEST [LEFT]*******************/
		BufferedImage cookimg = ImageIO.read(new File("H:\\Pics\\Cookie.png"));
		JLabel label = new JLabel(new ImageIcon(cookimg));
		cookiepanel.setLayout(new BorderLayout());
		cookiepanel.add(label, BorderLayout.WEST);
    /*****************************************************************/
		frame.add(ccta, BorderLayout.NORTH);
		frame.add(cookiepanel);
		label.setLocation(10,10);
		frame.setSize(JFRAME_LENGTH, JFRAME_HEIGHT);
		frame.getContentPane().setBackground(Color.WHITE);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

       runGame();
   }

	public static void runGame() {
		while(true) {
			updateText(ccta);
		}
	}

	public static void updateText(JTextArea ta) {
		ta.setText("Cookies: " + score);
		frame.add(ta);
	}

	public void mouseClicked(MouseEvent e) {
		int vertcent = JFRAME_HEIGHT/2;
		int horizcent = JFRAME_LENGTH/2;
		if((e.getX()<512) && (Math.abs(e.getY()-vertcent)<256)){
		cookieClicked();
		}
	}

	public void cookieClicked() {
   	System.out.println("COOKIE CLICKED!");
   	score += score_multiplier;
   	}


	public int getScore() {
		return score;
	}

	public int getScoreMult() {
		return score_multiplier;
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
