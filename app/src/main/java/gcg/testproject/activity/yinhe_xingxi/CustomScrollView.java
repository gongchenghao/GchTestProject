package gcg.testproject.activity.yinhe_xingxi;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import java.util.List;

import gcg.testproject.common.Word;
import gcg.testproject.utils.DensityUtils;
import gcg.testproject.utils.LogUtils;
import gcg.testproject.utils.ScreenUtils;

public class CustomScrollView extends FrameLayout{

	View mView;

	View mView2;

	static final int ANIMATED_SCROLL_GAP = 250;


	static final float MAX_SCROLL_FACTOR = 0.5f;


	private long mLastScroll;


	private final Rect mTempRect = new Rect();

	private Scroller mScroller;

	private boolean mScrollViewMovedFocus;


	private float mLastMotionY;

	private float mLastMotionX;

	private boolean mIsLayoutDirty = true;


	private View mChildToScrollTo = null;


	private boolean mIsBeingDragged = false;


	private VelocityTracker mVelocityTracker;


	private boolean mFillViewport;


	private boolean mSmoothScrollingEnabled = true;


	private int mTouchSlop;
	private int mMinimumVelocity;
	private int mMaximumVelocity;


	private int mActivePointerId = INVALID_POINTER;

	private static final int INVALID_POINTER = -1;


	private boolean mFlingEnabled = true; //判断是否需要惯性滑动

	private float downX = 0; //手指落下的点，缩放时用
	private float downY = 0; //手指落下的点，缩放时用

	public CustomScrollView(Context context) {
		this(context, null);
	}


	public CustomScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initScrollView();
	}


	@Override
	protected float getTopFadingEdgeStrength() {
		if (getChildCount() == 0) {
			return 0.0f;
		}


		final int length = getVerticalFadingEdgeLength();
		if (getScrollY() < length) {
			return getScrollY() / (float)length;
		}


		return 1.0f;
	}


	@Override
	protected float getLeftFadingEdgeStrength() {
		if (getChildCount() == 0) {
			return 0.0f;
		}


		final int length = getHorizontalFadingEdgeLength();
		if (getScrollX() < length) {
			return getScrollX() / (float)length;
		}


		return 1.0f;
	}


	@Override
	protected float getRightFadingEdgeStrength() {
		if (getChildCount() == 0) {
			return 0.0f;
		}


		final int length = getHorizontalFadingEdgeLength();
		final int rightEdge = getWidth() - getPaddingRight();
		final int span = getChildAt(0).getRight() - getScrollX() - rightEdge;
		if (span < length) {
			return span / (float)length;
		}


		return 1.0f;
	}


	@Override
	protected float getBottomFadingEdgeStrength() {
		if (getChildCount() == 0) {
			return 0.0f;
		}


		final int length = getVerticalFadingEdgeLength();
		final int bottomEdge = getHeight() - getPaddingBottom();
		final int span = getChildAt(0).getBottom() - getScrollY() - bottomEdge;
		if (span < length) {
			return span / (float)length;
		}


		return 1.0f;
	}


	/**
	 * @return The maximum amount this scroll view will scroll in response to
	 *   an arrow event.
	 */
	public int getMaxScrollAmountV() {
		return (int)(MAX_SCROLL_FACTOR * (getBottom() - getTop()));
	}


	public int getMaxScrollAmountH() {
		return (int)(MAX_SCROLL_FACTOR * (getRight() - getLeft()));
	}


	private void initScrollView() {
		mScroller = new Scroller(getContext());
		setFocusable(true);
		setDescendantFocusability(FOCUS_AFTER_DESCENDANTS);
		setWillNotDraw(false);
		final ViewConfiguration configuration = ViewConfiguration.get(getContext());
		mTouchSlop = configuration.getScaledTouchSlop();//触发事件距离
		mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();//最低速度
		mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();//最高速度
	}


	//只能包含一个子视图

	@Override
	public void addView(View child) {
		if (getChildCount() > 0) {
			throw new IllegalStateException("ScrollView can host only one direct child");
		}
		super.addView(child);
	}


	@Override
	public void addView(View child, int index) {
		if (getChildCount() > 0) {
			throw new IllegalStateException("ScrollView can host only one direct child");
		}


		super.addView(child, index);
	}


	@Override
	public void addView(View child, ViewGroup.LayoutParams params) {
		if (getChildCount() > 0) {
			throw new IllegalStateException("ScrollView can host only one direct child");
		}
		super.addView(child, params);
	}


	@Override
	public void addView(View child, int index, ViewGroup.LayoutParams params) {
		if (getChildCount() > 0) {
			throw new IllegalStateException("ScrollView can host only one direct child");
		}
		super.addView(child, index, params);
	}


	//垂直滚动
	private boolean canScrollV() {
		View child = getChildAt(0);
		if (child != null) {
			int childHeight = child.getHeight();
			return getHeight() < childHeight + getPaddingTop() + getPaddingBottom();
		}
		return false;
	}

	//水平滚动
	private boolean canScrollH() {
		View child = getChildAt(0);
		if (child != null) {
			int childWidth = child.getWidth();
			return getWidth() < childWidth + getPaddingLeft() + getPaddingRight();
		}
		return false;
	}


	/**
	 * 表明内容是否达到填充视窗
	 *
	 * @return True if the content fills the viewport, false otherwise.
	 */
	public boolean isFillViewport() {
		return mFillViewport;
	}


	/**
	 * Indicates this ScrollView whether it should stretch its content height to fill
	 * the viewport or not.
	 *
	 * @param fillViewport True to stretch the content's height to the viewport's
	 *        boundaries, false otherwise.
	 */
	public void setFillViewport(boolean fillViewport) {
		if (fillViewport != mFillViewport) {
			mFillViewport = fillViewport;
			requestLayout();
		}
	}



	//设置箭头推动过滤
	/**
	 * @return Whether arrow scrolling will animate its transition.
	 *
	 */
	public boolean isSmoothScrollingEnabled() {
		return mSmoothScrollingEnabled;
	}


	/**
	 * Set whether arrow scrolling will animate its transition.
	 * @param smoothScrollingEnabled whether arrow scrolling will animate its transition
	 */
	public void setSmoothScrollingEnabled(boolean smoothScrollingEnabled) {
		mSmoothScrollingEnabled = smoothScrollingEnabled;
	}


	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);


		if (!mFillViewport) {
			return;
		}


		final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		if (heightMode == MeasureSpec.UNSPECIFIED && widthMode == MeasureSpec.UNSPECIFIED) {
			return;
		}


		if (getChildCount() > 0) {
			final View child = getChildAt(0);
			int height = getMeasuredHeight();
			int width = getMeasuredWidth();
			if (child.getMeasuredHeight() < height || child.getMeasuredWidth() < width) {
				width -= getPaddingLeft();
				width -= getPaddingRight();
				int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);


				height -= getPaddingTop();
				height -= getPaddingBottom();
				int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
						MeasureSpec.EXACTLY);


				child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
			}
		}
	}


	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		// Let the focused view and/or our descendants get the key first
		return super.dispatchKeyEvent(event) || executeKeyEvent(event);
	}


	/**
	 * You can call this function yourself to have the scroll view perform
	 * scrolling from a key event, just as if the event had been dispatched to
	 * it by the view hierarchy.
	 *
	 * @param event The key event to execute.
	 * @return Return true if the event was handled, else false.
	 */
	public boolean executeKeyEvent(KeyEvent event) {
		mTempRect.setEmpty();


		boolean handled = false;


		if (event.getAction() == KeyEvent.ACTION_DOWN) {
			switch (event.getKeyCode()) {
				case KeyEvent.KEYCODE_DPAD_LEFT:
					if (canScrollH()) {
						if (!event.isAltPressed()) {
							handled = arrowScrollH(View.FOCUS_LEFT);
						} else {
							handled = fullScrollH(View.FOCUS_LEFT);
						}
					}
					break;
				case KeyEvent.KEYCODE_DPAD_RIGHT:
					if (canScrollH()) {
						if (!event.isAltPressed()) {
							handled = arrowScrollH(View.FOCUS_RIGHT);
						} else {
							handled = fullScrollH(View.FOCUS_RIGHT);
						}
					}
					break;
				case KeyEvent.KEYCODE_DPAD_UP:
					if (canScrollV()) {
						if (!event.isAltPressed()) {
							handled = arrowScrollV(View.FOCUS_UP);
						} else {
							handled = fullScrollV(View.FOCUS_UP);
						}
					}
					break;
				case KeyEvent.KEYCODE_DPAD_DOWN:
					if (canScrollV()) {
						if (!event.isAltPressed()) {
							handled = arrowScrollV(View.FOCUS_DOWN);
						} else {
							handled = fullScrollV(View.FOCUS_DOWN);
						}
					}
					break;
			}
		}
		return handled;
	}


	private boolean inChild(int x, int y) {
		if (getChildCount() > 0) {
			final int scrollX = getScrollX();
			final int scrollY = getScrollY();
			final View child = getChildAt(0);
			return !(y < child.getTop() - scrollY || y >= child.getBottom() - scrollY
					|| x < child.getLeft() - scrollX || x >= child.getRight() - scrollX);
		}
		return false;
	}


	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
        /*
         * This method JUST determines whether we want to intercept the motion.
         * If we return true, onMotionEvent will be called and we do the actual
         * scrolling there.
         */


        /*
         * Shortcut the most recurring case: the user is in the dragging state
         * and he is moving his finger. We want to intercept this motion.
         */
		final int action = ev.getAction();
		if ((action == MotionEvent.ACTION_MOVE) && (mIsBeingDragged)) {
			return true;
		}


		switch (action & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_MOVE: {
                /*
                 * mIsBeingDragged == false, otherwise the shortcut would have
                 * caught it. Check whether the user has moved far enough from
                 * his original down touch.
                 */


                /*
                 * Locally do absolute value. mLastMotionY is set to the y value
                 * of the down event.
                 */
				final int activePointerId = mActivePointerId;
				if (activePointerId == INVALID_POINTER) {
					// If we don't have a valid id, the touch down wasn't on
					// content.
					break;
				}


				final int pointerIndex = ev.findPointerIndex(activePointerId);
				final float y = ev.getY(pointerIndex);
				final int yDiff = (int)Math.abs(y - mLastMotionY);
				if (yDiff > mTouchSlop) {
					mIsBeingDragged = true;
					mLastMotionY = y;
				}
				final float x = ev.getX(pointerIndex);
				final int xDiff = (int)Math.abs(x - mLastMotionX);
				if (xDiff > mTouchSlop) {
					mIsBeingDragged = true;
					mLastMotionX = x;
				}
				break;
			}


			case MotionEvent.ACTION_DOWN: {
				final float x = ev.getX();
				final float y = ev.getY();
				if (!inChild((int)x, (int)y)) {
					mIsBeingDragged = false;
					break;
				}


                /*
                 * Remember location of down touch. ACTION_DOWN always refers to
                 * pointer index 0.
                 */
				mLastMotionY = y;
				mLastMotionX = x;
				mActivePointerId = ev.getPointerId(0);


                /*
                 * If being flinged and user touches the screen, initiate drag;
                 * otherwise don't. mScroller.isFinished should be false when
                 * being flinged.
                 */
				mIsBeingDragged = !mScroller.isFinished();
				break;
			}


			case MotionEvent.ACTION_CANCEL:
			case MotionEvent.ACTION_UP:
                /* Release the drag */
				mIsBeingDragged = false;
				mActivePointerId = INVALID_POINTER;
				break;
			case MotionEvent.ACTION_POINTER_UP:
				onSecondaryPointerUp(ev);
				break;
		}


        /*
         * The only time we want to intercept motion events is if we are in the
         * drag mode.
         */
		return mIsBeingDragged;
	}


	private boolean scrollableOutsideTouch = false;


	/**
	 * 设置scroller是否可以滑动内容当触屏事件在chileview之外 default:false
	 * @param b true-可以,false-不可
	 */
	public void setScrollableOutsideChile(boolean b) {
		scrollableOutsideTouch = b;
	}


	private boolean flexible = true; //设置是否有弹性动效


	/**
	 * 设置是否可有弹性效果
	 * @param b true-可以,false-不可
	 */
	public void setFlexible(boolean b) {
		flexible = b;
	}


	private long lastEvenTime;

	private void setScal(View view2,float i)
	{
		view2.setScaleX(i);
		view2.setScaleY(i);
	}

	private boolean needToHandle=false;
	private float scale = 1; //当前缩放的比例,默认为1，表示不缩放
	private float preScale = 1;// 默认前一次缩放比例为1
	private ScaleGestureDetector mScaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleGestureListener());;

	public class ScaleGestureListener implements ScaleGestureDetector.OnScaleGestureListener {

		@Override
		public boolean onScale(ScaleGestureDetector detector) {

			float previousSpan = detector.getPreviousSpan();// 前一次双指间距
			float currentSpan = detector.getCurrentSpan();// 本次双指间距
			if (currentSpan < previousSpan) {
				// 缩小
				scale = preScale - (previousSpan - currentSpan) / 1000;
			} else {
				// 放大
				scale = preScale + (currentSpan - previousSpan) / 1000;
			}
			if (scale < 0.1) {
				scale = (float) 0.1; //最小只能缩放到原来的百分之十
			}
			if (scale >= 1) {
				scale = 1; //最大只能和原来一样大
			}
			// 缩放view
			if (scale >= 0.1) { //只能缩小到原来的百分之十
				final RelativeLayout childAt = (RelativeLayout) getChildAt(0);
				if(scale != 1) //和原来大小一样的时候，就不缩放了
				{
					setScal(childAt, scale);
					//缩放完成后，移动到中心点
					int x = (int) Word.FIRST_SCROLLX;
					int y = (int) Word.FIRST_SCROLLY;
					scrollTo(x,y);
				}

			}
			if (scale < 0.1)
			{
				scale = (float) 0.1;
			}
			return false;
		}

		@Override
		public boolean onScaleBegin(ScaleGestureDetector detector) {
			// 一定要返回true才会进入onScale()这个函数
			return true;
		}

		@Override
		public void onScaleEnd(ScaleGestureDetector detector) {
			preScale = scale;// 记录本次缩放比例
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN && ev.getEdgeFlags() != 0) {
			// Don't handle edge touches immediately -- they may actually belong
			// to one of our
			// descendants.
			return false;
		}

		if (mVelocityTracker == null) {
			mVelocityTracker = VelocityTracker.obtain();
		}
		mVelocityTracker.addMovement(ev);


		final int action = ev.getAction();

		int pointerCount = ev.getPointerCount(); // 获得多少点
		if (pointerCount > 1) {// 多点触控，
			switch (ev.getAction()) {
				case MotionEvent.ACTION_DOWN:
					needToHandle=true;
					break;
				case MotionEvent.ACTION_MOVE:

					break;
				case MotionEvent.ACTION_POINTER_2_UP://第二个手指抬起的时候
					needToHandle=true;
					break;
				default:
					break;
			}
			return mScaleGestureDetector.onTouchEvent(ev);//让mScaleGestureDetector处理触摸事件
		} else { //单点触控
			switch (action & MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_DOWN: {
					needToHandle = false;//设置多点触控结束

					final float x = ev.getX();
					final float y = ev.getY();
					downX = ev.getX(); //保存手指降落的x y坐标，用来在缩放后移动时判断是向左移动还是向右移动
					downY = ev.getY();

					if (!(mIsBeingDragged = inChild((int)x, (int)y)) && !scrollableOutsideTouch) {
						return false;
					}
					// 阻止测试人员暴力测试
					if (System.currentTimeMillis() - lastEvenTime < 200) {
						ev.setAction(MotionEvent.ACTION_CANCEL);
					}
					lastEvenTime = System.currentTimeMillis();
                /*
                 * If being flinged and user touches, stop the fling. isFinished
                 * will be false if being flinged.
                 */
					if (!mScroller.isFinished()) {
						mScroller.abortAnimation();
					}


					// Remember where the motion event started
					mLastMotionY = y;
					mLastMotionX = x;
					mActivePointerId = ev.getPointerId(0);
					break;
				}
				case MotionEvent.ACTION_MOVE:
					if (needToHandle == true) //移动之前先判断多点触控是否结束
					{
						return false;
					}

					//x轴方向边界判断
					int offsetX = (int) (inner.getMeasuredWidth()*scale - getWidth()); //X轴移动的最大距离
					int maxScrollX = (int) Word.FIRST_SCROLLX + offsetX/2;
					int minScrollX = (int) Word.FIRST_SCROLLX - offsetX/2;
					int scrollX = getScrollX();
					float moveX = downX - ev.getX(); //手指移动的距离
					LogUtils.i("=============== MotionEvent.ACTION_MOVE ================");
					LogUtils.i("inner.getMeasuredWidth():"+inner.getMeasuredWidth());
					LogUtils.i("scale:"+scale);
					LogUtils.i("getWidth():"+getWidth());
					LogUtils.i("Word.FIRST_SCROLLX:"+Word.FIRST_SCROLLX);
					LogUtils.i("offsetX/2:"+offsetX/2);
					LogUtils.i("maxScrollX:"+maxScrollX);
					LogUtils.i("minScrollX:"+minScrollX);
					LogUtils.i("scrollX:"+scrollX);
					LogUtils.i("moveX:"+moveX);

					//y轴方向判断
					int offsety = (int) (inner.getMeasuredHeight()*scale - getHeight()); //y轴移动的最大距离
					int maxScrollY = (int) Word.FIRST_SCROLLY + offsety/2;
					int minScrollY = (int) Word.FIRST_SCROLLY - offsety/2;
					int scrollY = getScrollY();
					float moveY = downY - ev.getY(); //手指移动的距离
//					LogUtils.i("===================");
//					LogUtils.i("offsety:"+offsety);
//					LogUtils.i("maxScrollY:"+maxScrollY);
//					LogUtils.i("minScrollY:"+minScrollY);
//					LogUtils.i("scrollY:"+scrollY);
//					LogUtils.i("moveY:"+moveY);

					if (offsetX <= 0 && offsety <=0) //如果都小于0，表示x轴和y轴都已经缩放到小于或等于屏幕宽度的程度，不能进行移动了
					{
						return false;
					}

					if (scrollX <= minScrollX)//表示移动到了左边界，此时允许往右边移动，不允许往左边移动
					{
						LogUtils.i("----- scrollX <= minScrollX ------");
						if (offsety > 0 && offsetX<0) //图片高度大于屏幕高度，但是宽度小于屏幕宽度
						{
							LogUtils.i(" ---- offsety > 0 && offsetX<0 ----");
							myScrollBy(ev,false,true,minScrollX,maxScrollX,minScrollY,maxScrollY);//x轴不动，y轴动
							return true;
						}
						if (moveX < 0) //表示手指往右移动
						{
							LogUtils.i(" ---- moveX < 0 ----");
							return false;
						}
					}
					if (scrollX >= maxScrollX)//表示缩放后移动到了右边界或移出了右边界，此时允许往左边移动，不允许往右边移动
					{
						LogUtils.i("scrollX >= maxScrollX");

						if (offsety > 0 && offsetX<0) //图片高度大于屏幕高度，但是宽度小于屏幕宽度
						{
							LogUtils.i("offsety > 0 && offsetX<0");
							myScrollBy(ev,false,true,minScrollX,maxScrollX,minScrollY,maxScrollY); //x轴不动，y轴动
							return true;
						}
						if (moveX > 0) //表示往右移动
						{
							LogUtils.i("moveX > 0");
							return false;
						}
					}


					if (scrollY <= minScrollY)//表示移动到了下边界，此时允许往上边移动，不允许往下边移动
					{
						LogUtils.i("scrollY <= minScrollY");
						if (offsety <0 && offsetX>0) //图片高度小于屏幕高度，但是宽度大于屏幕宽度
						{
							LogUtils.i("offsety <0 && offsetX>0");
							myScrollBy(ev,true,false,minScrollX,maxScrollX,minScrollY,maxScrollY); //x轴动，y轴不动
							return true;
						}
						if (moveY < 0) //表示往下移动
						{
							LogUtils.i("moveY < 0");
							return false;
						}
					}
					if (scrollY >= maxScrollY)//表示缩放后移动到了上边界或移出了上边界，此时允许往下边移动，不允许往上边移动
					{
						LogUtils.i("scrollY >= maxScrollY");
						if (offsety <0 && offsetX>0) //图片高度小于屏幕高度，但是宽度大于屏幕宽度
						{
							LogUtils.i("offsety <0 && offsetX>0");
							myScrollBy(ev,true,false,minScrollX,maxScrollX,minScrollY,maxScrollY); //x轴动，y轴不动
							return true;
						}
						if (moveY > 0) //表示往上移动
						{
							LogUtils.i("moveY > 0");
							return false;
						}
					}
					LogUtils.i("所有判断都走完了");
					myScrollBy(ev,true,true,minScrollX,maxScrollX,minScrollY,maxScrollY); //移动
					break;
				case MotionEvent.ACTION_UP:
					if (mIsBeingDragged || scrollableOutsideTouch) {
						if (mFlingEnabled) {

							final VelocityTracker velocityTracker = mVelocityTracker;
							velocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
							int initialVelocitx = (int)velocityTracker.getXVelocity(mActivePointerId); //获取横向移动的速率
							int initialVelocity = (int)velocityTracker.getYVelocity(mActivePointerId); //获取纵向移动的速率

							if (getChildCount() > 0) {
								if (Math.abs(initialVelocitx) > initialVelocitx || Math.abs(initialVelocity) > mMinimumVelocity) {
									fling(-initialVelocitx, -initialVelocity);
								}
							}
						}
						if (isNeedAnimation()) {
							animation();
						}
						mActivePointerId = INVALID_POINTER;
						mIsBeingDragged = false;


						if (mVelocityTracker != null) {
							mVelocityTracker.recycle();
							mVelocityTracker = null;
						}
					}
					break;
				case MotionEvent.ACTION_CANCEL:
					if (mIsBeingDragged && getChildCount() > 0) {
						mActivePointerId = INVALID_POINTER;
						mIsBeingDragged = false;
						if (mVelocityTracker != null) {
							mVelocityTracker.recycle();
							mVelocityTracker = null;
						}
					}
					break;
				case MotionEvent.ACTION_POINTER_UP:
					onSecondaryPointerUp(ev);
					break;
			}
			return true;
		}
	}

	//scrollX：表示X轴方向是否可以移动，true：可以  false：不可以
	//scrollY：表示X轴方向是否可以移动，true：可以  false：不可以
	private void myScrollBy(MotionEvent ev,boolean canScrollX,boolean canScrollY,
							int minScrollX,int maxScrollX,int minScrollY,int maxScrollY)
	{
		if (mIsBeingDragged || scrollableOutsideTouch) {
			// Scroll to follow the motion event
			final int activePointerIndex = ev.findPointerIndex(mActivePointerId);
			if (activePointerIndex<0)
			{
				return ;
			}
			float y = ev.getY(activePointerIndex);
			int deltaY = (int)(mLastMotionY - y);
			LogUtils.i("deltaY:"+deltaY);
			mLastMotionY = y;


			float x = ev.getX(activePointerIndex);
			int deltaX = (int)(mLastMotionX - x);
			LogUtils.i("deltaX:"+deltaX);
			mLastMotionX = x;

			int scrollX = getScrollX();
			int scrollY = getScrollY();
			//控制移动距离，不能超出最左边和最右边
			if ((scrollX + deltaX) < minScrollX )
			{
				deltaX = minScrollX - scrollX;
				LogUtils.i("重新获取后的deltaX111："+deltaX);
			}
			if ((scrollX + deltaX) > maxScrollX)
			{
				deltaX = maxScrollX - scrollX;
				LogUtils.i("重新获取后的deltaX222："+deltaX);
			}

			if ((scrollY + deltaY) < minScrollY)
			{
				deltaY = minScrollY - scrollY;
				LogUtils.i("重新获取后的deltaY111："+deltaY);
			}
			if ((scrollY + deltaY) > maxScrollY)
			{
				deltaY = maxScrollY - scrollY;
				LogUtils.i("重新获取后的deltaY222："+deltaY);
			}

			if (canScrollX && canScrollY) //x轴和y轴都可以移动,全方向移动
			{
				LogUtils.i("scrollX && scrollY");
				scrollBy(deltaX, deltaY);
			}
			if (canScrollX && canScrollY == false) //x轴可以移动，y轴不可以
			{
				LogUtils.i("scrollX && scrollY == false");
				scrollBy(deltaX, 0);
			}
			if (canScrollX == false && canScrollY) //x轴不可以移动，y轴可以
			{
				LogUtils.i("scrollX == false && scrollY");
				scrollBy(0, deltaY);
			}

			// 当滚动到边界时就不会再滚动，这时移动布局
			if (isNeedMove(ev) && flexible) {
				if (normal.isEmpty()) {
					// 保存正常的布局属性
					normal.set(inner.getLeft(), inner.getTop(), inner.getRight(),
							inner.getBottom());
				}
				// 移动布局
				inner.layout(inner.getLeft() - deltaX / 2, inner.getTop() - deltaY / 2,
						inner.getRight() - deltaX / 2, inner.getBottom() - deltaY / 2);
			}
		}
	}

	private void onSecondaryPointerUp(MotionEvent ev) {
		final int pointerIndex = (ev.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
		final int pointerId = ev.getPointerId(pointerIndex);
		if (pointerId == mActivePointerId) {
			// This was our active pointer going up. Choose a new
			// active pointer and adjust accordingly.
			final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
			mLastMotionX = ev.getX(newPointerIndex);
			mLastMotionY = ev.getY(newPointerIndex);
			mActivePointerId = ev.getPointerId(newPointerIndex);
			if (mVelocityTracker != null) {
				mVelocityTracker.clear();
			}
		}
	}


	/**
	 * <p>
	 * Finds the next focusable component that fits in the specified bounds.
	 * </p>
	 *
	 * @param topFocus look for a candidate is the one at the top of the bounds
	 *                 if topFocus is true, or at the bottom of the bounds if topFocus is
	 *                 false
	 * @param top      the top offset of the bounds in which a focusable must be
	 *                 found
	 * @param bottom   the bottom offset of the bounds in which a focusable must
	 *                 be found
	 * @return the next focusable component in the bounds or null if none can
	 *         be found
	 */
	private View findFocusableViewInBoundsV(boolean topFocus, int top, int bottom) {


		List<View> focusables = getFocusables(View.FOCUS_FORWARD);
		View focusCandidate = null;


        /*
         * A fully contained focusable is one where its top is below the bound's
         * top, and its bottom is above the bound's bottom. A partially
         * contained focusable is one where some part of it is within the
         * bounds, but it also has some part that is not within bounds. A fully
         * contained focusable is preferred to a partially contained focusable.
         */
		boolean foundFullyContainedFocusable = false;


		int count = focusables.size();
		for (int i = 0; i < count; i++) {
			View view = focusables.get(i);
			int viewTop = view.getTop();
			int viewBottom = view.getBottom();


			if (top < viewBottom && viewTop < bottom) {
                /*
                 * the focusable is in the target area, it is a candidate for
                 * focusing
                 */


				final boolean viewIsFullyContained = (top < viewTop) && (viewBottom < bottom);


				if (focusCandidate == null) {
                    /* No candidate, take this one */
					focusCandidate = view;
					foundFullyContainedFocusable = viewIsFullyContained;
				} else {
					final boolean viewIsCloserToBoundary = (topFocus && viewTop < focusCandidate
							.getTop()) || (!topFocus && viewBottom > focusCandidate.getBottom());


					if (foundFullyContainedFocusable) {
						if (viewIsFullyContained && viewIsCloserToBoundary) {
                            /*
                             * We're dealing with only fully contained views, so
                             * it has to be closer to the boundary to beat our
                             * candidate
                             */
							focusCandidate = view;
						}
					} else {
						if (viewIsFullyContained) {
                            /*
                             * Any fully contained view beats a partially
                             * contained view
                             */
							focusCandidate = view;
							foundFullyContainedFocusable = true;
						} else if (viewIsCloserToBoundary) {
                            /*
                             * Partially contained view beats another partially
                             * contained view if it's closer
                             */
							focusCandidate = view;
						}
					}
				}
			}
		}


		return focusCandidate;
	}


	private View findFocusableViewInBoundsH(boolean leftFocus, int left, int right) {


		List<View> focusables = getFocusables(View.FOCUS_FORWARD);
		View focusCandidate = null;


        /*
         * A fully contained focusable is one where its left is below the
         * bound's left, and its right is above the bound's right. A partially
         * contained focusable is one where some part of it is within the
         * bounds, but it also has some part that is not within bounds. A fully
         * contained focusable is preferred to a partially contained focusable.
         */
		boolean foundFullyContainedFocusable = false;


		int count = focusables.size();
		for (int i = 0; i < count; i++) {
			View view = focusables.get(i);
			int viewLeft = view.getLeft();
			int viewRight = view.getRight();


			if (left < viewRight && viewLeft < right) {
                /*
                 * the focusable is in the target area, it is a candidate for
                 * focusing
                 */


				final boolean viewIsFullyContained = (left < viewLeft) && (viewRight < right);


				if (focusCandidate == null) {
                    /* No candidate, take this one */
					focusCandidate = view;
					foundFullyContainedFocusable = viewIsFullyContained;
				} else {
					final boolean viewIsCloserToBoundary = (leftFocus && viewLeft < focusCandidate
							.getLeft()) || (!leftFocus && viewRight > focusCandidate.getRight());


					if (foundFullyContainedFocusable) {
						if (viewIsFullyContained && viewIsCloserToBoundary) {
                            /*
                             * We're dealing with only fully contained views, so
                             * it has to be closer to the boundary to beat our
                             * candidate
                             */
							focusCandidate = view;
						}
					} else {
						if (viewIsFullyContained) {
                            /*
                             * Any fully contained view beats a partially
                             * contained view
                             */
							focusCandidate = view;
							foundFullyContainedFocusable = true;
						} else if (viewIsCloserToBoundary) {
                            /*
                             * Partially contained view beats another partially
                             * contained view if it's closer
                             */
							focusCandidate = view;
						}
					}
				}
			}
		}


		return focusCandidate;
	}


	/**
	 * <p>Handles scrolling in response to a "home/end" shortcut press. This
	 * method will scroll the view to the top or bottom and give the focus
	 * to the topmost/bottommost component in the new visible area. If no
	 * component is a good candidate for focus, this scrollview reclaims the
	 * focus.</p>
	 *
	 * @param direction the scroll direction: {@link View#FOCUS_UP}
	 *                  to go the top of the view or
	 *                  {@link View#FOCUS_DOWN} to go the bottom
	 * @return true if the key event is consumed by this method, false otherwise
	 */
	public boolean fullScrollV(int direction) {
		boolean down = direction == View.FOCUS_DOWN;
		int height = getHeight();


		mTempRect.top = 0;
		mTempRect.bottom = height;


		if (down) {
			int count = getChildCount();
			if (count > 0) {
				View view = getChildAt(count - 1);
				mTempRect.bottom = view.getBottom();
				mTempRect.top = mTempRect.bottom - height;
			}
		}


		return scrollAndFocusV(direction, mTempRect.top, mTempRect.bottom);
	}


	public boolean fullScrollH(int direction) {
		boolean right = direction == View.FOCUS_RIGHT;
		int width = getWidth();


		mTempRect.left = 0;
		mTempRect.right = width;


		if (right) {
			int count = getChildCount();
			if (count > 0) {
				View view = getChildAt(0);
				mTempRect.right = view.getRight();
				mTempRect.left = mTempRect.right - width;
			}
		}


		return scrollAndFocusH(direction, mTempRect.left, mTempRect.right);
	}


	/**
	 * <p>Scrolls the view to make the area defined by <code>top</code> and
	 * <code>bottom</code> visible. This method attempts to give the focus
	 * to a component visible in this area. If no component can be focused in
	 * the new visible area, the focus is reclaimed by this scrollview.</p>
	 *
	 * @param direction the scroll direction: {@link View#FOCUS_UP}
	 *                  to go upward
	 *                  {@link View#FOCUS_DOWN} to downward
	 * @param top       the top offset of the new area to be made visible
	 * @param bottom    the bottom offset of the new area to be made visible
	 * @return true if the key event is consumed by this method, false otherwise
	 */
	private boolean scrollAndFocusV(int direction, int top, int bottom) {
		boolean handled = true;


		int height = getHeight();
		int containerTop = getScrollY();
		int containerBottom = containerTop + height;
		boolean up = direction == View.FOCUS_UP;


		View newFocused = findFocusableViewInBoundsV(up, top, bottom);
		if (newFocused == null) {
			newFocused = this;
		}


		if (top >= containerTop && bottom <= containerBottom) {
			handled = false;
		} else {
			int delta = up ? (top - containerTop) : (bottom - containerBottom);
			doScrollY(delta);
		}


		if (newFocused != findFocus() && newFocused.requestFocus(direction)) {
			mScrollViewMovedFocus = true;
			mScrollViewMovedFocus = false;
		}


		return handled;
	}


	private boolean scrollAndFocusH(int direction, int left, int right) {
		boolean handled = true;


		int width = getWidth();
		int containerLeft = getScrollX();
		int containerRight = containerLeft + width;
		boolean goLeft = direction == View.FOCUS_LEFT;


		View newFocused = findFocusableViewInBoundsH(goLeft, left, right);
		if (newFocused == null) {
			newFocused = this;
		}


		if (left >= containerLeft && right <= containerRight) {
			handled = false;
		} else {
			int delta = goLeft ? (left - containerLeft) : (right - containerRight);
			doScrollX(delta);
		}


		if (newFocused != findFocus() && newFocused.requestFocus(direction)) {
			mScrollViewMovedFocus = true;
			mScrollViewMovedFocus = false;
		}


		return handled;
	}


	/**
	 * Handle scrolling in response to an up or down arrow click.
	 *
	 * @param direction The direction corresponding to the arrow key that was
	 *                  pressed
	 * @return True if we consumed the event, false otherwise
	 */
	public boolean arrowScrollV(int direction) {


		View currentFocused = findFocus();
		if (currentFocused == this)
			currentFocused = null;


		View nextFocused = FocusFinder.getInstance().findNextFocus(this, currentFocused, direction);


		final int maxJump = getMaxScrollAmountV();


		if (nextFocused != null && isWithinDeltaOfScreenV(nextFocused, maxJump, getHeight())) {
			nextFocused.getDrawingRect(mTempRect);
			offsetDescendantRectToMyCoords(nextFocused, mTempRect);
			int scrollDelta = computeScrollDeltaToGetChildRectOnScreenV(mTempRect);
			doScrollY(scrollDelta);
			nextFocused.requestFocus(direction);
		} else {
			// no new focus
			int scrollDelta = maxJump;


			if (direction == View.FOCUS_UP && getScrollY() < scrollDelta) {
				scrollDelta = getScrollY();
			} else if (direction == View.FOCUS_DOWN) {
				if (getChildCount() > 0) {
					int daBottom = getChildAt(0).getBottom();


					int screenBottom = getScrollY() + getHeight();


					if (daBottom - screenBottom < maxJump) {
						scrollDelta = daBottom - screenBottom;
					}
				}
			}
			if (scrollDelta == 0) {
				return false;
			}
			doScrollY(direction == View.FOCUS_DOWN ? scrollDelta : -scrollDelta);
		}


		if (currentFocused != null && currentFocused.isFocused() && isOffScreenV(currentFocused)) {
			// previously focused item still has focus and is off screen, give
			// it up (take it back to ourselves)
			// (also, need to temporarily force FOCUS_BEFORE_DESCENDANTS so we
			// are sure to get it)
			final int descendantFocusability = getDescendantFocusability(); // save
			setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
			requestFocus();
			setDescendantFocusability(descendantFocusability); // restore
		}
		return true;
	}


	public boolean arrowScrollH(int direction) {


		View currentFocused = findFocus();
		if (currentFocused == this)
			currentFocused = null;


		View nextFocused = FocusFinder.getInstance().findNextFocus(this, currentFocused, direction);


		final int maxJump = getMaxScrollAmountH();


		if (nextFocused != null && isWithinDeltaOfScreenH(nextFocused, maxJump)) {
			nextFocused.getDrawingRect(mTempRect);
			offsetDescendantRectToMyCoords(nextFocused, mTempRect);
			int scrollDelta = computeScrollDeltaToGetChildRectOnScreenH(mTempRect);
			doScrollX(scrollDelta);
			nextFocused.requestFocus(direction);
		} else {
			// no new focus
			int scrollDelta = maxJump;


			if (direction == View.FOCUS_LEFT && getScrollX() < scrollDelta) {
				scrollDelta = getScrollX();
			} else if (direction == View.FOCUS_RIGHT && getChildCount() > 0) {


				int daRight = getChildAt(0).getRight();


				int screenRight = getScrollX() + getWidth();


				if (daRight - screenRight < maxJump) {
					scrollDelta = daRight - screenRight;
				}
			}
			if (scrollDelta == 0) {
				return false;
			}
			doScrollX(direction == View.FOCUS_RIGHT ? scrollDelta : -scrollDelta);
		}


		if (currentFocused != null && currentFocused.isFocused() && isOffScreenH(currentFocused)) {
			// previously focused item still has focus and is off screen, give
			// it up (take it back to ourselves)
			// (also, need to temporarily force FOCUS_BEFORE_DESCENDANTS so we
			// are sure to get it)
			final int descendantFocusability = getDescendantFocusability(); // save
			setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
			requestFocus();
			setDescendantFocusability(descendantFocusability); // restore
		}
		return true;
	}


	/**
	 * @return whether the descendant of this scroll view is scrolled off
	 *  screen.
	 */
	private boolean isOffScreenV(View descendant) {
		return !isWithinDeltaOfScreenV(descendant, 0, getHeight());
	}


	private boolean isOffScreenH(View descendant) {
		return !isWithinDeltaOfScreenH(descendant, 0);
	}


	/**
	 * @return whether the descendant of this scroll view is within delta
	 *  pixels of being on the screen.
	 */
	private boolean isWithinDeltaOfScreenV(View descendant, int delta, int height) {
		descendant.getDrawingRect(mTempRect);
		offsetDescendantRectToMyCoords(descendant, mTempRect);


		return (mTempRect.bottom + delta) >= getScrollY()
				&& (mTempRect.top - delta) <= (getScrollY() + height);
	}


	private boolean isWithinDeltaOfScreenH(View descendant, int delta) {
		descendant.getDrawingRect(mTempRect);
		offsetDescendantRectToMyCoords(descendant, mTempRect);


		return (mTempRect.right + delta) >= getScrollX()
				&& (mTempRect.left - delta) <= (getScrollX() + getWidth());
	}


	/**
	 * Smooth scroll by a Y delta
	 *
	 * @param delta the number of pixels to scroll by on the Y axis
	 */
	private void doScrollY(int delta) {
		if (delta != 0) {
			if (mSmoothScrollingEnabled) {
				smoothScrollBy(0, delta);
			} else {
				scrollBy(0, delta);
			}
		}
	}


	private void doScrollX(int delta) {
		if (delta != 0) {
			if (mSmoothScrollingEnabled) {
				smoothScrollBy(delta, 0);
			} else {
				scrollBy(delta, 0);
			}
		}
	}


	/**
	 * Like {@link View#scrollBy}, but scroll smoothly instead of immediately.
	 *
	 * @param dx the number of pixels to scroll by on the X axis
	 * @param dy the number of pixels to scroll by on the Y axis
	 */
	public void smoothScrollBy(int dx, int dy) {
		if (getChildCount() == 0) {
			// Nothing to do.
			return;
		}
		long duration = AnimationUtils.currentAnimationTimeMillis() - mLastScroll;
		if (duration > ANIMATED_SCROLL_GAP) {
			final int height = getHeight() - getPaddingBottom() - getPaddingTop();
			final int bottom = getChildAt(0).getHeight();
			final int maxY = Math.max(0, bottom - height);
			final int scrollY = getScrollY();
			dy = Math.max(0, Math.min(scrollY + dy, maxY)) - scrollY;


			final int width = getWidth() - getPaddingRight() - getPaddingLeft();
			final int right = getChildAt(0).getWidth();
			final int maxX = Math.max(0, right - width);
			final int scrollX = getScrollX();
			dx = Math.max(0, Math.min(scrollX + dx, maxX)) - scrollX;

			mScroller.startScroll(scrollX, scrollY, dx, dy);
			invalidate();
		} else {
			if (!mScroller.isFinished()) {
				mScroller.abortAnimation();
			}
			scrollBy(dx, dy);
		}
		mLastScroll = AnimationUtils.currentAnimationTimeMillis();
	}


	/**
	 * Like {@link #scrollTo}, but scroll smoothly instead of immediately.
	 *
	 * @param x the position where to scroll on the X axis
	 * @param y the position where to scroll on the Y axis
	 */
	public final void smoothScrollTo(int x, int y) {
		smoothScrollBy(x - getScrollX(), y - getScrollY());
	}


	/**
	 * <p>The scroll range of a scroll view is the overall height of all of its
	 * children.</p>
	 */
	@Override
	protected int computeVerticalScrollRange() {
		final int count = getChildCount();
		final int contentHeight = getHeight() - getPaddingBottom() - getPaddingTop();
		if (count == 0) {
			return contentHeight;
		}


		return getChildAt(0).getBottom();
	}


	@Override
	protected int computeHorizontalScrollRange() {
		final int count = getChildCount();
		final int contentWidth = getWidth() - getPaddingLeft() - getPaddingRight();
		if (count == 0) {
			return contentWidth;
		}


		return getChildAt(0).getRight();
	}


	@Override
	protected int computeVerticalScrollOffset() {
		return Math.max(0, super.computeVerticalScrollOffset());
	}


	@Override
	protected int computeHorizontalScrollOffset() {
		return Math.max(0, super.computeHorizontalScrollOffset());
	}


	@Override
	protected void measureChild(View child, int parentWidthMeasureSpec, int parentHeightMeasureSpec) {
		int childWidthMeasureSpec;
		int childHeightMeasureSpec;


		childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);


		childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);


		child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
	}


	@Override
	protected void measureChildWithMargins(View child, int parentWidthMeasureSpec, int widthUsed,
										   int parentHeightMeasureSpec, int heightUsed) {
		final MarginLayoutParams lp = (MarginLayoutParams)child.getLayoutParams();


		final int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(lp.leftMargin
				+ lp.rightMargin, MeasureSpec.UNSPECIFIED);
		final int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(lp.topMargin
				+ lp.bottomMargin, MeasureSpec.UNSPECIFIED);


		child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
	}


	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			// This is called at drawing time by ViewGroup. We don't want to
			// re-show the scrollbars at this point, which scrollTo will do,
			// so we replicate most of scrollTo here.
			//
			// It's a little odd to call onScrollChanged from inside the
			// drawing.
			//
			// It is, except when you remember that computeScroll() is used to
			// animate scrolling. So unless we want to defer the
			// onScrollChanged()
			// until the end of the animated scrolling, we don't really have a
			// choice here.
			//
			// I agree. The alternative, which I think would be worse, is to
			// post
			// something and tell the subclasses later. This is bad because
			// there
			// will be a window where mScrollX/Y is different from what the app
			// thinks it is.
			//
			int x = mScroller.getCurrX();
			int y = mScroller.getCurrY();


			if (getChildCount() > 0) {
				View child = getChildAt(0);
				x = clamp(x, getWidth() - getPaddingRight() - getPaddingLeft(), child.getWidth());
				y = clamp(y, getHeight() - getPaddingBottom() - getPaddingTop(), child.getHeight());
				super.scrollTo(x, y);
				// getHeight()- child.getHeight()=y ->底部 y=0 ->顶端
				// 惯性强度 mScroller.getDuration()
			}
			awakenScrollBars();


			// Keep on drawing until the animation has finished.
			postInvalidate();
		}
	}


	/**
	 * Scrolls the view to the given child.
	 *
	 * @param child the View to scroll to
	 */
	private void scrollToChild(View child) {
		child.getDrawingRect(mTempRect);


        /* Offset from child's local coordinates to ScrollView coordinates */
		offsetDescendantRectToMyCoords(child, mTempRect);


		int scrollDeltaV = computeScrollDeltaToGetChildRectOnScreenV(mTempRect);
		int scrollDeltaH = computeScrollDeltaToGetChildRectOnScreenH(mTempRect);


		if (scrollDeltaH != 0 || scrollDeltaV != 0) {
			scrollBy(scrollDeltaH, scrollDeltaV);
		}
	}


	/**
	 * If rect is off screen, scroll just enough to get it (or at least the
	 * first screen size chunk of it) on screen.
	 *
	 * @param rect      The rectangle.
	 * @param immediate True to scroll immediately without animation
	 * @return true if scrolling was performed
	 */
	private boolean scrollToChildRect(Rect rect, boolean immediate) {
		final int deltaV = computeScrollDeltaToGetChildRectOnScreenV(rect);
		final int deltaH = computeScrollDeltaToGetChildRectOnScreenH(rect);
		final boolean scroll = deltaH != 0 || deltaV != 0;
		if (scroll) {
			if (immediate) {
				scrollBy(deltaH, deltaV);
			} else {
				smoothScrollBy(deltaH, deltaV);
			}
		}
		return scroll;
	}


	/**
	 * Compute the amount to scroll in the Y direction in order to get
	 * a rectangle completely on the screen (or, if taller than the screen,
	 * at least the first screen size chunk of it).
	 *
	 * @param rect The rect.
	 * @return The scroll delta.
	 */
	protected int computeScrollDeltaToGetChildRectOnScreenV(Rect rect) {
		if (getChildCount() == 0)
			return 0;


		int height = getHeight();
		int screenTop = getScrollY();
		int screenBottom = screenTop + height;


		int fadingEdge = getVerticalFadingEdgeLength();


		// leave room for top fading edge as long as rect isn't at very top
		if (rect.top > 0) {
			screenTop += fadingEdge;
		}


		// leave room for bottom fading edge as long as rect isn't at very
		// bottom
		if (rect.bottom < getChildAt(0).getHeight()) {
			screenBottom -= fadingEdge;
		}


		int scrollYDelta = 0;


		if (rect.bottom > screenBottom && rect.top > screenTop) {
			// need to move down to get it in view: move down just enough so
			// that the entire rectangle is in view (or at least the first
			// screen size chunk).


			if (rect.height() > height) {
				// just enough to get screen size chunk on
				scrollYDelta += (rect.top - screenTop);
			} else {
				// get entire rect at bottom of screen
				scrollYDelta += (rect.bottom - screenBottom);
			}


			// make sure we aren't scrolling beyond the end of our content
			int bottom = getChildAt(0).getBottom();
			int distanceToBottom = bottom - screenBottom;
			scrollYDelta = Math.min(scrollYDelta, distanceToBottom);


		} else if (rect.top < screenTop && rect.bottom < screenBottom) {
			// need to move up to get it in view: move up just enough so that
			// entire rectangle is in view (or at least the first screen
			// size chunk of it).


			if (rect.height() > height) {
				// screen size chunk
				scrollYDelta -= (screenBottom - rect.bottom);
			} else {
				// entire rect at top
				scrollYDelta -= (screenTop - rect.top);
			}


			// make sure we aren't scrolling any further than the top our
			// content
			scrollYDelta = Math.max(scrollYDelta, -getScrollY());
		}
		return scrollYDelta;
	}


	protected int computeScrollDeltaToGetChildRectOnScreenH(Rect rect) {
		if (getChildCount() == 0)
			return 0;


		int width = getWidth();
		int screenLeft = getScrollX();
		int screenRight = screenLeft + width;


		int fadingEdge = getHorizontalFadingEdgeLength();


		// leave room for left fading edge as long as rect isn't at very left
		if (rect.left > 0) {
			screenLeft += fadingEdge;
		}


		// leave room for right fading edge as long as rect isn't at very right
		if (rect.right < getChildAt(0).getWidth()) {
			screenRight -= fadingEdge;
		}


		int scrollXDelta = 0;


		if (rect.right > screenRight && rect.left > screenLeft) {
			// need to move right to get it in view: move right just enough so
			// that the entire rectangle is in view (or at least the first
			// screen size chunk).


			if (rect.width() > width) {
				// just enough to get screen size chunk on
				scrollXDelta += (rect.left - screenLeft);
			} else {
				// get entire rect at right of screen
				scrollXDelta += (rect.right - screenRight);
			}


			// make sure we aren't scrolling beyond the end of our content
			int right = getChildAt(0).getRight();
			int distanceToRight = right - screenRight;
			scrollXDelta = Math.min(scrollXDelta, distanceToRight);


		} else if (rect.left < screenLeft && rect.right < screenRight) {
			// need to move right to get it in view: move right just enough so
			// that
			// entire rectangle is in view (or at least the first screen
			// size chunk of it).


			if (rect.width() > width) {
				// screen size chunk
				scrollXDelta -= (screenRight - rect.right);
			} else {
				// entire rect at left
				scrollXDelta -= (screenLeft - rect.left);
			}


			// make sure we aren't scrolling any further than the left our
			// content
			scrollXDelta = Math.max(scrollXDelta, -getScrollX());
		}
		return scrollXDelta;
	}


	@Override
	public void requestChildFocus(View child, View focused) {
		if (!mScrollViewMovedFocus) {
			if (!mIsLayoutDirty) {
				scrollToChild(focused);
			} else {
				// The child may not be laid out yet, we can't compute the
				// scroll yet
				mChildToScrollTo = focused;
			}
		}
		super.requestChildFocus(child, focused);
	}


	/**
	 * When looking for focus in children of a scroll view, need to be a little
	 * more careful not to give focus to something that is scrolled off screen.
	 *
	 * This is more expensive than the default {@link ViewGroup}
	 * implementation, otherwise this behavior might have been made the default.
	 */
	@Override
	protected boolean onRequestFocusInDescendants(int direction, Rect previouslyFocusedRect) {


		// convert from forward / backward notation to up / down / left / right
		// (ugh).
		// if (direction == View.FOCUS_FORWARD) {
		// direction = View.FOCUS_RIGHT;
		// } else if (direction == View.FOCUS_BACKWARD) {
		// direction = View.FOCUS_LEFT;
		// }


		final View nextFocus = previouslyFocusedRect == null ? FocusFinder.getInstance()
				.findNextFocus(this, null, direction) : FocusFinder.getInstance()
				.findNextFocusFromRect(this, previouslyFocusedRect, direction);


		if (nextFocus == null) {
			return false;
		}


		// if (isOffScreenH(nextFocus)) {
		// return false;
		// }


		return nextFocus.requestFocus(direction, previouslyFocusedRect);
	}


	@Override
	public boolean requestChildRectangleOnScreen(View child, Rect rectangle, boolean immediate) {
		// offset into coordinate space of this scroll view
		rectangle.offset(child.getLeft() - child.getScrollX(), child.getTop() - child.getScrollY());


		return scrollToChildRect(rectangle, immediate);
	}


	@Override
	public void requestLayout() {
		mIsLayoutDirty = true;
		super.requestLayout();
	}


	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		mIsLayoutDirty = false;
		// Give a child focus if it needs it
		if (mChildToScrollTo != null && isViewDescendantOf(mChildToScrollTo, this)) {
			scrollToChild(mChildToScrollTo);
		}
		mChildToScrollTo = null;


		// Calling this with the present values causes it to re-clam them
		scrollTo(getScrollX(), getScrollY());
	}


	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);


		View currentFocused = findFocus();
		if (null == currentFocused || this == currentFocused)
			return;


		// If the currently-focused view was visible on the screen when the
		// screen was at the old height, then scroll the screen to make that
		// view visible with the new screen height.
		if (isWithinDeltaOfScreenV(currentFocused, 0, oldh)) {
			currentFocused.getDrawingRect(mTempRect);
			offsetDescendantRectToMyCoords(currentFocused, mTempRect);
			int scrollDelta = computeScrollDeltaToGetChildRectOnScreenV(mTempRect);
			doScrollY(scrollDelta);
		}


		final int maxJump = getRight() - getLeft();
		if (isWithinDeltaOfScreenH(currentFocused, maxJump)) {
			currentFocused.getDrawingRect(mTempRect);
			offsetDescendantRectToMyCoords(currentFocused, mTempRect);
			int scrollDelta = computeScrollDeltaToGetChildRectOnScreenH(mTempRect);
			doScrollX(scrollDelta);
		}
	}


	/**
	 * Return true if child is an descendant of parent, (or equal to the parent).
	 */
	private boolean isViewDescendantOf(View child, View parent) {
		if (child == parent) {
			return true;
		}


		final ViewParent theParent = child.getParent();
		return (theParent instanceof ViewGroup) && isViewDescendantOf((View)theParent, parent);
	}


	/**
	 * Fling the scroll view
	 *
	 * @param velocityY The initial velocity in the Y direction. Positive
	 *                  numbers mean that the finger/cursor is moving down the screen,
	 *                  which means we want to scroll towards the top.
	 */
//	public void fling(int velocityX, int velocityY) {
//		if (getChildCount() > 0) {
//			int width = getWidth() - getPaddingRight() - getPaddingLeft();
//			int right = getChildAt(0).getWidth();
//
//
//			int height = getHeight() - getPaddingBottom() - getPaddingTop();
//			int bottom = getChildAt(0).getHeight();
//			mScroller.fling(getScrollX(), getScrollY(), velocityX, velocityY, 0, Math.max(0, right - width), 0, Math.max(0, bottom - height));
//			invalidate();
//		}
//	}


	//惯性滑动
	public void fling(int velocityX, int velocityY) {
		if (getChildCount() > 0) {
			//x轴方向边界判断
			int offsetX = (int) (inner.getMeasuredWidth()*scale - getWidth()); //X轴移动的最大距离
			int maxScrollX = (int) Word.FIRST_SCROLLX + offsetX/2;
			int minScrollX = (int) Word.FIRST_SCROLLX - offsetX/2;
			if (offsetX < 0) //缩小到小于屏幕时，禁止滑动
			{
				return;
			}

			//y轴方向判断
			int offsety = (int) (inner.getMeasuredHeight()*scale - getHeight()); //y轴移动的最大距离
			int maxScrollY = (int) Word.FIRST_SCROLLY + offsety/2;
			int minScrollY = (int) Word.FIRST_SCROLLY - offsety/2;

			if (offsety < 0) //缩小到小于屏幕时，禁止滑动
			{
				return;
			}

			//缩放后x轴移动的最小值、y轴移动的最小值都变了
			mScroller.fling(getScrollX(), getScrollY(), velocityX, velocityY, minScrollX, maxScrollX, minScrollY, maxScrollY);
			invalidate();
		}
	}


	/**
	 * {@inheritDoc}
	 *
	 * <p>This version also clamps the scrolling to the bounds of our child.
	 */


	@Override
	public void scrollTo(int x, int y) {
		// we rely on the fact the View.scrollBy calls scrollTo.
		if (getChildCount() > 0) {
			View child = getChildAt(0);
			x = clamp(x, getWidth() - getPaddingRight() - getPaddingLeft(), child.getWidth());
			y = clamp(y, getHeight() - getPaddingBottom() - getPaddingTop(), child.getHeight());
			if (x != getScrollX() || y != getScrollY()) {
				super.scrollTo(x, y);
			}else if (x==0 && y==0) //用于处理登录成功后切换到星系界面时不移动到中心的问题
			{
				super.scrollTo(DensityUtils.dp2px(getContext(), Word.XING_XI_DP) / 2 - ScreenUtils.getScreenWidth(getContext()) / 2,
						DensityUtils.dp2px(getContext(), Word.XING_XI_DP) / 2 - ScreenUtils.getScreenHeight(getContext()) / 2);

				Word.FIRST_SCROLLX = getScrollX(); //保存X轴初始移动距离
				Word.FIRST_SCROLLY = getScrollY();//保存Y轴初始移动距离
			}
		}
	}




	private int clamp(int n, int my, int child) {
		if (my >= child || n < 0) {
            /*
             * my >= child is this case: |--------------- me ---------------|
             * |------ child ------| or |--------------- me ---------------|
             * |------ child ------| or |--------------- me ---------------|
             * |------ child ------| n < 0 is this case: |------ me ------|
             * |-------- child --------| |-- mScrollX --|
             */
			return 0;
		}
		if ((my + n) > child) {
            /*
             * this case: |------ me ------| |------ child ------| |-- mScrollX
             * --|
             */
			return child - my;
		}
		return n;
	}


	public boolean isFlingEnabled() {
		return mFlingEnabled;
	}


	public void setFlingEnabled(boolean flingEnabled) {
		this.mFlingEnabled = flingEnabled;
	}


	/** scrollview内容与属性记录*/
	private View inner;
	private Rect normal = new Rect();


	@Override
	protected void onFinishInflate() {
		if (getChildCount() > 0) {
			inner = getChildAt(0);
		}
	}


	// 是否需要开启动画
	public boolean isNeedAnimation() {
		return !normal.isEmpty();
	}


	// 开启动画移动
	public void animation() {
//		// 开启移动动画
		TranslateAnimation ta = new TranslateAnimation(0, -inner.getLeft(), 0, -inner.getTop());
		ta.setDuration(200);
		inner.startAnimation(ta);
		// 设置回到正常的布局位置
		new Handler().postDelayed(new Runnable() {
			public void run() {
				inner.clearAnimation();
				inner.layout(normal.left, normal.top, normal.right, normal.bottom);
				normal.setEmpty();
			}
		}, 200);
	}


	// 是否需要移动布局
//	public boolean isNeedMove() {
//		int offsetX = inner.getMeasuredWidth() - getWidth();
//		int scrollX = getScrollX();
//		if (scrollX == 0 || scrollX == offsetX) {
//			return true;
//		}
//
//
//		int offsetY = inner.getMeasuredHeight() - getHeight();
//		int scrollY = getScrollY();
//		if (scrollY == 0 || scrollY == offsetY) {
//			return true;
//		}
//		return false;
//	}

	//判断是否移动到边界  true:表示移动到边界  false:表示没有移动到边界
	public boolean isNeedMove(MotionEvent ev) {
		//x轴方向边界判断
		int offsetX = (int) (inner.getMeasuredWidth()*scale - getWidth()); //X轴移动的最大距离
		int maxScrollX = (int) Word.FIRST_SCROLLX + offsetX/2;
		int minScrollX = (int) Word.FIRST_SCROLLX - offsetX/2;

		int scrollX = getScrollX();
		float moveX = downX - ev.getX(); //手指移动的距离
		if (offsetX <= 0) //如果小于0，表示已经缩放到小于或等于屏幕宽度的程度，不能进行移动了
		{
			return true;
		}
		if (scrollX <= minScrollX)//表示移动到了左边界，此时允许往右边移动，不允许往左边移动
		{
			if (moveX < 0) //表示往左移动
			{
				return true;
			}
		}
		if (scrollX >= maxScrollX)//表示缩放后移动到了右边界或移出了右边界，此时允许往左边移动，不允许往右边移动
		{
			if (moveX > 0) //表示往右移动
			{
				return true;
			}
		}

		//y轴方向判断
		int offsety = (int) (inner.getMeasuredHeight()*scale - getHeight()); //y轴移动的最大距离
		int maxScrollY = (int) Word.FIRST_SCROLLY + offsety/2;
		int minScrollY = (int) Word.FIRST_SCROLLY - offsety/2;

		int scrollY = getScrollY();
		float moveY = downY - ev.getY(); //手指移动的距离

		if (offsety <= 0) //如果小于0，表示已经缩放到小于或等于屏幕宽度的程度，不能进行移动了
		{
			return true;
		}
		if (scrollY <= minScrollY)//表示移动到了下边界，此时允许往上边移动，不允许往下边移动
		{
			if (moveY < 0) //表示往左移动
			{
				return true;
			}
		}
		if (scrollY >= maxScrollY)//表示缩放后移动到了上边界或移出了上边界，此时允许往下边移动，不允许往上边移动
		{
			if (moveY > 0) //表示往上移动
			{
				return true;
			}
		}
		return false;
	}

	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		if(mView2!=null){
			mView2.scrollTo(l, t);
		}

		if (mView != null) {
			mView.scrollTo(l, t);
		}
	}

	public void setScrollView(View view,View view1) {
		mView = view;
		mView2=view1;
	}
}
