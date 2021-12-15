package com.tomasajt.karesz;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public abstract class AbstractLevel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	public int[][] levelData;
	private BufferedImage[] images = new BufferedImage[4];
	private Karesz karesz;
	private Color[] colors = { Color.white, Color.gray, Color.black, Color.red, Color.green, Color.yellow };
	private int timeStep = 100;
	private boolean isRunning = false;
	private int levelWidth;
	private int levelHeight;
	private JButton button = new JButton("Start");
	public JLabel labelBlack = new JLabel();
	public JLabel labelRed = new JLabel();
	public JLabel labelGreen = new JLabel();
	public JLabel labelYellow = new JLabel();
	public JLabel labelTime = new JLabel();
	private BufferedImage im = null;

	public static final int empty = 0;
	public static final int wall = 1;
	public static final int black = 2;
	public static final int red = 3;
	public static final int green = 4;
	public static final int yellow = 5;

	public static final int north = 0;
	public static final int east = 1;
	public static final int south = 2;
	public static final int west = 3;

	public static final int right = 1;
	public static final int left = -1;

	public AbstractLevel(int levelWidth, int levelHeight) {
		this.setLayout(null);
		try {
			im = ImageIO.read(new File("C:\\Users\\TomaSajt\\Desktop\\Source\\bruh.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.levelWidth = levelWidth;
		this.levelHeight = levelHeight;
		this.levelData = new int[levelWidth][levelHeight];
		reload();
		this.setPreferredSize(new Dimension(levelWidth * getTileWidth() + 1 + 14 + 14,
				levelHeight * getCurrImage().getHeight() + 1 + 40 + 14 + 1));
		Insets insets = this.getInsets();
		button.setBounds(14 + insets.left, 2 + insets.top, 121, 36);
		labelBlack.setBounds(14 + 121 + 14 + insets.left, 1 + insets.top, 300, 20);
		labelRed.setBounds(14 + 121 + 14 + insets.left, 19 + insets.top, 300, 20);
		labelGreen.setBounds(14 + 121 + 14 + 100 + insets.left, 1 + insets.top, 300, 20);
		labelYellow.setBounds(14 + 121 + 14 + 100 + insets.left, 19 + insets.top, 300, 20);
		labelTime.setBounds(14 + 121 + 14 + 100 + 100 + insets.left, 1 + insets.top, 300, 20);
		button.addActionListener(this);
		this.add(button);
		this.add(labelBlack);
		this.add(labelRed);
		this.add(labelGreen);
		this.add(labelYellow);
		this.add(labelTime);
		karesz.updateLabels();
	}

	abstract void doInstructions();

	protected void Lépj() {
		sleep(timeStep);
		karesz.Step();
		repaint();
	}

	protected void Fordulj_jobbra() {
		sleep(timeStep);
		karesz.Turn(right);
		repaint();
	}

	protected void Fordulj_balra() {
		Fordulj(left);
	}

	protected void Fordulj(int irány) {
		sleep(timeStep);
		karesz.Turn(irány);
		repaint();
	}

	protected void Vegyél_fel_egy_kavicsot() {
		karesz.pickUpStone();
	}

	protected void Tegyél_le_egy_kavicsot() {
		Tegyél_le_egy_kavicsot(black);
	}

	protected void Tegyél_le_egy_kavicsot(int color) {
		sleep(timeStep);
		karesz.placeDownStone(color);
		repaint();
	}

	protected boolean Északra_néz() {
		return Merre_néz() == north;
	}

	protected boolean Délre_néz() {
		return Merre_néz() == north;
	}

	protected boolean Keletre_néz() {
		return Merre_néz() == north;
	}

	protected boolean Nyugatra_néz() {
		return Merre_néz() == north;
	}

	protected int Merre_néz() {
		return karesz.getDirection();
	}

	protected boolean Van_e_itt_kavics() {
		return karesz.isThereAStoneHere();
	}

	protected int Mi_van_alattam() {
		return karesz.whatIsUnderMe();
	}

	protected boolean Van_e_előtem_fal() {
		return karesz.whatIsInFrontOfMe() == 1;
	}

	protected boolean Kilépek_e_a_pályáról() {
		return karesz.whatIsInFrontOfMe() == -1;
	}

	private static void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void reload() {
		try {
			this.images[0] = ImageIO.read(new File("res/Karesz0.png"));
			this.images[1] = ImageIO.read(new File("res/Karesz1.png"));
			this.images[2] = ImageIO.read(new File("res/Karesz2.png"));
			this.images[3] = ImageIO.read(new File("res/Karesz3.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		//this.karesz = new Karesz(this, levelWidth/2, levelHeight/2);
		this.karesz = new Karesz(this, 0, 0);
		this.levelData = new int[levelWidth][levelHeight];
	}

	private BufferedImage getCurrImage() {
		return images[karesz.getDirection()];
	}

	private int getTileWidth() {
		return getCurrImage().getWidth();
	}

	private int getTileHeight() {
		return getCurrImage().getHeight();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.translate(14, 40);
		int tw = getTileWidth();
		int th = getTileHeight();
		g2d.setColor(colors[empty]);
		g2d.fillRect(0, 0, levelWidth * tw + 1, levelHeight * th + 1);
		for (int j = 0; j < levelHeight; j++) {
			for (int i = 0; i < levelWidth; i++) {
				if (levelData[i][j] >= 1 || levelData[i][j] <= 5) {
					g2d.setColor(colors[levelData[i][j]]);
					if (levelData[i][j] == wall)
						g2d.fillRect(i * tw, j * th, tw, th);
					else if (levelData[i][j] > wall) // kő
						g2d.drawImage(im, i * tw + 2, j * th + 2, tw - 4, th - 4, null);
				}
			}
		}
		g2d.drawImage(getCurrImage(), karesz.getX() * tw, karesz.getY() * th, null);
		g2d.setColor(Color.gray);
		for (int i = 1; i < levelWidth; i++) {
			g2d.drawLine(0, th * i, levelWidth * tw, th * i);
		}
		for (int i = 1; i < levelHeight; i++) {
			g2d.drawLine(tw * i, 0, tw * i, levelHeight * th);
		}
		g2d.translate(-14, -14);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "Start" && !isRunning) {
			Thread thread = new Thread() {

				public void run() {
					isRunning = true;
					doInstructions();
					isRunning = false;
					showMessage("Vége");
				}
			};
			thread.start();
		}
	}

	public void showMessage(String message) {
		JOptionPane.showMessageDialog(this, message, "", JOptionPane.DEFAULT_OPTION);
	}
	
	public int getLevelWidth() {
		return levelWidth;
	}
	public int getLevelHeight() {
		return levelHeight;
	}
}
