/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.character.enemy.ai;

/**
 *  dùng AIRandom cho Minvo  cho màn có  AIBomber  chơi
 * @author DoQuangTrung
 */
public class AIRandom extends AI{

    @Override
    public int calculateDirection() {       
        return random.nextInt(4);

    }
    
    
}
