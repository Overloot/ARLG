package com.rommax;

/**
 * Created by Maxim on 18.11.2015.
 */
public class GameObject extends Entity {

    // резисты
    private Stat RFire;
    private Stat RCold;
    private Stat RElec;
    private Stat RPoison;
    private Stat RNormal;

    private Stat life;

    private String loot;
    //TODO: планируется как параметр, в котором будет перечислен возможный лут.
    // Примерное содержимое: wood|95|ENTS|5
    // означает, что с вероятность 95% выпадет древесина, а в 5% случаях дерево станет Энтом.

    private String behavior; //TODO: поведение объекта. Будет ли он нападать первым, враг или друг и т.д.

    private boolean destroyable; //TODO: разрушаемый объект или нет.
    // Планируется, что все объекты разрушаемые, но как разрушить портал или радугу?

    private String weaknessFor; //TODO: лучше всего реализовать на манер loot, примерное содержимое:
    // SWORD|50|AXE|100|ARROW|0|MLIGHT|100
    // значает, что, к примеру скелет, получает 50% повреждений от удара мечом, 100% от удара топором,
    // 0% от удара стрелой и 100% от удара магией света
    // крайне важный параметр. Он убережет деревья от вырубки ножом для намазывания масла =)

    // можно пойти дальше и привязать loot к используемуму для убийства оружию. Т.е. при ударе
    // файрболлом по дереву древесина не выпадет, при ударе топором будет древесина, а при использовании
    // кинжала можно будет наковырять коры, но не древесины =)

    public GameObject(int maxHP, int RNormal, int RFire, int RCold, int RElec, int RPoison) {
        //резисты
        this.RFire = new Stat(RFire, RFire);
        this.RCold = new Stat(RCold, RCold);
        this.RPoison = new Stat(RPoison, RPoison);
        this.RElec = new Stat(RElec, RElec);
        this.RNormal = new Stat(RNormal, RNormal);

        this.life = new Stat(maxHP, maxHP);
    }

    public GameObject(int id, String name, String path, int level) { //TODO: костыль для BaseMonster
        super(id, name, path, level);
    }
}
