package org.example;

import org.apache.pekko.actor.typed.ActorRef;
import org.apache.pekko.actor.typed.Behavior;
import org.apache.pekko.actor.typed.javadsl.AbstractBehavior;
import org.apache.pekko.actor.typed.javadsl.ActorContext;
import org.apache.pekko.actor.typed.javadsl.Behaviors;
import org.apache.pekko.actor.typed.javadsl.Receive;

public class SystemActor extends AbstractBehavior<SystemActor.MessageInterface> {

    public interface MessageInterface {}

    public record BeginDayMessage (
            String day,
            Integer nOfAtms
    ) implements MessageInterface {}

    private final ActorRef<BankActor.MessageInterface> bankActor;

    public static Behavior<MessageInterface> create() {
        return Behaviors.setup(SystemActor::new);
    }

    public SystemActor(ActorContext<MessageInterface> context) {
        super(context);
        bankActor = getContext().spawn(BankActor.create(), "bankActor");
    }

    @Override
    public Receive<MessageInterface> createReceive() {
        return newReceiveBuilder()
                .onMessage(BeginDayMessage.class, this::onBeginDayMessage)
                .build();
    }

    private Behavior<MessageInterface> onBeginDayMessage(BeginDayMessage beginDayMessage) throws InterruptedException {
        for (int i = 0; i < beginDayMessage.nOfAtms; i++) {
            ActorRef<AtmActor.MessageInterface> atmActor = getContext().spawn(AtmActor.create(), "atmActor_" + i);
            atmActor.tell(new AtmActor.BeginTransactionsMessage(100, bankActor));
        }

		// The order of messages is not guaranteed between more than 2 actors.
		// More specifically, in this example, the BankActor *MIGHT* (very probably)
		// be processing other messages in the queue received from AtmActors
		// of types "DepositMessage" and "WithdrawMessage".
		//
		// As such, I am introducing a Thread.sleep with an estimated time of
		// how long it would take BankActor to finish processing all of the messages
		// in its mailbox, before sending it another message asking for its balance.
		//
		// This is obviously NOT what we'd want to do in an actual solution.
		// Instead, we'd need to further refine our solution and maybe make use
		// of the "ask" pattern.
		// This is to show the drawback that actors ARE NOT (easily) composable.
        Thread.sleep(1000);
        bankActor.tell(new BankActor.GetBalanceMessage());
        return this;
    }
}
