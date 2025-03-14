package org.example;

import org.apache.pekko.actor.typed.ActorSystem;

public class Main {
    public static void main(String[] args) {
        ActorSystem<SystemActor.MessageInterface> systemActor = ActorSystem.create(SystemActor.create(), "systemActor");
        systemActor.tell(new SystemActor.BeginDayMessage("Today", 10));
    }
}