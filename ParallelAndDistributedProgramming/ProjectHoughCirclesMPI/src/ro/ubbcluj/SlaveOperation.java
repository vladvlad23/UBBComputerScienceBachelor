package ro.ubbcluj;

import mpi.MPI;
import ro.ubbcluj.Tasks.BlurringSubTask;
import ro.ubbcluj.Tasks.EdgeFilteringSubTask;
import ro.ubbcluj.Tasks.FilteringNonMaximumSubTask;
import ro.ubbcluj.Tasks.GradientSubTask;

import java.util.logging.Filter;

public class SlaveOperation {
    public void startReceivingCommands() {
        Object[] receivedCommands = new Object[1];
        System.out.println("Rank: " + MPI.COMM_WORLD.Rank() + " starts listening for commands");
        MPI.COMM_WORLD.Recv(receivedCommands, 0, 1, MPI.OBJECT,0,MPI.COMM_WORLD.Rank());
        if(receivedCommands[0] instanceof BlurringSubTask){
            receivedCommands[0] = ((BlurringSubTask)receivedCommands[0]).run();
            MPI.COMM_WORLD.Send(receivedCommands,0,1,MPI.OBJECT,0,12);
        }
        else if(receivedCommands[0] instanceof GradientSubTask){
            receivedCommands = ((GradientSubTask)receivedCommands[0]).run();
            MPI.COMM_WORLD.Send(receivedCommands,0,2,MPI.OBJECT,0,12);
        }
        else if(receivedCommands[0] instanceof FilteringNonMaximumSubTask){
            receivedCommands = ((FilteringNonMaximumSubTask)receivedCommands[0]).run();
            MPI.COMM_WORLD.Send(receivedCommands,0,2,MPI.OBJECT,0,12);
        }
        else if(receivedCommands[0] instanceof EdgeFilteringSubTask){
            receivedCommands = ((EdgeFilteringSubTask)receivedCommands[0]).run();
            MPI.COMM_WORLD.Send(receivedCommands,0,1,MPI.OBJECT,0,12);
        }
        else if(receivedCommands[0] instanceof String){
            return;
        }
        startReceivingCommands();

    }
}
