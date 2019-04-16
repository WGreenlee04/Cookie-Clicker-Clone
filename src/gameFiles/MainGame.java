package gameFiles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class MainGame extends JFrame implements MouseListener {

	// FINAL PIXEL LENGTHS (CONSTANTS)
	static final int JFRAME_HEIGHT = 1080;
	static final int JFRAME_LENGTH = 1920;
	static final int vertcent = JFRAME_HEIGHT / 2;
	static final int horizcent = JFRAME_LENGTH / 2;

	// GAME TOOLS
	private static Color baseColor = new Color(132, 86, 60);
	private static Font baseFont = new Font("Comic Sans MS", Font.BOLD, 40);
	private static int framecount;
	private static int sec = 0;
	static double score = 0;
	static int cumulative_score = 0;
	static int score_multiplier = 1;
	private static String cooklvstr = "";
	private static int cooklvint = 0;
	private static boolean passedTH1;
	private static boolean passedTH2;
	private static boolean passedTH3;
	private final static int THRESHOLD1 = 500;
	private final static int THRESHOLD2 = 2000;
	private final static int THRESHOLD3 = 10000;
	private static double timepassed = 0;
	private static double cps = 0;
	// SHOP TOOLS
	private static final int INFLATION_RATE = 10; // percent
	private static double cursorCost = 6;
	private static int dispCC = 6;
	private static int cursorCount = 0;
	private static int gmaCost = 50;
	private static int gmaCount = 0;
	// J STUFF
	private static JFrame frame = new JFrame("Cookie Clicker");
	private static JTextArea ccta = new JTextArea("Cookies: " + score);
	private static JTextArea shopta = new JTextArea("");
	private static JPanel cookiepanel = new JPanel();
	private static JPanel mainPanel = new JPanel();
	private static JPanel shoppanel = new JPanel();
	private static JTextArea costta = new JTextArea("Cost: " + gmaCost);

	public MainGame() {
		framecount = 0;
		passedTH1 = false;
		passedTH2 = false;
	}

	public static void main(String[] args) throws IOException {
		/********************** INSTANTIATE ********************************/
		frame.setLayout(new BorderLayout());
		ccta.setFont(baseFont);
		shopta.setFont(baseFont);
		shopta.setBounds(0, JFRAME_HEIGHT - 128, JFRAME_LENGTH, 50);
		JLabel bg1 = new JLabel();
		bg1.setOpaque(true);
		bg1.setBackground(baseColor);
		bg1.setLayout(null);
		bg1.setBounds(0, 56, JFRAME_LENGTH - 512, vertcent - 400);
		JLabel bg2 = new JLabel();
		bg2.setOpaque(true);
		bg2.setBackground(baseColor);
		bg2.setLayout(null);
		bg2.setBounds(0, vertcent + 156, JFRAME_LENGTH - 512, JFRAME_HEIGHT - vertcent - 256);
		/**************************** ADD IMAGES ***************************/
		BufferedImage cookimg = ImageIO.read(new File("D:\\Everything else\\Eclipse\\TheGame\\src\\pics\\Cookie.jpg")); // Jack
																														// home:
																														// D:\Everything
																														// else\Eclipse\TheGame\src\pics\Cookie.jpg
		JLabel label = new JLabel(new ImageIcon(cookimg));
		cookiepanel.setLayout(new BorderLayout());
		cookiepanel.add(label, BorderLayout.WEST);
		BufferedImage cursorimg = ImageIO
				.read(new File("D:\\Everything else\\Eclipse\\TheGame\\src\\pics\\Cursor.jpg")); // Jack
																									// home:
																									// D:\Everything
																									// else\Eclipse\TheGame\src\pics\Cursor.jpg
		JLabel cursorlabel = new JLabel(new ImageIcon(cursorimg));
		BufferedImage gmaimg = ImageIO.read(new File("D:\\Everything else\\Eclipse\\TheGame\\src\\pics\\Gma.jpg")); // Jack
																													// home:
																													// D:\Everything
																													// else\Eclipse\TheGame\src\pics\Gma.jpg
		JLabel gmalabel = new JLabel(new ImageIcon(gmaimg));
		shoppanel.add(cursorlabel, BorderLayout.EAST);
		shoppanel.add(gmalabel, BorderLayout.EAST);
		shoppanel.add(costta, BorderLayout.EAST);
		/**************************** J SETUP ******************************/
		frame.add(bg1);
		frame.add(bg2);
		frame.add(mainPanel);
		frame.add(ccta, BorderLayout.NORTH);
		frame.add(shopta);
		frame.add(cookiepanel);
		frame.add(shoppanel, BorderLayout.EAST);
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
			updateShop();
			cps();
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
			if (cumulative_score > THRESHOLD3) {
				if (!passedTH3) {
					upgradeCookie();
					update();
					passedTH3 = true;
				}
			}
			/**************************************/
		}
	}

	/***************** UPDATE DURING GAME ******************/

	public static void updateText(JTextArea ta) {
		ta.setText(
				"Cookies: " + round(score, 0) + "     CPS: " + round(cps, 2) + "     Time Wasted: " + calculateTime());
		frame.add(ta);
	}

	public static void updateImg(BufferedImage bi) {
		JLabel newimglabel = new JLabel(new ImageIcon(bi));
		cookiepanel.removeAll();
		cookiepanel.add(newimglabel, BorderLayout.WEST);
	}

	public static void cps() {
		score += cps / 11111;
	}

	/********************** SHOP FUNCTIONS ******************/
	public static void purchase(String item) {
		if (item.equals("Grandma")) { // GRANDMA
			if (score >= gmaCost) {
				gmaCount++;
				score -= gmaCost;
				gmaCost += gmaCost / INFLATION_RATE;
				cps += 1;
			} else {
				NoMoney nm = new NoMoney(gmaCost, (int) score);
			}
		} else if (item.equals("Cursor")) { // CURSOR
			if (score >= dispCC) {
				cursorCount++;
				score -= dispCC;
				cursorCost += cursorCost / INFLATION_RATE;
				dispCC = (int) cursorCost;
				cps += 0.1;
			} else {
				NoMoney nm = new NoMoney(dispCC, (int) score);
			}
		}
	}

	public static void updateShop() {
		shopta.setText("Cursors: " + cursorCount + " Grandmas: " + gmaCount);
		costta.setText("Cost: " + dispCC + "\nCost: " + gmaCost);
	}

	/********************* TIME FUNCTIONS *******************/
	public static String calculateTime() {
		double secondspassed = timepassed / 11111;
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

	/****************** UPON CLICK FUNCS ********************/

	public static void checkClick(MouseEvent e) {
		if ((e.getX() < 512) && (Math.abs(e.getY() - vertcent) < 256)) {
			cookieClicked();
		} else if (e.getX() > JFRAME_LENGTH - 150 && e.getY() > 40 && e.getY() < 40 + 130) {
			purchase("Grandma");
		} else if (e.getX() > JFRAME_LENGTH - 278 && e.getY() > 40 && e.getY() < 40 + 130) {
			purchase("Cursor");
		}
	}

	public static void cookieClicked() {
		score += score_multiplier;
		cumulative_score += score_multiplier;
		updateText(ccta);
	}

	/******************** COOKIE & UPDATE *******************/

	public static void upgradeCookie() throws IOException {
		cooklvint++;
		cooklvstr = String.valueOf(cooklvint);
		if (cooklvint < 4) {
			BufferedImage bi = ImageIO.read(new File("H:\\git\\TheGame\\src\\pictures\\Cookie" + cooklvstr + ".png"));
			updateImg(bi);
		}
	}

	public static void update() {
		frame.setSize(JFRAME_LENGTH, JFRAME_HEIGHT);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	/******** MOUSE LISTENER REQ METHODS *******/
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
	/***************** END **********************/

}
