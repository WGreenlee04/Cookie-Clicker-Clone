package gameFiles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

public class MainGame implements MouseListener {
	
	//FINAL PIXEL LENGTHS (CONSTANTS)
	static final int JFRAME_HEIGHT = 1080;
	static final int JFRAME_LENGTH = 1920;
	static final int vertcent = JFRAME_HEIGHT/2;
	static final int horizcent = JFRAME_LENGTH/2;

	//GAME TOOLS
	private static int framecount;
	static int score = 0;
	static int cumulative_score = 0;
	private int score_multiplier;
	private static String cooklvstr = "";
	private static int cooklvint = 0;
	private final static int THRESHOLD1 = 5000;
	private static boolean passedTH1;
	private static boolean passedTH2;
	private final static int THRESHOLD2 = 20000;

	//J STUFF
	private static JFrame frame = new JFrame("Cookie Clicker");
	private static JTextArea ccta = new JTextArea("Cookies: " + score);
	private static JPanel cookiepanel = new JPanel();

	public MainGame() {
		score_multiplier = 1;
		framecount = 0;
		passedTH1 = false;
		passedTH2 = false;
}

	public static void main(String [] args) throws IOException {

	/**********************INSTANTIATE********************************/
		frame.setLayout(new BorderLayout());
		ccta.setFont(new Font("Comic Sans MS", Font.BOLD, 40));
		JLabel bg1 = new JLabel();
		bg1.setOpaque(true);
		bg1.setBackground(new Color(132, 86, 60));
		bg1.setLayout(null);
		bg1.setBounds(0, 56, JFRAME_LENGTH-512, vertcent-247-40);
		JLabel bg2 = new JLabel();
		bg2.setOpaque(true);
		bg2.setBackground(new Color(132, 86, 60));
		bg2.setLayout(null);
		bg2.setBounds(0, vertcent+256, JFRAME_LENGTH-512, JFRAME_HEIGHT-vertcent-256);
    /***************ADD IMAGE AND CENTER WEST [LEFT]******************/
		BufferedImage cookimg = ImageIO.read(new File("D:\\Everything else\\Eclipse\\TheGame\\src\\pics\\Cookie.jpg"));
		JLabel label = new JLabel(new ImageIcon(cookimg));
		cookiepanel.setLayout(new BorderLayout());
		cookiepanel.add(label, BorderLayout.WEST);
    /*************************JFRAME STUFF****************************/
		frame.add(bg1);
		frame.add(bg2);
		frame.add(ccta, BorderLayout.NORTH);
		frame.add(cookiepanel);
		frame.setSize(JFRAME_LENGTH, JFRAME_HEIGHT);
		frame.getContentPane().setBackground(Color.WHITE);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    /****************************RUN GAME*****************************/
       runGame();
    /**************************END OF MAIN****************************/
   }

	public static void runGame() throws IOException {
		while(true) {
			framecount++;
			updateText(ccta);
			frame.addMouseListener(new MouseAdapter() { 
		          public void mousePressed(MouseEvent me) { 
		            mouseClicked(me);
		            System.out.println("MOUSE PRESSED");
		          } 
		        }); 
			if(cumulative_score > THRESHOLD1) {
				if(!passedTH1) {
					upgradeCookie();
					passedTH1 = true;
				}
			}
			if(cumulative_score > THRESHOLD2) {
				if(!passedTH2) {
					upgradeCookie();
					passedTH2 = true;
				}
			}
			frame.setSize(JFRAME_LENGTH, JFRAME_HEIGHT);
			frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		}
	}

	public static void updateText(JTextArea ta) {
		ta.setText("Cookies: " + score);
		frame.add(ta);
	}
	
	public static void updateImg(BufferedImage bi) {
		JLabel newimglabel = new JLabel(new ImageIcon(bi));
		cookiepanel.removeAll();
		cookiepanel.add(newimglabel, BorderLayout.WEST);
	}

	public void mouseClicked(MouseEvent e) {
		if((e.getX()<512) && (Math.abs(e.getY()-vertcent)<256)){
		cookieClicked();
		}
	}

	public void cookieClicked() {
		System.out.println("COOKIE CLICKED!");
		score += score_multiplier;
		cumulative_score += score_multiplier;
   	}
	
	public static void upgradeCookie() throws IOException {
		cooklvint++;
		cooklvstr = String.valueOf(cooklvint);
		BufferedImage bi = ImageIO.read(new File("D:\\Everything else\\Eclipse\\TheGame\\src\\pics\\Cookie"+ cooklvstr + ".jpg"));
		updateImg(bi);
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
