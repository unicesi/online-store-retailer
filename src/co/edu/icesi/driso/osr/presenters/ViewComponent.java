package co.edu.icesi.driso.osr.presenters;

public interface ViewComponent<T> {

	/**
	 * The presenter needs to introduce itself
	 * 
	 * @param presenter The presenter
	 */
	public void setPresenter(Presenter presenter);
	
	/**
	 * Encapsulates the events to be handled
	 */
	public void bindEvents();
	
	/**
	 * The view might be a cached one; the presenter needs to reset visual
	 * components
	 */
	public void reset();
	
	/**
	 * A wildcard to update data on the view
	 * 
	 * @param key	An identifier to recognize what object(s) to update
	 * @param data	The new object
	 */
	public void setData(String key, T data);
}
