package gcg.testproject.activity.chongDianDongHua;

/**
 * Created by gongchenghao on 2019/6/28 0028.
 * describe:
 */
public class BubbleBean
{
    private float randomY=3;
    private float x;
    private float y;
    private int index;
    public BubbleBean(float x, float y,float randomY,int index) {
        this.x = x;
        this.y = y;
        this.randomY = randomY;
        this.index = index;
    }

    public void set(float x, float y,float randomY,int index){
        this.x = x;
        this.y = y;
        this.randomY = randomY;
        this.index = index;
    }

    public void setMove(int screenHeight,int maxDistance){
        if (y-maxDistance<110){
            this.y-=2;
            return;
        }

        if (maxDistance<=y&&screenHeight-y>110){
            this.y-=randomY;
        }else {
            this.y-=0.6;//气泡开始的移动的时候比较慢，造成气泡开始的 黏性气泡
        }

        if (index==0){
            this.x-=0.4;
        }else if (index==2){
            this.x+=0.4;
        }
    }


    public int getIndex(){
        return  index;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
