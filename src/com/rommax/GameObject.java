package com.rommax;

import java.util.Random;

/**
 * Created by Maxim on 18.11.2015.
 */
public abstract class GameObject extends Entity {

    // резисты
    private Stat RFire;
    private Stat RCold;
    private Stat RElec;
    private Stat RPoison;
    private Stat RNormal;

    private Stat life; // очки жизни объекта

    private String loot; // лут который может выпасть с объекта

    private boolean destroyable; // разрушаемый объект или нет.

    private String weaknessFor; // слабость к урону от ...

    // BaseItem
    public GameObject(int id, String name, String path, int level, boolean destroyable, int maxHP, String loot) {
        super(id, name, path, level);
        this.destroyable = destroyable;
        this.life = new Stat(maxHP, maxHP);
        this.loot = loot;
    }

    // BaseTile
    public GameObject(int id, String name, String path, boolean destroyable, int maxHP, String loot, String weaknessFor)
    {
        super(id, name, path);
        this.destroyable = destroyable;
        this.life = new Stat(maxHP, maxHP);
        this.loot = loot;
        this.weaknessFor = weaknessFor;
    }

    // BaseMonster
    public GameObject(int id, String name, String path, int level, String loot, boolean destroyable, String weaknessFor)
    {
        super(id, name, path, level);
        this.loot = loot;
        this.destroyable = destroyable;
        this.weaknessFor = weaknessFor;
    }

    public boolean gathering(Map map, int ny, int nx)
    {
        map.getGame().logMessage("Добываем ресурс");
        makeDamage(map, Tileset.getTile(map.field[ny][nx].getID()).getWeaknessFor());
        if (this.life.getCurrent() <= 0) {
            makeLoot(map, Tileset.getTile(map.field[ny][nx].getID()).getLoot());
            map.setTileAt(ny, nx, map.getDefaultTile());
            this.life.setCurrent(100); //TODO: костыль. Необходимо, чтоб следующий объект этого типа не разрушался с первого раза.
            return true;
        }
        return false;
    }

    public void makeLoot(Map map, String loot)
    {
        lootScriptParser(map, loot);
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
            case "I_WOOD" :
                if (Integer.valueOf(param) > new Random().nextInt(100)) { map.getGame().addItem(map.getGame().player.getY(), map.getGame().player.getX(), ItemSet.WOOD, map); }
                break;
            case "I_CRUDE_SWORD" :
                if (Integer.valueOf(param) > new Random().nextInt(100)) { map.getGame().addItem(this.getY(), this.getX(), ItemSet.CRUDE_SWORD, map); }
                break;
            default: break;
        }
    }

    public void makeDamage(Map map, String weaknessFor)
    {
        weaknessScriptParser(map, weaknessFor);
    }

    private void weaknessScriptParser(Map map, String weaknessFor)
    {
        String script = "";
        String param = "";

        int startCell = -1;

        for (int i = 0; i < weaknessFor.length(); i++) {
            char c = weaknessFor.charAt(i);

            if (c == '#') {
                startCell = i + 1;
            }
            if (i == startCell) {
                while (true) {
                    if ((!Character.isDigit(c)) && (c != '#') && (c != '|')) script = script + c;
                    if (Character.isDigit(c)) param = param + c;
                    i++;
                    c = weaknessFor.charAt(i);
                    if (c == '#') {
                        i--;
                        doDamage(map, script, param);
                        script = "";
                        param = "";
                        break;
                    }
                }
            }
        }
    }

    private void doDamage(Map map, String script, String param)
    {
        int damage;
        try {
            switch (script) {
                case "W_SWORD":
                    if (map.getGame().player.Equipment[ItemSet.SLOT_LEFT_ARM].getType() == ItemSet.TYPE_MELEE_WEAPON_SWORD) {
                        damage = (int) map.getGame().player.getDNormal().getCurrent() * Integer.valueOf(param) / 100;
                        this.life.setCurrent(this.life.getCurrent() - damage);
                        break;
                    }
                case "W_AXE":
                    if (map.getGame().player.Equipment[ItemSet.SLOT_LEFT_ARM].getType() == ItemSet.TYPE_MELEE_WEAPON_AXE) {
                        damage = (int) map.getGame().player.getDNormal().getCurrent() * Integer.valueOf(param) / 100;
                        this.life.setCurrent(this.life.getCurrent() - damage);
                        break;
                    }
                default:
                    damage = 1;
                    this.life.setCurrent(this.life.getCurrent() - damage);
                    break;
            }
        } catch (NullPointerException e) { //Перехватчик нужен, т.к. ошибка будет в том случае, если игрок не экипирован оружием.
            damage = 1;
            this.life.setCurrent(this.life.getCurrent() - damage);
        }
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

    public String getWeaknessFor()
    {
        return this.weaknessFor;
    }
}