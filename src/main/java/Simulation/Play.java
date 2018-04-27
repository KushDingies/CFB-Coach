package Simulation;

import java.util.ArrayList;

public class Play {

    public String name;
    public String description;

    protected Play(String n, String d) {
        name = n;
        description = d;
    }

    public static ArrayList<Play> getOffensivePlays() {
        ArrayList<Play> plays = new ArrayList<Play>();
        plays.add(new OffensivePlay("Run", "Run the ball.", OffensivePlay.type.RUN));
        plays.add(new OffensivePlay("Pass", "Pass the ball.", OffensivePlay.type.PASS));
        plays.add(new OffensivePlay("Punt", "Punt the ball.", OffensivePlay.type.PUNT));
        plays.add(new OffensivePlay("Kick", "Try to kick the ball through the uprights.", OffensivePlay.type.KICK));

        return plays;
    }

    public static ArrayList<Play> getDefensivePlays() {
        ArrayList<Play> plays = new ArrayList<Play>();
        plays.add(new DefensivePlay("Balanced", "A nice balanced defense.", DefensivePlay.expect.BALANCED));
        plays.add(new DefensivePlay("Expect Run", "Tell your players to expect a run.", DefensivePlay.expect.RUN));
        plays.add(new DefensivePlay("Expect Pass", "Tell your players to be ready for a pass.", DefensivePlay.expect.PASS));

        return plays;
    }
}

