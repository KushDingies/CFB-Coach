package Simulation;

public class DefensivePlay extends Play {

    public DefensivePlay(String name, String description, expect e) {
        super(name, description);
        defPlayExpect = e;
    }

    public enum expect { RUN, PASS, BALANCED }

    public expect defPlayExpect;
}
