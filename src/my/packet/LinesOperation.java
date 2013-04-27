package my.packet;

import java.awt.Polygon;
import java.awt.geom.Line2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Set;

public class LinesOperation {
	public static void startFromPPoints()
	{
		double[] coords = new double[6];
		for(int i = 0; i < Global.plist.size(); i++)
		{
			PathIterator itPoly = Global.plist.get(i).getPathIterator(null);
			while(!itPoly.isDone())
			{
				itPoly.currentSegment(coords);
				makeLines(coords[0],coords[1]);
				itPoly.next();
			}
			
		}
	}
	private static void makeLines(double x, double y)
	{
		double[] coords = new double[6];
		for(int i = 0; i < Global.plist.size(); i++)
		{
			PathIterator itPoly = Global.plist.get(i).getPathIterator(null);
			while(!itPoly.isDone())
			{
				itPoly.currentSegment(coords);
				Global.llist.add(new Line2D.Double(x, y, coords[0],coords[1]));
				itPoly.next();
			}
		}
	}
	public static void deleteBadLines()
	{
		for(int i = 0; i < Global.plist.size(); i++)
		{
			for(int j = (Global.llist.size()-1); j >= 0; j--)
			{
				System.out.println("J = " + j);
				Set<Point2D> SPoints = null;
				try {
					SPoints = PolyLineIntersec.getIntersections(Global.plist.get(i), Global.llist.get(j));
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(!SPoints.isEmpty())
				{
					System.out.println("Удаляем " + j);
					Global.llist.remove(j);
				}
			}
		}
	}
}
