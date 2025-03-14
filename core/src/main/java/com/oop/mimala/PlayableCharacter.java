package com.oop.mimala;

public class PlayableCharacter extends Character{
    private String name;
    private String specialTrait;
    private String weakness;

    public PlayableCharacter(String name, float attackDamage, float specialAttackDamage, float speed, float stamina, float defense, float attackSpeed, float health, String specialTrait, String weakness) {
        super(attackDamage, specialAttackDamage, speed, stamina, defense, attackSpeed, health);
        this.name = name;
        this.specialTrait = specialTrait;
        this.weakness = weakness;
    }

    //passive traits etc.
    @Override
    public void specialAbility() {
        System.out.println(name + " uses their unique ability!");
    }

    //still optional
    @Override
    public void displayStats() {
        System.out.println("Character: " + name);
        super.displayStats();
        System.out.println("Special Trait: " + specialTrait);
        System.out.println("Weakness: " + weakness);
    }
}
