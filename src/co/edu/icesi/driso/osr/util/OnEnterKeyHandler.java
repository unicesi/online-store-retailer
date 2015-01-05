package co.edu.icesi.driso.osr.util;

import com.vaadin.event.FieldEvents;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.ui.TextField;

public abstract class OnEnterKeyHandler {

    final ShortcutListener enterShortCut = new ShortcutListener(
               "EnterOnTextAreaShorcut", ShortcutAction.KeyCode.ENTER, null) {
    	
				private static final long serialVersionUID = 1L;

				@Override
                   public void handleAction(Object sender, Object target) {
                       onEnterKeyPressed();
                   }
               };

    public void installOn(final TextField component){
       
    	component.addFocusListener(new FieldEvents.FocusListener() {
				private static final long serialVersionUID = 1L;

				@Override
                   public void focus(FieldEvents.FocusEvent event) {
                       component.addShortcutListener(enterShortCut);
                   }
               }
       );

       component.addBlurListener(new FieldEvents.BlurListener() {
				private static final long serialVersionUID = 1L;

				@Override
                   public void blur(FieldEvents.BlurEvent event) {
                       component.removeShortcutListener(enterShortCut);
                   }
               }
       );
    }

    public abstract void onEnterKeyPressed();

}