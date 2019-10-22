package com.example.androidparticlestarter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.particle.android.sdk.cloud.ParticleCloud;
import io.particle.android.sdk.cloud.ParticleCloudSDK;
import io.particle.android.sdk.cloud.ParticleDevice;
import io.particle.android.sdk.cloud.exceptions.ParticleCloudException;
import io.particle.android.sdk.utils.Async;

public class MainActivity extends AppCompatActivity {
    // MARK: Debug info
    private final String TAG="";

    // MARK: Particle Account Info
    private final String PARTICLE_USERNAME = "hemantpatel564@gmail.com";
    private final String PARTICLE_PASSWORD = "Hemant564";

    // MARK: Particle device-specific info
    private final String DEVICE_ID = "270018001047363333343437";

    // MARK: Particle Publish / Subscribe variables
    private long subscriptionId;

    // MARK: Particle device
    private ParticleDevice mDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Initialize your connection to the Particle API
        ParticleCloudSDK.init(this.getApplicationContext());

        // 2. Setup your device variable
        getDeviceFromCloud();

    }

    public void APressed(View view)
    {
        play("a");
    }
    public void aPressed(View view)
    {
        play("b");
    }
    public void BPressed(View view)
    {
        play("c");
    }

    public void bPressed(View view)
    {
        play("d");
    }

    public void CPressed(View view)
    {
        play("e");
    }

    public void cPressed(View view)
    {
        play("a");
    }




    public void play(String str)




{
    Async.executeAsync(ParticleCloudSDK.getCloud(), new Async.ApiWork<ParticleCloud, Object>() {
        @Override
        public Object callApi(@NonNull ParticleCloud particleCloud) throws ParticleCloudException, IOException {
            // put your logic here to talk to the particle
            // --------------------------------------------

            // what functions are "public" on the particle?
            Log.d(TAG, "Availble functions: " + mDevice.getFunctions());


            List<String> functionParameters = new ArrayList<String>();
            functionParameters.add(str);
            try {

                mDevice.callFunction("playSound",functionParameters);

            } catch (ParticleDevice.FunctionDoesNotExistException e1) {
                e1.printStackTrace();
            }


            return -1;
        }

        @Override
        public void onSuccess(Object o) {
            // put your success message here
            Log.d(TAG, "Success!");
        }

        @Override
        public void onFailure(ParticleCloudException exception) {
            // put your error handling code here
            Log.d(TAG, exception.getBestMessage());
        }
    });
}

    /**
     * Custom function to connect to the Particle Cloud and get the device
     */
    public void getDeviceFromCloud() {
        // This function runs in the background
        // It tries to connect to the Particle Cloud and get your device
        Async.executeAsync(ParticleCloudSDK.getCloud(), new Async.ApiWork<ParticleCloud, Object>() {

            @Override
            public Object callApi(@NonNull ParticleCloud particleCloud) throws ParticleCloudException, IOException {
                particleCloud.logIn(PARTICLE_USERNAME, PARTICLE_PASSWORD);
                mDevice = particleCloud.getDevice(DEVICE_ID);
                return -1;
            }

            @Override
            public void onSuccess(Object o) {
                Log.d(TAG, "Successfully got device from Cloud");
            }

            @Override
            public void onFailure(ParticleCloudException exception) {
                Log.d(TAG, exception.getBestMessage());
            }
        });
    }

}
