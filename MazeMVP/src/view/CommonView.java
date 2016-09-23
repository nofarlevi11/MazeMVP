package view;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Observable;
import java.util.Observer;


// TODO: Auto-generated Javadoc
/**
 * The Class CommonView.
 */
public abstract class CommonView extends Observable implements View, Observer {

	/** The in. */
	protected BufferedReader in;
	
	/** The out. */
	protected PrintWriter out;
	
	/** The cli. */
	protected CLI cli;
	
	/**
	 * Instantiates a new common view.
	 *
	 * @param in the in
	 * @param out the out
	 */
	public CommonView (BufferedReader in, PrintWriter out) {
		this.in = in;
		this.out=out;
		cli = new CLI (in, out);
		cli.addObserver(this); //adding the ciew to be an observer of the cli
	};

}
