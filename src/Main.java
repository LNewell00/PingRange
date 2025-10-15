import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        System.out.print("Numbers of Threads: ");
        int threadCount = Integer.parseInt(new Scanner(System.in).nextLine());

        int rangeSize = 256 / threadCount;

        Thread[] t = new Thread[threadCount];
        myThread[] tasks = new myThread[threadCount];

        int i = 0;
        while( i < threadCount ) {
            int[] start = {i * rangeSize, 0, 0, 0};
            int[] end = {((i + 1) * rangeSize) - 1, 255, 255, 255};

            tasks[i] = new myThread(start, end);

            t[i] = new Thread(tasks[i]);
            t[i].start();
            i++;
        }

        for(Thread thread : t) {
            thread.join();
        }

        for(myThread task : tasks) {
            for(String host : task.reachableHosts) {
                System.out.println(host);
            }
        }

    }

}
class myThread implements Runnable{

    int[] start;
    int[] end;

    ArrayList<String> reachableHosts = new ArrayList<>();

    public myThread(int[] start, int[] end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {

        for(int i = start[3]; i <= end[3]; i++) {
            for(int j = start[2]; j <= end[2]; j++) {
                for(int k = start[1]; k <= end[1]; k++) {
                    for(int l = start[0]; l <= end[0]; l++) {
                        String address = i + "." + j + "." + k + "." + l;
                        try {
                            sendPingRequest(address);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
        System.out.println("FINISHED");

    }

    public void sendPingRequest(String ipAddress)
            throws UnknownHostException, IOException
    {
        InetAddress geek = InetAddress.getByName(ipAddress);
//        System.out.println("Sending Ping Request to " + ipAddress);
        if (geek.isReachable(2000)) {
            reachableHosts.add(ipAddress);
            System.out.println("Host is reachable");
        }
    }

}