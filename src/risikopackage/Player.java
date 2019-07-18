package risikopackage;

public class Player {
    private String color;
    private String playerMission;
    private int playerArmys;
    private int playernew;

    public Player(String pcolor, String mission, int armys, int newarmys) {
        color = pcolor;
        playerMission = mission;
        playerArmys = armys;
        playernew = newarmys;
    }

    @Override
    public String toString() {
        return color + playerMission + playerArmys + playernew;
    }

    public boolean continentComplete(Continent continent) {
        return false;
    }
}
