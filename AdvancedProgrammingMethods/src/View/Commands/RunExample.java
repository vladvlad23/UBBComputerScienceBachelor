package View.Commands;

import Controller.ControllerApp;
import Domain.Exceptions.MyException;

public class RunExample extends Command {
    private ControllerApp ctr;

    public RunExample(String key, String desc, ControllerApp ctr) {
        super(key, desc);
        this.ctr = ctr;
    }

    @Override
    public void execute() {
        try {
            ctr.oldAllStepEvaluation();
        } catch (MyException e) {
            e.printStackTrace();
        }
    } //here you must treat the exceptions that can not be solved in the controller
}