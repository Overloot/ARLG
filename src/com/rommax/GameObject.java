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
        String count = "";

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
                    if ((c == 'C') && (Character.isDigit(loot.charAt(i + 1)))) {
                        i++;
                        c = loot.charAt(i);
                        while (true) {
                            if (!Character.isDigit(c)) break;
                            count = count + c;
                            i++;
                            c = loot.charAt(i);
                        }
                    }
                    if (c == '#') {
                        i--;
                        dropLoot(map, script, param, count);
                        script = "";
                        param = "";
                        count = "";
                        break;
                    }
                }
            }
        }
    }

    private void dropLoot(Map map, String script, String param, String count)
    {
        int loot = -1;
        if (count == "") count = "1";
        switch (script){
            case "I_EMPTY_JAR" :
                if (Integer.valueOf(param) > new Random().nextInt(100)) { loot = ItemSet.EMPTY_JAR; }
                break;
            case "I_LEATHER" :
                if (Integer.valueOf(param) > new Random().nextInt(100)) { loot = ItemSet.LEATHER; }
                break;
            case "I_METALS" :
                if (Integer.valueOf(param) > new Random().nextInt(100)) { loot = ItemSet.METALS; }
                break;
            case "I_EMPTY_SCROOL" :
                if (Integer.valueOf(param) > new Random().nextInt(100)) { loot = ItemSet.EMPTY_SCROOL; }
                break;
            case "I_WOOD" :
                if (Integer.valueOf(param) > new Random().nextInt(100)) { loot = ItemSet.WOOD; }
                break;
            case "I_CRUDE_SWORD" :
                if (Integer.valueOf(param) > new Random().nextInt(100)) { loot = ItemSet.CRUDE_SWORD; }
                break;
            default: break;
        }
        if (loot >= 0) {
            for (int howMuchItems = 0; howMuchItems < Integer.valueOf(count); howMuchItems++)
                map.getGame().addItem(map.getGame().player.getY(), map.getGame().player.getX(), loot, map);
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

    // это методы для крафта. для крафта требуются теже ресурсы,
    // что и могут выпасть из предмета при разборе, но в двойном кол-ве.
    public String craftResource(String loot) {
        String needResource = craftScriptParser(loot);
        return needResource;
    }

    private String craftScriptParser(String loot)
    {
        String script = "";
        String param = "";
        String count = "";
        String needResource = "";

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
                    if ((c == 'C') && (Character.isDigit(loot.charAt(i + 1)))) {
                        i++;
                        c = loot.charAt(i);
                        while (true) {
                            if (!Character.isDigit(c)) break;
                            count = count + c;
                            i++;
                            c = loot.charAt(i);
                        }
                    }
                    if (c == '#') {
                        i--;
                        needResource = needResource + "\n" + craftNeedResource(script, param, count);
                        script = "";
                        param = "";
                        count = "";
                        break;
                    }
                }
            }
        }
        return needResource;
    }

    // param не используется, а надо ли?
    private String craftNeedResource(String script, String param, String count)
    {
        int loot = -1;
        if (count == "") count = "1";
        int resCount = Integer.parseInt(count);
        resCount = resCount * 2;
        String needResource = "";
        switch (script){
            case "I_EMPTY_JAR" :
                needResource = needResource + "Пустая банка     " + resCount + "шт.";
                loot = 1;
                break;
            case "I_LEATHER" :
                needResource = needResource + "Кожа             " + resCount + "шт.";
                loot = 1;
                break;
            case "I_METALS" :
                needResource = needResource + "Металл           " + resCount + "шт.";
                loot = 1;
                break;
            case "I_EMPTY_SCROOL" :
                needResource = needResource + "Пустой свиток    " + resCount + "шт.";
                loot = 1;
                break;
            default: break;
        }
        if (loot >= 0) return needResource;
        else return "ОШИБКА!! craftNeedResource";
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