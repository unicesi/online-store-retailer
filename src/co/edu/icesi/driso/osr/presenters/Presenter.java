package co.edu.icesi.driso.osr.presenters;

/**
 * This is a parent interface, intended to wrap the necessary methods to handle
 * events from the view. Specific interfaces (specific presenters) must extends
 * this interface.
 * 
 * @author migueljimenez
 *
 * @param <T>	The type of element being presented
 */
public interface Presenter {
	
	/**
	 * The presenter needs to perform initializations (e.g., RPC calls)
	 */
	public void init();

}
