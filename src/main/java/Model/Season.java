package main.java.Model;

public enum Season {
    SPRING("Spring", 70, (float) 0.35),
    SUMMER("Summer", 90, (float) 0.3),
    FALL("Fall", 65, (float) 0.2),
    WINTER("Winter", 40, (float) 0.25);

    private String name;
    private int temperature;
    private float rainChance;

    Season(String name, int temperature, float rainChance) {
        this.name = name;
        this.temperature = temperature;
        this.rainChance = rainChance;
    }

    /**
     * Returns the next season
     * @param season current season
     * @return next season
     */
    public Season getNext(Season season) {
        if (season.equals(Season.SPRING)) {
            return Season.SUMMER;
        } else if (season.equals(Season.SUMMER)) {
            return Season.FALL;
        } else if (season.equals(Season.FALL)) {
            return Season.WINTER;
        } else {
            return Season.SPRING;
        }
    }
}
