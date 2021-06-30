package xyz.monkefy.database;

import xyz.monkefy.utilities.StackUtil;

public class SQLThread extends Thread {
    private boolean running = false;

    private DataManager sql;

    private long delay;

    public SQLThread(DataManager sql, long delay) {
        this.sql = sql;
        this.delay = delay;
    }

    public void run() {
        while(this.running) {
            try {
                this.sql.close();
                this.sql.open();
            } catch (Throwable t) {
                StackUtil.dumpStack(t);
            }
            try {
                Thread.sleep(this.delay * 1000L);
            } catch (InterruptedException interruptedException) {}
        }
    }

    public void start(long delay) {
        this.delay = delay;
        if(this.running) interrupt();
        start();
    }

    public void start() {
        if(this.running) return;
        this.running = true;
        super.start();
    }

}
