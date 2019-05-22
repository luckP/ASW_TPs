package rsa.quad;

import java.util.HashSet;
import java.util.Set;

public class PointQuadtree<T extends HasPoint> {
	private Trie<T> trie;

	public PointQuadtree(double topLeftX, double topLeftY, double bottomRightX, double bottomRightY) {
		this.trie = new LeafTrie<T>(topLeftX, topLeftY, bottomRightX, bottomRightY);
	}

	// Delete given point from QuadTree, if it exists there
	void delete(T point) {
		if (point.getX() < trie.getTopLeftX() || point.getX() > trie.getBottomRightX()
				|| point.getY() > trie.getTopLeftY() || point.getY() < trie.getBottomRightY())
			throw new PointOutOfBoundException();
		
		this.trie.delete(point);
	}

	// Find a recorded point with the same coordinates of given point
	T find(T point) {
		if (point.getX() < trie.getTopLeftX() || point.getX() > trie.getBottomRightX()
				|| point.getY() > trie.getTopLeftY() || point.getY() < trie.getBottomRightY())
			throw new PointOutOfBoundException();
		return this.trie.find(point);
	}

	// Returns a set of points at a distance smaller or equal to radius from point
	// with given coordinates.
	public Set<T> findNear(double x, double y, double radius) {
		Set<T> points = new HashSet<T>();
		this.trie.collectNear(x, y, radius, points);
		return points;
	}

	// A set with all points in the QuadTree
	Set<T> getAll() {
		Set<T> points = new HashSet<T>();
		this.trie.collectAll(points);
		return points;
	}

	// Insert given point in the QuadTree
	public void insert(T point) throws PointOutOfBoundException {
		if (point.getX() < trie.getTopLeftX() || point.getX() > trie.getBottomRightX()
				|| point.getY() > trie.getTopLeftY() || point.getY() < trie.getBottomRightY())
			throw new PointOutOfBoundException();

		this.trie = this.trie.insert(point);
	}

	// Insert point, replacing existing point in the same position
	void insertReplace(T point) throws PointOutOfBoundException {
		if (point.getX() < trie.getTopLeftX() || point.getX() > trie.getBottomRightX()
				|| point.getY() > trie.getTopLeftY() || point.getY() < trie.getBottomRightY())
			throw new PointOutOfBoundException();

		this.trie = this.trie.insertReplace(point);
	}
	
	
	@Override
	public String toString() {
		return trie.toString();
	}
}
