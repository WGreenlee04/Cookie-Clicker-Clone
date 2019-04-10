package gameFiles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
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

	private int score;
	private int score_multiplier;
	private int baseScore;
	private String dir;

	public MainGame() {
		score = 0;
		score_multiplier = 1;
		baseScore = 1;
		dir = "H:/pics/Cookie.JFIF";
	}

    public static void main(String [] args) throws IOException {

        JFrame frame = new JFrame("Cookie Clicker");

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        /**********************PROMPT FOR IMAGE DIRECTORY******************/
        JFrame prompt=new JFrame("Where dat File?");
        JPanel pPanel=new JPanel();
        JLabel label2 = new JLabel();
        pPanel.setLayout(new BorderLayout());
        pPanel.add(label2, BorderLayout.WEST);
        pPanel.addMouseListener(null);
        prompt.add(pPanel);
        /******************************************************************/

        /***************ADD IMAGE AND CENTER WEST [LEFT]*******************/
        BufferedImage cookimg = ImageIO.read(new File("H:/pics/Cookie.JFIF"));
        JLabel label = new JLabel(new ImageIcon(cookimg));
        panel.setLayout(new BorderLayout());
        panel.add(label, BorderLayout.WEST);
        panel.addMouseListener(null);
        /*****************************************************************/

        frame.add(panel);
        label.setLocation(10,10);
        frame.setSize(JFRAME_LENGTH, JFRAME_HEIGHT);
        //frame.getContentPane().setBackground(Color.BLACK);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void mouseClicked(MouseEvent e) {
    	int vertcent = JFRAME_HEIGHT/2;
    	int horizcent = JFRAME_LENGTH/2;
    	if((e.getX()<512) && (Math.abs(e.getY()-vertcent)<256)){
    		onCookieClick();
    	}
    }

    public void onCookieClick() {
    	System.out.println("COOKIE CLICKED!");
    	score += (baseScore* score_multiplier);

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