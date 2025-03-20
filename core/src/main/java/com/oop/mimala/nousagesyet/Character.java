package com.oop.mimala.nousagesyet;

abstract class Character implements CharacterStats {
    //protected used here for classes within the same package can access
    protected float attackDamage;
    protected float specialAttackDamage;
    protected float speed;
    protected float stamina;
    protected float defense;
    protected float attackSpeed;
    protected float health;

    //idk about this yet, gipa debug ra nako sa ai nya gahatag siya ni constructor so optional maybe, it depends
    public Character(float attackDamage, float specialAttackDamage, float speed, float stamina, float defense, float attackSpeed, float health) {
        this.attackDamage = attackDamage;
        this.specialAttackDamage = specialAttackDamage;
        this.speed = speed;
        this.stamina = stamina;
        this.defense = defense;
        this.attackSpeed = attackSpeed;
        this.health = health;
    }

    @Override
    public void setAttackDamage(float damage) {
        this.attackDamage = damage;
    }

    @Override
    public void setSpecialAttackDamage(float damage) {
        this.specialAttackDamage = damage;
    }

    @Override
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    @Override
    public void setStamina(float stamina) {
        this.stamina = stamina;
    }

    @Override
    public void setDefense(float defense) {
        this.defense = defense;
    }

    @Override
    public void setAttackSpeed(float attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    @Override
    public void setHealth(float health) {
        this.health = health;
    }

    @Override
    public float getAttackDamage() {
        return attackDamage;
    }

    @Override
    public float getSpecialAttackDamage() {
        return specialAttackDamage;
    }

    @Override
    public float getSpeed() {
        return speed;
    }

    @Override
    public float getStamina() {
        return stamina;
    }

    @Override
    public float getDefense() {
        return defense;
    }

    @Override
    public float getAttackSpeed() {
        return attackSpeed;
    }

    @Override
    public float getHealth() {
        return health;
    }

    //still optional (maybe of use later)
    @Override
    public void displayStats() {
        System.out.println("Attack Damage: " + attackDamage);
        System.out.println("Special Attack Damage: " + specialAttackDamage);
        System.out.println("Speed: " + speed);
        System.out.println("Stamina: " + stamina);
        System.out.println("Defense: " + defense);
        System.out.println("Attack Speed: " + attackSpeed);
        System.out.println("Health: " + health);
    }

    //maybe for display, but used for calculations for the passive traits and updating the base stats
    public abstract void specialAbility();
}
