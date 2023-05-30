package com.survival.game.allapotok;

import com.survival.game.JatekPanel;
import com.survival.game.grafika.SpriteSheet;
import com.survival.game.math.Vector2f;
import com.survival.game.tile.TileManager;
import com.survival.game.ui.JatekosUI;
import com.survival.game.util.Kamera;
import com.survival.game.util.JatekObjectHeap;
import com.survival.game.util.KeyHandler;
import com.survival.game.util.MouseHandler;
import com.survival.game.entitas.Ellenseg;
import com.survival.game.entitas.Jatekos;

import java.awt.Graphics2D;

public class PlayAllapot extends JatekAllapot {

	public Jatekos jatekos;
	private JatekObjectHeap jatekObject;
	private TileManager tm;
	private Kamera kam;
	private JatekosUI pui;

	public static Vector2f map;
	private double heapIdo;

	public PlayAllapot(JatekAllapotManager gsm, Kamera kam) {
		super(gsm);
		
		map = new Vector2f();
		Vector2f.setWorldVar(map.x, map.y);
		this.kam = kam;

		SpriteSheet tileset = new SpriteSheet("tile/overworldOP.png", 32, 32);

		tm = new TileManager(tileset, 100, kam);

		jatekObject = new JatekObjectHeap();
		jatekos = new Jatekos(kam, new SpriteSheet("entity/wizardPlayer.png", 64, 64), new Vector2f(0 + (JatekPanel.szelesseg / 2) - 32, 0 + (JatekPanel.magassag / 2) - 32), 64, tm);
		pui = new JatekosUI(jatekos);
		kam.target(jatekos);
	}

	public JatekObjectHeap getGameObjects() { return jatekObject; }
	public Vector2f getPlayerPos() { return jatekos.getPos(); }

	private boolean canBuildHeap(int offset, int si, double time) {

		if(jatekObject.size() > 3 && (heapIdo / si) + offset < (time / si)) {
			return true;
		}

		return false;
	}

	public void update(double time) {
		Vector2f.setWorldVar(map.x, map.y);

		if(!gsm.isStateActive(JatekAllapotManager.PAUSE)) {
			if(!gsm.isStateActive(JatekAllapotManager.EDIT)) {
				if(jatekos.getDeath()) {
					gsm.add(JatekAllapotManager.GAMEOVER);
					gsm.pop(JatekAllapotManager.PLAY);
				}

				for(int i = 0; i < jatekObject.size(); i++) {
					if(jatekObject.get(i).go instanceof Ellenseg) {
						Ellenseg ellenseg = ((Ellenseg) jatekObject.get(i).go);
						if(jatekos.getHitBounds().collides(ellenseg.getBounds())) {
							jatekos.setTargetEnemy(ellenseg);
						}

						if(ellenseg.getDeath()) {
							jatekObject.remove(ellenseg);
						} else {
							ellenseg.update(jatekos, time);
						}

						if(canBuildHeap(2500, 1000000, time)) {
							jatekObject.get(i).value = ellenseg.getBounds().distance(jatekos.getPos());
						}

						continue;
					}
				}

				if(canBuildHeap(3, 1000000000, time)) {
					heapIdo = System.nanoTime();
					jatekObject.buildHeap();
				}
				
				jatekos.update(time);
			}
			kam.update();
		}
	}

	public void input(MouseHandler mouse, KeyHandler key) {
		key.escape.tick();
		key.f1.tick();

		if(!gsm.isStateActive(JatekAllapotManager.PAUSE)) {
			if(kam.getTarget() == jatekos) {
				jatekos.input(mouse, key);
			}
			kam.input(mouse, key);

			if(key.f1.clicked) {
				if(gsm.isStateActive(JatekAllapotManager.EDIT)) {
					gsm.pop(JatekAllapotManager.EDIT);
					kam.target(jatekos);
				} else {
					gsm.add(JatekAllapotManager.EDIT);
					kam.target(null);
				}
			}

		} else if(gsm.isStateActive(JatekAllapotManager.EDIT)) {
			gsm.pop(JatekAllapotManager.EDIT);
			kam.target(jatekos);
		}

		if (key.escape.clicked) {
			if(gsm.isStateActive(JatekAllapotManager.PAUSE)) {
				gsm.pop(JatekAllapotManager.PAUSE);
			} else {
				gsm.add(JatekAllapotManager.PAUSE);
			}
		}
	}

	public void render(Graphics2D g) {
		tm.render(g);

		jatekos.render(g);
		for(int i = 0; i < jatekObject.size(); i++) {
			if(kam.getBounds().collides(jatekObject.get(i).getBounds())) {
				jatekObject.get(i).go.render(g);
			}
		}

		pui.render(g);

		kam.render(g);
	}
}
