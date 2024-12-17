package controller;

import model.Model;
import view.View;

public class Main {
	public static void main(String[] args) {
		Model model = new Model();
		View view = new View();
		Controller controller = new Controller(view, model);
	}
}
