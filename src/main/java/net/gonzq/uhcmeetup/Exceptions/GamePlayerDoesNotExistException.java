package net.gonzq.uhcmeetup.Exceptions;

public class GamePlayerDoesNotExistException extends Exception{

    public GamePlayerDoesNotExistException(String name) {super("Player " + name + " doesn't exist");}
}
