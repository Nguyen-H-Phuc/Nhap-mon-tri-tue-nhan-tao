package Lab2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class DFS {
    public List<Node> dfsUsingStack(Node initial, int n) {
        Stack<Node> stack = new Stack<>();
        Set<Node> visited = new HashSet<>();
        Map<Node, Node> parentMap = new HashMap<>();
        List<Node> solutions = new ArrayList<>(); // 
        stack.add(initial);

        while (!stack.isEmpty()) {
            Node node = stack.pop();

            if (visited.contains(node)) {
                continue;
            }

            visited.add(node);
           
            if (node.state.size() == n) {
                solutions.add(node); 
                continue; 
            }
            
            for (Node neighbour : node.getNeighbours()) {
                if (!visited.contains(neighbour)) {
                    stack.push(neighbour);
                    parentMap.put(neighbour, node);  
                }
            }
        }

        if (solutions.isEmpty()) {
            System.out.println("Goal does not exist!");
        } else {
            System.out.println("Found " + solutions.size() + " solutions.");
        }

        return solutions; 
    }
}
