package boot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import model.MyModel;
import presenter.Presenter;
import view.MyView;

public class Run {

	public static void main(String[] args) {
//		Display display = new Display ();
//		Shell shell = new Shell (display);
//		shell.setText("Snir Balgali");
//		shell.setSize(300, 200);
//		shell.open();
//		while(!shell.isDisposed()){ // while window isn't closed
//			
//			   // 1. read events, put then in a queue.
//			   // 2. dispatch the assigned listener
//			   if(!display.readAndDispatch()){ 	// if the queue is empty
//			      display.sleep(); 			// sleep until an event occurs 
//			   }
//			
//			} // shell is disposed
//
//			display.dispose(); // dispose OS components
//		
//		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
				
		MazeWindow view = new MazeWindow();
		MyModel model = new MyModel();
		
		Presenter presenter = new Presenter(model, view);
		model.addObserver(presenter);
		view.addObserver(presenter);
				
		view.start();
	}

}
