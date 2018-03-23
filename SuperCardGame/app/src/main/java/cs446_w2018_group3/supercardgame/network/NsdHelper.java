/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cs446_w2018_group3.supercardgame.network;

import android.content.Context;
import android.net.nsd.NsdServiceInfo;
import android.net.nsd.NsdManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import cs446_w2018_group3.supercardgame.model.network.Session;

public class NsdHelper {

    private final Context mContext;
    private final NsdManager mNsdManager;
    private final SessionListListener sessionListListener;
    private final NsdManager.RegistrationListener mRegistrationListener;
    private final NsdManager.DiscoveryListener mDiscoveryListener;

    private static final String SERVICE_TYPE = "_http._tcp.";
    private static final String TAG = "NsdHelper";
    private static final String SERVICE_NAME = "MyGame";
    private String mServiceName = SERVICE_NAME + ":" + UUID.randomUUID().toString();

    private final List<Session> sessions = new ArrayList<>();

    NsdServiceInfo mService;

    public NsdHelper(Context context, SessionListListener sessionListListener) {
        mContext = context;
        mNsdManager = (NsdManager) context.getSystemService(Context.NSD_SERVICE);
        this.sessionListListener = sessionListListener;
        this.mRegistrationListener = getRegistrationListener();
        this.mDiscoveryListener = getDiscoveryListener();
    }

    private NsdManager.DiscoveryListener getDiscoveryListener() {
        return new NsdManager.DiscoveryListener() {
            @Override
            public void onDiscoveryStarted(String regType) {
                Log.i(TAG, "Service discovery started");
            }

            @Override
            public void onServiceFound(NsdServiceInfo service) {
                Log.i(TAG, "Service discovery success " + service);
                if (!service.getServiceType().equals(SERVICE_TYPE)) {
                    Log.i(TAG, "Unknown Service Type: " + service.getServiceType());
                } else if (service.getServiceName().equals(mServiceName)) {
                    Log.i(TAG, "Same machine: " + mServiceName);
                } else if (service.getServiceName().contains(SERVICE_NAME)) {
                    // TODO: create new listener for each revoke
                    mNsdManager.resolveService(service, getResolveListener());
                }
            }

            @Override
            public void onServiceLost(NsdServiceInfo service) {
                Log.i(TAG, "service lost: " + service);

                // update
                Session session = new Session(service.getServiceName(), service.getHost(), service.getPort());
                sessions.remove(session);
                sessionListListener.onServiceListChanged(sessions);
            }

            @Override
            public void onDiscoveryStopped(String serviceType) {
                Log.i(TAG, "Discovery stopped: " + serviceType);
            }

            @Override
            public void onStartDiscoveryFailed(String serviceType, int errorCode) {
                Log.e(TAG, "Discovery failed: Error code:" + errorCode);
            }

            @Override
            public void onStopDiscoveryFailed(String serviceType, int errorCode) {
                Log.e(TAG, "Discovery failed: Error code:" + errorCode);
            }
        };
    }

    private NsdManager.ResolveListener getResolveListener() {
        return new NsdManager.ResolveListener() {

            @Override
            public void onResolveFailed(NsdServiceInfo serviceInfo, int errorCode) {
                Log.e(TAG, "Resolve failed" + errorCode);
            }

            @Override
            public void onServiceResolved(NsdServiceInfo serviceInfo) {
                Log.e(TAG, "Resolve Succeeded. " + serviceInfo);

                if (serviceInfo.getServiceName().equals(mServiceName)) {
                    Log.i(TAG, "Same IP.");
                    return;
                }

                // update
                Session session = new Session(serviceInfo.getServiceName(), serviceInfo.getHost(), serviceInfo.getPort());
                if (!sessions.contains(session)) {
                    sessions.add(session);
                    sessionListListener.onServiceListChanged(sessions);
                }
            }
        };
    }

    private NsdManager.RegistrationListener getRegistrationListener() {
        return new NsdManager.RegistrationListener() {
            @Override
            public void onServiceRegistered(NsdServiceInfo NsdServiceInfo) {
                mServiceName = NsdServiceInfo.getServiceName();
                Log.i(TAG, "Service registered: " + mServiceName);
            }

            @Override
            public void onRegistrationFailed(NsdServiceInfo arg0, int arg1) {
                Log.i(TAG, "Service registration failed: " + arg1);
            }

            @Override
            public void onServiceUnregistered(NsdServiceInfo arg0) {
                Log.i(TAG, "Service unregistered: " + arg0.getServiceName());
            }

            @Override
            public void onUnregistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
                Log.i(TAG, "Service unregistration failed: " + errorCode);
            }

        };
    }

    void registerService(int port) {
        tearDown();  // Cancel any previous registration request
        NsdServiceInfo serviceInfo = new NsdServiceInfo();
        serviceInfo.setPort(port);
        serviceInfo.setServiceName(mServiceName);
        serviceInfo.setServiceType(SERVICE_TYPE);

        mNsdManager.registerService(serviceInfo, NsdManager.PROTOCOL_DNS_SD, mRegistrationListener);
    }

    void discoverServices() {
        stopDiscovery();  // Cancel any existing discovery request
        mNsdManager.discoverServices(SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, mDiscoveryListener);
    }

    void stopDiscovery() {
        try {
            mNsdManager.stopServiceDiscovery(mDiscoveryListener);
        }
        catch (IllegalArgumentException e) {
            // ignore
        }
    }

    void tearDown() {
        try {
            mNsdManager.unregisterService(mRegistrationListener);
        }
        catch (IllegalArgumentException err) {
            Log.i(TAG, err.toString());
        }

    }

    public interface SessionListListener {
        void onServiceListChanged(List<Session> sessions);
    }
}
