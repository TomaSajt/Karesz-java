package com.tomasajt.karesz;

import javax.swing.JFrame;

public class KareszMain extends JFrame {
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		new KareszMain();

	}

	public KareszMain() {
		super("Karesz");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.add(new Level(4, 4));
		this.pack();
		this.setVisible(true);
		this.setResizable(true);
	}

}
