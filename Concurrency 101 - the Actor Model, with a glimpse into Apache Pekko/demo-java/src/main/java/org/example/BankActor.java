package org.example;

import org.apache.pekko.actor.typed.Behavior;
import org.apache.pekko.actor.typed.javadsl.AbstractBehavior;
import org.apache.pekko.actor.typed.javadsl.ActorContext;
import org.apache.pekko.actor.typed.javadsl.Behaviors;
import org.apache.pekko.actor.typed.javadsl.Receive;

public class BankActor extends AbstractBehavior<BankActor.MessageInterface> {
    public interface MessageInterface {}

    public record DepositMessage(
            Integer amount
    ) implements MessageInterface {}

    public record WithdrawMessage(
            Integer amount
    ) implements MessageInterface {}

    public record GetBalanceMessage() implements MessageInterface {}

    private Integer balance;

    public static Behavior<BankActor.MessageInterface> create() {
        return Behaviors.setup(BankActor::new);
    }

    private BankActor(ActorContext<MessageInterface> context) {
        super(context);
        this.balance = 0;
    }

    @Override
    public Receive<MessageInterface> createReceive() {
        return newReceiveBuilder()
                .onMessage(DepositMessage.class, this::onDepositMessage)
                .onMessage(WithdrawMessage.class, this::onWithdrawMessage)
                .onMessage(GetBalanceMessage.class, this::onGetBalanceMessage)
                .build();
    }

    private Behavior<MessageInterface> onDepositMessage(DepositMessage depositMessage) {
        balance += depositMessage.amount;
        getContext().getLog().info("depositing 1, balance = {}", balance);
        return this;
    }

    private Behavior<MessageInterface> onWithdrawMessage(WithdrawMessage withdrawMessage) {
        balance -= withdrawMessage.amount;
        getContext().getLog().info("withdrawing 1, balance = {}", balance);
        return this;
    }

    private Behavior<MessageInterface> onGetBalanceMessage(GetBalanceMessage getBalanceMessage) {
        getContext().getLog().info("balance at the end of the day: {}", balance);
        return this;
    }
}
