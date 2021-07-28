package main.java.Model;

public class RandomEvent {
    private int coolDown;
    //Use this variable to count until cool down is completed
    private int relativeDay;

    public RandomEvent(int coolDown, int relativeDay) {
        this.coolDown = coolDown;
        this.relativeDay = relativeDay;
    }

    public RandomEvent(int coolDown) {
        this(coolDown, 1);
    }
    public int getCoolDown() {
        return coolDown;
    }
    public int getRelativeDay() {
        return relativeDay;
    }
    public void setRelativeDay(int relativeDay) {
        this.relativeDay = relativeDay;
    }

    /**
     * Function called during daily update that triggers random event based on cool down
     * @param farm farm on which update takes place
     * @return true if random event happened, false otherwise
     * */
    public boolean updateDay(Farm farm) {
        if (relativeDay == coolDown) {
            makeEventHappen(farm);
            relativeDay = 1;
            return true;
        }
        relativeDay++;
        return false;
    }
    //Random Event
    private void makeEventHappen(Farm farm) {

    }
}
