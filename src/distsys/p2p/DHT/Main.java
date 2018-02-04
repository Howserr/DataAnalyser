package distsys.p2p.DHT;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        ResultProcessor processor = new ResultProcessor();
        int replicationCount = 1;
        try {
//            processor.loadFromFile("speedtest-1-100.txt", replicationCount, 100);
//            processor.loadFromFile("speedtest-1-1000.txt", replicationCount, 1000);
//            processor.loadFromFile("speedtest-1-5000.txt", replicationCount, 5000);
//            processor.loadFromFile("speedtest-1-10000.txt", replicationCount, 10000);
//            processor.loadFromFile("speedtest-1-15000.txt", replicationCount, 15000);
            processor.loadFromFile("speedtest-20-20000.txt", replicationCount, 20000);

            //processor.printResults("combined-20replica-put.txt", "put");
            processor.printResults("combined-20replica-get.txt", "get");

            //processor.printSuccessRatio("successratio-1replica.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
