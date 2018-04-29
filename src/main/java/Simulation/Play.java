package Simulation;

import java.util.ArrayList;

public class Play {

    public String name;
    private String description;
    public int runBlocking, runStopping, runPotential, passBlocking, passRush, passPotential;
    public int catchMod; // affects receiver drop chance
    public int intMod; // affects interception chance
    public int sackMod; // affects QB sack chance
    public int kickBlockMod;
    public int kickReturnMod;

    private ArrayList<String> hintStrings;
    private ArrayList<Integer> hintWeights;
    private int hintWeightsTotal;

    final static int scale = 2;

    protected Play(String n, String d) {
        name = n;
        description = d;

        hintStrings = new ArrayList<>();
        hintWeights = new ArrayList<>();
        hintWeightsTotal = 0;
    }

    protected void addHintString(String hint, int weight) {
        hintStrings.add(hint);
        hintWeights.add(weight);
        hintWeightsTotal += weight;
    }

    public String getRandomHintString() {
        int hintRoll = (int)Math.random() * hintWeightsTotal;
        for (int hintIndex = 0; hintIndex < hintStrings.size(); hintIndex++) {
            hintRoll -= hintWeights.get(hintIndex);
            if (hintRoll <= 0)
                return hintStrings.get(hintIndex);
        }

        return "The other team isn't doing anything noteworthy.";
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
        if (kickBlockMod != 0) {
            fullDesc += "\n";
            if (kickBlockMod > 0)
                fullDesc += "+";
            fullDesc += kickBlockMod + " to kick blocking";
        }
        if (kickReturnMod != 0) {
            fullDesc += "\n";
            if (kickReturnMod > 0)
                fullDesc += "+";
            fullDesc += kickReturnMod + " to kick return";
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
        insideRun.runPotential = -1;
        insideRun.addHintString("The running back is lining up near the inside.", 1);
        plays.add(insideRun);

        OffensivePlay outsideRun = new OffensivePlay("Outside run", "A run to the outside with more risk but more potential.", OffensivePlay.type.RUN);
        outsideRun.runBlocking = -2 * scale;
        outsideRun.runPotential = 2;
        outsideRun.addHintString("The running back is lining up near the outside.", 1);
        plays.add(outsideRun);

        OffensivePlay shortPass = new OffensivePlay("Short pass", "A conservative pass with a higher completion percentage.", OffensivePlay.type.PASS);
        shortPass.passBlocking = 1 * scale;
        shortPass.catchMod = 2 * scale;
        shortPass.passPotential = -1;
        shortPass.addHintString("The receivers are getting ready for a short pass.", 1);
        plays.add(shortPass);

        OffensivePlay mediumPass = new OffensivePlay("Medium pass", "A regular pass.", OffensivePlay.type.PASS);
        mediumPass.addHintString("The receivers are getting ready for a medium pass.", 1);
        plays.add(mediumPass);

        OffensivePlay deepPass = new OffensivePlay("Deep pass", "A longer pass that's harder to achieve.", OffensivePlay.type.PASS);
        deepPass.passBlocking = -1 * scale;
        deepPass.sackMod = 2 * scale;
        deepPass.passPotential = 3;
        deepPass.addHintString("The receivers are getting ready to book it.", 1);
        plays.add(deepPass);

        OffensivePlay puntPlay = new OffensivePlay("Punt", "Punt the ball.", OffensivePlay.type.PUNT);
        puntPlay.addHintString("The special teams unit is out on the field in punt formation.", 1);
        plays.add(puntPlay);

        OffensivePlay fgPlay = new OffensivePlay("Field Goal", "Try to kick the ball through the uprights.", OffensivePlay.type.KICK);
        fgPlay.addHintString("The kicking team is coming out and the kicker is warming up.", 1);
        plays.add(fgPlay);

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
        ArrayList<DefensivePlay> plays = new ArrayList<>();

        plays.add(new DefensivePlay("Balanced", "A nice balanced defense.", DefensivePlay.expect.BALANCED));

        DefensivePlay expectRun = new DefensivePlay("Expect Run", "Tell your players to expect a run.", DefensivePlay.expect.RUN);
        expectRun.runStopping = 2 * scale;
        expectRun.runPotential = -1;
        expectRun.passRush = -2 * scale;
        expectRun.passPotential = 2;
        expectRun.catchMod = 2 * scale;
        expectRun.intMod = -2 * scale;
        expectRun.addHintString("The linebackers are stuffing the box.", 1);
        plays.add(expectRun);

        DefensivePlay expectPass = new DefensivePlay("Expect Pass", "Tell your players to be ready for a pass.", DefensivePlay.expect.PASS);
        expectPass.runStopping = -2 * scale;
        expectPass.runPotential = 2;
        expectPass.passRush = 2 * scale;
        expectPass.passPotential = -2;
        expectPass.catchMod = -2 * scale;
        expectPass.intMod = 2 * scale;
        expectPass.addHintString("The defense is backing away from the line of scrimmage.", 1);
        plays.add(expectPass);

        DefensivePlay blitz = new DefensivePlay("Blitz", "Send extra defenders forward. Higher chance of creating big plays on either side.", DefensivePlay.expect.BALANCED);
        blitz.runStopping = 4 * scale;
        blitz.passRush = 4 * scale;
        blitz.runPotential = 3;
        blitz.passPotential = 3;
        blitz.sackMod = 3 * scale;
        blitz.intMod = 3 * scale;
        blitz.addHintString("The defensive backs are cheating forward.", 1);
        plays.add(blitz);

        DefensivePlay puntReturn = new DefensivePlay("Punt return", "Put a man deep to return a punt.", DefensivePlay.expect.PUNT_RETURN);
        puntReturn.runStopping = -4 * scale;
        puntReturn.passRush = -4 * scale;
        puntReturn.runPotential = 3;
        puntReturn.passPotential = 3;
        puntReturn.sackMod = -4 * scale;
        puntReturn.kickReturnMod = 20;
        puntReturn.addHintString("The defense put a return man deep.", 1);
        plays.add(puntReturn);

        DefensivePlay blockKick = new DefensivePlay("Block kick", "Try to block a punt or a field goal.", DefensivePlay.expect.KICK_BLOCK);
        blockKick.runPotential = 3;
        blockKick.passPotential = 3;
        blockKick.kickBlockMod = 5;
        blockKick.addHintString("The defense put everyone on the line of scrimmage.", 1);
        plays.add(blockKick);

        return plays;
    }

    public static DefensivePlay getRandomDefensivePlay() {
        ArrayList<DefensivePlay> possiblePlays = getDefensivePlaysNoAuto();
        int playIndex = (int)(Math.random() * possiblePlays.size());
        return possiblePlays.get(playIndex);
    }

    public static ArrayList<DefensivePlay> getDefensivePlaysByType(DefensivePlay.expect t) {
        ArrayList<DefensivePlay> plays = new ArrayList<>();

        for (DefensivePlay dp : getDefensivePlaysNoAuto()) {
            if (dp.defPlayType == t)
                plays.add(dp);
        }

        return plays;
    }

    public static DefensivePlay getRandomDefensivePlayByType(DefensivePlay.expect t) {
        ArrayList<DefensivePlay> possiblePlays = getDefensivePlaysByType(t);
        int playIndex = (int)(Math.random() * possiblePlays.size());
        return possiblePlays.get(playIndex);
    }

    public static DefensivePlay getRandomDefensivePlayNoSpecialTeams() {
        ArrayList<DefensivePlay> possiblePlays = new ArrayList<DefensivePlay>();

        for (DefensivePlay dp : getDefensivePlaysNoAuto()) {
            if (dp.defPlayType != DefensivePlay.expect.KICK_BLOCK && dp.defPlayType != DefensivePlay.expect.PUNT_RETURN)
                possiblePlays.add(dp);
        }

        int playIndex = (int)(Math.random() * possiblePlays.size());
        return possiblePlays.get(playIndex);
    }

    public static DefensivePlay getRandomDefensivePlayOnlySpecialTeams() {
        ArrayList<DefensivePlay> possiblePlays = new ArrayList<DefensivePlay>();

        for (DefensivePlay dp : getDefensivePlaysNoAuto()) {
            if (dp.defPlayType == DefensivePlay.expect.KICK_BLOCK || dp.defPlayType == DefensivePlay.expect.PUNT_RETURN)
                possiblePlays.add(dp);
        }

        int playIndex = (int)(Math.random() * possiblePlays.size());
        return possiblePlays.get(playIndex);
    }

    public static Play getAutoPlay() {
        return new Play("Auto", "Let the computer choose for you.");
    }
}

