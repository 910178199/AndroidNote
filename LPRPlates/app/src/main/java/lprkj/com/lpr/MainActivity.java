package lprkj.com.lpr;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import com.lpr.*;
import lprkj.com.lpr.R.id;
import lprkj.com.lpr.R.layout;
import lprkj.com.lpr.R.menu;

import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Size;
import android.telephony.TelephonyManager;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends Activity  implements SurfaceHolder.Callback, Camera.PreviewCallback{
	private static final String PATH = Environment.getExternalStorageDirectory().toString() + "/DCIM/Camera/";
	private Camera mycamera;
	private SurfaceView surfaceView;
	private RelativeLayout re_c;
	private SurfaceHolder surfaceHolder;
	private LPR   api=null;
	private int preWidth = 0;
	private int preHeight = 0;
	private int width;
	private int height;
	private TimerTask timer;
	private Timer timer2;
	private byte[] tackData;
	private LPRfinderView myView=null;
	private long recogTime;
	private boolean isFatty = false;
	private boolean  bInitKernal=false;
	private ImageButton back;
	private ImageButton info;
	AlertDialog alertDialog = null;
	AlertDialog alertDialoginfo = null;
	Toast toast;
	String FilePath="";
	private Vibrator mVibrator;
	private String resultStr =null;
	private boolean bROI=false;
	private int[] m_ROI={0,0,0,0};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		int metricwidth = metric.widthPixels; // 屏幕宽度（像素）
		int metricheight = metric.heightPixels; // 屏幕高度（像素）
		try {
			copyDataBase();
		} catch (IOException e) {
			e.printStackTrace();
		}
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 竖屏
		Configuration cf= this.getResources().getConfiguration(); //获取设置的配置信息
		int noriention=cf.orientation;
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
		// // 屏幕常亮
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		setContentView(R.layout.activity_main);
		findView();
		
	}
	private void findView() {
		surfaceView = (SurfaceView) findViewById(R.id.surfaceViwe);
		re_c = (RelativeLayout) findViewById(R.id.re_c);
		back = (ImageButton) findViewById(R.id.back);
		info = (ImageButton) findViewById(R.id.info);
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		width = metric.widthPixels; // 屏幕宽度（像素）
		height = metric.heightPixels; // 屏幕高度（像素）
		
		int back_w = (int) (height * 0.066796875);
		int back_h = (int) (back_w * 1);
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(back_w, back_h);
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
		layoutParams.topMargin = (int) (( back_h/2));
		layoutParams.leftMargin = (int) (width * 0.15);
		back.setLayoutParams(layoutParams);
		
		int info_w = (int) (height * 0.066796875);
		int info_h = (int) (info_w );
		 layoutParams = new RelativeLayout.LayoutParams(info_w, info_h);
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
		layoutParams.topMargin = (int) (( back_h/2));
		layoutParams.rightMargin = (int) (height * 0.15);
		info.setLayoutParams(layoutParams);
		
		 if(myView==null)
  	   {
  		   if (isFatty)
  		   {
  			   myView = new LPRfinderView(MainActivity.this, width, height, isFatty);
  		   }
  		   else
  		   {
  			   myView = new LPRfinderView(MainActivity.this, width, height);
  		   } 
  		   re_c.addView(myView);
  	   }
		
	
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		info.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				if (!getDialogStatus())
//				{
//					alertDialoginfo.setTitle("联系我们");
//					alertDialoginfo.setMessage("北京科技有限公司\r\n\r\n电话:01089,1886555558\r\n\r\nQQ:55555555555555\r\n");
//					Window window = alertDialoginfo.getWindow();
//					WindowManager.LayoutParams lp = window.getAttributes();
//					// 设置透明度为0.3
//					lp.alpha = 0.8f;
//					//lp.width = width * 1/2;
//					// lp.flags= 0x00000020;
//					window.setAttributes(lp);
//					window.setGravity(Gravity.CENTER);
//					alertDialoginfo.show();
//				}
				
			}
		});
		surfaceHolder = surfaceView.getHolder();
		surfaceHolder.addCallback(MainActivity.this);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		surfaceView.setFocusable(true);
		//surfaceView.invali.date();
		
  }
	public void copyDataBase() throws IOException {
	      //  Common common = new Common();
	        String dst = Environment.getExternalStorageDirectory().toString() + "/lpr.key";
	         
	            File file = new File(dst);
	            if (!file.exists()) {
	                // file.createNewFile();
	            } else {
	                file.delete();
	            }

	            try {
	                InputStream myInput = getAssets().open("lpr.key");
	                OutputStream myOutput = new FileOutputStream(dst);
	                byte[] buffer = new byte[1024];
	                int length;
	                while ((length = myInput.read(buffer)) > 0) {
	                    myOutput.write(buffer, 0, length);
	                }
	                myOutput.flush();
	                myOutput.close();
	                myInput.close();
	            } catch (Exception e) {
	                System.out.println("lpr.key" + "is not found");
	            }
	        }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void surfaceCreated(SurfaceHolder holder) {

		if (mycamera == null) {
			try {
				mycamera = Camera.open();
			} catch (Exception e) {
				e.printStackTrace();
				String mess = "打开摄像头失败";
				Toast.makeText(getApplicationContext(), mess, Toast.LENGTH_LONG).show();
				return;
			}
		}
		if(mycamera!=null)
		{
			try {
	
				mycamera.setPreviewDisplay(holder);
				//mycamera.setDisplayOrientation(180);
				timer2 = new Timer();
				if (timer == null)
				{
					timer = new TimerTask()
					{
						public void run()
						{
							if (mycamera != null)
							{
								try
								{
									mycamera.autoFocus(new AutoFocusCallback()
									{
										public void onAutoFocus(boolean success, Camera camera)
										{
										
										}
									});
								}
								catch (Exception e)
								{
									e.printStackTrace();
								}
							}
						};
					};
				}
				timer2.schedule(timer, 500, 2500);
				initCamera();
				//mycamera.startPreview();
				//mycamera.autoFocus(null);

			} catch (IOException e) {
				e.printStackTrace();

			}
		}
		if(api==null)
		{
			api= new LPR();
			String FilePath =Environment.getExternalStorageDirectory().toString()+"/lpr.key";
			int nRet = api.Init(this,m_ROI[0], m_ROI[1], m_ROI[2], m_ROI[3], preHeight, preWidth,FilePath);
			if(nRet!=0)
			{
				Toast.makeText(getApplicationContext(), "激活失败", Toast.LENGTH_SHORT).show();
				bInitKernal =false;
			}
			else
			{
				bInitKernal=true;
			}
		}
		if(alertDialog==null){
			alertDialog = new AlertDialog.Builder(this).create();
			alertDialoginfo = new AlertDialog.Builder(this).create();
		}
		
	}
  
	public boolean getDialogStatus()
	{
		if(alertDialog!=null&&alertDialoginfo!=null){
			if	(alertDialog.isShowing()||alertDialoginfo.isShowing()){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void surfaceChanged(final SurfaceHolder holder, int format, int width, int height) {
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		try {
			if (mycamera != null) {
				mycamera.setPreviewCallback(null);
				mycamera.stopPreview();
				mycamera.release();
				mycamera = null;
			}
		} catch (Exception e) {
		}
		if(bInitKernal){
			bInitKernal=false;
			api = null;
		}
		   if(toast!=null){
			   toast.cancel(); 
			   toast = null;
		   	}
		   if(timer2!=null){
				timer2.cancel();
				timer2=null;
			}
		   if(alertDialog!=null)
			{
			   alertDialog.dismiss();
			   alertDialog.cancel();
				alertDialog=null;
			}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			try {
				if (mycamera != null) {
					mycamera.setPreviewCallback(null);
					mycamera.stopPreview();
					mycamera.release();
					mycamera = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(bInitKernal)
			{
			  bInitKernal=false;
			  api = null;
			}
			finish();
			  if(toast!=null){
				   toast.cancel(); 
				   toast=null;
			  	}
			  if(timer2!=null){
					timer2.cancel();
					timer2=null;
				}
			  if(alertDialog!=null)
				{
					alertDialog.cancel();
					alertDialog=null;
				}
			 // android.os.Process.killProcess(android.os.Process.myPid());
			 // System.exit(0);
		}
		return super.onKeyDown(keyCode, event);
	}

	@TargetApi(14)
	private void initCamera() {
		Camera.Parameters parameters = mycamera.getParameters();
		List<Camera.Size> list = parameters.getSupportedPreviewSizes();
		Camera.Size size;
		Size tmpsize = getOptimalPreviewSize(list,height,width);
		int length = list.size();
		int previewWidth = list.get(0).width;
		int previewheight = list.get(0).height;
		int second_previewWidth = 0;
		int second_previewheight = 0;
		int nlast = -1;
		int nThird =-1;
		int Third_previewWidth = 0;
		int Third_previewheight = 0;
		previewWidth = tmpsize.width;
		previewheight = tmpsize.height;
		if (length == 1) {
		   preWidth = previewWidth;
	       preHeight = previewheight;
		}
		else
		{
			second_previewWidth=previewWidth;
			second_previewheight = previewheight;
			for (int i = 0; i < length; i++) {
				size = list.get(i);
				if(size.height>700&&size.height< previewheight)
				{
					if(size.width * previewheight == size.height * previewWidth && size.height<second_previewheight)
					{
						second_previewWidth =size.width;
						second_previewheight= size.height;
					}
				}
			}
			 preWidth = second_previewWidth;
		       preHeight = second_previewheight;
		}
		
	   parameters.setPictureFormat(PixelFormat.JPEG);

		parameters.setPreviewSize(preWidth,preHeight);
		if (!bROI) {
			int l,t,r,b;
			l =  10;
			r = width-10;
			int ntmpH =(r-l)*58/88;
			t = (height-ntmpH)/2;
			b =  t+ntmpH;
			double proportion = (double) width / (double) preHeight;
			double hproportion=(double)height/(double)  preWidth;
			l = (int) (l /proportion);
			t = (int) (t /hproportion);
			r = (int) (r /proportion);
			b = (int) (b / hproportion);
			m_ROI[0]=l;
			m_ROI[1]=t;
			m_ROI[2]=r;
			m_ROI[3]=b;
		//	m_ROI[0]=0;
		//	m_ROI[1]=0;
		//	m_ROI[2]=preHeight;
		//	m_ROI[3]=preWidth;
			bROI = true;
		}
		if (parameters.getSupportedFocusModes().contains(
				parameters.FOCUS_MODE_AUTO))
		{
			parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);// 1连续对焦
		}
		if(parameters.isZoomSupported()){
		     parameters.setZoom(2);
		}
		mycamera.setPreviewCallback(MainActivity.this);
		mycamera.setParameters(parameters);
		mycamera.setDisplayOrientation(90);
		mycamera.startPreview();
	}
	public void onPreviewFrame(byte[] data, Camera camera) {
		//surfaceView.invalidate();
		   tackData = data;
		   resultStr = "";
		if (!getDialogStatus()&& bInitKernal ) {
//			int buffl = 256;
//			char recogval[] = new char[buffl];
//			Date dt = new Date();
//			int dataLen = preWidth*preHeight*3/2;
//			short picData[]= new short[dataLen];
//			for	(int i =0;i<dataLen;i++)
//			{
//				picData[i]= (tackData[i]);
//			}
			byte result[];//[] = new byte[10];
			String res="";
			result = api.VideoRec(tackData, preWidth, preHeight, 1);
			try {
				res = new String(result,"gb2312");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(res!=null&&!"".equals(res.trim()))
			//if(res!="")
			{
				Camera.Parameters parameters = mycamera.getParameters();
				mVibrator = (Vibrator) getApplication().getSystemService(
						Service.VIBRATOR_SERVICE);
				mVibrator.vibrate(50);
				// 删除正常识别保存图片功能
				int[] datas = convertYUV420_NV21toARGB8888(tackData,
						parameters.getPreviewSize().width,
						parameters.getPreviewSize().height);

				BitmapFactory.Options opts = new BitmapFactory.Options();
				opts.inInputShareable = true;
				opts.inPurgeable = true;
				Bitmap bitmap = Bitmap.createBitmap(datas,
						parameters.getPreviewSize().width,
						parameters.getPreviewSize().height,
						android.graphics.Bitmap.Config.ARGB_8888);
				savePicture(bitmap, "M");
				resultStr = "";
				resultStr =res;
				if (resultStr != "") {
					alertDialog.setMessage(resultStr);
					alertDialog.setTitle("识别结果");
					Window window = alertDialog.getWindow();
					WindowManager.LayoutParams lp = window.getAttributes();
					// 设置透明度为0.3
					lp.alpha = 0.8f;
					lp.width = width * 2 / 3;
					// lp.flags= 0x00000020;
					window.setAttributes(lp);
					window.setGravity(Gravity.LEFT | Gravity.BOTTOM);
					alertDialog.show();
				}
			}
				

		}
				
	}	 
	
	public String savePicture(Bitmap bitmap, String tag) {
		String strCaptureFilePath = PATH + tag + "_LPR_" + pictureName() + ".jpg";
		File dir = new File(PATH);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(strCaptureFilePath);
		if (file.exists()) {
			file.delete();
		}
		try {
			file.createNewFile();
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));

			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
			bos.flush();
			bos.close();

		} catch (IOException e) {
			Toast.makeText(getApplicationContext(), "图片存储失败,请检查SD卡", Toast.LENGTH_SHORT).show();
		}
		return strCaptureFilePath;
	}

	public String pictureName() {
		String str = "";
		Time t = new Time();
		t.setToNow(); // 取得系统时间。
		int year = t.year;
		int month = t.month + 1;
		int date = t.monthDay;
		int hour = t.hour; // 0-23
		int minute = t.minute;
		int second = t.second;
		if (month < 10)
			str = String.valueOf(year) + "0" + String.valueOf(month);
		else {
			str = String.valueOf(year) + String.valueOf(month);
		}
		if (date < 10)
			str = str + "0" + String.valueOf(date + "_");
		else {
			str = str + String.valueOf(date + "_");
		}
		if (hour < 10)
			str = str + "0" + String.valueOf(hour);
		else {
			str = str + String.valueOf(hour);
		}
		if (minute < 10)
			str = str + "0" + String.valueOf(minute);
		else {
			str = str + String.valueOf(minute);
		}
		if (second < 10)
			str = str + "0" + String.valueOf(second);
		else {
			str = str + String.valueOf(second);
		}
		return str;
	}

	private Size getOptimalPreviewSize(List<Size> sizes, int w, int h) {
        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio = (double) w / h;
        if (sizes == null) return null;

        Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        int targetHeight = h;

        // Try to find an size match aspect ratio and size
        for (Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }

        // Cannot find the one match the aspect ratio, ignore the requirement
        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        return optimalSize;
    }
	public  int[] convertYUV420_NV21toARGB8888(byte[] data, int width, int height) {
		int size = width * height;
		int offset = size;
		int[] pixels = new int[size];
		int u, v, y1, y2, y3, y4;

		// i along Y and the final pixels
		// k along pixels U and V
		for (int i = 0, k = 0; i < size; i += 2, k += 2) {
			y1 = data[i] & 0xff;
			y2 = data[i + 1] & 0xff;
			y3 = data[width + i] & 0xff;
			y4 = data[width + i + 1] & 0xff;

			u = data[offset + k] & 0xff;
			v = data[offset + k + 1] & 0xff;
			u = u - 128;
			v = v - 128;

			pixels[i] = convertYUVtoARGB(y1, u, v);
			pixels[i + 1] = convertYUVtoARGB(y2, u, v);
			pixels[width + i] = convertYUVtoARGB(y3, u, v);
			pixels[width + i + 1] = convertYUVtoARGB(y4, u, v);

			if (i != 0 && (i + 2) % width == 0)
				i += width;
		}

		return pixels;
	}
	private   int convertYUVtoARGB(int y, int u, int v) {
		int r, g, b;

		r = y + (int) 1.402f * u;
		g = y - (int) (0.344f * v + 0.714f * u);
		b = y + (int) 1.772f * v;
		r = r > 255 ? 255 : r < 0 ? 0 : r;
		g = g > 255 ? 255 : g < 0 ? 0 : g;
		b = b > 255 ? 255 : b < 0 ? 0 : b;
		return 0xff000000 | (r << 16) | (g << 8) | b;
	}
	
}
