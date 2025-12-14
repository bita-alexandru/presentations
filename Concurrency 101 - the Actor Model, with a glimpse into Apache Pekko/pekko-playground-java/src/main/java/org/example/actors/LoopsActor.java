package org.example.actors;

import org.apache.pekko.actor.typed.Behavior;
import org.apache.pekko.actor.typed.javadsl.AbstractBehavior;
import org.apache.pekko.actor.typed.javadsl.ActorContext;
import org.apache.pekko.actor.typed.javadsl.Behaviors;
import org.apache.pekko.actor.typed.javadsl.Receive;
import org.slf4j.Logger;

public class LoopsActor extends AbstractBehavior<Integer> {
    private final Logger logger;
    private final String name;
    private Integer requestNum;

    public static Behavior<Integer> create() {
        return Behaviors.setup(LoopsActor::new);
    }

    private LoopsActor(ActorContext<Integer> context) {
        super(context);
        this.logger = context.getLog();
        this.name = context.getSelf().path().name();
        this.requestNum = 0;

        logger.info("Spawned {}", name);
    }

    @Override
    public Receive<Integer> createReceive() {
        return newReceiveBuilder()
                .onMessage(Integer.class, this::onInteger)
                .build();
    }

    private Behavior<Integer> onInteger(Integer loops) {
        requestNum += 1;
        long t1 = System.nanoTime();
        logger.info("{} executing {} loops for request id #{}", name, loops, requestNum);
        for (int i = 0; i < loops; i++) {
            int _ = i * i;
        }
        long t2 = System.nanoTime() - t1;
        logger.info("{} finished executing {} loops for request id #{} in {} ns", name, loops, requestNum, t2);
        return this;
    }
}
