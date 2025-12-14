package org.example;

import org.apache.pekko.actor.typed.ActorRef;
import org.apache.pekko.actor.typed.ActorSystem;
import org.example.actors.MainActor;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        final ActorRef<MainActor.MessageInterface> actorSystem = ActorSystem.create(MainActor.create(), "actorSystem");
        String alice = "Alice";
        String bob = "Bob";
        String charlie = "Charlie";

        actorSystem.tell(new MainActor.SpawnMessage(alice));
        actorSystem.tell(new MainActor.SpawnMessage(bob));
        actorSystem.tell(new MainActor.SpawnMessage(charlie));

        actorSystem.tell(new MainActor.LoopsMessage(alice, 1_000_000));
        actorSystem.tell(new MainActor.LoopsMessage(bob, 0));
        actorSystem.tell(new MainActor.LoopsMessage(charlie, 0));

        actorSystem.tell(new MainActor.LoopsMessage(alice, 0));
        actorSystem.tell(new MainActor.LoopsMessage(bob, 1_000_000));
        actorSystem.tell(new MainActor.LoopsMessage(charlie, 1));

        Thread.sleep(1000);
        Thread.ofVirtual().start(() -> {
            long t1 = System.nanoTime();
            for (int i = 0; i < 1_000_000; i++) {
                int _ = i * i;
            }
            long t2 = System.nanoTime() - t1;
            System.out.println("V1 finished in " + t2);
        });
        Thread.ofVirtual().start(() -> {
            long t1 = System.nanoTime();
            for (int i = 0; i < 0; i++) {
                int _ = i * i;
            }
            long t2 = System.nanoTime() - t1;
            System.out.println("V2 finished in " + t2);
        });
    }
}