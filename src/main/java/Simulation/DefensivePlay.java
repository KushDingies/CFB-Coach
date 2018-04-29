package Simulation;

public class DefensivePlay extends Play {

    public DefensivePlay(String name, String description, expect e) {
        super(name, description);
        defPlayType = e;
    }

    public enum expect { RUN, PASS, BALANCED, PUNT_RETURN, KICK_BLOCK }

    public expect defPlayType;
}
