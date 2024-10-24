package Lab2;

import java.util.List;

public class Queens {
    private int n;
    private List<Node> goals;

    public Queens(int n) {
        this.n = n;
    }

    public void dfs() {
        DFS dfs = new DFS();
        this.goals = dfs.dfsUsingStack(new Node(n), n);

        if (this.goals != null && !this.goals.isEmpty()) {
            System.out.println("Solutions found:");
            for (Node goal : goals) {
                System.out.println(goal.state);
            }
        } else {
            System.out.println("No solution found.");
        }
    }

    public static void main(String[] args) {
        Queens q = new Queens(4);
        q.dfs();
    }
}
