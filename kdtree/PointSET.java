import java.util.TreeSet;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.RectHV;

public class PointSET {
    TreeSet<Point2D> pointSet;

    // construct an empty set of points
    public PointSET() {
        pointSet = new TreeSet<>();
    }

    public boolean isEmpty() {
        return pointSet.isEmpty();
    }

    public int size() {
        return pointSet.size();
    }

    public void insert(Point2D p) {
        if (p == null) throw new NullPointerException();
        pointSet.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException();
        return pointSet.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        StdDraw.setPenRadius(0.02);
        StdDraw.setPenColor(StdDraw.BLUE);
        for (Point2D point : pointSet) {
            point.draw();
        }
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NullPointerException();
        TreeSet<Point2D> inRangeSet = new TreeSet<>();
        for (Point2D point : pointSet) {
            if (point.x() > rect.xmin() && point.x() < rect.xmax()
                && point.y() > rect.ymin() && point.y() < rect.ymax()) {
                inRangeSet.add(point);
            }
        }
        return inRangeSet;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new NullPointerException();
        Point2D champion = pointSet.first();
        for (Point2D point : pointSet) {
            if (point.distanceTo(p) < champion.distanceTo(p)) {
                champion = point;
            }
        }
        System.out.println("query: " + p);
        System.out.println(champion);
        return champion;
    }

    public static void main(String[] args) {
        RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
        StdDraw.show(0);
        PointSET brute = new PointSET();
        while (true) {
            if (StdDraw.mousePressed()) {
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();
                StdOut.printf("%8.6f %8.6f\n", x, y);
                Point2D p = new Point2D(x, y);
                if (rect.contains(p)) {
                    StdOut.printf("%8.6f %8.6f\n", x, y);
                    brute.insert(p);
                    StdDraw.clear();
                    brute.draw();
                }
            }
            StdDraw.show(50);
        }

    }
}