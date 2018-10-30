package study.inno.simple.queue;

public class WindowClass extends Thread {
    private static final int SLEEP_BETWEEN_OPERATIONS = 10;
    private final Operation operation;
    private final TicketMachine ticketMachine;
    private final int windowNo;

    public WindowClass(TicketMachine ticketMachine, int windowNo) {
        operation = TicketMachine.getRandomOperation();
        this.ticketMachine = ticketMachine;
        this.windowNo = windowNo;

        System.out.println(String.format("create window with no %s for operation %s", windowNo, operation.toString()));
        setDaemon(true);
        start();
    }

    public Operation getOperation() {
        return operation;
    }

    @Override
    public void run() {
        while (true) {
            Integer guest = ticketMachine.getNextNumberForOperation(operation);

//            System.out.println(guest == null ?
//                    String.format("window %s simple some time", windowNo) :
//                    String.format("guest %s went to window %s, operation(%s)", guest, windowNo, operation.toString()));
            if (guest != null)
                System.out.println(String.format("guest %s went to window %s, operation(%s)", guest, windowNo, operation.toString()));

            try {
                sleep(guest == null ? SLEEP_BETWEEN_OPERATIONS : operation.getOpLen());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
