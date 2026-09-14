package com.xycm.frain.minecore.command;

import com.xycm.frain.minecore.config.MessageConfig;
import com.xycm.frain.minecore.service.MessageService;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import revxrsal.commands.bukkit.exception.BukkitExceptionHandler;
import revxrsal.commands.bukkit.exception.SenderNotPlayerException;
import revxrsal.commands.exception.*;
import revxrsal.commands.node.ParameterNode;


public final class ExceptionHandler extends BukkitExceptionHandler {

    public static final ExceptionHandler instance = new ExceptionHandler();

    @Override
    public void onNoPermission(NoPermissionException exception, BukkitCommandActor actor) {
        MessageService.send(actor.sender(), MessageConfig.getInstance().getNoPermission());
    }

    @Override
    public void onUnknownCommand(UnknownCommandException exception, BukkitCommandActor actor) {
        MessageService.send(actor.sender(), MessageConfig.getInstance().getExceptionUnknownCommand());
    }

    @Override
    public void onMissingArgument(MissingArgumentException exception, BukkitCommandActor actor, ParameterNode<BukkitCommandActor, ?> parameter) {
        MessageService.send(actor.sender(), MessageConfig.getInstance().getExceptionMissingArgument(), "{param}", parameter.parameter().name());
    }

    @Override
    public void onInvalidInteger(InvalidIntegerException exception, BukkitCommandActor actor) {
        MessageService.send(actor.sender(), MessageConfig.getInstance().getExceptionInvalidInteger(), "{input}", exception.input());
    }

    @Override
    public void onInvalidDecimal(InvalidDecimalException exception, BukkitCommandActor actor) {
        MessageService.send(actor.sender(), MessageConfig.getInstance().getExceptionInvalidDecimal(), "{input}", exception.input());
    }

    @Override
    public void onCommandInvocation(CommandInvocationException exception, BukkitCommandActor actor) {
        MessageService.send(actor.sender(), MessageConfig.getInstance().getExceptionCommandError(), "{error}", exception.cause().getMessage() != null ? exception.cause().getMessage() : "未知错误");
    }

    @Override
    public void onSenderNotPlayer(SenderNotPlayerException exception, BukkitCommandActor actor) {
        MessageService.send(actor.sender(), MessageConfig.getInstance().getPlayerOnly());
    }
}
