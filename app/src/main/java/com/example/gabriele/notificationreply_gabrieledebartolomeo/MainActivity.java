package com.example.gabriele.notificationreply_gabrieledebartolomeo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_TEXT_REPLY = "key_text_reply";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button_send = (Button) findViewById(R.id.button_send);

        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createNotification();


            }
        });
        checkIfRepliedFromNotification(getIntent());

    }



    public void createNotification(){

        // Creates an explicit intent for an Activity in your app

        Intent resultIntent = new Intent(this,MainActivity.class);

        resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, resultIntent,
                PendingIntent.FLAG_ONE_SHOT);


        // Creates the Clickable Lable "Reply" of the notification ,to which attach the RemoteInput

        String replyLabel = "Reply" ;
        android.support.v4.app.RemoteInput remoteInput = new android.support.v4.app.RemoteInput.Builder(KEY_TEXT_REPLY)
                .setLabel(replyLabel)
                .build();

        //Attachment of the RemoteInput to the Clickable Lable "Reply" of the notification

        NotificationCompat.Action action= new NotificationCompat.Action.Builder(R.mipmap.ic_launcher,"Reply",pendingIntent)
                .addRemoteInput(remoteInput)
                .build( ) ;

        //Creation of the Notification with all its attributes

        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Reply Notification APP")
                        .setContentText ( "Hello World!" )
                        .addAction(action)
                        .setContentIntent(pendingIntent);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //issue of the notification
        mNotificationManager.notify(0, mBuilder.build( ) );



    }

    //Method to "fetch" the text that the intent brings with itself

    private CharSequence getMessageText(Intent intent) {
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null) {
            return remoteInput.getCharSequence(KEY_TEXT_REPLY);
        }
        return null;
    }



    //This one is to check if there effectively is a CharSequence (a text) contained in the intent
    //if the contained text ISN'T null then sets the fetched text into the textview seen in the MainActivity
    //
    //Courtesy of Pere Barberan
    //

    private void checkIfRepliedFromNotification(Intent intent) {
        if(getMessageText(intent)==null){
            return;
        }
        Notification repliedNotification=new Notification.Builder(getApplicationContext())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("Replied!")
                .build();

        TextView text=(TextView) findViewById(R.id.sent_text);
        text.setText(getMessageText(intent));
        NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,repliedNotification);

    }



}









