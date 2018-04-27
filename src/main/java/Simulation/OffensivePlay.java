package Simulation;

public class OffensivePlay extends Play {

    public OffensivePlay(String name, String description, type t) {
        super(name, description);
        offPlayType = t;
    }

    public enum type { RUN, PASS, PUNT, KICK }

    public type offPlayType;

}
