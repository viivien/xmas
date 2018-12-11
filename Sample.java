import java.util.List;
import java.util.Arrays;
class Sample {
	private static class Vector {
	    private static String doubleToString(double d) {
	        return String.format("%.3f", d);
	    }
	    public final double x;
	    public final double y;
	    public static double COMPARISON_TOLERANCE = 0.0000001;
	    public Vector(Coord coord) {
	        this(coord.x, coord.y);
	    }
	    public Vector(double x, double y) {
	        this.x = x;
	        this.y = y;
	    }
	    public Vector(Vector other) {
	        this(other.x, other.y);
	    }
	    public Vector add(Vector other) {
	        return new Vector(x + other.x, y + other.y);
	    }
	    public Vector negate() {
	        return new Vector(-x, -y);
	    }
	    public Vector rotateInDegree(double degree){
	    	return rotateInRadian(Math.toRadians(degree));
	    }
	    public Vector rotateInRadian(double radians) {
	        final double length = length();
	        double angle = angleInRadian();
	        angle += radians;
	        final Vector result = new Vector(Math.cos(angle), Math.sin(angle));
	        return result.multiply(length);
	    }
	    public double angleInDegree() {
	        return Math.toDegrees(angleInRadian());
	    }
		private double angleInRadian() {
			return Math.atan2(y, x);
		}
	    public double dot(Vector other) {
	        return x * other.x + y * other.y;
	    }
	    @Override
	    public boolean equals(Object obj) {
	        if (this == obj) {
	            return true;
	        }
	        if (obj == null) {
	            return false;
	        }
	        if (getClass() != obj.getClass()) {
	            return false;
	        }
	        final Vector other = (Vector) obj;
	        if (Math.abs(x - other.x) > COMPARISON_TOLERANCE) {
	            return false;
	        }
	        if (Math.abs(y - other.y) > COMPARISON_TOLERANCE) {
	            return false;
	        }
	        return true;
	    }
	    @Override
	    public int hashCode() {
	        final int prime = 31;
	        int result = 1;
	        long temp;
	        temp = Double.doubleToLongBits(x);
	        result = prime * result + (int) (temp ^ (temp >>> 32));
	        temp = Double.doubleToLongBits(y);
	        result = prime * result + (int) (temp ^ (temp >>> 32));
	        return result;
	    }
	    public double length() {
	        return Math.sqrt(x * x + y * y);
	    }
	    public double length2() {
	        return x * x + y * y;
	    }
	    public Vector minus(Vector other) {
	        return new Vector(x - other.x, y - other.y);
	    }
	    public Vector multiply(double factor) {
	        return new Vector(x * factor, y * factor);
	    }
	    public Vector norm() {
	        final double length = length();
	        if (length>0)
	        	return new Vector(x / length, y / length);
	        return new Vector(0,0);
	    }
	    public Vector ortho() {
	        return new Vector(-y, x);
	    }
	    @Override
	    public String toString() {
	        return "[x=" + doubleToString(x) + ", y=" + doubleToString(y) + "]";
	    }
	}
	private static class Coord {
	    public final int x;
	    public final int y;
	    public Coord(int x, int y) {
	        this.x = x;
	        this.y = y;
	    }
	    public Coord(Vector v) {
	        this.x = (int) v.x;
	        this.y = (int) v.y;
	    }
	    public Coord add(Coord coord) {
	        return new Coord(x + coord.x, y + coord.y);
	    }
	    public double distance(Coord coord) {
	        return Math.sqrt(distanceSquare(coord));
	    }
	    public long distanceSquare(Coord coord) {
	        long dx = coord.x - x;
	        long dy = coord.y - y;
	        return dx * dx + dy * dy;
	    }
	    public Coord minus(Coord coord) {
	        return new Coord(x - coord.x, y - coord.y);
	    }
	    @Override
	    public boolean equals(Object obj) {
	        if (this == obj) {
	            return true;
	        }
	        if (obj == null) {
	            return false;
	        }
	        if (getClass() != obj.getClass()) {
	            return false;
	        }
	        final Coord other = (Coord) obj;
	        if (x != other.x) {
	            return false;
	        }
	        if (y != other.y) {
	            return false;
	        }
	        return true;
	    }
	    @Override
	    public int hashCode() {
	        final int prime = 31;
	        int result = 1;
	        result = prime * result + x;
	        result = prime * result + y;
	        return result;
	    }
	    @Override
	    public String toString() {
	        return "[x=" + x + ", y=" + y + "]";
	    }
	}
	public static void main(String[] args) {
		List<String> importString = Arrays.asList("to", "test", "imports");
		Coord coord = new Coord(1, 2);
		System.out.println(coord.toString());
		System.out.println(importString);
	}
}
