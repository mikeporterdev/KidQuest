package com.michael.kidquest.gcm;

import android.app.IntentService;
import android.content.Intent;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.michael.kidquest.R;
import com.michael.kidquest.services.CharacterService;

import java.io.IOException;

/**
 * Created by m_por on 30/03/2016.
 */
public class RegistrationIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public RegistrationIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        InstanceID instanceID = InstanceID.getInstance(this);
        try {
            String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            CharacterService characterService = new CharacterService(this);
            characterService.setGcmId(token);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
