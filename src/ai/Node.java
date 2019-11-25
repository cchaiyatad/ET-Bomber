package ai;

import java.util.ArrayList;
import java.util.List;

public class Node {
	private int x;
	private int y;
	private Node parentNode;
	private Node startNode;
	private Node destinate;

	public Node(int x, int y, Node parentNode, Node startNode, Node destinate) {
		this.x = x;
		this.y = y;
		this.parentNode = parentNode;
		this.startNode = startNode;
		this.destinate = destinate;
	}

	public int getF() {
		int g = Math.abs(startNode.x - x) + Math.abs(startNode.y - y);
		int h = Math.abs(destinate.x - x) + Math.abs(destinate.y - y);
		return g + h;
	}

	public boolean isFinish() {
		return x == destinate.x && y == destinate.y;
	}

	public int[] getXY() {
		int[] xy = { x, y };
		return xy;
	}
	
	public Node getParent() {
		return this.parentNode;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Node) {
//			System.out.println(this.x + " " + ((Node) obj).x+ " " + this.y + " " + ((Node) obj).y);
			return this.x == ((Node) obj).x && this.y == ((Node) obj).y;
		}
		return false;
	}

}
