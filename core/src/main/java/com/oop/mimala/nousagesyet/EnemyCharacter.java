package com.oop.mimala.nousagesyet;

public class EnemyCharacter extends Character {
    private String name;
    private String specialTrait;
    private String weakness;

    public EnemyCharacter(String name, float attackDamage, float specialAttackDamage, float speed, float stamina, float defense, float attackSpeed, float health, String specialTrait, String weakness) {
        super(attackDamage, specialAttackDamage, speed, stamina, defense, attackSpeed, health);
        this.name = name;
        this.specialTrait = specialTrait;
        this.weakness = weakness;
    }

    //passive traits etc.
    @Override
    public void specialAbility() {
        System.out.println(name + " unleashes a terrifying power!");
    }

    //still optional
    @Override
    public void displayStats() {
        System.out.println("Enemy: " + name);
        super.displayStats();
        System.out.println("Special Trait: " + specialTrait);
        System.out.println("Weakness: " + weakness);
    }
}
