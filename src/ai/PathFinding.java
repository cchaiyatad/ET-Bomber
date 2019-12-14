package ai;

import java.util.ArrayList;
import java.util.List;

import controller.GameController;
import player.PlayerBase;

public class PathFinding {

	private GameController gameController;

	public PathFinding(GameController gameController) {
		this.gameController = gameController;
	}

	public int[] findPath(int startX, int startY, int destinationX, int destinationY, PlayerBase player)
			throws CannotReachDestinateException {
		if (startX == destinationX && startY == destinationY) {
			return new int[]{ startX, startY };
		}
		Node destinateNode = new Node(startX, startY, null, null, null);
		Node startNode = new Node(destinationX, destinationY, null, null, destinateNode);
		List<Node> nodeList = new ArrayList<Node>();
		List<Node> checkList = new ArrayList<Node>();
		nodeList.add(startNode);
		checkList.add(startNode);
		Node currentNode = null;
		while (true) {
			nodeSort(nodeList);
			if (nodeList.isEmpty()) {
				throw new CannotReachDestinateException();
			}
			currentNode = nodeList.get(0);
			nodeList.remove(0);
			if (currentNode.isFinish()) {
				break;
			}
			for (int i = 0; i < 2; i++) {
				for (int j = -1; j <= 1; j += 2) {
					int newX = currentNode.getXY()[0] + (i * j);
					int newY = currentNode.getXY()[1] + ((1 - i) * j);
					if (!(newX < 1 || newX > 13 || newY < 1 || newY > 13)
							&& gameController.checkMove(newX * 50, newY * 50, player)) {
						Node newNode = new Node(newX, newY, currentNode, startNode, destinateNode);
						if (!checkContain(newNode, checkList)) {
							nodeList.add(newNode);
							checkList.add(newNode);
						}
					}
				}
			}
		}
		return currentNode.getParent().getXY();
	}

	private void nodeSort(List<Node> nodeList) {
		if (nodeList.size() == 1) {
			return;
		}
		for (int i = 0; i < nodeList.size() - 1; i++) {
			for (int j = 0; j < nodeList.size() - 1; j++) {
				if (nodeList.get(j).getF() > nodeList.get(j + 1).getF()) {
					Node temp = nodeList.get(j);
					nodeList.set(j, nodeList.get(j + 1));
					nodeList.set(j + 1, temp);
				}
			}
		}
	}

	private boolean checkContain(Node node, List<Node> nodeList) {
		for (Node nodes : nodeList) {
			if (nodes.equals(node)) {
				return true;
			}
		}
		return false;
	}
	
	private class Node {
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
				return this.x == ((Node) obj).x && this.y == ((Node) obj).y;
			}
			return false;
		}

	}
}
