package org.jit.sose.eschool.lunbotu;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 该类是图片轮播的核心类
 */

public class ImageBarnnerViewGroup extends ViewGroup{

    private  int children;    //我们子视图总个数
    private int childwidth;        //子视图宽度
    private int childheight;      //子视图高度

    private  int x;
    private  int index = 0;

    private Scroller scroller;

    /**
     * 点击事件的获取方法：利用一个单击变量开关进行判断在用户离开屏幕一瞬间是移动还是点击
     */

    private boolean isClick;

    private ImageBarnnerListener listener;

    public ImageBarnnerListener getListener() {
        return listener;
    }

    public void setListener(ImageBarnnerListener listener) {
        this.listener = listener;
    }

    public interface ImageBarnnerListener{
        void clickImageIndex(int pos);    //pos代表当前图片的具体索引值
    }

    private ImageBarnnerViewGroupListener barnnerViewGroupListener;

    public ImageBarnnerViewGroupListener getBarnnerViewGroupListener() {
        return barnnerViewGroupListener;
    }

    public void setBarnnerViewGroupListener(ImageBarnnerViewGroupListener barnnerViewGroupListener) {
        this.barnnerViewGroupListener = barnnerViewGroupListener;
    }

    /**
     * 实现图片轮播的底部圆点以及底部圆点切换步骤思路
     * 1.我们要自定义一个继承Framelayout的布局，利用Framelayout布局的特性（在同一位置放置不同的View最终显示的是最后一个View）
     * 2.我们需要准备素材，就是底部圆点的素材
     * 3.继承Framelayout来自定义一个类，在该类的实现过程中，我们去加载我们自定义的ImageBarnnerViewGroup核心类和我们需要的底部圆点的布局，用linerlayout实现
     */


    //自动轮播
    private boolean isAuto = true;    //默认开启自动轮播
    private Timer timer = new Timer();
    private TimerTask task;
    private Handler autoHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:  //此时我们需要图片的自动轮播

                    if (++index >= children){           //说明如果是最后一张图片，则从重头开始滑动
                        index = 0;
                    }
                    scrollTo(childwidth*index,0);
                    barnnerViewGroupListener.selectImage(index);//1
                    break;
            }
        }
    };

    private void startAuto(){

        isAuto=true;
    }

    private void stopAuto(){

        isAuto=false;
    }




    /**
     * 采用Timer，TimerTask,Handler三者相结合的方式来实现自动轮播
     * 用startAuto（），stopAuto（）来控制是否自动轮播，实际希望控制的是自动开启轮播图的开关
     * @param context
     */



    public ImageBarnnerViewGroup(Context context) {
        super(context);
        initObj();
    }

    public ImageBarnnerViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        initObj();
    }

    public ImageBarnnerViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initObj();
    }

    private void initObj(){

        scroller = new Scroller(getContext());

        task = new TimerTask() {
            @Override
            public void run() {
                if (isAuto){            //开启轮播图
                    autoHandler.sendEmptyMessage(0);
                }
            }
        };
        timer.schedule(task,100,3000);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(scroller.computeScrollOffset()){
            scrollTo(scroller.getCurrX(),0);
            invalidate();
        }
    }

    /*
     *我们在自定义viewgroup中，必须要实现的方法有：测量   布局    绘制
     */

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        /*
         *由于我们要实现的是一个VIEWGROUP的容器，那么我们就应该需要知道该容器中的所有子视图
         * 我们要想测量我们的viewgroup的宽度和高度，我们就必须先去测量子视图的宽度和高度之和
         */

        //1.求出子视图的个数
        children =  getChildCount();
        if(0 == children){
            setMeasuredDimension(0,0);
        }
        else{
            //2.测量子视图的宽度和高度
            measureChildren(widthMeasureSpec,heightMeasureSpec);
            //此时我们以第一个子视图为基准，即viewgroup的高度为第一个子视图的高度，宽度为第一个子视图的宽度*子视图个数
            View view=getChildAt(0);//因为此时第一个视图绝对是存在的

            //3.根据子视图的宽度和高度，来求出改viewgroup的宽度和高度
            childwidth=view.getMeasuredWidth();
            childheight=view.getMeasuredHeight();
            int width=view.getMeasuredWidth()*children;
            setMeasuredDimension(width,childheight);
        }

    }

    /*
     *事件传递过程中的调用方法：调用容器的拦截方法onInterceptTouchEvent
     */

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    /*
     *实现轮播图的手动轮播
     */


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:    //用户按下的一瞬间
                stopAuto();
                if(!scroller.isFinished()){
                        scroller.abortAnimation();
                }
                isClick=true;
                x=(int)event.getX();
                break;
            case MotionEvent.ACTION_MOVE:    //用户按下之后再屏幕移动的过程
                int moveX=(int)event.getX();
                int distance= moveX - x;
                scrollBy(-distance,0);
                x=moveX;
                isClick=false;
                break;
            case MotionEvent.ACTION_UP:      //用户抬起的一瞬间

                int scrollX = getScrollX();
                index = (scrollX+childwidth/2)/childwidth;
                if(index<0){            //已经滑到最左边第一张图片
                    index=0;
                }else if(index>children-1){             //已将滑到了最右边最后一张图片
                    index=children-1;
                }

                if(isClick){
                    listener.clickImageIndex(index);
                }else{
                    int dx = index*childwidth-scrollX;
                    scroller.startScroll(scrollX,0,dx,0);
                    postInvalidate();   //2
                    barnnerViewGroupListener.selectImage(index);
                }

                startAuto();
            //scrollTo(index*childwidth,0);

                break;
            default:
        }
        return true;
    }
    /*
     *继承viewgroup必须要实现布局onlayout方法
     * boolean changed:代表当我们viewgroup布局位置发生改变的时候为true，没有发生改变为false
     *
     */

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        if(changed){
            int leftMargin = 0;
            for(int i=0;i<children;i++){
                View view = getChildAt(i);
                view.layout(leftMargin,0,leftMargin + childwidth,childheight);
                leftMargin +=childwidth;
            }
        }

    }


    public  interface ImageBarnnerViewGroupListener{
        void selectImage(int index);
    }

}
