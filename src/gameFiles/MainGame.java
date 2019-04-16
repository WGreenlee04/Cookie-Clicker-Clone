package gameFiles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class MainGame implements MouseListener {

	// FINAL PIXEL LENGTHS (CONSTANTS)
	static final int JFRAME_HEIGHT = 1080;
	static final int JFRAME_LENGTH = 1920;
	static final int vertcent = JFRAME_HEIGHT / 2;
	static final int horizcent = JFRAME_LENGTH / 2;

	// GAME TOOLS
	private static int framecount;
	static int score = 0;
	static int cumulative_score = 0;
	static int score_multiplier = 1;
	private static String cooklvstr = "";
	private static int cooklvint = 0;
	private final static int THRESHOLD1 = 5000;
	private static boolean passedTH1;
	private static boolean passedTH2;
	private final static int THRESHOLD2 = 20000;
	private static double timepassed = 0;
	private static int truetime = 0;

	// J STUFF
	private static JFrame frame = new JFrame("Cookie Clicker");
	private static JTextArea ccta = new JTextArea("Cookies: " + score);
	private static JPanel cookiepanel = new JPanel();
	private static JPanel mainPanel = new JPanel();

	// VERY IMPORTANT, FILL THESE.
	private static String fileLocation = "H:\\pics\\Cookie.JFIF";
	private static File outputFile = new File("H:\\output(DO NOT DELETE).txt");

	// SAVE STATE DATA
	private int saveNum;

	public MainGame() throws Exception {
		framecount = 0;
		passedTH1 = false;
		passedTH2 = false;

		if (outputFile.createNewFile()) {
			System.out.println("File Created Successfully");
		} else {
			System.out.println("File Already Exists");
		}

		try {
			fileGet(outputFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void fileGet(File outputFile2) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(outputFile2));
		while (br.readLine() != null) {
			fileLocation = br.readLine();
		}
	}

	public static void main(String[] args) throws Exception {

		/********************** INSTANTIATE ********************************/
		frame.setLayout(new BorderLayout());
		ccta.setFont(new Font("Comic Sans MS", Font.BOLD, 40));
		JLabel bg1 = new JLabel();
		bg1.setOpaque(true);
		bg1.setBackground(new Color(132, 86, 60));
		bg1.setLayout(null);
		bg1.setBounds(0, 56, JFRAME_LENGTH - 512, vertcent - 247 - 40);
		JLabel bg2 = new JLabel();
		bg2.setOpaque(true);
		bg2.setBackground(new Color(132, 86, 60));
		bg2.setLayout(null);
		bg2.setBounds(0, vertcent + 256, JFRAME_LENGTH - 512, JFRAME_HEIGHT - vertcent - 256);

		// Cookie location ask
		try (BufferedReader br = new BufferedReader(new FileReader(outputFile))) {
			String line;
			if ((line = br.readLine()) == null) {

				Scanner scan = new Scanner(System.in);
				System.out.println("Paste the full file path of your cookie picture here");
				String filepath = scan.nextLine();

				File out = outputFile;
				FileWriter fw = new FileWriter(out);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(filepath);
				bw.newLine();

				// end and close
				bw.close();
				scan.close();
			}
		}

		/*************** ADD IMAGE AND CENTER WEST [LEFT] ******************/
		BufferedImage cookimg = ImageIO.read(new File(fileLocation));
		JLabel label = new JLabel(new ImageIcon(cookimg));
		cookiepanel.setLayout(new BorderLayout());
		cookiepanel.add(label, BorderLayout.WEST);
		/**************************** J SETUP ******************************/
		frame.add(bg1);
		frame.add(bg2);
		frame.add(mainPanel);
		frame.add(ccta, BorderLayout.NORTH);
		frame.add(cookiepanel);
		frame.setSize(JFRAME_LENGTH, JFRAME_HEIGHT);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		mainPanel.setBounds(frame.getBounds());
		mainPanel.setOpaque(false);
		mainPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				checkClick(e);
			}
		});
		/**************************** RUN GAME *****************************/
		runGame();
		/************************** END OF MAIN ****************************/
	}

	public static void runGame() throws IOException {
		while (true) {
			/************* BASIC UPDATES *************/
			framecount++;
			timepassed++;
			updateText(ccta);
			/********* COOKIE UPGRADE CHECKS *********/
			if (cumulative_score > THRESHOLD1) {
				if (!passedTH1) {
					upgradeCookie();
					update();
					passedTH1 = true;
				}
			}
			if (cumulative_score > THRESHOLD2) {
				if (!passedTH2) {
					upgradeCookie();
					update();
					passedTH2 = true;
				}
			}
			/**************************************/
		}
	}

	public static void updateText(JTextArea ta) {
		ta.setText("Cookies: " + score + "\t\t\t\t\t  Time Wasted: " + calculateTime());
		frame.add(ta);
	}

	public static void updateImg(BufferedImage bi) {
		JLabel newimglabel = new JLabel(new ImageIcon(bi));
		cookiepanel.removeAll();
		cookiepanel.add(newimglabel, BorderLayout.WEST);
	}

	public static String calculateTime() {
		double secondspassed = (timepassed) / 230;
		if (secondspassed > 60 && secondspassed < 3600) {
			return String.valueOf(round(secondspassed / 60, 2)) + " minutes";
		} else if (secondspassed > 3600) {
			return String.valueOf(round(secondspassed / 3600, 2)) + " hours lol";
		} else {
			return String.valueOf(round(secondspassed, 2)) + " seconds";
		}

	}

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	public static void checkClick(MouseEvent e) {
		if ((e.getX() < 512) && (Math.abs(e.getY() - vertcent) < 256)) {
			cookieClicked();
		} else if (true) { // implement stuff l8r

		}
	}

	public static void cookieClicked() {
		score += score_multiplier;
		cumulative_score += score_multiplier;
		updateText(ccta);
	}

	public static void upgradeCookie() throws IOException {
		cooklvint++;
		cooklvstr = String.valueOf(cooklvint);
		if (cooklvint < 3) {
			BufferedImage bi = ImageIO
					.read(new File("D:\\Everything else\\Eclipse\\TheGame\\src\\pics\\Cookie" + cooklvstr + ".jpg"));
			updateImg(bi);
		}
	}

	public static void update() {
		frame.setSize(JFRAME_LENGTH, JFRAME_HEIGHT);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
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
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
