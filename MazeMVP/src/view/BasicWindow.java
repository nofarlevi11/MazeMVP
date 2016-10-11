package view;

import java.util.Observable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public abstract class BasicWindow extends Observable implements Runnable {

	protected Display display;
	protected Shell shell;
	
	
	protected abstract void initWidgets();
	
	@Override
	public void run() {
		display = new Display();  // our display
		shell = new Shell(display); // our window

		initWidgets();
		
		shell.open();
		
		//addZoomListener();
		
		// main event loop
		while(!shell.isDisposed()){ // while window isn't closed
		
		   // 1. read events, put then in a queue.
		   // 2. dispatch the assigned listener
		   if(!display.readAndDispatch()){ 	// if the queue is empty
		      display.sleep(); 			// sleep until an event occurs 
		   }
		
		} // shell is disposed

		display.dispose(); // dispose OS components
	}

//	private void addZoomListener(){
//		shell.addMouseWheelListener(new MouseWheelListener() {
//			
//			@Override
//			public void mouseScrolled(MouseEvent e) {
//				int wheelCount = e.count;
//				if ((e.stateMask & SWT.CONTROL) == SWT.CONTROL) {
//					if ((wheelCount < 0 && shell.getSize().x > 200 && shell.getSize().y > 200)
//							|| (wheelCount > 0 && shell.getSize().x < 2000 && shell.getSize().y < 2000)) {
//						shell.setSize(shell.getSize().x + wheelCount, shell.getSize().y + wheelCount);
//					}
//				}
//				
//			}
//		});
//	}
}