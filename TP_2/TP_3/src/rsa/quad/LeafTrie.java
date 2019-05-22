package rsa.quad;

import java.util.HashSet;
import java.util.Set;

public class LeafTrie<T extends HasPoint> extends Trie<T> {

	private HashSet<T> leafPoints;

	public LeafTrie(double topLeftX, double topLeftY, double bottomRightX, double bottomRightY) {

		super(topLeftX, topLeftY, bottomRightX, bottomRightY);
		this.leafPoints = new HashSet<T>();
	}

	// Collect all points in this node and its descendants in given set
	@Override
	void collectAll(Set<T> nodes) {
		for (T aux : leafPoints) {
			nodes.add(aux);
		}

	}

	// Collect points at a distance smaller or equal to radius from (x,y) and place
	// them in given list
	@Override
	void collectNear(double x, double y, double radius, Set<T> nodes) {
		
		for (T aux : leafPoints) {
			if( Math.sqrt( ((aux.getX()-x)*(aux.getX()-x))+((aux.getY()-y)*(aux.getY()-y))) <=radius) {
				nodes.add(aux);
			}
		}
	}

	// Delete given point
	@Override
	void delete(T point) {
		leafPoints.remove(point);
	}

	// Find a recorded point with the same coordinates of given point
	@Override
	T find(T point) {

		if (leafPoints.remove(point)) {
			leafPoints.add(point);
			return point;
		}

		return null;
	}

	// Insert given point
	@Override
	Trie<T> insert(T point) {

		if (leafPoints.add(point)) {
			if (this.leafPoints.size() > Trie.getCapacity()) {
				NodeTrie<T> nodeTrie = new NodeTrie<T>(topLeftX, topLeftY, bottomRightX, bottomRightY);

				for (T p : leafPoints) {
					nodeTrie.insert(p);
				}
				
				return nodeTrie;
			}
		}

		return this;
	}

	// Insert given point, replacing existing points in same location
	@Override
	Trie<T> insertReplace(T point) {
		for(T l: this.leafPoints) {
			if(point.getX() == l.getX() && point.getY() == l.getY()){
				this.leafPoints.remove(l);
				
			}
		}
		
		
		return insert(point);
	}

	@Override
	public String toString() {
		return "LeafTrie: [leafPoints=" + leafPoints + "]";
	}

	

}
