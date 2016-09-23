/*******************************************************************************
 * The MIT License (MIT)
 * 
 * Copyright (c) 2013 Triggertrap Ltd
 * Author Neil Davies
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 ******************************************************************************//*

package com.wongs.views;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.wongs.emidemo.R;
import com.wongs.utils.LogUtil;

import java.lang.annotation.Target;

*/
/**
 * 
 * SeekArc.java
 * 
 * This is a class that functions much like a SeekBar but
 * follows a circle path instead of a straight line.
 * 
 * @author Neil Davies
 * 
 *//*

public class SeekArc extends View {

	private static final String TAG = SeekArc.class.getSimpleName();
	private static int INVALID_PROGRESS_VALUE = -1;
	// The initial rotational offset -90 means we start at 12 o'clock
	private final int mAngleOffset = -90;

	public void setMode(int mode) {
		this.mode = mode;
	}

	private int mode;
	*/
/**
	 * The Drawable for the seek arc thumbnail
	 *//*

	private Drawable mThumb;


	private Drawable mThumb_2;
	*/
/**
	 * The Maximum value that this SeekArc can be set to
	 *//*

	private int mMax = 100;
	
	*/
/**
	 * The Current value that the SeekArc is set to
	 *//*

	private int mProgress = 0;
		
	*/
/**
	 * The width of the progress line for this SeekArc
	 *//*

	private int mProgressWidth = 10;
	
	*/
/**
	 * The Width of the background arc for the SeekArc 
	 *//*

	private int mArcWidth = 2;
	
	*/
/**
	 * The Angle to start drawing this Arc from
	 *//*

	private int mStartAngle = 0;
	
	*/
/**
	 * The Angle through which to draw the arc (Max is 360)
	 *//*

	private int mSweepAngle = 360;
	
	*/
/**
	 * The rotation of the SeekArc- 0 is twelve o'clock
	 *//*

	private int mRotation = 0;
	
	*/
/**
	 * Give the SeekArc rounded edges
	 *//*

	private boolean mRoundedEdges = false;
	
	*/
/**
	 * Enable touch inside the SeekArc
	 *//*

	private boolean mTouchInside = true;
	
	*/
/**
	 * Will the progress increase clockwise or anti-clockwise
	 *//*

	private boolean mClockwise = true;
	*/
/**
	 *
	 *//*

	private float cool_sweep = 0;

	private static final int VIEW_MODE = 1;
	private static final int COOL_MODE =2;
	private static final int HEAT_MODE = 3;
    private static final int NO_MODE = 4;
	// Internal variables
	private int mArcRadius = 0;
	private float mProgressSweep = 0;
	private float sweepRotation = 45;
	private RectF mArcRect = new RectF();
	private Paint mArcPaint;
	private Paint mProgressPaint;
	private Paint mPaintLine;
	private Paint mProgressPaint_2;
	private int mTranslateX;
	private int mTranslateY;
	private int mThumbXPos;
	private int mThumbYPos;

	private int mThumbXPos_2;
	private int mThumbYPos_2;
	private double mTouchAngle;
	private float mTouchIgnoreRadius;

	private Point heatPoint = new Point();
	private Point coolPoint = new Point();
	private Point touchPoint = new Point();

	private float mArcCenterX;
	private float mArcCenterY;
	private float mStartLineX;
	private float mStartLineY;
	private float mEndLineX;
	private float mEndLineY;
	private OnSeekArcChangeListener mOnSeekArcChangeListener;

	public interface OnSeekArcChangeListener {

		*/
/**
		 * Notification that the progress level has changed. Clients can use the
		 * fromUser parameter to distinguish user-initiated changes from those
		 * that occurred programmatically.
		 * 
		 * @param seekArc
		 *            The SeekArc whose progress has changed
		 * @param progress
		 *            The current progress level. This will be in the range
		 *            0..max where max was set by
		 *            {@link ProgressArc#setMax(int)}. (The default value for
		 *            max is 100.)
		 * @param fromUser
		 *            True if the progress change was initiated by the user.
		 *//*

		void onProgressChanged(SeekArc seekArc, int progress, boolean fromUser);

		*/
/**
		 * Notification that the user has started a touch gesture. Clients may
		 * want to use this to disable advancing the seekbar.
		 * 
		 * @param seekArc
		 *            The SeekArc in which the touch gesture began
		 *//*

		void onStartTrackingTouch(SeekArc seekArc);

		*/
/**
		 * Notification that the user has finished a touch gesture. Clients may
		 * want to use this to re-enable advancing the seekarc.
		 * 
		 * @param seekArc
		 *            The SeekArc in which the touch gesture began
		 *//*

		void onStopTrackingTouch(SeekArc seekArc);
	}

	public SeekArc(Context context) {
		super(context);
		init(context, null, 0);
	}

	public SeekArc(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs, R.attr.seekArcStyle);
	}

	public SeekArc(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs, defStyle);
	}

	private void init(Context context, AttributeSet attrs, int defStyle) {

		Log.d(TAG, "Initialising SeekArc");
		final Resources res = getResources();
		float density = context.getResources().getDisplayMetrics().density;

		// Defaults, may need to link this into theme settings
		int arcColor = res.getColor(R.color.progress_gray);
		int progressColor = res.getColor(R.color.color_green);
		int thumbHalfheight = 0;
		int thumbHalfWidth = 0;
		mThumb = res.getDrawable(R.drawable.seek_arc_control_selector);
		mThumb_2 = res.getDrawable(R.drawable.seek_arc_control_selector);
		// Convert progress width to pixels for current density
		mProgressWidth = (int) (mProgressWidth * density);

		
		if (attrs != null) {
			// Attribute initialization
			final TypedArray a = context.obtainStyledAttributes(attrs,
					R.styleable.SeekArc, defStyle, 0);

			Drawable thumb = a.getDrawable(R.styleable.SeekArc_thumb);
			if (thumb != null) {
				mThumb = thumb;
			}

			
			
			thumbHalfheight = (int) mThumb.getIntrinsicHeight() / 2;
			thumbHalfWidth = (int) mThumb.getIntrinsicWidth() / 2;

			int thumbHalfheight_2 = mThumb_2.getIntrinsicHeight()/2;
			int thumbHalfWidth_2 = mThumb_2.getIntrinsicWidth()/2;

			mThumb_2.setBounds(-thumbHalfWidth_2,-thumbHalfheight_2,thumbHalfWidth_2,thumbHalfheight_2);

			mThumb.setBounds(-thumbHalfWidth, -thumbHalfheight, thumbHalfWidth,
					thumbHalfheight);

			mMax = a.getInteger(R.styleable.SeekArc_max, mMax);
			mProgress = a.getInteger(R.styleable.SeekArc_progress, mProgress);
			mProgressWidth = (int) a.getDimension(
					R.styleable.SeekArc_progressWidth, mProgressWidth);
			mArcWidth = (int) a.getDimension(R.styleable.SeekArc_arcWidth,
					mArcWidth);
			mStartAngle = a.getInt(R.styleable.SeekArc_startAngle, mStartAngle);
			mSweepAngle = a.getInt(R.styleable.SeekArc_sweepAngle, mSweepAngle);
			mRotation = a.getInt(R.styleable.SeekArc_rotation, mRotation);
			mRoundedEdges = a.getBoolean(R.styleable.SeekArc_roundEdges,
					mRoundedEdges);
			mTouchInside = a.getBoolean(R.styleable.SeekArc_touchInside,
					mTouchInside);
			mClockwise = a.getBoolean(R.styleable.SeekArc_clockwise,
					mClockwise);
			
			arcColor = a.getColor(R.styleable.SeekArc_arcColor, arcColor);
			progressColor = a.getColor(R.styleable.SeekArc_progressColor,
					progressColor);

			a.recycle();
		}

		mProgress = (mProgress > mMax) ? mMax : mProgress;
		mProgress = (mProgress < 0) ? 0 : mProgress;
		mSweepAngle = (mSweepAngle > 360) ? 360 : mSweepAngle;
		mSweepAngle = (mSweepAngle < 0) ? 0 : mSweepAngle;

		mStartAngle = (mStartAngle > 360) ? 0 : mStartAngle;
		mStartAngle = (mStartAngle < 0) ? 0 : mStartAngle;

		mArcPaint = new Paint();
		mArcPaint.setColor(arcColor);
		mArcPaint.setAntiAlias(true);
		mArcPaint.setStyle(Paint.Style.STROKE);
		mArcPaint.setStrokeWidth(mArcWidth);
		//mArcPaint.setAlpha(45);
		mPaintLine = new Paint();
		mPaintLine.setColor(res.getColor(R.color.main_color));
		mPaintLine.setAntiAlias(true);
		mPaintLine.setStyle(Paint.Style.STROKE);
		mPaintLine.setStrokeWidth(8);

		mProgressPaint_2 = new Paint();
		mProgressPaint_2.setColor(res.getColor(R.color.color_cyan));
		mProgressPaint_2.setAntiAlias(true);
		mProgressPaint_2.setStyle(Paint.Style.STROKE);
		mProgressPaint_2.setStrokeWidth(mArcWidth);

		mProgressPaint = new Paint();
		mProgressPaint.setColor(progressColor);
		mProgressPaint.setAntiAlias(true);
		mProgressPaint.setStyle(Paint.Style.STROKE);
		mProgressPaint.setStrokeWidth(mArcWidth);

		if (mRoundedEdges) {
			mArcPaint.setStrokeCap(Paint.Cap.ROUND);
			mProgressPaint.setStrokeCap(Paint.Cap.ROUND);
		}
		setMode(VIEW_MODE);
	}

	@Override
	protected void onDraw(Canvas canvas) {

		if(!mClockwise) {
			canvas.scale(-1, 1, mArcRect.centerX(), mArcRect.centerY() );
		}
		mArcCenterX = mArcRect.centerX();
		mArcCenterY = mArcRect.centerY();
	//	LogUtil.d(TAG,"mArcRect.centerX():"+mArcRect.centerX());
				drawSeekArc(canvas);


	}
	private int heatPosX;
	private int heatPosY;
	private int coolPosX;
	private int coolPosY;
	*/
/**
	 * when first in draw view
	 * @param canvas
	 *//*

	public void drawSeekArc(Canvas canvas){

		final int arcStart = mStartAngle + mAngleOffset + mRotation;
		final int arcSweep = mSweepAngle;
		canvas.drawArc(mArcRect, arcStart, arcSweep, false, mArcPaint);
		//draw progress one
		canvas.drawArc(mArcRect, arcStart, mProgressSweep, false,
				mProgressPaint);
		//draw progress two
		canvas.drawArc(mArcRect,mStartAngle+mSweepAngle+90,cool_sweep,false,mProgressPaint_2);

	//	int a = (int) (mProgressSweep + 45);
		//	drawLine(a, canvas);
		// Draw the thumb nail
		heatPosX = mTranslateX - mThumbXPos;
		heatPosY = mTranslateY - mThumbYPos;

        coolPosX = mTranslateX - mThumbXPos_2;
        coolPosY = mTranslateY - mThumbYPos_2;
		heatPoint.set(heatPosX, heatPosY);
		coolPoint.set(coolPosX, coolPosY);
		if (mode == VIEW_MODE || mode == HEAT_MODE) {
			canvas.translate(heatPosX, heatPosY);
			mThumb.draw(canvas);
			canvas.translate(coolPosX - heatPosX, coolPosY - heatPosY);
			mThumb_2.draw(canvas);
		}
		if (mode == COOL_MODE) {
			canvas.translate(coolPosX,coolPosY);
			mThumb_2.draw(canvas);
			canvas.translate(heatPosX-coolPosX, heatPosY-coolPosY);
			mThumb.draw(canvas);
		}

	}

	public void drawLine(int progress,Canvas canvas){
		float x = 0,y = 0;
		int arc =0;
		if (progress < 90  && progress >= 0){
			arc = progress;
			x = (float) (mArcRadius * Math.abs(Math.cos(Math.toRadians(arc))));
			y = (float) (mArcRadius * Math.abs(Math.sin(Math.toRadians(arc))));
			mStartLineX = mArcCenterX - x;
			mStartLineY = mArcCenterY + y;
		//	LogUtil.d(TAG,"mProgressSweep 0-90:"+mProgressSweep);
		}
		else if (progress >= 90 && progress < 180){
			arc = progress-90;
			x = (float) (mArcRadius * Math.abs(Math.cos(Math.toRadians(arc))));
			y = (float) (mArcRadius * Math.abs(Math.sin(Math.toRadians(arc))));
			mStartLineX = mArcCenterX - x;
			mStartLineY = mArcCenterY - y;
		//	LogUtil.d(TAG,"mProgressSweep90-180:"+mProgressSweep);
		}
		else if (progress >= 180 && progress < 270){
			arc = progress-180;
			x = (float) (mArcRadius * Math.abs(Math.cos(Math.toRadians(arc))));
			y = (float) (mArcRadius * Math.abs(Math.sin(Math.toRadians(arc))));
			mStartLineX = mArcCenterX + x;
			mStartLineY =mArcCenterY - y;
		//	LogUtil.d(TAG,"mProgressSweep 180 -270:"+mProgressSweep);
		}
		else if (progress >= 270 && progress <= 360){
			arc = progress-270;
			x = (float) (mArcRadius * Math.abs(Math.cos(Math.toRadians(arc))));
			y = (float) (mArcRadius * Math.abs(Math.sin(Math.toRadians(arc))));
			mStartLineX = mArcCenterX + x;
			mStartLineY = mArcCenterY + y;
		//	LogUtil.d(TAG,"mProgressSweep 270-360:"+mProgressSweep);
		}

	//	LogUtil.d(TAG, "mStartLineX:" + mStartLineX);
	//	LogUtil.d(TAG, "mStartLineY:" + mStartLineY);


		canvas.drawLine(mStartLineX, mStartLineY, mArcCenterX, mArcCenterY, mPaintLine);
	}


	*/
/*private boolean isPointOnThumb(float eventX, float eventY) {
		boolean result = false;
		double distance = Math.sqrt(Math.pow(eventX - mTranslateX, 2)
				+ Math.pow(eventY - mTranslateY, 2));
		if (distance < mSeekBarSize && distance > (mSeekBarSize / 2 - mThumbWidth)){
			result = true;
		}
		return result;
	}*//*


	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		final int height = getDefaultSize(getSuggestedMinimumHeight(),
				heightMeasureSpec);
		final int width = getDefaultSize(getSuggestedMinimumWidth(),
				widthMeasureSpec);
		final int min = Math.min(width, height);
		float top = 0;
		float left = 0;
		int arcDiameter = 0;

		mTranslateX = (int) (width * 0.5f);
		mTranslateY = (int) (height * 0.5f);

	//	LogUtil.d(TAG,"mTranslateX:"+mTranslateX);
	//	LogUtil.d(TAG,"mTranslateY:"+mTranslateY);

		arcDiameter = min - getPaddingLeft()-mArcWidth;
		mArcRadius = arcDiameter / 2;
		top = height / 2 - (arcDiameter / 2);
		left = width / 2 - (arcDiameter / 2);
		mArcRect.set(left, top, left + arcDiameter, top + arcDiameter);
		//final int arcStart = mStartAngle + mAngleOffset + mRotation;
		int arcStart = (int)mProgressSweep + mStartAngle + mRotation + 90;

		mThumbXPos = (int) (mArcRadius * Math.cos(Math.toRadians(arcStart)));
		mThumbYPos = (int) (mArcRadius * Math.sin(Math.toRadians(arcStart)));
	//	LogUtil.e(TAG, "180d * Math.PI :" +  Math.PI/180d);
	//	LogUtil.e(TAG, "Math.toRadians(arcStart) :" + Math.toRadians(arcStart));
		int arcStart_2 = (int)cool_sweep+ mRotation+mStartAngle+90+mSweepAngle;
	//	LogUtil.d(TAG, "arcStart 22:" + arcStart_2);
		mThumbXPos_2 = (int) (mArcRadius * Math.cos(Math.toRadians(arcStart_2)));
		mThumbYPos_2 = (int) (mArcRadius * Math.sin(Math.toRadians(arcStart_2)));
*/
/*
		LogUtil.d(TAG, "mThumbXPos X:" + mThumbXPos);
		LogUtil.d(TAG, "mThumbXPos2222 X:" + mThumbXPos_2);
		LogUtil.d(TAG, "mThumbXPos Y:" + mThumbYPos);
		LogUtil.d(TAG, "mThumbXPos2222 Y:" + mThumbYPos_2);
*//*


		setTouchInSide(mTouchInside);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	public void lineRefresh(int temperature){





	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			onStartTrackingTouch();
			updateOnTouch(event);
            break;
		case MotionEvent.ACTION_MOVE:
			updateOnTouch(event);
            break;
		case MotionEvent.ACTION_UP:
			onStopTrackingTouch();
			setPressed(false);
			break;
		case MotionEvent.ACTION_CANCEL:
			onStopTrackingTouch();
			setPressed(false);

			break;
		}

		return true;
	}
	public int pointArcClosest(Point event){

		*/
/*Log.e(TAG, "pointsAreClose\nevent.x:" + event.x
                + " event.y:" + event.y
                + "\nheatPonit.x:" + heatPoint.x
                + " heatPonit.y:" + heatPoint.y);

		Log.e(TAG, "pointsAreClose\nevent.x:" + event.x
				+ " event.y:" + event.y
				+ "\ncoolPonit.x:" + coolPoint.x
				+ " coolPonit.y:" + coolPoint.y);*//*

		if ((event.x < heatPoint.x+50)&&(event.x > heatPoint.x-50)&&(event.y < heatPoint.y+50)&&(event.y > heatPoint.y-50)){
             setMode(HEAT_MODE);
			LogUtil.e(TAG,"current mode == HEAT_MODE");
            return HEAT_MODE;
        }

		if ((event.x < coolPoint.x+50)&&(event.x > coolPoint.x-50)&&(event.y < coolPoint.y+50)&&(event.y > coolPoint.y-50)) {
			setMode(COOL_MODE);
			LogUtil.e(TAG, "current mode == COOL_MODE");
            return COOL_MODE;
        }
		LogUtil.e(TAG, "current mode == VIEW_MODE");
        return VIEW_MODE;
	}
	@Override
	protected void drawableStateChanged() {
        super.drawableStateChanged();
		if (mThumb != null && mThumb.isStateful()) {
			int[] state = getDrawableState();
			mThumb.setState(state);
		}
		invalidate();
	}

	private void onStartTrackingTouch() {
		if (mOnSeekArcChangeListener != null) {
			mOnSeekArcChangeListener.onStartTrackingTouch(this);
		}
	}

	private void onStopTrackingTouch() {
		if (mOnSeekArcChangeListener != null) {
			mOnSeekArcChangeListener.onStopTrackingTouch(this);
		}
	}

	private void updateOnTouch(MotionEvent event) {
		boolean ignoreTouch = ignoreTouch(event.getX(), event.getY());
		if (ignoreTouch) {
			return;
		}

		if(pointArcClosest(new Point((int)event.getX(),(int)event.getY())) == COOL_MODE) {
		//	setPressed(true);
			mTouchAngle =   getTouchDegrees(event.getX(), event.getY());
			LogUtil.d(TAG,"COOL MODE mTouchAngle::"+mTouchAngle);
		//	int progress = getProgressForAngle(mTouchAngle);
		//	LogUtil.d(TAG,"COOL MODE progress::"+progress);
			cool_sweep = (float) mTouchAngle - 270;
			LogUtil.d(TAG,"COOL MODE cool_sweep::"+cool_sweep);
			updateCoolPosition();
			//onProgressRefresh((int) mTouchAngle, true);
		}
		if(pointArcClosest(new Point((int)event.getX(),(int)event.getY())) == HEAT_MODE) {
			//	setPressed(true);
			mTouchAngle =   getTouchDegrees(event.getX(), event.getY());
			LogUtil.d(TAG,"heatCOOL MODE mTouchAngle::"+mTouchAngle);
			//	int progress = getProgressForAngle(mTouchAngle);
			//	LogUtil.d(TAG,"COOL MODE progress::"+progress);
			LogUtil.d(TAG,"heat MODE mProgressSweep::"+mProgressSweep);
			mProgressSweep = (float) mTouchAngle;
			updateHeatPosition();
		}
		invalidate();
	}

	private boolean ignoreTouch(float xPos, float yPos) {
		boolean ignore = false;
		float x = xPos - mTranslateX;
		float y = yPos - mTranslateY;

		float touchRadius = (float) Math.sqrt(((x * x) + (y * y)));
		if (touchRadius < mTouchIgnoreRadius) {
			ignore = true;
		}
		return ignore;
	}

	private double getTouchDegrees(float xPos, float yPos) {
		float x = xPos - mTranslateX;
		float y = yPos - mTranslateY;
		//invert the x-coord if we are rotating anti-clockwise
		x= (mClockwise) ? x:-x;
		// convert to arc Angle
		double angle = Math.toDegrees(Math.atan2(y, x) + (Math.PI / 2)
				- Math.toRadians(mRotation));

		if (angle < 0) {
			angle = 360 + angle;
		}
		angle -= mStartAngle;
		return angle;
	}

	private int getProgressForAngle(double angle) {
		int touchProgress = (int) Math.round(valuePerDegree() * angle);
		LogUtil.d(TAG,"COOL MODE touchProgress::"+touchProgress);
		*/
/*touchProgress = (touchProgress < 0) ? INVALID_PROGRESS_VALUE
				: touchProgress;
		touchProgress = (touchProgress > mMax) ? INVALID_PROGRESS_VALUE
				: touchProgress;*//*

		LogUtil.d(TAG,"COOL MODE touchProgress11111::"+touchProgress);
		return touchProgress;
	}

	private float valuePerDegree() {
		return (float) mMax / mSweepAngle;
	}

	private void onProgressRefresh(int progress, boolean fromUser) {
		updateProgress(progress, fromUser);
	}

	private void updateHeatPosition() {
		int thumbAngle = (int) (mStartAngle + mProgressSweep + mRotation + 90);
		mThumbXPos = (int) (mArcRadius * Math.cos(Math.toRadians(thumbAngle)));
        mThumbYPos = (int) (mArcRadius * Math.sin(Math.toRadians(thumbAngle)));
	}

    private void updateCoolPosition(){
		int arcStart_2 = (int)cool_sweep+ mRotation+mStartAngle+90+mSweepAngle;
		mThumbXPos_2 = (int) (mArcRadius * Math.cos(Math.toRadians(arcStart_2)));
		mThumbYPos_2 = (int) (mArcRadius * Math.sin(Math.toRadians(arcStart_2)));
		LogUtil.d(TAG,"updateCoolPosition arcStart_2::"+arcStart_2);
		LogUtil.d(TAG,"updateCoolPosition mThumbXPos_2::"+mThumbXPos_2);
		LogUtil.d(TAG,"updateCoolPosition mThumbYPos_2::"+mThumbYPos_2);
    }
	private void updateProgress(int progress, boolean fromUser) {


		if (mOnSeekArcChangeListener != null) {
			mOnSeekArcChangeListener
					.onProgressChanged(this, progress, fromUser);
		}
		if (mode == COOL_MODE){
			progress = mMax-progress;
			cool_sweep =-(float) progress / mMax * mSweepAngle;
			LogUtil.d(TAG,"update cool_sweep=="+cool_sweep);
			updateCoolPosition();
		}else if (mode == HEAT_MODE){

			if (progress == INVALID_PROGRESS_VALUE) {
				return;
			}
			progress = (progress > mMax) ? mMax : progress;
			progress = (mProgress < 0) ? 0 : progress;
			mProgress = progress;
			mProgressSweep = (float) progress / mMax * mSweepAngle;
			updateHeatPosition();
		}

		invalidate();
	}

	*/
/**
	 * Sets a listener to receive notifications of changes to the SeekArc's
	 * progress level. Also provides notifications of when the user starts and
	 * stops a touch gesture within the SeekArc.
	 * 
	 * @param l
	 *            The seek bar notification listener
	 * 
	 * @see SeekArc.OnSeekBarChangeListener
	 *//*

	public void setOnSeekArcChangeListener(OnSeekArcChangeListener l) {
		mOnSeekArcChangeListener = l;
	}

	public void setProgress(int progress) {
		updateProgress(progress, false);
	}

	public int getProgressWidth() {
		return mProgressWidth;
	}

	public void setProgressWidth(int mProgressWidth) {
		this.mProgressWidth = mProgressWidth;
		mProgressPaint.setStrokeWidth(mProgressWidth);
	}
	
	public int getArcWidth() {
		return mArcWidth;
	}

	public void setArcWidth(int mArcWidth) {
		this.mArcWidth = mArcWidth;
		mArcPaint.setStrokeWidth(mArcWidth);
	}
	public int getArcRotation() {
		return mRotation;
	}

	public void setArcRotation(int mRotation) {
		this.mRotation = mRotation;
        updateHeatPosition();
	}

	public int getStartAngle() {
		return mStartAngle;
	}

	public void setStartAngle(int mStartAngle) {
		this.mStartAngle = mStartAngle;
        updateHeatPosition();
	}

	public int getSweepAngle() {
		return mSweepAngle;
	}

	public void setSweepAngle(int mSweepAngle) {
		this.mSweepAngle = mSweepAngle;
        updateHeatPosition();
	}
	
	public void setRoundedEdges(boolean isEnabled) {
		mRoundedEdges = isEnabled;
		if (mRoundedEdges) {
			mArcPaint.setStrokeCap(Paint.Cap.ROUND);
			mProgressPaint.setStrokeCap(Paint.Cap.ROUND);
		} else {
			mArcPaint.setStrokeCap(Paint.Cap.SQUARE);
			mProgressPaint.setStrokeCap(Paint.Cap.SQUARE);
		}
	}
	
	public void setTouchInSide(boolean isEnabled) {
		int thumbHalfheight = (int) mThumb.getIntrinsicHeight() / 2;
		int thumbHalfWidth = (int) mThumb.getIntrinsicWidth() / 2;
		mTouchInside = isEnabled;
		if (mTouchInside) {
			mTouchIgnoreRadius = (float) mArcRadius / 4;
		} else {
			// Don't use the exact radius makes interaction too tricky
			mTouchIgnoreRadius = mArcRadius
					- Math.min(thumbHalfWidth, thumbHalfheight);
		}
	}
	
	public void setClockwise(boolean isClockwise) {
		mClockwise = isClockwise;
	}
}
*/
