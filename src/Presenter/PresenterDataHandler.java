package Presenter;

import Modules.Handler;
import View.JDialogDataHandler;

import javax.swing.*;
import java.nio.FloatBuffer;

public class PresenterDataHandler {

    private MainPresenter mainPresenter ;
    private Handler handler ;
    private JDialogDataHandler view ;

    public PresenterDataHandler(){
    }

    public PresenterDataHandler(Handler handler) {
        this.handler = handler;
    }

    public void init(MainPresenter mainPresenter){
        this.mainPresenter  = mainPresenter ;
        view = new JDialogDataHandler(this);
        this.mainPresenter.setViewEnable(false);
        loadData() ;
    }

    private void loadData(){
        view.setName(handler.getName()) ;
        view.setStates(handler.getState());
    }

    public void ableMainWindow(){
        mainPresenter.setViewEnable(true);
    }
    public void changeSettings(){
        try {
           // view.validateFields();
            String name = view.getName();
            String state = view.getState() ;
            handler.setName(name);
            handler.setState(state);
            mainPresenter.establishStateName(name + " - "+ state);
            ableMainWindow();
            view.dispose() ;
        }catch (Exception e ){
            view.showMessage(e.getMessage()) ;
        }
    }
}
