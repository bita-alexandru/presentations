package org.example.actors;

import org.apache.pekko.actor.typed.ActorRef;
import org.apache.pekko.actor.typed.Behavior;
import org.apache.pekko.actor.typed.javadsl.AbstractBehavior;
import org.apache.pekko.actor.typed.javadsl.ActorContext;
import org.apache.pekko.actor.typed.javadsl.Behaviors;
import org.apache.pekko.actor.typed.javadsl.Receive;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActor extends AbstractBehavior<MainActor.MessageInterface> {
    public interface MessageInterface {}

    public record SpawnMessage(
        String name
    ) implements MainActor.MessageInterface {}

    public record LoopsMessage(
        String name,
        Integer loops
    ) implements MainActor.MessageInterface {}


    private final ActorContext<MessageInterface> context;
    private final Map<String, ActorRef<Integer>> actorRefMap;

    public static Behavior<MessageInterface> create() {
        return Behaviors.setup(MainActor::new);
    }

    private MainActor(ActorContext<MessageInterface> context) {
        super(context);
        this.context = context;
        this.actorRefMap = new HashMap<>();
    }

    @Override
    public Receive<MessageInterface> createReceive() {
        return newReceiveBuilder()
                .onMessage(MainActor.SpawnMessage.class, this::onSpawnMessage)
                .onMessage(MainActor.LoopsMessage.class, this::onLoopsMessage)
                .build();
    }

    private Behavior<MessageInterface> onSpawnMessage(SpawnMessage spawnMessage) {
        ActorRef<Integer> loopsActor = context.spawn(LoopsActor.create(), spawnMessage.name());
        actorRefMap.put(spawnMessage.name(), loopsActor);
        return this;
    }

    private Behavior<MessageInterface> onLoopsMessage(LoopsMessage loopsMessage) {
        actorRefMap
                .entrySet()
                .stream()
                .filter(entry -> Objects.equals(entry.getKey(), loopsMessage.name()))
                .findFirst()
                .ifPresent(entry ->
                        entry.getValue().tell(loopsMessage.loops())
                );
        return this;
    }
}
