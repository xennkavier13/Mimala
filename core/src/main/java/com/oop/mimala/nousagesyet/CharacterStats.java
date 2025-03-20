package com.oop.mimala.nousagesyet;

public interface CharacterStats {
    //setters
    void setAttackDamage(float damage);
    void setSpecialAttackDamage(float damage);
    void setSpeed(float speed);
    void setStamina(float stamina);
    void setDefense(float defense);
    void setAttackSpeed(float attackSpeed);
    void setHealth(float health);

    //getters
    float getAttackDamage();
    float getSpecialAttackDamage();
    float getSpeed();
    float getStamina();
    float getDefense();
    float getAttackSpeed();
    float getHealth();

    //optional display (maybe of some use later)
    void displayStats();
}
