package org.guanzonsms.Object;

import static android.content.Context.JOB_SCHEDULER_SERVICE;

import android.annotation.SuppressLint;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;

public class ServiceScheduler {
    private static final String TAG = ServiceScheduler.class.getSimpleName();

    public static long EIGHT_HOUR_PERIODIC = 28800000;
    public static long FIFTEEN_MINUTE_PERIODIC = 900000;

    @SuppressLint({"MissingPermission", "NewApi"})
    public static boolean isJobRunning(Context context, int JobID){
        JobScheduler scheduler = (JobScheduler) context.getSystemService( JOB_SCHEDULER_SERVICE ) ;

        boolean hasBeenScheduled = false ;

        for ( JobInfo jobInfo : scheduler.getAllPendingJobs() ) {
            if (jobInfo.getId() == JobID) {
                hasBeenScheduled = true ;
                break ;
            }
        }

        return hasBeenScheduled;
    }

    @SuppressLint({"MissingPermission", "NewApi"})
    public static void scheduleJob(Context context, Class<?> jobService, long periodic, int ServiceID){
        ComponentName loComp = new ComponentName(context, jobService);
        JobInfo loJob = new JobInfo.Builder(ServiceID, loComp)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true)
                .setPeriodic(periodic)
                .build();
        JobScheduler loScheduler = (JobScheduler)context.getSystemService(JOB_SCHEDULER_SERVICE);
        int liResult = loScheduler.schedule(loJob);
        if(liResult == JobScheduler.RESULT_SUCCESS){
            Log.e(TAG, jobService.getSimpleName() + " Service Scheduled");
        } else {
            Log.e(TAG, jobService.getSimpleName() + " Service Scheduled Failed.");
        }
    }
}
