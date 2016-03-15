package com.gamebuddy.bigshow.core;

/**
 *
 * created by tindle
 * created time 15/12/18 下午5:35
 * reference https://github.com/MrFuFuFu/RxFace/.../FaceApi
 */
public class Api {

    public static Api instance;

    public static Api getInstance(){
        if(null==instance){
            synchronized (Api.class){
                if(null==instance)
                    instance = new Api();
            }
        }
        return instance;
    }

//    private final IApi iApi;

//    public Api(){
//        RestAdapter restAdapter = new RestAdapter.Builder()
//                .setEndpoint(Constant.SERVER_ADDRESS)
//                .setClient(new OkClient(new OkHttpClient()))
//                .setLogLevel(Config.DEBUG_MODE?RestAdapter.LogLevel.FULL:RestAdapter.LogLevel.NONE)
//                .setRequestInterceptor(mRequestInterceptor)
//                .build();
//        iApi = restAdapter.create(IApi.class);
//    }
//
//    private RequestInterceptor mRequestInterceptor = new RequestInterceptor() {
//        @Override
//        public void intercept(RequestFacade request) {
////            request.addHeader("connection", "keep-alive");
////            request.addHeader("Content-Type", "multipart/form-data; boundary="+ getBoundary() + "; charset=UTF-8");
//        }
//    };

//    public Observable<String> getCreateRoomData(){
//        return iApi.createNewRecord()
//                .timeout(Constant.TIME_OUT, TimeUnit.MILLISECONDS)
//                .concatMap(new Func1<String, Observable<? extends String>>() {
//                    @Override
//                    public Observable<? extends String> call(String s) {
//                        return Observable.just(s);
//                    }
//                }).compose(SchedulersCompat.<String>applyExecutorSchedulers());
//    }

}
