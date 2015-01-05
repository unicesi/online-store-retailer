package co.edu.icesi.driso.osr.presenters;

public class GenericPresenter<T> implements Presenter {
	
	private final ViewComponent<T> viewComponent;
	private final String dataKey;
	private final T data;
	
	public GenericPresenter(ViewComponent<T> viewComponent, String dataKey, T data){
		this.viewComponent = viewComponent;
		this.dataKey = dataKey;
		this.data = data;
	}

	@Override
	public void init() {
		viewComponent.setData(dataKey, data);
	}

}
