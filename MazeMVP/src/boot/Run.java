package boot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;

import model.MyModel;
import presenter.Presenter;
import view.MazeWindow;
import view.MyView;
import view.View;

public class Run {

	public static void main(String[] args) {


		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);

		HashMap <String, View> userInterfaces = new HashMap<String,View>();
		userInterfaces.put("CLI", new MyView(in,out));
		userInterfaces.put("GUI", new MazeWindow());
		
		MyModel model = new MyModel();
		String userInterface = model.getProperties().getUserInterface();
		View view = userInterfaces.get(userInterface);

		
		Presenter presenter = new Presenter(model, view);
		model.addObserver(presenter);
		view.addObserver(presenter);
				
		view.start();
	}
}
