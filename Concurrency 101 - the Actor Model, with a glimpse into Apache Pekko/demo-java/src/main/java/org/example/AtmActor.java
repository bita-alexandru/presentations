package org.example;

import org.apache.pekko.actor.typed.ActorRef;
import org.apache.pekko.actor.typed.Behavior;
import org.apache.pekko.actor.typed.javadsl.AbstractBehavior;
import org.apache.pekko.actor.typed.javadsl.ActorContext;
import org.apache.pekko.actor.typed.javadsl.Behaviors;
import org.apache.pekko.actor.typed.javadsl.Receive;

public class AtmActor extends AbstractBehavior<AtmActor.MessageInterface> {
    public interface MessageInterface {}

    public record BeginTransactionsMessage(
            Integer nOfTransactions,
            ActorRef<BankActor.MessageInterface> bankActor
    ) implements MessageInterface {}

    public static Behavior<MessageInterface> create() {
        return Behaviors.setup(AtmActor::new);
    }

    private AtmActor(ActorContext<MessageInterface> context) {
        super(context);
    }

    @Override
    public Receive<MessageInterface> createReceive() {
        return newReceiveBuilder()
                .onMessage(BeginTransactionsMessage.class, this::onBeginTransactionsMessage)
                .build();
    }

    private Behavior<MessageInterface> onBeginTransactionsMessage(BeginTransactionsMessage beginTransactionsMessage) {
        for (int i = 0; i < beginTransactionsMessage.nOfTransactions; i++) {
            beginTransactionsMessage.bankActor.tell(new BankActor.DepositMessage(1));
            beginTransactionsMessage.bankActor.tell(new BankActor.WithdrawMessage(1));
        }
        return this;
    }
}
