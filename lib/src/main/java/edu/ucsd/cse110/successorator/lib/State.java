package edu.ucsd.cse110.successorator.lib;

public enum State{
    TODAY(1),
    TOMORROW(2),
    PENDING(3),
    RECURRING(4);

    private final int state;

    State(int state){this.state = state;}

    public int getState(){return state;}
}
