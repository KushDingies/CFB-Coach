package Simulation;

import java.util.ArrayList;

public class Play {

    public String name;
    private String description;
    public int runBlocking, runStopping, runPotential, passBlocking, passRush, passPotential;
    public int catchMod; // affects receiver drop chance
    public int intMod; // affects interception chance
    public int sackMod; // affects QB sack chance

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

        OffensivePlay insideRun = new OffensivePlay("Inside run", "A conservative run up the middle.", OffensivePlay.type.RUN);
        insideRun.runBlocking = 2;
        insideRun.runPotential = -1;
        plays.add(insideRun);

        OffensivePlay outsideRun = new OffensivePlay("Outside run", "A run to the outside with more risk but more potential.", OffensivePlay.type.RUN);
        outsideRun.runBlocking = -2;
        outsideRun.runPotential = 2;
        plays.add(outsideRun);

        OffensivePlay shortPass = new OffensivePlay("Short pass", "A conservative pass with a higher completion percentage.", OffensivePlay.type.PASS);
        shortPass.catchMod = 2;
        shortPass.passPotential = -1;
        plays.add(shortPass);

        OffensivePlay mediumPass = new OffensivePlay("Medium pass", "A regular pass.", OffensivePlay.type.PASS);
        plays.add(mediumPass);

        OffensivePlay deepPass = new OffensivePlay("Deep pass", "A longer pass that's harder to achieve.", OffensivePlay.type.PASS);
        deepPass.passBlocking = -1;
        deepPass.sackMod = 2;
        deepPass.passPotential = 5;
        plays.add(deepPass);

        plays.add(new OffensivePlay("Punt", "Punt the ball.", OffensivePlay.type.PUNT));
        plays.add(new OffensivePlay("Kick", "Try to kick the ball through the uprights.", OffensivePlay.type.KICK));

        return plays;
    }

    public static ArrayList<Play> getDefensivePlays() {
        ArrayList<Play> plays = new ArrayList<Play>();
        plays.add(getAutoPlay());

        plays.add(new DefensivePlay("Balanced", "A nice balanced defense.", DefensivePlay.expect.BALANCED));

        DefensivePlay expectRun = new DefensivePlay("Expect Run", "Tell your players to expect a run.", DefensivePlay.expect.RUN);
        expectRun.runStopping = 2;
        expectRun.runPotential = -1;
        expectRun.passRush = -2;
        expectRun.passPotential = 3;
        expectRun.catchMod = 2;
        expectRun.intMod = -2;
        plays.add(expectRun);

        DefensivePlay expectPass = new DefensivePlay("Expect Pass", "Tell your players to be ready for a pass.", DefensivePlay.expect.PASS);
        expectPass.runStopping = -3;
        expectPass.runPotential = 2;
        expectPass.passRush = 2;
        expectPass.passPotential = -2;
        expectPass.catchMod = -2;
        expectPass.intMod = 2;
        plays.add(expectPass);

        DefensivePlay blitz = new DefensivePlay("Blitz", "Send extra defenders forward. Higher chance of creating big plays on either side.", DefensivePlay.expect.BALANCED);
        blitz.runStopping = 4;
        blitz.passRush = 4;
        blitz.runPotential = 4;
        blitz.passPotential = 6;
        blitz.sackMod = 3;
        blitz.intMod = 3;
        plays.add(blitz);

        return plays;
    }

    public static Play getAutoPlay() {
        return new Play("Auto", "Let the computer choose for you.");
    }
}

