package com.lpr;

import android.content.Context;

public class LPR {
	static {
		System.loadLibrary("LPRecognition");
	}
	public native int Init(Context context,int roileft,int roitop,int roiright,int roibottom,int nwidth,int nheight,String filePath);
	public native byte[]  VideoRec(byte[] ImageStreamNV21, int width, int height,int imgflag);
	//public native byte[] DetectLPR(short[] ImageStreamNV21, int width, int height,String imputstring);
}
