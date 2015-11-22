package com.rommax;

import java.util.Random;

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

    private Stat life; // очки жизни объекта

    private String loot;
    //TODO: планируется как параметр, в котором будет перечислен возможный лут.
    // Примерное содержимое: #I_WOOD|95#M_ENTS|5#T_GRASS#
    // означает, что с вероятность 95% выпадет итем древесина, а в 5% случаях дерево станет монстром Энтом и тайл изменится на grass

    private int defaultTile; //TODO временное решение. Хранит в себе тайл, который будет установлен на место предыдущего. Эту задачу должен выполнять loot

    private boolean destroyable; // разрушаемый объект или нет.

    private String weaknessFor; //TODO: лучше всего реализовать на манер loot, примерное содержимое:
    // SWORD|50|AXE|100|ARROW|0|MLIGHT|100
    // означает, что, к примеру скелет, получает 50% повреждений от удара мечом, 100% от удара топором,
    // 0% от удара стрелой и 100% от удара магией света
    // крайне важный параметр. Он убережет деревья от вырубки ножом для намазывания масла =)

    // можно пойти дальше и привязать loot к используемуму для убийства оружию. Т.е. при ударе
    // файрболлом по дереву древесина не выпадет, при ударе топором будет древесина, а при использовании
    // кинжала можно будет наковырять коры, но не древесины =)

    // BaseItem
    public GameObject(int id, String name, String path, int level, boolean destroyable, int maxHP, String loot) {
        super(id, name, path, level);
        this.destroyable = destroyable;
        this.life = new Stat(maxHP, maxHP);
        this.loot = loot;
    }

    // BaseTile
    public GameObject(int id, String name, String path, boolean destroyable, int maxHP, String loot)
    {
        super(id, name, path);
        this.destroyable = destroyable;
        this.life = new Stat(maxHP, maxHP);
        this.loot = loot;
    }

    public boolean getDestroyable()
    {
        return this.destroyable;
    }

    public int getLife()
    {
        return this.life.getMax();
    }

    public String getLoot()
    {
        return this.loot;
    }

    public int getDefaultTile()
    {
        return this.defaultTile;
    }

    public boolean gathering(Map map, int ny, int nx)
    {
        map.getGame().logMessage("Добываем ресурс");
        this.life.setCurrent(this.life.getCurrent() - map.getGame().player.getDNormal().getCurrent());
        if (this.life.getCurrent() <= 0) {
            makeLoot(map, Tileset.getTile(map.field[ny][nx].getID()).getLoot());
            map.setTileAt(ny, nx, map.getDefaultTile());
            this.life.setCurrent(100); //TODO: костыль. Необходимо, чтоб следующий объект этого типа не разрушался с первого раза.
            return true;
        }
        return false;
    }

    private void lootScriptParser(Map map, String loot)
    {
        String script = "";
        String param = "";

        int startCell = -1;

        for (int i = 0; i < loot.length(); i++) {
            char c = loot.charAt(i);

            if (c == '#') {
                startCell = i + 1;
            }
            if (i == startCell) {
                while (true) {
                    if ((!Character.isDigit(c)) && (c != '#') && (c != '|')) script = script + c;
                    if (Character.isDigit(c)) param = param + c;
                    i++;
                    c = loot.charAt(i);
                    if (c == '#') {
                        i--;
                        //getGame().logMessage(script + " " + param);
                        dropLoot(map, script, param);
                        script = "";
                        param = "";
                        break;
                    }
                }
            }
        }
    }

    private void dropLoot(Map map, String script, String param)
    {
        switch (script){
            case "I_EMPTY_JAR" :
                if (Integer.valueOf(param) > new Random().nextInt(100)) { map.getGame().addItem(map.getGame().player.getY(), map.getGame().player.getX(), ItemSet.EMPTY_JAR, map); }
                break;
            case "I_LEATHER" :
                if (Integer.valueOf(param) > new Random().nextInt(100)) { map.getGame().addItem(map.getGame().player.getY(), map.getGame().player.getX(), ItemSet.LEATHER, map); }
                break;
            case "I_METALS" :
                if (Integer.valueOf(param) > new Random().nextInt(100)) { map.getGame().addItem(map.getGame().player.getY(), map.getGame().player.getX(), ItemSet.METALS, map); }
                break;
            case "I_EMPTY_SCROOL" :
                if (Integer.valueOf(param) > new Random().nextInt(100)) { map.getGame().addItem(map.getGame().player.getY(), map.getGame().player.getX(), ItemSet.EMPTY_SCROOL, map); }
                break;
            default: break;
        }
    }

    public void makeLoot(Map map, String loot)
    {
        lootScriptParser(map, loot);
    }

}