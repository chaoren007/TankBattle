package com.tank.view;

import java.awt.Graphics;
import java.awt.Image;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JPanel;

import com.tank.entity.Bomb;
import com.tank.entity.Brick;
import com.tank.entity.Bullet;
import com.tank.entity.Element;
import com.tank.entity.Iron;
import com.tank.entity.RobotTank;
import com.tank.entity.Tank;
import com.tank.entity.Water;
import com.tank.map.Map;
import com.tank.view.controller.TankController;

public class Draw {
	/**
	 * 画出东西（包括坦克、障碍物。。）
	 * 
	 * @param g
	 *            Graphics
	 * @param Element
	 *            东西对象
	 * @param panel
	 *            被画的那个面板
	 */
	public void drawStuff(Graphics g, Element element, JPanel panel) {
		switch (element.getType()) {
		case Element.BRICK:
			g.drawImage(TankImage.stuffImg[Element.BRICK], element.getX() - 10, element.getY() - 10, 20, 20, panel);
			break;
		case Element.IRON:
			g.drawImage(TankImage.stuffImg[Element.IRON], element.getX() - 10, element.getY() - 10, 20, 20, panel);
			break;
		case Element.WATER:
			g.drawImage(TankImage.stuffImg[Element.WATER], element.getX() - 10, element.getY() - 10, 20, 20, panel);
			break;
		}

	}

	public void drawTank(Graphics g, Tank tank, JPanel panel) {
		switch (tank.getDirect()) { // 判断所朝的方向
		case Element.NORTH:
			this.drawNorth(g, tank, panel);
			break;
		case Element.SOUTH:
			this.drawSouth(g, tank, panel);
			break;
		case Element.WEST:
			this.drawWest(g, tank, panel);
			break;
		case Element.EAST:
			this.drawEast(g, tank, panel);
			break;
		}
	}

	/**
	 * 画出地图
	 * 
	 * @param g
	 *            Graphics
	 * @param map
	 *            地图对象
	 * @param panel
	 *            被画的那个面板
	 */
	public void drawMap(Graphics g, Map map, JPanel panel) {
		CopyOnWriteArrayList<Brick> bricks = map.getBricks();
		CopyOnWriteArrayList<Iron> irons = map.getIrons();
		CopyOnWriteArrayList<Water> waters = map.getWaters();
		for (int i = 0; i < bricks.size(); i++) {
			this.drawStuff(g, bricks.get(i), panel);
		}
		for (int i = 0; i < irons.size(); i++) {
			this.drawStuff(g, irons.get(i), panel);
		}
		for (int i = 0; i < waters.size(); i++) {
			this.drawStuff(g, waters.get(i), panel);
		}
	}

	/**
	 * 画出一个面朝北的坦克
	 * 
	 * @param g
	 *            Graphics
	 * @param tank
	 *            东西对象
	 * @param panel
	 *            被画的那个面板
	 */
	public void drawNorth(Graphics g, Tank tank, JPanel panel) {
		Image image;
		if (tank.getTankId().equals(TankController.tankId)) {
			image = TankImage.myTankImg[Element.NORTH];// 初始化图片
		} else {
			image = TankImage.enemyTankImg[Element.NORTH];// 初始化图片
		}
		g.drawImage(image, tank.getX() - 20, tank.getY() - 20, 40, 40, panel);
	}

	public void drawSouth(Graphics g, Tank tank, JPanel panel) {
		Image image;
		if (tank.getTankId().equals(TankController.tankId)) {
			image = TankImage.myTankImg[Element.SOUTH];// 初始化图片
		} else {
			image = TankImage.enemyTankImg[Element.SOUTH];// 初始化图片
		}
		g.drawImage(image, tank.getX() - 20, tank.getY() - 20, 40, 40, panel);
	}

	public void drawWest(Graphics g, Tank tank, JPanel panel) {
		Image image;
		if (tank.getTankId().equals(TankController.tankId)) {
			image = TankImage.myTankImg[Element.WEST];// 初始化图片
		} else {
			image = TankImage.enemyTankImg[Element.WEST];// 初始化图片
		}
		g.drawImage(image, tank.getX() - 20, tank.getY() - 20, 40, 40, panel);
	}

	public void drawEast(Graphics g, Tank tank, JPanel panel) {
		Image image;
		if (tank.getTankId().equals(TankController.tankId)) {
			image = TankImage.myTankImg[Element.EAST];// 初始化图片
		} else {
			image = TankImage.enemyTankImg[Element.EAST];// 初始化图片
		}
		g.drawImage(image, tank.getX() - 20, tank.getY() - 20, 40, 40, panel);
	}

	/**
	 * 画出玩家的坦克
	 * 
	 * @param g
	 * @param myTanks
	 * @param panel
	 */
	public void drawPlayerTank(Graphics g, ConcurrentHashMap<String, Tank> playerTanks, JPanel panel) {

		for (Tank tank : playerTanks.values()) {
			if (tank.isAlive() == true)
				this.drawTank(g, tank, panel); // 画出坦克
			// 画出坦克的子弹
			for (Bullet b : tank.getBullets().values()) {
				g.drawImage(TankImage.bullet, b.getX() - 2, b.getY() - 2, 4, 4, panel);
			}
		}
	}

	/**
	 * 画出机器坦克
	 * 
	 * @param g
	 * @param robots
	 * @param panel
	 */
	public void drawRobotTank(Graphics g, ConcurrentHashMap<String, RobotTank> robots, JPanel panel) {

		for (RobotTank robot : robots.values()) {
			if (robot.isAlive() == true)
				this.drawTank(g, robot, panel); // 画出坦克
			// 画出坦克的子弹
			for (Bullet b : robot.getBullets().values()) {
				g.drawImage(TankImage.bullet, b.getX() - 2, b.getY() - 2, 4, 4, panel);
			}
		}
	}

	public void drawBombs(Graphics g, CopyOnWriteArrayList<Bomb> bombs, JPanel panel) {

		for (int i = 0; i < bombs.size(); i++) {
			int l = bombs.get(i).getWidth();
			Bomb b = bombs.get(i); // 从炸弹容器中取出一颗炸弹
			boolean flag = g.drawImage(TankImage.bomb[4], b.getX() - l / 2, b.getY() - l / 2, l, l, panel);
			b.lifeDown(); // 生命随时间衰减
			if (b.getLifeValue() <= 0) { // 该炸弹死亡
				b.setAlive(false);
				TankPanel.resource.getBombs().remove(b);
			}

		}

	}

}
