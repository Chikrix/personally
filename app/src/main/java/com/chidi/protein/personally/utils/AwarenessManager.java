package com.chidi.protein.personally.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import com.google.android.gms.awareness.Awareness;
import com.google.android.gms.awareness.snapshot.DetectedActivityResult;
import com.google.android.gms.awareness.snapshot.HeadphoneStateResult;
import com.google.android.gms.awareness.state.HeadphoneState;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;
import io.reactivex.subjects.PublishSubject;

/**
 * Utility class that publishes changes in the awareness being checked (walking, driving or headset)
 *
 * Lets me connect for Awareness and relays awareness update to a subscriber.
 */
public class AwarenessManager {
  private GoogleApiClient googleApiClient;
  public PublishSubject<Boolean> isHeadphonePluggedIn = PublishSubject.create();
  public PublishSubject<Boolean> isWalkingOrDriving = PublishSubject.create();

  public AwarenessManager(Context context) {
    googleApiClient = new GoogleApiClient.Builder(context).addApi(Awareness.API).build();
  }

  public void connectForAwareness() {
    googleApiClient.connect();
    getSearchAwareContext();

  }

  private void getSearchAwareContext() {
    updateOnHeadphoneState();
    updateOnMovementActivity();
  }

  private void updateOnHeadphoneState() {
    Awareness.SnapshotApi.getHeadphoneState(googleApiClient)
        .setResultCallback(new ResultCallback<HeadphoneStateResult>() {
          @Override public void onResult(@NonNull HeadphoneStateResult headphoneStateResult) {
            if (headphoneStateResult.getStatus().isSuccess()) {
              HeadphoneState headphoneState = headphoneStateResult.getHeadphoneState();
              int state = headphoneState.getState();
              setHeadphonePluggedIn(state == HeadphoneState.PLUGGED_IN);
            }
          }
        });
  }

  private void updateOnMovementActivity() {
    Awareness.SnapshotApi.getDetectedActivity(googleApiClient)
        .setResultCallback(new ResultCallback<DetectedActivityResult>() {
          @Override public void onResult(@NonNull DetectedActivityResult detectedActivityResult) {
            if (detectedActivityResult.getStatus().isSuccess()) {
              ActivityRecognitionResult recognitionResult =
                  detectedActivityResult.getActivityRecognitionResult();
              DetectedActivity mostProbableActivity = recognitionResult.getMostProbableActivity();
              updateActiveActivity(mostProbableActivity.getType());
            }
          }
        });
  }

  private void updateActiveActivity(int activity) {
    switch (activity) {
      case DetectedActivity.IN_VEHICLE:
      case DetectedActivity.WALKING:
        setWalkingOrDriving(true);
      default:
        setWalkingOrDriving(false);
    }
  }

  private void setHeadphonePluggedIn(boolean headphonePluggedIn) {
    isHeadphonePluggedIn.onNext(headphonePluggedIn);
  }

  private void setWalkingOrDriving(boolean walkingOrDriving) {
    isWalkingOrDriving.onNext(walkingOrDriving);
  }
}
