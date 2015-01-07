package co.edu.icesi.driso.osr.presenters;

import co.edu.icesi.driso.osr.ui.components.SquaredProductSummary;

public class SquareProductPresenter implements Presenter {

	private final SquaredProductSummary viewComponent;
	
	public SquareProductPresenter(SquaredProductSummary viewComponent){
		this.viewComponent = viewComponent;
	}
	
	@Override
	public void init() {
		viewComponent.setPresenter(this);
		viewComponent.reset();
	}

}
