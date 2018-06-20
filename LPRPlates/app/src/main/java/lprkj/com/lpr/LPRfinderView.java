package lprkj.com.lpr;


import lprkj.com.lpr.R;
import lprkj.com.lpr.R.color;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public final class LPRfinderView extends View {
	private static final long ANIMATION_DELAY = 50;
	private final Paint paint;
	private final Paint paintLine;
	private final int maskColor;
	private final int frameColor;
	private final int laserColor;

	private Paint mTextPaint;  
    private String mText;  
	/**
	 * 涓棿婊戝姩绾跨殑锟�?椤剁浣嶇疆
	 */
	// private int slideTop;
	// private int slideTop1;

	/**
	 * 涓棿婊戝姩绾跨殑锟�?搴曠浣嶇疆
	 */
	// private int slideBottom;
	/**
	 * 涓棿閭ｆ潯绾挎瘡娆″埛鏂扮Щ鍔ㄧ殑璺濈
	 */
	// private static final int SPEEN_DISTANCE = 10;
	/**
	 * 鎵弿妗嗕腑鐨勭嚎鐨勫锟�?
	 */
	// private static final int MIDDLE_LINE_WIDTH = 0;
	// private boolean isFirst = false;
	/**
	 * 鍥涘懆杈规鐨勫锟�?
	 */
	// private static final int FRAME_LINE_WIDTH = 0;
	private Rect frame;

	int w, h;
	boolean boo = false;
	int mPaddingLeft ;  
    int  mPaddingTop ;  
    int mPaddingRight ;  
     int mPaddingBottom ; 

	public LPRfinderView(Context context, int w, int h) {
		super(context);
		this.w = w;
		this.h = h;
		paint = new Paint();
		paintLine = new Paint();
		Resources resources = getResources();
		maskColor = resources.getColor(R.color.viewfinder_mask);
		frameColor = resources.getColor(R.color.viewfinder_frame);// 缁胯壊
		laserColor = resources.getColor(R.color.viewfinder_laser);// 鐧借壊
		  
	}

	public LPRfinderView(Context context, int w, int h, boolean boo) {
		super(context);
		this.w = w;
		this.h = h;
		this.boo = boo;
		paint = new Paint();
		paintLine = new Paint();
		Resources resources = getResources();
		maskColor = resources.getColor(R.color.viewfinder_mask);
		frameColor = resources.getColor(R.color.viewfinder_frame);// 缁胯壊
		laserColor = resources.getColor(R.color.viewfinder_laser);// 绾㈣壊
	}

	@Override
	public void onDraw(Canvas canvas) {
		int width = canvas.getWidth();
		int height = canvas.getHeight();

		int t;
		int b;
		int l;
		int r;
		
		l =  10;
		r = width-10;
		int ntmpH =(r-l)*58/88;
		t = (height-ntmpH)/2;
		b =  t+ntmpH;
		
			frame = new Rect(l, t, r, b);
		// 鐢诲嚭鎵弿妗嗗闈㈢殑闃村奖閮ㄥ垎锛屽叡鍥�;涓儴鍒嗭紝鎵弿妗嗙殑涓婇潰鍒板睆骞曚笂闈紝鎵弿妗嗙殑涓嬮潰鍒板睆骞曚笅闈�
		// 鎵弿妗嗙殑宸﹁竟闈㈠埌灞忓箷宸﹁竟锛屾壂鎻忔鐨勫彸杈瑰埌灞忓箷鍙宠竟
		paint.setColor(maskColor);
		canvas.drawRect(0, 0, width, frame.top, paint);
		canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
		canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1,
				paint);
		canvas.drawRect(0, frame.bottom + 1, width, height, paint);
		
		paintLine.setColor(frameColor);
		paintLine.setStrokeWidth(16);
		paintLine.setAntiAlias(true);
		int num = (r - l) / 10;
		canvas.drawLine(l - 8, t, l + num, t, paintLine);
		canvas.drawLine(l, t, l, t + num, paintLine);

		canvas.drawLine(r + 8, t, r - num, t, paintLine);
		canvas.drawLine(r, t, r, t + num, paintLine);

		canvas.drawLine(l - 8, b, l + num, b, paintLine);
		canvas.drawLine(l, b, l, b - num, paintLine);

		canvas.drawLine(r + 8, b, r - num, b, paintLine);
		canvas.drawLine(r, b, r, b - num, paintLine);

		paintLine.setColor(laserColor);
		paintLine.setAlpha(100);
		paintLine.setStrokeWidth(3);
		paintLine.setAntiAlias(true);
		canvas.drawLine(l, t + num, l, b - num, paintLine);

		canvas.drawLine(r, t + num, r, b - num, paintLine);

		canvas.drawLine(l + num, t, r - num, t, paintLine);

		canvas.drawLine(l + num, b, r - num, b, paintLine);

		
	     mText = "请将车牌置于框内";
	    // String mText1= "鑻ラ暱鏃堕棿鏃犳硶璇嗗埆锛岃鎷嶇収璇嗗埆";
	     mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);  
	     mTextPaint.setStrokeWidth(3);  
	     mTextPaint.setTextSize(50);  
	     mTextPaint.setColor(frameColor);  
	      mTextPaint.setTextAlign(Paint.Align.CENTER);  
	     canvas.drawText(mText,w/2,h/2, mTextPaint); 
	     //canvas.drawText(mText1,w/2,h/2+h/5, mTextPaint); 
		if (frame == null) {
			return;
		}

		/**
		 * 褰撴垜浠幏寰楃粨鏋滅殑鏃讹拷?锟斤紝鎴戜滑鏇存柊鏁翠釜灞忓箷鐨勫唴锟�?
		 */
		postInvalidateDelayed(ANIMATION_DELAY);
	}


}
