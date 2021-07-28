package main.java.Model;

public class Farm {
    // Number of milliseconds between ticks
    private static final long TICKRATE = 100000;

    // Number of ticks per day
    private static final long DAYRATE = 50;

    // Number of days per season
    private static final long SEASONRATE = 10;

    private long startTime;
    private Difficulty difficulty;
    private int level;
    private Season season;
    private Player player;
    private PlantType plantType;
    private Plot[] plots;
    private Rain rain;
    private Drought drought;
    private Locusts locusts;
    private int harvest;
    private int water;

    private int plotCount;

    public Farm() {
        plotCount = 10;
        season = Season.SPRING;
        difficulty = Difficulty.TRYHARD;
        plantType = PlantType.CARROT;
        player = new Player("Bob", 0, 10);
        plots = generatePlots(true);
        startTime = System.currentTimeMillis();
        rain = new Rain(5);
        drought = new Drought(7);
        locusts = new Locusts(8);
        harvest = 0;
        water = 0;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;

        int coolDownBase = 3;
        if (difficulty == Difficulty.CHAD) {
            coolDownBase = 4;
        }
        if (difficulty == Difficulty.TRYHARD) {
            coolDownBase = 5;
        }
        if (difficulty == Difficulty.NOOB) {
            coolDownBase = 6;
        }

        rain = new Rain(coolDownBase);
        drought = new Drought(coolDownBase + 2);
        locusts = new Locusts(coolDownBase + 3);
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Generates an array of plots for the farm (currently hard coded to 10).
     * @param randomFill if true, fills the plots with random plants at random growth levels
     * @return an array of initialized plots
     */
    public Plot[] generatePlots(boolean randomFill) {

        System.out.println(plotCount);

        //Add plot and random plant for 10 plots and empty plot for rest
        Plot[] temp = new Plot[16];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = new Plot(this);
            if (randomFill && i < plotCount) {
                temp[i].setIsCleared(true);
                temp[i].fillRandomPlant(this.getTick());
            }
        }
        return temp;
    }

    public Plot[] getPlots() {
        return plots;
    }

    public int updateDay() {
        System.out.println("Day " + getDay());
        for (Plot plot:plots) {
            plot.updateDay();
        }
        //Aazia's way of finding out what random event went down: (WE'RE GOING BINARY BOYS)
        //Rain (2^0), Drought (2^1), Locust (2^2)
        //0 - Nothing, 1 - Rain, 2 - Drought, 3 - Rain + Drought, 4 - Locust, 5 - Rain + Locust,
        //6 - Drought + Locust, 7 - Drought + Locust + Rain
        int event = 0;
        //Add 2^0 for rain
        if (rain.updateDay(this)) {
            event++;
        }
        //Add 2^1 for drought
        if (drought.updateDay(this)) {
            event += 2;
        }
        //Add 2^2 for locust
        if (locusts.updateDay(this)) {
            event += 4;
        }

        harvest = 0;
        water = 0;

        return event;
    }

    public int forceRain() {
        //rain.forceRain(this);
        rain.forceRain(this);
        for (Plot plot:plots) {
            plot.updateDay();
        }
        rain.forceRain(this);
        return 1;
    }

    public int forceDrought() {
        drought.forceDrought(this);
        rain.forceRain(this);
        for (Plot plot:plots) {
            plot.updateDay();
        }
        return 2;
    }

    public int forceLocusts() {
        locusts.forceLocusts(this);
        for (Plot plot:plots) {
            plot.updateDay();
        }
        rain.forceRain(this);
        return 4;
    }

    public boolean harvest() {
        //harvest the plant functionalty
        System.out.println("this is the " + (harvest + 1) + " harvest attempt");
        return harvest++ < player.getHarvestCap();
    }

    public boolean water() {
        //water the plant functionalty
        System.out.println("this is the " + (water + 1) + " water attempt");
        return water++ < player.getWaterCap();
    }

    /**
     * Gives the current day
     * @return current day
     */
    public int getDay() {
        return (int) (this.getTick() / DAYRATE);
    }

    public void setSeedType(PlantType plantType) {
        this.plantType = plantType;
    }

    public PlantType getSeedType() {
        return this.plantType;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getTimeSinceStart() {
        return System.currentTimeMillis() - startTime;
    }

    public long getCurrentTime() {
        return System.currentTimeMillis();
    }

    /**
     * Gives the current game-tick
     * @return current tick
     */
    public long getTick() {
        return getTimeSinceStart() / TICKRATE;
    }

    /**
     * Increases the day by 1 (beginning of next day)
     * @return an int
     */
    public int incrementDay() {
        // decrease start time by the amount of time left in current day cycle
        // ticks left in current day
        long ticksLeft = (this.getDay() + 1) * DAYRATE - this.getTick();
        // decrements startTime by the number of ticks left in current day. Now will be next day.
        startTime -= (TICKRATE * ticksLeft);

        // Updates plots
        return this.updateDay();
    }

    public void incPlotCount() {
        plotCount++;
    }

    public int getPlotCount() {
        return plotCount;
    }

    public void setPlots(Plot[] plots) {
        this.plots = plots;
    }
}
