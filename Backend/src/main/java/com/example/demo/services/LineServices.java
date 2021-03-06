package com.example.demo.services;

import com.example.demo.shapes.Point;
import org.springframework.stereotype.Component;

@Component
public class LineServices {

    public boolean checkPoint(Point p1, Point p2, Point click) {
        boolean isVertical = checkVerticalLine(p1, p2);
        double dist1 = Math.pow((p1.getX() - click.getX()), 2) + Math.pow((p1.getY() - click.getY()), 2);
        double dist2 = Math.pow((p2.getX() - click.getX()), 2) + Math.pow((p2.getY() - click.getY()), 2);
        double length = getLength(p1, p2);
        if (!isVertical) {
            double c = getC(p1, p2);
            double m = getM(p1, p2, c);
            double y = m * click.getX() + c;
            if (Math.sqrt(dist1) <= length && Math.sqrt(dist2) <= length && Math.abs(click.getY() - y) <= 20) {
                return true;
            }
        } else {
            if (Math.abs(p1.getX() - click.getX()) <= 5 && Math.sqrt(dist1) <= length && Math.sqrt(dist2) <= length) {
                return true;
            }
        }
        return false;
    }

    public double getC(Point p1, Point p2) {
        return (p1.getX() * p2.getY() - p1.getY() * p2.getX()) / (p1.getX() - p2.getX());
    }

    public double getM(Point p1, Point p2, double c) {
        return (p1.getY() - c) / p1.getX();
    }

    public double getLength(Point p1, Point p2) {
        return Math.sqrt((Math.pow((p1.getX() - p2.getX()), 2) + Math.pow((p1.getY() - p2.getY()), 2)));
    }

    public boolean checkVerticalLine(Point p1, Point p2) {
        return Math.abs(p1.getX() - p2.getX()) <= 5;

    }

    public Point afterMove(Point click, double m, Point p1, Point p2, double length) {
        boolean isVertical = checkVerticalLine(p1, p2);
        if (!isVertical) {
            double x1, x2, y1, y2, cDash, a, b;
            double c = click.getY() - m * click.getX();
            a = Math.pow(m, 2) + 1;
            b = 2 * m * c - 2 * m * click.getY() - 2 * click.getX();
            cDash = c * c - 2 * c * click.getY() - length * length + Math.pow(click.getY(), 2) + Math.pow(click.getX(), 2);
            double sqr00t = Math.sqrt(b * b - 4 * a * cDash);
            x1 = (-b + sqr00t) / (2 * a);
            x2 = (-b - sqr00t) / (2 * a);
            y1 = m * x1 + c;
            y2 = m * x2 + c;
            return changeP2(x1, x2, y1, y2, detectType(p1, p2));
        } else {
            if (p1.getX() <= p2.getX()) {
                p2.setX(click.getX() + length);
            } else {
                p2.setX(click.getX() - length);
            }
            p2.setY(click.getY());
            return p2;
        }

    }

    public int detectType(Point p1, Point p2) {
        if (p1.getY() <= p2.getY() && p1.getX() <= p2.getX()) {
            return 1;
        } else if (p1.getY() >= p2.getY() && p1.getX() <= p2.getX()) {
            return 2;
        } else if (p1.getY() >= p2.getY() && p1.getX() >= p2.getX()) {
            return 3;
        } else {
            return 4;
        }
    }

    public Point changeP2(double x1, double x2, double y1, double y2, int type) {
        switch (type) {
            case 1:
                return (new Point(Math.max(x1, x2), Math.max(y1, y2)));
            case 2:
                return (new Point(Math.max(x1, x2), Math.min(y1, y2)));
            case 3:
                return (new Point(Math.min(x1, x2), Math.min(y1, y2)));
            case 4:
                return (new Point(Math.min(x1, x2), Math.max(y1, y2)));
            default:
                return null;
        }
    }


}
