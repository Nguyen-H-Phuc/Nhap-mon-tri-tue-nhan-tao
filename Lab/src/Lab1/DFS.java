package Lab1;

import java.util.Stack;

import java.util.Stack;

public class DFS {
    public void dfsUsingStack(Node initial, int goal) {
        Stack<Node> stack = new Stack<>();
        initial.visited = true;
        stack.add(initial);

        while (!stack.isEmpty()) {
            Node node = stack.pop();
            if (node.state == goal) {
                
                String s = "";
                while (node != initial) {
                    s = node.state + " " + s; 
                    node = node.parent;
                }
                System.out.println(initial.state + " " + s);
                return;  
            }


            for (Node neighbours : node.getNeigbours()) {
                if (!neighbours.visited) {
                    neighbours.visited = true;
                    neighbours.parent = node;
                    stack.push(neighbours);
                }
            }
        }
 
        System.out.println("Goal does not exist!");
    }
}
