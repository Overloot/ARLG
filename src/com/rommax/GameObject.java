package com.rommax;

/**
 * Created by Maxim on 18.11.2015.
 */
public class GameObject extends Entity {

    // �������
    private Stat RFire;
    private Stat RCold;
    private Stat RElec;
    private Stat RPoison;
    private Stat RNormal;

    private Stat life;

    private String loot;
    //TODO: ����������� ��� ��������, � ������� ����� ���������� ��������� ���.
    // ��������� ����������: wood|95|ENTS|5
    // ��������, ��� � ����������� 95% ������� ���������, � � 5% ������� ������ ������ �����.

    private String behavior; //TODO: ��������� �������. ����� �� �� �������� ������, ���� ��� ���� � �.�.

    private boolean destroyable; //TODO: ����������� ������ ��� ���.
    // �����������, ��� ��� ������� �����������, �� ��� ��������� ������ ��� ������?

    private String weaknessFor; //TODO: ����� ����� ����������� �� ����� loot, ��������� ����������:
    // SWORD|50|AXE|100|ARROW|0|MLIGHT|100
    // �������, ���, � ������� ������, �������� 50% ����������� �� ����� �����, 100% �� ����� �������,
    // 0% �� ����� ������� � 100% �� ����� ������ �����
    // ������ ������ ��������. �� �������� ������� �� ������� ����� ��� ����������� ����� =)

    // ����� ����� ������ � ��������� loot � ������������� ��� �������� ������. �.�. ��� �����
    // ���������� �� ������ ��������� �� �������, ��� ����� ������� ����� ���������, � ��� �������������
    // ������� ����� ����� ���������� ����, �� �� ��������� =)

    public GameObject(int maxHP, int RNormal, int RFire, int RCold, int RElec, int RPoison) {
        //�������
        this.RFire = new Stat(RFire, RFire);
        this.RCold = new Stat(RCold, RCold);
        this.RPoison = new Stat(RPoison, RPoison);
        this.RElec = new Stat(RElec, RElec);
        this.RNormal = new Stat(RNormal, RNormal);

        this.life = new Stat(maxHP, maxHP);
    }

    public GameObject(int id, String name, String path, int level) { //TODO: ������� ��� BaseMonster
        super(id, name, path, level);
    }
}
