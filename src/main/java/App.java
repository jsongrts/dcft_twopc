import com.jsongrts.twopc.coordinator.Coordinator;
import com.jsongrts.twopc.participant.Participant;

public class App {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            usage();
        }
        else if (args[0].equalsIgnoreCase("participant")) {
            String[] a = _removeHead(args);
            Participant participant = new Participant(Integer.parseInt(a[0]), a[1], a[2]);
            participant.start();
            participant.blockUntilTermination();
        }
        else if (args[0].equalsIgnoreCase("coordinator")) {
            // txnId newValue logFilePath port1 port2 port3 ...
            String[] a = _removeHead(args);
            String txnId = a[0];
            String newValue = a[1];
            String logFilePath = a[2];
            int[] ports = new int[a.length - 3];
            for (int i = 3; i < a.length; i++) {
                ports[i-3] = Integer.parseInt(a[i]);
            }
            Coordinator coordinator = new Coordinator(txnId, newValue, ports, logFilePath);
        }
        else {
            usage();
        }
    }


    private static String[] _removeHead(String[] args) {
        String[] r = new String[args.length - 1];
        System.arraycopy(args, 1, r, 0, args.length - 1);
        return r;
    }

    private static void usage() {
        System.out.println("Usage: app participant|coordinator params...");
        System.exit(1);
    }
}
