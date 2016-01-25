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

    // добыча ресурсов с объектов на карте
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

    // запускает обработчик лута
    public void makeLoot(Map map, String loot)
    {
        lootScriptParser(map, loot);
    }

    // метод выясняет какой лут может выбросить объект
    // в параметры String loot содержится скрипт указывающий на возможный лут
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

    // метод "дает" лут
    private void dropLoot(Map map, String script, String param, String count)
    {
        int loot = -1;
        if (count == "") count = "1";
        switch (script){
            case "I_EMPTY_JAR" :
                if (Integer.valueOf(param) > new Random().nextInt(100)) { loot = ItemSet.getEmptyJarID(); }
                break;
            case "I_LEATHER" :
                if (Integer.valueOf(param) > new Random().nextInt(100)) { loot = ItemSet.getLeatherMaterialID(); }
                break;
            case "I_METALS" :
                if (Integer.valueOf(param) > new Random().nextInt(100)) { loot = ItemSet.getMetalMaterialID(); }
                break;
            case "I_EMPTY_SCROOL" :
                if (Integer.valueOf(param) > new Random().nextInt(100)) { loot = ItemSet.getEmptyScrollID(); }
                break;
            case "I_WOOD" :
                if (Integer.valueOf(param) > new Random().nextInt(100)) { loot = ItemSet.getWoodMaterialID(); }
                break;
            case "I_CRUDE_SWORD" :
                if (Integer.valueOf(param) > new Random().nextInt(100)) { loot = ItemSet.getCrudeSwordID(); }
                break;
            default: break;
        }
        if (loot >= 0) {
            for (int howMuchItems = 0; howMuchItems < Integer.valueOf(count); howMuchItems++) {
                if (this instanceof Monster)
                    map.getGame().addItem(this.getY(), this.getX(), loot, map); // так будет падать на позицию объекта, но не дерева, т.к. оно еще не объект
                else
                    map.getGame().addItem(map.getGame().player.getY(), map.getGame().player.getX(), loot, map); // так будет падать на позицию игрока
            }
        }

    }

    // вызывает метод обработки скрипта уязвимостей объекта к определенному типу урона
    public void makeDamage(Map map, String weaknessFor)
    {
        weaknessScriptParser(map, weaknessFor);
    }

    // метод обрабатывает скрипт уязвимостей объекта к определенному типу уровна
    // скрипт в weaknessFor
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

    // метод наносит повреждение текущему атакуемому объекту согласну его скрипту уязвимостей
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

    // запускает обработчик скрипта, который выясняет кол-во необходимых для крафта ресурсов
    // полученное значение в формате строки возвращается обратно
    public String craftResource(Map map, String loot) {
        String needResource = craftScriptParser(map, loot);
        return needResource;
    }

    // обработчик скрипта, который выясняет кол-во необходимых для крафта ресурсов
    // полученное значение в формате строки возвращается обратно
    private String craftScriptParser(Map map, String loot)
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
                        if (needResource != "") needResource = needResource + " & ";
                        needResource = needResource + craftNeedResource(map, script, param, count);
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

    // метод возвращает строку с кол-вом необходимых ресурсов назад по цепочке
    private String craftNeedResource(Map map, String script, String param, String count)
    {
        int loot = -1;
        if (count == "") count = "1";
        int resCount = Integer.parseInt(count);
        resCount = resCount * 2;
        String needResource = "";
        switch (script){
            case "I_EMPTY_JAR" :
                needResource = needResource + "Пустая банка (" + map.getGame().player.howMuchItemPlayerHave(ItemSet.getEmptyJarID()) + "/" + resCount + ")";
                loot = 1;
                break;
            case "I_LEATHER" :
                needResource = needResource + "Кожа (" + map.getGame().player.howMuchItemPlayerHave(ItemSet.getLeatherMaterialID()) + "/" + resCount + ")";
                loot = 1;
                break;
            case "I_METALS" :
                needResource = needResource + "Металл (" + map.getGame().player.howMuchItemPlayerHave(ItemSet.getMetalMaterialID()) + "/" + resCount + ")";
                loot = 1;
                break;
            case "I_EMPTY_SCROOL" :
                needResource = needResource + "Пустой свиток (" + map.getGame().player.howMuchItemPlayerHave(ItemSet.getEmptyScrollID()) + "/" + resCount + ")";
                loot = 1;
                break;
            case "I_WOOD" :
                needResource = needResource + "Древесина (" + map.getGame().player.howMuchItemPlayerHave(ItemSet.getWoodMaterialID()) + "/" + resCount + ")";
                loot = 1;
                break;
            default: break;
        }
        if (loot >= 0) return needResource;
        else return "ОШИБКА!! Неправильно указан требуемый ресурс GameObject.craftNeedResource";
    }

    // вызывает обработчик скрипта выясняющий какие ресурсы требуются для крафта
    public void doCraft(String loot, int id) {
        doCraftScriptParser(loot, id);
    }

    // расшифровывает скрипт и передает данные в doCraftNeedResource
    // высчитывающий необходимые ресурсы
    private void doCraftScriptParser(String loot, int id)
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
                        doCraftNeedResource(script, param, count, id);
                        script = "";
                        param = "";
                        count = "";
                        break;
                    }
                }
            }
        }
    }

    // param не используется, а надо ли?
    // вычитает необходимое кол-во ресурсов для крафта из инвентаря игрока
    private void doCraftNeedResource(String script, String param, String count, int id)
    {
        int loot = -1;
        if (count == "") count = "1";
        int needRes = Integer.parseInt(count);
        needRes = needRes * 2; // для крафта требуется в два раза больше ресурсов, чем может выпасть из крафтуемого предмета
        switch (script){
            case "I_EMPTY_JAR" :
                if (getGame().player.removeItemForCraft(needRes, ItemSet.getEmptyJarID())) loot = 1;
                break;
            case "I_LEATHER" :
                if (getGame().player.removeItemForCraft(needRes, ItemSet.getLeatherMaterialID())) loot = 1;
                break;
            case "I_WOOD" :
                if (getGame().player.removeItemForCraft(needRes, ItemSet.getWoodMaterialID())) loot = 1;
                break;
            case "I_METALS" :
                if (getGame().player.removeItemForCraft(needRes, ItemSet.getMetalMaterialID())) loot = 1;
                break;
            case "I_EMPTY_SCROOL" :
                if (getGame().player.removeItemForCraft(needRes, ItemSet.getEmptyScrollID())) loot = 1;
                break;
            default: break;
        }
        if (loot == 1) getGame().player.addItemToInventory(id, 1);
    }

    public boolean getDestroyable(){return this.destroyable;}
    public int getLife(){return this.life.getMax();}
    public String getLoot(){return this.loot;}
    public String getWeaknessFor(){return this.weaknessFor;}
}