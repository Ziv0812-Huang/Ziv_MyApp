package com.myapp.manager;

public class ApiServiceManager {

    public static ApiService getApiServiceManager(String url){
        return new AppClientManager(url).getClient().create(ApiService.class);
    }

    public static ApiService getApiServiceManager(){
        return new AppClientManager().getClient().create(ApiService.class);
    }
}
