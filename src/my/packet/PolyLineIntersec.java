package my.packet;

import java.awt.Polygon;
import java.awt.geom.Line2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Set;

public class PolyLineIntersec {
	public static Set<Point2D> getIntersections(final Polygon poly,
			final Line2D.Double line) throws Exception {

		final PathIterator polyIt = poly.getPathIterator(null); // Getting an
																// iterator
																// along the
																// polygon path
		final double[] coords = new double[6]; // Double array with length 6
												// needed by iterator
		final double[] firstCoords = new double[2]; // First point (needed for
													// closing polygon path)
		final double[] lastCoords = new double[2]; // Previously visited point
		final Set<Point2D> intersections = new HashSet<Point2D>(); // List to
																	// hold
																	// found
																	// intersections
		polyIt.currentSegment(firstCoords); // Getting the first coordinate pair
		lastCoords[0] = firstCoords[0]; // Priming the previous coordinate pair
		lastCoords[1] = firstCoords[1];
		polyIt.next();
		double lx2 = 0,ly2 = 0;
		if(line.getX1()>line.getX2())
			lx2 = 0.1;
		else if(line.getX1()<line.getX2())
			lx2 = -0.1;
		if(line.getY1()>line.getY2())
			lx2 = 0.1;
		else if(line.getY1()<line.getY2())
			ly2 = -0.1;
		while (!polyIt.isDone()) {
			final int type = polyIt.currentSegment(coords);
			switch (type) {
			case PathIterator.SEG_LINETO: {
				final Line2D.Double currentLine = new Line2D.Double(
						lastCoords[0], lastCoords[1], coords[0], coords[1]);
				if ( line.getX1() != currentLine.getX1() && line.getY1() != currentLine.getY1() &&
						line.getX1() != currentLine.getX2() && line.getY1() != currentLine.getY2() &&
						line.getX2() != currentLine.getX1() && line.getY2() != currentLine.getY1() &&
						line.getX2() != currentLine.getX2() && line.getY2() != currentLine.getY2() ) {
					if (currentLine.intersectsLine(line))
						intersections.add(getIntersection(currentLine, line));
				}
				
				lastCoords[0] = coords[0];
				lastCoords[1] = coords[1];
				break;
			}
			case PathIterator.SEG_CLOSE: {
				final Line2D.Double currentLine = new Line2D.Double(coords[0],
						coords[1], firstCoords[0], firstCoords[1]);
				if (line.getX1() != currentLine.getX1() && line.getY1() != currentLine.getY1() &&
						line.getX1() != currentLine.getX2() && line.getY1() != currentLine.getY2() &&
						line.getX2() != currentLine.getX1() && line.getY2() != currentLine.getY1() &&
						line.getX2() != currentLine.getX2() && line.getY2() != currentLine.getY2() ) {
					if (currentLine.intersectsLine(line))
						intersections.add(getIntersection(currentLine, line));
				}
				
				break;
			}
			default: {
				throw new Exception("Unsupported PathIterator segment type.");
			}
			}
			polyIt.next();
		}
		return intersections;

	}

	public static Point2D getIntersection(final Line2D.Double line1,
			final Line2D.Double line2) {

		final double x1, y1, x2, y2, x3, y3, x4, y4;
		x1 = line1.x1;
		y1 = line1.y1;
		x2 = line1.x2;
		y2 = line1.y2;
		x3 = line2.x1;
		y3 = line2.y1;
		x4 = line2.x2;
		y4 = line2.y2;
		final double x = ((x2 - x1) * (x3 * y4 - x4 * y3) - (x4 - x3)
				* (x1 * y2 - x2 * y1))
				/ ((x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4));
		final double y = ((y3 - y4) * (x1 * y2 - x2 * y1) - (y1 - y2)
				* (x3 * y4 - x4 * y3))
				/ ((x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4));

		return new Point2D.Double(x, y);

	}
}
