/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.character.enemy;

import java.util.Random;


public class Bot {
    Random random = new Random();
    public Bot() {

    }
    public int calculateDirection() {       
        return random.nextInt(4);
    }
}
