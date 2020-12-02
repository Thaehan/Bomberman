package entities.character;

import java.util.ArrayList;
import factory.Board;
import factory.Game;
import entities.Entity;
import entities.bomb.Bomb;
import graphics.Renderer;
import graphics.Sprite;
import factory.Controller;

import java.util.Iterator;
import java.util.List;
import entities.bomb.Flame;
import entities.bomb.FlameRange;
import entities.character.enemy.Enemy;
import entities.barrier.item.Item;
import entities.PositionHandling;

public class Bomber extends Character {
    private List<Bomb> bomb;
    protected Controller input;
    public static List<Item> _powerups = new ArrayList<Item>();
    
    
    public void powerUp(Item item) {
        if(item.isRemoved()) return;
        _powerups.add(item);
        item.powerUp ();
    }
    
    
    /**
     * nếu giá trị này < 0 thì cho phép đặt đối tượng Bomb tiếp theo,
     * cứ mỗi lần đặt 1 Bomb mới, giá trị này sẽ được reset về 0 và giảm dần trong mỗi lần update()
     */
    protected int bombPlantDelay = 0;

    public Bomber(int x, int y, Board board) {
        super(x, y, board);
        bomb = this.board.getBombs();
        input = this.board.getInput();
        sprite = Sprite.player_right;
        
    }

    @Override
    public void update() {
        clearBombs();
        if (!isAlive) {
            afterKill();
            return;
        }

        if (bombPlantDelay < -7500) bombPlantDelay = 0;
        else bombPlantDelay--;

        animate();
        calculateMove();
        checkPlacedBom ();
    }

    @Override
    public void render(Renderer renderer) {
        if (isAlive)
            chooseSprite();
        else
            sprite = Sprite.player_dead1;

        renderer.renderEntity((int) positionX , (int) positionY - sprite.SIZE, this);
    }

    // kiểm tra  nút đăt bom ,
    // số quả đặt bomrate >0
    // thời gian giữa 2 lần đăt bom lien tiếp
    // toa độ trung tâm của nhân vật (trung tâm của) trong tâm
    // giảm số bom có thể đặt đi 1 quả
    // set thời gian chờ cho đợt bom tới

    private void checkPlacedBom() {
        if(bombPlantDelay < 0 && Game.getPresentBombs () > 0 && input.space) {
            int xg = PositionHandling.pixelToTile(positionX + sprite.getSize() / 2);
            int yg = PositionHandling.pixelToTile((positionY + sprite.getSize() / 2) - sprite.getSize());
            placeBomb(xg,yg);
            Game.addBombRate(-1);
            bombPlantDelay = 25;
        }
    }

    protected void placeBomb(int x, int y) {
        Bomb bom = new Bomb(x, y, board);
        board.addBomb(bom);
    }

    private void clearBombs() {
        Iterator<Bomb> bs = bomb.iterator();
        Bomb b;
        while (bs.hasNext()) {
            b = bs.next();
            if (b.isRemoved()) {
                bs.remove();
                Game.addBombRate(1);
            }
        }
    }

    public void kill() {
        if (!isAlive) {
            return;
        }
        isAlive = false;
        board.addLives(-1);
    }

    protected void afterKill() {
        if (timeAfter > 0) {
            --timeAfter;
        }
        else {
            if(bomb.size() == 0) {
				if(board.getLives() == 0) {
                    board.endGame();
                }
				else{
					board.restartLevel();
					this._powerups.clear();
					Game.resetPower ();
				}
			}
        }
    }

    protected void calculateMove() {
        int xa = 0, ya = 0;
        if(input.left) xa--;
        if(input.right) xa++;
        if(input.up) ya--;
	    if(input.down) ya++;
	    // kiểm tra xem đã ấn nút để di chuyển chưa? rồi  gọi move() để thực hiện di chuyển
	    if(xa != 0 || ya != 0)  {
	        // di chuyển phụ thộc tốc độ
		    move(xa * Game.getBomberSpeed(), ya * Game.getBomberSpeed());
            moveAble = true;
                
	    }
	    else {
		    moveAble = false;
	    }
    }


    /*
       x y nhân 2 3 giá trị  0 +- 1    tương ứng 3 trạng thái đứng im và di chuyển
        _x _y top left của nhân vật
        //chia cho toan độ 1 vi
        từ tọa độ của boober _x, _y trong R^2 thì ta chuyển nó về tọa độ của ô
        dọc 13 ô , ngang 31/ 15 ô
        c0 c1 //  trên
        c2 c3 //  dưới
     */
    public boolean canMove(double x, double y) {

        for (int c = 0; c < 4; c++) { //kiểm tra 4 góc 

            double xt = ((positionX + x ) + c % 2 * 11 ) / Game.DEFAULT_SIZE;
            double yt = ((positionY + y ) + c / 2 * 12 - 15 ) / Game.DEFAULT_SIZE;

            Entity a = board.getEntity(xt, yt, this);
		    if(!a.collide(this)) {
                return false;
            }
	    }
        return true;
    }

    //              1 sang trái
    //              3 sang phải
    //              2 lên trên
    //              0 xuống dưới
    //              thông số đê choosepire chọn
    // kiểm tra xem liệu nước tiếp theo  có đi được không ?
    @Override
    public void move(double xa, double ya) {
        if(xa > 0) moveMent = 1;
        if(xa < 0) moveMent = 3;
        if(ya > 0) moveMent = 2;
        if(ya < 0) moveMent = 0;

	    if (canMove(0, ya)) {
            positionY += ya;
	    }
		
	    if (canMove(xa, 0)) {
            positionX += xa;
	    }

    }

    public boolean collide(Entity e) {
        if (e instanceof Enemy){
            kill();
            return true;           
        }
        if (e instanceof FlameRange) {
            kill();
            return true;
        }

         // xử lý va chạm với Flame của bom
        if (e instanceof Flame) {
            kill();
            return true;
        }
        if (e instanceof Bomb){
            if (((Bomb) e).isExploded() && ((Bomb)e).isAllowedToPassThroght()){
                kill();
                return true;
            }
        }

        return true;
    }

    private void chooseSprite() {
        switch (moveMent) {
            case 0:
                sprite = Sprite.player_up;
                if (moveAble) {
                    sprite = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, animation , 20);
                }
                break;
            case 1:
                if (moveAble) {
                    sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, animation , 20);
                }
                break;
            case 2:
                sprite = Sprite.player_down;
                if (moveAble) {
                    sprite = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, animation , 20);
                }
                break;
            case 3:
                sprite = Sprite.player_left;
                if (moveAble) {
                    sprite = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, animation , 20);
                }
                break;
            default:
                sprite = Sprite.player_right;
                if (moveAble) {
                    sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, animation , 20);
                }
                break;
        }
    }

    public List<Bomb> getBombs() {
        return bomb;
    }
    
     
}
