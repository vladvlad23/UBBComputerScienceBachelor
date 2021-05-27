package ro.ubbcluj;

import mpi.MPI;

import javax.imageio.ImageIO;
import javax.print.attribute.standard.JobHoldUntil;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

public class Main {

    public static void main(String[] args) {
        Instant start = Instant.now();

        MPI.Init(args);
        if(MPI.COMM_WORLD.Rank() == 0) {
            //Master process
            try {
                HoughTransformation houghTransformation = new HoughTransformation();
                houghTransformation.applyHoughTransformation(ImageIO.read(new File("smaller.png")), 5, 15, 100, 0.4);
                for(int i=1;i<MPI.COMM_WORLD.Size();i++){
                    Object[] endSignal = new String[1];
                    endSignal[0] = "endThis";
                    MPI.COMM_WORLD.Send(endSignal,0,1,MPI.OBJECT,i,i);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            SlaveOperation slaveOperation = new SlaveOperation();
            slaveOperation.startReceivingCommands();
        }
        MPI.Finalize();

        Instant end = Instant.now();
        System.out.println(Duration.between(start,end).toSeconds());
    }
}
