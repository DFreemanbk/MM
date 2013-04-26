package my.packet;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;

public class GraphicsMain extends JFrame {

	public GraphicsMain() {
		super("Нахождение кратчайшего пути");
		setSize(1024, 768);
		setVisible(true);
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D gr2d = (Graphics2D) g;
		gr2d.setBackground(Color.green);
		// Рисуем многоугольник (треуголник или звезда
		// частный случай многоугольника)
		BasicStroke с = new BasicStroke(1); // толщина линии 3 многоугольника
		gr2d.setStroke(с);
		gr2d.setPaint(Color.GRAY);
		for (int i = 0; i < Global.plist.size(); i++) {
			gr2d.drawPolygon(Global.plist.get(i));
			gr2d.fillPolygon(Global.plist.get(i));
		}

	}

	public static void main(String args[]) {
		xmlparse xml = new xmlparse();
		xml.startXMLParse();
		GraphicsMain app = new GraphicsMain();
	}
}