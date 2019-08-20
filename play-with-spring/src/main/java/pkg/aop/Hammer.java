package pkg.aop;

public class Hammer {

    private String sound;

    public Hammer(String sound) {
        this.sound = sound;
    }

    @hitSound
    public void hit(){
        System.out.println(sound);
    }
}
