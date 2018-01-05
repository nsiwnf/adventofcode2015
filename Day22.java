

import java.util.Stack;

public class Day22 {

  private static final int MISSILE = 53;

  private static final int DRAIN = 73;

  private static final int SHIELD = 113;

  private static final int POISON = 173;

  private static final int RECHARGE = 229;

  private static int part1() {
    int minSpent = Integer.MAX_VALUE;

    Stack<Point> points = new Stack<>();
    Point start = new Point(55, 50, 500, 0);
    // Point start = new Point(13, 10, 250, 0);
    start.playerTurn = true;
    points.add(start);

    while (!points.isEmpty()) {
      Point p = points.pop();

      p.applyEffects();

      if (p.won()) {
        minSpent = Math.min(minSpent, p.manaSpent);
      }
      else if (p.hp > 0 && p.manaSpent < minSpent) {
        if (p.playerTurn) {
          if (p.mana > MISSILE) {
            points.push(
                new Point(p.bossHp - 4, p.hp, p.mana - MISSILE, p.shield, p.poison, p.recharge, p.manaSpent + MISSILE));
          }
          if (p.mana > DRAIN) {
            points.push(
                new Point(p.bossHp - 2, p.hp + 2, p.mana - DRAIN, p.shield, p.poison, p.recharge, p.manaSpent + DRAIN));
          }
          if (p.mana > SHIELD && p.shield == 0) {
            points.push(new Point(p.bossHp, p.hp, p.mana - SHIELD, 6, p.poison, p.recharge, p.manaSpent + SHIELD));
          }
          if (p.mana > POISON && p.poison == 0) {
            points.push(new Point(p.bossHp, p.hp, p.mana - POISON, p.shield, 6, p.recharge, p.manaSpent + POISON));
          }
          if (p.mana > RECHARGE && p.recharge == 0) {
            points.push(new Point(p.bossHp, p.hp, p.mana - RECHARGE, p.shield, p.poison, 5, p.manaSpent + RECHARGE));
          }
        }
        else {
          p.hp -= 8;
          p.playerTurn = true;
          points.push(p);
        }
      }

    }

    return minSpent;
  }

  static class Point {
    boolean playerTurn = false;

    int bossHp;

    int hp;

    int mana;

    int manaSpent;

    // counters
    int shield;

    int poison;

    int recharge;

    Point(int bossHp, int hp, int mana, int manaSpent) {
      this.bossHp = bossHp;
      this.hp = hp;
      this.mana = mana;
      this.manaSpent = manaSpent;
    }

    Point(int bossHp, int hp, int mana, int shield, int poison, int recharge, int manaSpent) {
      this.bossHp = bossHp;
      this.hp = hp;
      this.mana = mana;
      this.shield = shield;
      this.poison = poison;
      this.recharge = recharge;
      this.manaSpent = manaSpent;
    }

    boolean won() {
      return bossHp <= 0 && hp > 0;
    }

    void applyEffects() {
      if(playerTurn) {
        hp--;
      }
      if (bossHp > 0 && hp > 0) {
        if (shield > 0) {
          if (!playerTurn && hp > 1)
            hp += 7;
          shield--;
        }

        if (poison > 0) {
          bossHp -= 3;
          poison--;
        }

        if (recharge > 0) {
          mana += 101;
          recharge--;
        }
      }
    }
  }

  public static void main(String[] args) {
    // BOSS
    // Hit Points: 55
    // Damage: 8

    // YOU
    // HP 50
    // Mana 500
    System.out.println(part1()); // 953

  }
}
