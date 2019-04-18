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
import javax.swing.JButton;
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
	static double cumulative_score = 0;
	static int score_multiplier = 1;
	private static String cooklvstr = "";
	private static int cooklvint = 0;
	private static boolean passedTH1;
	private static boolean passedTH2;
	private static boolean passedTH3;
	private final static int THRESHOLD1 = 5000;
	private final static int THRESHOLD2 = 250000;
	private final static int THRESHOLD3 = 1000000;
	private static double timepassed = 0;
	private static double cps = 0;
	// SHOP TOOLS
	private static final int INFLATION_RATE = 10; // percent

	private static int clickupgCost = 500;

	private static double cursorCost = 6;
	private static int dispCC = 6;
	private static int cursorCount = 0;
	private static int gmaCost = 50;
	private static int gmaCount = 0;
	private static int factCost = 4500;
	private static int factCount = 0;
	private static int bankCost = 20000;
	private static int bankCount = 0;
	private static int portalCost = 950000;
	private static int portalCount = 0;
	private static int cfracCost = 90000000;
	private static int cfracCount = 0;
	// J STUFF
	private static JButton savebutton = new JButton("Save Game");
	private static JButton loadbutton = new JButton("Load Game");
	private static JFrame frame = new JFrame("Cookie Clicker");
	private static JTextArea ccta = new JTextArea("Cookies: " + score);
	private static JTextArea shopta = new JTextArea("");
	private static JPanel cookiepanel = new JPanel();
	private static JLabel main = new JLabel();
	private static JPanel shoppanel = new JPanel();

	// SAVE GAME DATA
	private File save;

	public MainGame() {
		framecount = 0;
		passedTH1 = false;
		passedTH2 = false;
	}

	public static void main(String[] args) throws IOException {
		/********************** INSTANTIATE ********************************/
		frame.setLayout(new BorderLayout());
		ccta.setFont(baseFont);
		shopta.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
		shopta.setBounds(530, 140, 1200, 70);
		shopta.setBackground(new Color(192, 142, 116));
		ccta.setBounds(0, 787, JFRAME_LENGTH, 50);
		savebutton.setLayout(null);
		savebutton.setBounds(1359, 525, 240, 128);
		loadbutton.setLayout(null);
		loadbutton.setBounds(1359, 653, 240, 128);
		JLabel bg1 = new JLabel();
		bg1.setOpaque(true);
		bg1.setBackground(baseColor);
		bg1.setLayout(null);
		bg1.setBounds(0, 0, 512, 160);
		JLabel bg2 = new JLabel();
		bg2.setOpaque(true);
		bg2.setBackground(baseColor);
		bg2.setLayout(null);
		bg2.setBounds(0, vertcent + 100, JFRAME_LENGTH - 240, JFRAME_HEIGHT - vertcent - 256);
		JLabel bg3 = new JLabel();
		bg3.setOpaque(true);
		bg3.setBackground(baseColor);
		bg3.setLayout(null);
		bg3.setBounds(718, 210, JFRAME_LENGTH - 240, JFRAME_HEIGHT - 580);
		/**************************** ADD IMAGES ***************************/
		BufferedImage cookimg = ImageIO.read(new File("./src/pictures/Cookie.png"));
		JLabel label = new JLabel(new ImageIcon(cookimg));
		cookiepanel.setLayout(new BorderLayout());
		cookiepanel.add(label);
		// SHOP IMAGES
		BufferedImage cursorimg = ImageIO.read(new File("./src/pictures/Cursor.jpg"));
		JLabel cursorlabel = new JLabel(new ImageIcon(cursorimg));
		BufferedImage gmaimg = ImageIO.read(new File("./src/pictures/Gma.png"));
		JLabel gmalabel = new JLabel(new ImageIcon(gmaimg));
		BufferedImage facimg = ImageIO.read(new File("./src/pictures/Factory.png"));
		JLabel faclabel = new JLabel(new ImageIcon(facimg));
		BufferedImage bnkimg = ImageIO.read(new File("./src/pictures/Bank.png"));
		JLabel bnklabel = new JLabel(new ImageIcon(bnkimg));
		BufferedImage prtlimg = ImageIO.read(new File("./src/pictures/Portal.png"));
		JLabel prtllabel = new JLabel(new ImageIcon(prtlimg));
		BufferedImage cfracimg = ImageIO.read(new File("./src/pictures/CookieFractal.jpg"));
		JLabel cfraclabel = new JLabel(new ImageIcon(cfracimg));
		BufferedImage tempimg = ImageIO.read(new File("./src/pictures/temp.png"));
		JLabel templabel = new JLabel(new ImageIcon(tempimg));
		BufferedImage clickupgimg = ImageIO.read(new File("./src/pictures/ClickUpgrade.jpg"));
		JLabel clickupglabel = new JLabel(new ImageIcon(clickupgimg));
		shoppanel.add(cursorlabel, BorderLayout.EAST);
		shoppanel.add(gmalabel, BorderLayout.EAST);
		shoppanel.add(faclabel, BorderLayout.EAST);
		shoppanel.add(bnklabel, BorderLayout.EAST);
		shoppanel.add(prtllabel, BorderLayout.EAST);
		shoppanel.add(cfraclabel, BorderLayout.EAST);
		shoppanel.add(templabel, BorderLayout.EAST);
		shoppanel.add(clickupglabel, BorderLayout.EAST);
		shoppanel.setBackground(baseColor);

		/**************************** J SETUP ******************************/
		main.setLayout(null);
		main.setBounds(0, 0, 1920, 1080);
		main.setOpaque(false);
		frame.add(loadbutton, BorderLayout.SOUTH);
		frame.add(savebutton, BorderLayout.SOUTH);
		frame.add(main);
		frame.add(bg1);
		frame.add(bg2);
		frame.add(bg3);
		frame.setLayout(new BorderLayout());
		frame.add(ccta, BorderLayout.SOUTH);
		frame.add(shopta, BorderLayout.EAST);
		frame.add(cookiepanel, BorderLayout.WEST);
		frame.add(shoppanel, BorderLayout.EAST);
		frame.setSize(JFRAME_LENGTH, JFRAME_HEIGHT);
		frame.getContentPane().setBackground(baseColor);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		main.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				checkClick(e);
				// System.out.println(e);
			}
		});
		savebutton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				checkClick(e);
				// System.out.println(e);
			}
		});
		loadbutton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				checkClick(e);
				// System.out.println(e);
			}
		});
		/**************************** RUN GAME *****************************/
		runGame();
		/************************** END OF MAIN ****************************/
	}

	public static void runGame() throws IOException {
		for (int i = 0; i < 1; i--) {
			/************* BASIC UPDATES *************/
			if (i == 0) {
				update();
			}
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
		ta.setText("Cookies: " + (int) score + " CPS: " + round(cps, 2) + " CuS: " + (int) cumulative_score
				+ " Time Wasted: " + calculateTime());
		frame.add(ta, BorderLayout.SOUTH);
	}

	public static void updateImg(BufferedImage bi) {
		JLabel newimglabel = new JLabel(new ImageIcon(bi));
		cookiepanel.removeAll();
		cookiepanel.add(newimglabel, BorderLayout.WEST);
	}

	public static void cps() {
		score += cps / 11111;
		cumulative_score += cps / 11111;
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
		} else if (item.equals("Factory")) {
			if (score >= factCost) {
				factCount++;
				score -= factCost;
				factCost += factCost / INFLATION_RATE;
				cps += 10;
			} else {
				NoMoney nm = new NoMoney(factCost, (int) score);
			}
		} else if (item.equals("Bank")) {
			if (score >= bankCost) {
				bankCount++;
				score -= bankCost;
				bankCost += bankCost / INFLATION_RATE;
				cps += 75;
			}
		} else if (item.equals("Portal")) {
			if (score >= portalCost) {
				portalCount++;
				score -= portalCost;
				portalCost += portalCost / INFLATION_RATE;
				cps += 400;
			}
		} else if (item.equals("CookieFractal")) {
			if (score >= cfracCost) {
				cfracCount++;
				score -= cfracCost;
				cfracCost += cfracCost / INFLATION_RATE;
				cps += 5000;
			}
		} else if (item.equals("Click Upgrade")) {
			if (score >= clickupgCost) {
				score -= clickupgCost;
				score_multiplier *= 2;
			}
		}
	}

	public static void updateShop() {
		shopta.setText("Cost: " + dispCC + "     Cost: " + gmaCost + "   Cost: " + ((double) factCost) / 1000
				+ "k Cost: " + (bankCost / 1000) + "k  Cost: " + (portalCost / 1000) + "k Cost: "
				+ (cfracCost / 1000000) + "m\t  Cost: " + clickupgCost + "\nCursors: " + cursorCount + " Gmas:   "
				+ gmaCount + "  Facs: " + factCount + "     Banks: " + bankCount + "   Prtls: " + portalCount
				+ "    CFracs: " + cfracCount + "\t  Mult.: " + score_multiplier);
	}

	/********************* TIME FUNCTIONS *******************/
	public static String calculateTime() {
		double secondspassed = timepassed / 11111;
		if (secondspassed > 60 && secondspassed < 3600) {
			return String.valueOf(round(secondspassed / 60, 2)) + " mins";
		} else if (secondspassed > 3600) {
			return String.valueOf(round(secondspassed / 3600, 2)) + " hours lol";
		} else {
			return String.valueOf(round(secondspassed, 2)) + " secs";
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
		if (e.getSource().equals(savebutton)) {
			save();
		} else if (e.getSource().equals(loadbutton)) {
			load();
		} else {
			if (e.getX() > 34 && e.getX() < 500 && e.getY() > 228 && e.getY() < 702) {
				cookieClicked();
			} else if (e.getX() > 669 && e.getX() < 795 && e.getY() > 5 && e.getY() < 132) {
				purchase("Grandma");
			} else if (e.getX() > 536 && e.getX() < 662 && e.getY() > 6 && e.getY() < 130) {
				purchase("Cursor");
			} else if (e.getX() > 804 && e.getX() < 927 && e.getY() > 6 && e.getY() < 131) {
				purchase("Factory");
			} else if (e.getX() > 937 && e.getX() < 1060 && e.getY() > 5 && e.getY() < 131) {
				purchase("Bank");
			} else if (e.getX() > 1070 && e.getX() < 1194 && e.getY() > 5 && e.getY() < 131) {
				purchase("Portal");
			} else if (e.getX() > 1202 && e.getX() < 1326 && e.getY() > 5 && e.getY() < 131) {
				purchase("Cookie Fractal");
			} else if (e.getX() > 1468 && e.getX() < 1591 && e.getY() > 5 && e.getY() < 131) {
				purchase("Click Upgrade");
			} // MORE TO COME
		}
	}

	public static void cookieClicked() {
		score += score_multiplier;
		cumulative_score += score_multiplier;
		updateText(ccta);
	}

	public static void save() {
		System.out.println("SAVED!");
	}

	public static void load() {
		System.out.println("LOADED!");
	}

	/******************** COOKIE & UPDATE *******************/

	public static void upgradeCookie() throws IOException {
		cooklvint++;
		cooklvstr = String.valueOf(cooklvint);
		if (cooklvint < 4) {
			BufferedImage bi = ImageIO.read(new File("./src/pictures/Cookie" + cooklvstr + ".png"));
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
