package com.tools.apps.wallpaper.http;

import com.library.networklibrary.bean.BaseData;
import com.tools.apps.wallpaper.bean.TestBean;
import com.tools.apps.wallpaper.bean.TestNewsBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {

    @GET("v2/book/1220562")
    Observable<TestBean> getTestData();

    @GET("https://www.apiopen.top/journalismApi")
    Observable<BaseData<TestNewsBean>> getNewsTestData();

    @GET("v2/book/1220562")
    Observable<String> getTestData2();

}
