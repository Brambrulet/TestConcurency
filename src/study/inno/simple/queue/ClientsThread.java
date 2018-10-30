package study.inno.simple.queue;

import java.util.Random;

public class ClientsThread extends Thread {
    private static final int MAX_CLIENTS = 100;
    private static final int MAX_SLEEP_TIME = 30;
    private final Random rand = new Random();
    private final TicketMachine ticketMachine;

    public ClientsThread(TicketMachine ticketMachine) {
        this.ticketMachine = ticketMachine;
        start();
    }

    @Override
    public void run() {
        try {
            for (int iClient = 0; iClient < MAX_CLIENTS; iClient++) {
                sleep(rand.nextInt(MAX_SLEEP_TIME));
                ticketMachine.welcomeGuest();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
