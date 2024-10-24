package Lab3;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class LocalSearch {

    
    public int checkHorizontal(Node node) {
        int count = 0;
        for (int i = 0; i < node.state.size() -1; i++) {
            for (int j = i + 1; j < node.state.size(); j++) { // Bắt đầu từ i+1 để không trùng lặp
                if (node.state.get(i)== node.state.get(j)) {
                    count++;
                }
            }
        }
        return count;
    }


    public int checkDiagonal(Node node) {
        int count = 0;
        for (int i = 0; i < node.state.size() - 1; i++) {
            for (int j = i + 1; j < node.state.size(); j++) { // Bắt đầu từ i+1 để không trùng lặp
                if (Math.abs(node.state.get(i) - node.state.get(j)) == Math.abs(i - j)) {
                    count++;
                }
            }
        }
        return count;
    }


    public int heuristic(Node node) {
        return checkHorizontal(node) + checkDiagonal(node);
    }

  
    public int tryMovingOneQueen(Node node, int col, int newRow) {
        List<Integer> newState = new ArrayList<>(node.state);
        newState.set(col, newRow); 

        Node newNode = new Node(node.n, newState);
        return heuristic(newNode);
    }


    public SortedMap<Integer, Node> generateNeighbours(Node node) {
        SortedMap<Integer, Node> neighbours = new TreeMap<>();

        // Duyệt qua từng cột
        for (int col = 0; col < node.n; col++) {
            int currentRow = node.state.get(col); // Hàng hiện tại của quân hậu ở cột 'col'

            // Duyệt qua từng hàng để thử di chuyển quân hậu
            for (int newRow = 0; newRow < node.n; newRow++) {
                if (newRow != currentRow) { // Chỉ di chuyển nếu vị trí mới khác với hàng hiện tại
                    int heuristicValue = tryMovingOneQueen(node, col, newRow); // Sử dụng hàm để thử di chuyển

                    // Tạo node mới với trạng thái đã được cập nhật
                    List<Integer> newState = new ArrayList<>(node.state);
                    newState.set(col, newRow);
                    Node neighbour = new Node(node.n, newState);

                    // Giữ lại chỉ một node cho mỗi giá trị heuristic
                    if (!neighbours.containsKey(heuristicValue)) {
                        neighbours.put(heuristicValue, neighbour);
                    }
                }
            }
        }
        return neighbours; // Trả về danh sách các node lân cận
    }


    public void run() {
        Node initial = new Node(4); // Hoặc 4,5,6,7 tùy n
        if (heuristic(initial) == 0) { // Đã đạt trạng thái đích
            System.out.println("Goal found at initial state: " + initial.state);
            return;
        }

        System.out.println("Initial state is: " + initial.state);
        Node node = initial;
        SortedMap<Integer, Node> neighbours = generateNeighbours(node);
        Integer bestHeuristic = neighbours.firstKey();
        int previousHeuristic = heuristic(node); // Lưu lại heuristic trước đó

        while (bestHeuristic < previousHeuristic) { // Dừng nếu không cải thiện
            node = neighbours.get(bestHeuristic);
            neighbours = generateNeighbours(node);
            previousHeuristic = bestHeuristic;
            bestHeuristic = neighbours.firstKey();
        }

        if (heuristic(node) == 0) {
            System.out.println("Goal is: " + node.state);
        } else {
            System.out.println("Cannot find goal state! Best state is: " + node.state);
        }
    }

}
