package Simulation;

import java.util.ArrayList;

public class Play {

    public String name;
    private String description;
    public int runBlocking, runStopping, runPotential, passBlocking, passRush, passPotential;
    public int catchMod; // affects receiver drop chance
    public int intMod; // affects interception chance
    public int sackMod; // affects QB sack chance

    final static int scale = 2;

    protected Play(String n, String d) {
        name = n;
        description = d;
    }

    public String getFullDescription() {
        String fullDesc = "";
        fullDesc += description;
        if (runBlocking != 0) {
            fullDesc += "\n";
            if (runBlocking > 0)
                    fullDesc += "+";
            fullDesc += runBlocking + " run blocking";
        }
        if (runStopping != 0) {
            fullDesc += "\n";
            if (runStopping > 0)
                fullDesc += "+";
            fullDesc += runStopping + " run stopping";
        }
        if (runPotential != 0) {
            fullDesc += "\n";
            if (runPotential > 0)
                fullDesc += "+";
            fullDesc += runPotential + " run potential";
        }
        if (passBlocking != 0) {
            fullDesc += "\n";
            if (passBlocking > 0)
                fullDesc += "+";
            fullDesc += passBlocking + " pass blocking";
        }
        if (passRush != 0) {
            fullDesc += "\n";
            if (passRush > 0)
                fullDesc += "+";
            fullDesc += passRush + " pass rush";
        }
        if (passPotential != 0) {
            fullDesc += "\n";
            if (passPotential > 0)
                fullDesc += "+";
            fullDesc += passPotential + " pass potential";
        }
        if (catchMod != 0) {
            fullDesc += "\n";
            if (catchMod > 0)
                fullDesc += "+";
            fullDesc += catchMod + " catch modifier";
        }
        if (intMod != 0) {
            fullDesc += "\n";
            if (intMod > 0)
                fullDesc += "+";
            fullDesc += intMod + " interception modifier";
        }
        if (sackMod != 0) {
            fullDesc += "\n";
            if (sackMod > 0)
                fullDesc += "+";
            fullDesc += sackMod + " sack modifier";
        }


        return fullDesc;
    }

    public static ArrayList<Play> getOffensivePlays() {
        ArrayList<Play> plays = new ArrayList<Play>();
        plays.add(getAutoPlay());

        for (OffensivePlay op : getOffensivePlaysNoAuto()) {
            plays.add(op);
        }

        return plays;
    }

    public static ArrayList<OffensivePlay> getOffensivePlaysNoAuto() {
        ArrayList<OffensivePlay> plays = new ArrayList<OffensivePlay>();

        OffensivePlay insideRun = new OffensivePlay("Inside run", "A conservative run up the middle.", OffensivePlay.type.RUN);
        insideRun.runBlocking = 2 * scale;
        insideRun.runPotential = -1 * scale;
        plays.add(insideRun);

        OffensivePlay outsideRun = new OffensivePlay("Outside run", "A run to the outside with more risk but more potential.", OffensivePlay.type.RUN);
        outsideRun.runBlocking = -2 * scale;
        outsideRun.runPotential = 2 * scale;
        plays.add(outsideRun);

        OffensivePlay shortPass = new OffensivePlay("Short pass", "A conservative pass with a higher completion percentage.", OffensivePlay.type.PASS);
        shortPass.passBlocking = 1 * scale;
        shortPass.catchMod = 2 * scale;
        shortPass.passPotential = -1 * scale;
        plays.add(shortPass);

        OffensivePlay mediumPass = new OffensivePlay("Medium pass", "A regular pass.", OffensivePlay.type.PASS);
        plays.add(mediumPass);

        OffensivePlay deepPass = new OffensivePlay("Deep pass", "A longer pass that's harder to achieve.", OffensivePlay.type.PASS);
        deepPass.passBlocking = -1 * scale;
        deepPass.sackMod = 2 * scale;
        deepPass.passPotential = 5 * scale;
        plays.add(deepPass);

        plays.add(new OffensivePlay("Punt", "Punt the ball.", OffensivePlay.type.PUNT));
        plays.add(new OffensivePlay("Kick", "Try to kick the ball through the uprights.", OffensivePlay.type.KICK));

        return plays;
    }

    public static ArrayList<OffensivePlay> getOffensivePlaysByType(OffensivePlay.type t) {
        ArrayList<OffensivePlay> plays = new ArrayList<OffensivePlay>();

        for (OffensivePlay op : getOffensivePlaysNoAuto()) {
            if (op.offPlayType == t)
                plays.add(op);
        }

        return plays;
    }

    public static OffensivePlay getRandomOffensivePlayByType(OffensivePlay.type t) {
        ArrayList<OffensivePlay> possiblePlays = getOffensivePlaysByType(t);
        int playIndex = (int)(Math.random() * possiblePlays.size());
        return possiblePlays.get(playIndex);
    }

    public static ArrayList<Play> getDefensivePlays() {
        ArrayList<Play> plays = new ArrayList<Play>();
        plays.add(getAutoPlay());

        for (DefensivePlay dp : getDefensivePlaysNoAuto()) {
            plays.add(dp);
        }

        return plays;
    }

    public static ArrayList<DefensivePlay> getDefensivePlaysNoAuto() {
        ArrayList<DefensivePlay> plays = new ArrayList<DefensivePlay>();

        plays.add(new DefensivePlay("Balanced", "A nice balanced defense.", DefensivePlay.expect.BALANCED));

        DefensivePlay expectRun = new DefensivePlay("Expect Run", "Tell your players to expect a run.", DefensivePlay.expect.RUN);
        expectRun.runStopping = 2 * scale;
        expectRun.runPotential = -1 * scale;
        expectRun.passRush = -2 * scale;
        expectRun.passPotential = 3 * scale;
        expectRun.catchMod = 2 * scale;
        expectRun.intMod = -2 * scale;
        plays.add(expectRun);

        DefensivePlay expectPass = new DefensivePlay("Expect Pass", "Tell your players to be ready for a pass.", DefensivePlay.expect.PASS);
        expectPass.runStopping = -3 * scale;
        expectPass.runPotential = 2 * scale;
        expectPass.passRush = 2 * scale;
        expectPass.passPotential = -2 * scale;
        expectPass.catchMod = -2 * scale;
        expectPass.intMod = 2 * scale;
        plays.add(expectPass);

        DefensivePlay blitz = new DefensivePlay("Blitz", "Send extra defenders forward. Higher chance of creating big plays on either side.", DefensivePlay.expect.BALANCED);
        blitz.runStopping = 4 * scale;
        blitz.passRush = 4 * scale;
        blitz.runPotential = 4 * scale;
        blitz.passPotential = 6 * scale;
        blitz.sackMod = 3 * scale;
        blitz.intMod = 3 * scale;
        plays.add(blitz);

        return plays;
    }

    public static DefensivePlay getRandomDefensivePlay() {
        ArrayList<DefensivePlay> possiblePlays = getDefensivePlaysNoAuto();
        int playIndex = (int)(Math.random() * possiblePlays.size());
        return possiblePlays.get(playIndex);
    }

    public static Play getAutoPlay() {
        return new Play("Auto", "Let the computer choose for you.");
    }
}

