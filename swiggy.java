1. Player Class

public class Player {
    private String name;
    private int health;
    private int strength;
    private int attack;

    public Player(String name, int health, int strength, int attack) {
        this.name = name;
        this.health = health;
        this.strength = strength;
        this.attack = attack;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getStrength() {
        return strength;
    }

    public int getAttack() {
        return attack;
    }

    public boolean isAlive() {
        return this.health > 0;
    }
}

2. Die Class


import java.util.Random;

public class Die {
    private static final int SIDES = 6;
    private Random random;

    public Die() {
        this.random = new Random();
    }

    public int roll() {
        return random.nextInt(SIDES) + 1;
    }
}


3. MagicalArena Class

public class MagicalArena {
    private Player playerA;
    private Player playerB;
    private Die attackingDie;
    private Die defendingDie;

    public MagicalArena(Player playerA, Player playerB) {
        this.playerA = playerA;
        this.playerB = playerB;
        this.attackingDie = new Die();
        this.defendingDie = new Die();
    }

    public void startBattle() {
        while (playerA.isAlive() && playerB.isAlive()) {
            takeTurn(playerA, playerB);
            if (!playerB.isAlive()) break;
            takeTurn(playerB, playerA);
        }

        if (playerA.isAlive()) {
            System.out.println(playerA.getName() + " wins!");
        } else {
            System.out.println(playerB.getName() + " wins!");
        }
    }

    private void takeTurn(Player attacker, Player defender) {
        int attackRoll = attackingDie.roll();
        int defendRoll = defendingDie.roll();

        int attackDamage = attacker.getAttack() * attackRoll;
        int defendStrength = defender.getStrength() * defendRoll;
        int damageToDefender = Math.max(0, attackDamage - defendStrength);

        defender.setHealth(defender.getHealth() - damageToDefender);

        System.out.println(attacker.getName() + " attacks and rolls " + attackRoll + ". " + defender.getName() + " defends and rolls " + defendRoll + ".");
        System.out.println(attacker.getName() + " deals " + damageToDefender + " damage to " + defender.getName() + ". " + defender.getName() + "'s health is now " + defender.getHealth() + ".");
    }
}


4. Main Class

public class Main {
    public static void main(String[] args) {
        Player playerA = new Player("Player A", 50, 5, 10);
        Player playerB = new Player("Player B", 100, 10, 5);

        MagicalArena arena = new MagicalArena(playerA, playerB);
        arena.startBattle();
    }
}


5. Unit Tests

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlayerTest {
    private Player player;

    @BeforeEach
    public void setUp() {
        player = new Player("TestPlayer", 50, 5, 10);
    }

    @Test
    public void testPlayerInitialization() {
        assertEquals("TestPlayer", player.getName());
        assertEquals(50, player.getHealth());
        assertEquals(5, player.getStrength());
        assertEquals(10, player.getAttack());
    }

    @Test
    public void testHealthModification() {
        player.setHealth(40);
        assertEquals(40, player.getHealth());
    }

    @Test
    public void testIsAlive() {
        assertTrue(player.isAlive());
        player.setHealth(0);
        assertFalse(player.isAlive());
    }
}

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DieTest {
    private Die die;

    @BeforeEach
    public void setUp() {
        die = new Die();
    }

    @Test
    public void testDieRoll() {
        for (int i = 0; i < 100; i++) {
            int roll = die.roll();
            assertTrue(roll >= 1 && roll <= 6);
        }
    }
}

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MagicalArenaTest {
    private Player playerA;
    private Player playerB;
    private MagicalArena arena;

    @BeforeEach
    public void setUp() {
        playerA = new Player("Player A", 50, 5, 10);
        playerB = new Player("Player B", 100, 10, 5);
        arena = new MagicalArena(playerA, playerB);
    }

    @Test
    public void testBattle() {
        arena.startBattle();
        assertTrue(!playerA.isAlive() || !playerB.isAlive());
    }
}
