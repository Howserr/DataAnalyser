package distsys.p2p.DHT;

import java.io.*;
import java.util.ArrayList;

public class ResultProcessor {

    ArrayList<PutOperationResult> putResults;
    ArrayList<GetOperationResult> getResults;

    public ResultProcessor() {
        this.putResults = new ArrayList<>();
        this.getResults = new ArrayList<>();
    }

    void loadFromFile(String fileName, int replicationCount, int swarmSize) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;

        while ((line = reader.readLine()) != null) {
            String split[] = line.split(",");

            if (split.length == 2) {
                long time = Long.parseLong(split[1]);
                PutOperationResult put = new PutOperationResult(swarmSize, replicationCount, time);
                putResults.add(put);
            }
            if (split.length == 4) {
                long time = Long.parseLong(split[1]);
                boolean success = Boolean.parseBoolean(split[2]);
                int numberOfFindNodes = Integer.parseInt(split[3]);
                GetOperationResult get = new GetOperationResult(swarmSize, replicationCount, time, success, numberOfFindNodes);
                getResults.add(get);
            }
        }
    }

    void printResults(String fileName, String putOrGet) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(fileName);

        if (putOrGet == "put") {
            for (PutOperationResult result : putResults) {
                result.print(writer);
            }
        } else {
            for (GetOperationResult result : getResults) {
                result.print(writer);
            }
        }
        writer.close();
    }

    void printSuccessRatio(String fileName) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(fileName);
        for (GetOperationResult result : getResults) {
            result.printSuccessRatio(writer);
        }
        writer.close();
    }
    
    abstract class OperationResult {
        int swarmSize;
        int replicationCount;
        long timeTaken;

        void print(PrintWriter writer) {
            String toWrite = swarmSize + "," + timeTaken;
            writer.println(toWrite);
        }
    }


    class PutOperationResult extends OperationResult {
        public PutOperationResult(int swarmSize, int replicationCount, long timeTaken) {
            this.swarmSize = swarmSize;
            this.replicationCount = replicationCount;
            this.timeTaken = timeTaken;
        }
    }

    class GetOperationResult extends OperationResult {
        int swarmSize;
        int replicationCount;
        long timeTaken;
        boolean wasSuccessful;
        int numberOfFindNodes;

        public GetOperationResult(int swarmSize, int replicationCount, long timeTaken, boolean wasSuccessful, int numberOfFindNodes) {
            this.swarmSize = swarmSize;
            this.replicationCount = replicationCount;
            this.timeTaken = timeTaken;
            this.wasSuccessful = wasSuccessful;
            this.numberOfFindNodes = numberOfFindNodes;
        }

        @Override
        void print(PrintWriter writer) {
            if (wasSuccessful) {
                String toWrite = swarmSize + "," + timeTaken + "," + numberOfFindNodes;
                writer.println(toWrite);
            }
        }

        void printSuccessRatio(PrintWriter writer) {
            if (!wasSuccessful) {
                String toWrite = swarmSize + "," + timeTaken + "," + numberOfFindNodes;
                writer.println(toWrite);
            }
        }
    }
}
