package com.example.sameeksha.popular_movies2;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {
    private static VolleySingleton Instance;
    private RequestQueue RequestQueue;

    private VolleySingleton(){
        RequestQueue = Volley.newRequestQueue(MyApplication.getAppContext());
    }


    public  static VolleySingleton getInstance(){
        if(Instance==null){
            Instance = new VolleySingleton();
        }
        return Instance;

    }

    public RequestQueue getRequestQueue() {
        return RequestQueue;
    }

}
