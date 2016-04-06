import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.In;
import java.util.ArrayList;

public class KdTree {
    private Node root;
    private int N;
    // construct an empty set of points
    public KdTree() {
        root = null;
    }
    private static class Node {
       private boolean isOddLevel;
       private Point2D p;      // the point
       private RectHV rect;    // the axis-aligned r corresponding to this node
       private Node lb;        // the left/bottom subtree
       private Node rt;        // the right/top subtree
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return N;
    }

    public void insert(Point2D p) {
        if (p == null) throw new NullPointerException();
        RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
        root = insert(root, p, rect, false);
    }

    private Node insert(Node head, Point2D p, RectHV rect, boolean isOddLevel) {
        if (head == null) {
            Node x = new Node();
            x.p = p;
            x.isOddLevel = isOddLevel;
            x.rect = rect;
            N++;
            return x;
        }

        if (!head.p.equals(p) && !isOddLevel) {
            boolean left = p.x() < head.p.x();
            if (left) {
                RectHV r = new RectHV(head.rect.xmin(), head.rect.ymin(), head.p.x(), head.rect.ymax());
                head.lb = insert(head.lb, p, r, !isOddLevel);
            } else {
                RectHV r = new RectHV(head.p.x(), head.rect.ymin(), head.rect.xmax(), head.rect.ymax());
                head.rt = insert(head.rt, p, r, !isOddLevel);
            }
        } else if (!head.p.equals(p) && isOddLevel) {
            boolean bottom = p.y() < head.p.y();
            if (bottom) {
                RectHV r = new RectHV(head.rect.xmin(), head.rect.ymin(), head.rect.xmax(), head.p.y());
                head.lb = insert(head.lb, p, r, !isOddLevel);
            } else {
                RectHV r = new RectHV(head.rect.xmin(), head.p.y(), head.rect.xmax(), head.rect.ymax());
                head.rt = insert(head.rt, p, r, !isOddLevel);
            }
        }
        return head;
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException();
        return contains(root, p);
    }

    // does head contains key
    private boolean contains(Node head, Point2D key) {
        if (head == null) return false;

        if (head.p.equals(key)) {
            return true;
        }
        if (!head.isOddLevel) {
            boolean left = key.x() < head.p.x();
            if (left) {
                return contains(head.lb, key);
            } else {
                return contains(head.rt, key);
            }
        } else {
            boolean bottom = key.y() < head.p.y();
            if (bottom) {
                return contains(head.lb, key);
            } else {
                return contains(head.rt, key);
            }
        }
    }

    // draw all points to standard draw
    public void draw() {
        draw(root);
    }
    private void draw(Node head) {
        if (head != null) {
            if (!head.isOddLevel) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.setPenRadius(0.005);
                StdDraw.line(head.p.x(), head.rect.ymin(), head.p.x(), head.rect.ymax());
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.setPenRadius(0.01);
                head.p.draw();
            } else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.setPenRadius(0.005);
                StdDraw.line(head.rect.xmin(), head.p.y(), head.rect.xmax(), head.p.y());
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.setPenRadius(0.01);
                head.p.draw();
            }
            draw(head.lb);
            draw(head.rt);
        }
    }
    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NullPointerException();
        ArrayList<Point2D> inRangeArr = new ArrayList<>();
        rangeSearch(root, rect, inRangeArr);
        return inRangeArr;
    }

    private void rangeSearch(Node head, RectHV range, ArrayList<Point2D> inRangeArr) {
        if (head == null) return;
        if (range.contains(head.p))         inRangeArr.add(head.p);
        if (head.lb != null && range.intersects(head.lb.rect)) rangeSearch(head.lb, range, inRangeArr);
        if (head.rt != null && range.intersects(head.rt.rect)) rangeSearch(head.rt, range, inRangeArr);
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D query) {
        if (query == null) throw new NullPointerException();
        if (root == null) return null;

        Point2D champion = root.p;
        champion = nearestSearch(query, root, champion);

        return champion;
    }

    private Point2D nearestSearch(Point2D query, Node head, Point2D champion) {
        if (query.distanceSquaredTo(head.p) < query.distanceSquaredTo(champion)) {
            champion = head.p;
        }
        if (head.lb != null && head.lb.rect.distanceSquaredTo(query) < query.distanceSquaredTo(champion)) {
            champion = nearestSearch(query, head.lb, champion);
        }
        if (head.rt != null && head.rt.rect.distanceSquaredTo(query) < query.distanceSquaredTo(champion)) {
            champion = nearestSearch(query, head.rt, champion);
        }
        return champion;
    }

    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);

        StdDraw.show(0);

        // initialize the two data structures with point from standard input
        KdTree kdtree = new KdTree();

        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }
        kdtree.draw();
        StdDraw.show(50);

    }
}