package pkg.customized;

public class Game implements GameMBean {
    private String name;

    @Override
    public void playFootball(String clubName) {
        System.out.println(clubName);
    }

    @Override
    public String getPlayerName() {
        System.out.println("getPlayerName");
        return name;
    }

    @Override
    public void setPlayerName(String playerName) {
        System.out.println("setPlayerName");
        this.name = playerName;
    }
}
