import java.io.IOException;
import java.net.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Main main = new Main();

        int[] startOctet = {192,168,0,0};
        int[] endOctet = {192,168, 8, 15};

        main.walkIpRange(startOctet, endOctet);

    }

    public void walkIpRange(int[] startOctet, int[] endOctet) throws IOException {

        for(int i = startOctet[0]; i <= 255; i++) {

            if(i == endOctet[0] + 1) {
                break;
            }

            for(int j = startOctet[1]; j <= 255; j++) {

                if(j == endOctet[1] + 1 && i == endOctet[0]) {
                    break;
                }

                for(int k = startOctet[2]; k <= 255; k++) {

                    if(k == endOctet[2] + 1 && j == endOctet[1]) {
                        break;
                    }

                    for(int l = startOctet[3]; l <= 255; l++) {

                        if(l == endOctet[3] + 1 && k == endOctet[2]) {
                            break;
                        }

                        String address = String.format("%d.%d.%d.%d", i, j, k, l);
                        InetAddress inetAddress = InetAddress.getByName(address);
                        System.out.printf("%s reachable: %s\n", address, inetAddress.isReachable(5));

                        if(l == 1 && !inetAddress.isReachable(5)) {
                            break;
                        }
                    }
                }
            }
        }

    }
}