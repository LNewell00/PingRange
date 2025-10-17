import java.io.IOException;
import java.net.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Main main = new Main();

        int[] startOctet = {192,168,0,0};
        int[] endOctet = {192,168, 8, 15};
    }

    private int[] currentAddress = {0,0,0,0};

    public synchronized void setCurrentAddress(int[] currentAddress) {
        this.currentAddress = currentAddress;
    }

    public synchronized void setCurrentAddress(String currentAddress) {
        String[] data = currentAddress.split("\\.");
        this.currentAddress[0] = Integer.parseInt(data[0]);
        this.currentAddress[1] = Integer.parseInt(data[1]);
        this.currentAddress[2] = Integer.parseInt(data[2]);
        this.currentAddress[3] = Integer.parseInt(data[3]);
    }

    public synchronized String getNextAddress() {
        String next = String.format("%d.%d.%d.%d", currentAddress[0], currentAddress[1], currentAddress[2], currentAddress[3]);

        currentAddress[3]++;
        for (int i = 3; i >= 0; i--) {
            if (currentAddress[i] > 255) {
                currentAddress[i] = 0;
                if (i > 0)
                    currentAddress[i - 1]++;
            }
        }

        if (currentAddress[0] > 255)
            return null;


        return next;
    }
}