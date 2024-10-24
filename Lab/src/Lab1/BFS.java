package Lab1;

import java.util.LinkedList;
import java.util.Queue;

public class BFS {
	public void bfsUsingQueue(Node initial, int goal) {
		Queue<Node> queue = new LinkedList<Node>();
		initial.visited = true;
		queue.add(initial);

		while (!queue.isEmpty()) {
			Node nodeCurrent = queue.poll();
			if (nodeCurrent.state == goal) {
				String s = "";
				while (nodeCurrent != initial) {
					s = nodeCurrent.state + " " + s;
					nodeCurrent = nodeCurrent.parent;
				}
				System.out.println(initial.state + " " + s);
				return;
			}

			for (Node neighbour : nodeCurrent.getNeigbours()) {
				if (!neighbour.visited) {
					neighbour.visited = true;
					neighbour.parent = nodeCurrent;
					queue.add(neighbour);
				}
			}
		}
		System.out.println("Khong tim thay goal");
	}
}
