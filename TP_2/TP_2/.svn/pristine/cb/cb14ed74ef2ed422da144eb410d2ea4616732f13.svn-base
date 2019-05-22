package rsa.quad;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class NodeTrie<T extends HasPoint> extends Trie<T> {
	Map<Trie.Quadrant, Trie<T>> tries;

	protected NodeTrie(double topLeftX, double topLeftY, double bottomRightX, double bottomRightY) {
		super(topLeftX, topLeftY, bottomRightX, bottomRightY);
		tries = new HashMap<Trie.Quadrant, Trie<T>>();

		double centerX = (topLeftX + bottomRightX) / 2;
		double centerY = (topLeftY + bottomRightY) / 2;

		tries.put(Trie.Quadrant.NW, new LeafTrie<>(topLeftX, topLeftY, centerX, centerY));
		tries.put(Trie.Quadrant.NE, new LeafTrie<>(centerX, topLeftY, bottomRightX, centerY));
		tries.put(Trie.Quadrant.SW, new LeafTrie<>(topLeftX, centerY, centerX, bottomRightY));
		tries.put(Trie.Quadrant.SE, new LeafTrie<>(centerX, centerY, bottomRightX, bottomRightY));

		// TODO Auto-generated constructor stub
	}

	@Override
	public void collectAll(Set<T> points) {
		for (Trie<T> aux : tries.values()) {
			aux.collectAll(points);
		}

	}

	@Override
	public void collectNear(double x, double y, double radius, Set<T> points) {

		if (overlap(x, y, radius)) {
			for (Trie<T> aux : tries.values()) {
				aux.collectNear(x, y, radius, points);
			}
		}

	}

	@Override
	public void delete(T point) {
		// TODO Auto-generated method stub

		Trie.Quadrant quad = quadrant(point);

		if (quad != null) {
			tries.get(quad).delete(point);
		}
		
	}

	@Override
	public T find(T point) {
		Trie.Quadrant quad = quadrant(point);

		if (quad != null) {
			return tries.get(quad).find(point);
		}
		
		return null;
	}

	@Override
	public Trie<T> insert(T point) {
		Trie.Quadrant quad = quadrant(point);

		if (quad != null) {
			Trie<T> t = tries.get(quad).insert(point);
			tries.replace(quad, t);
		}

		return this;
	}

	@Override
	public Trie<T> insertReplace(T point) {
		Trie.Quadrant quad = quadrant(point);

		if (quad != null) {
			tries.replace(quad, tries.get(quad).insertReplace(point));
		}
		return this;
	}

	private Trie.Quadrant quadrant(T point) {
		double mpx = (this.getBottomRightX() + this.getTopLeftX()) / 2;
		double mpy = (this.getBottomRightY() + this.getTopLeftY()) / 2;

		double lx = this.getTopLeftX(); // left x
		double rx = this.getBottomRightX(); // right x

		double ty = this.getTopLeftY(); // top y
		double by = this.getBottomRightY(); // bottom y

		if (point.getX() >= lx && point.getX() <= rx && point.getY() >= by && point.getY() <= ty) {

			if ((point.getX() <= mpx) && (point.getY() >= mpy)) {
				return Trie.Quadrant.NW;
			} else if ((point.getX() >= mpx) && (point.getY() >= mpy)) {

				return Trie.Quadrant.NE;
			} else if ((point.getX() <= mpx) && (point.getY() >= by)) {

				return Trie.Quadrant.SW;
			} else {

				return Trie.Quadrant.SE;
			}

		}

		return null;
	}

	@Override
	public String toString() {
		return "NodeTrie: [tries=" + tries + "]";
	}

	
	
}
