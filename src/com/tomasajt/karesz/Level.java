package com.tomasajt.karesz;

import java.util.Random;

public class Level extends AbstractLevel {
	private static final long serialVersionUID = 1L;

	public Level(int levelWidth, int levelHeight) {
		super(levelWidth, levelHeight);
	}

	@Override
	void doInstructions() {
		Random r = new Random();
		while (true) {
			Fordulj_jobbra();
			for (int i = 0; i < 3; i++) {
				if (Van_e_itt_kavics()) {
					Vegyél_fel_egy_kavicsot();

				} else {
					Tegyél_le_egy_kavicsot(r.nextInt(4) + 2);
				}
				Lépj();
			}
			Fordulj_jobbra();
			if (Van_e_itt_kavics()) {
				Vegyél_fel_egy_kavicsot();

			} else {
				Tegyél_le_egy_kavicsot(r.nextInt(4) + 2);
			}
			Lépj();
			Fordulj_jobbra();
			if (Van_e_itt_kavics()) {
				Vegyél_fel_egy_kavicsot();

			} else {
				Tegyél_le_egy_kavicsot(r.nextInt(4) + 2);
			}
			Lépj();
			Fordulj_balra();
			if (Van_e_itt_kavics()) {
				Vegyél_fel_egy_kavicsot();

			} else {
				Tegyél_le_egy_kavicsot(r.nextInt(4) + 2);
			}
			Lépj();
			Fordulj_balra();
			if (Van_e_itt_kavics()) {
				Vegyél_fel_egy_kavicsot();

			} else {
				Tegyél_le_egy_kavicsot(r.nextInt(4) + 2);
			}
			Lépj();
			Fordulj_jobbra();
			if (Van_e_itt_kavics()) {
				Vegyél_fel_egy_kavicsot();

			} else {
				Tegyél_le_egy_kavicsot(r.nextInt(4) + 2);
			}
			Lépj();
		}
	}

}
