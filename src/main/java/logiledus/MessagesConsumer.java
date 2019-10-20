package logiledus;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class MessagesConsumer extends AnimationTimer {
    private BlockingQueue<String> msgQueue = new LinkedBlockingDeque<>();
    private Label infoLabel;

    public static MessagesConsumer getInstance(){ return MessagesConsumerHolder.INSTANCE; }

    public void setInstance(Label infoLabel){ this.infoLabel = infoLabel; }

    private static class MessagesConsumerHolder{
        private static final MessagesConsumer INSTANCE = new MessagesConsumer();
    }

    @Override
    public void handle(long l) {
        ArrayList<String> messages = new ArrayList<>();
        int msgRecieved = msgQueue.drainTo(messages);
        if (msgRecieved > 0)
            messages.forEach(infoLabel::setText);
    }

    public void inform(String text){
        try {
            this.msgQueue.put(text);
        }
        catch (InterruptedException ignored) {}
    }
}