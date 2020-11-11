package kr.hongik.mbti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

public class UpdateActivity extends AppCompatActivity {

    final String TAG = UpdateActivity.class.getName();

    private StorageReference mStorageRef;
    private Button btn_updateButton;
    private ImageView imageview;
    File localFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        btn_updateButton = findViewById(R.id.btn_updateButton);
        imageview = (ImageView)findViewById(R.id.ImageForUpdate);
        mStorageRef = FirebaseStorage.getInstance().getReference();

        getProfileImage();

        btn_updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileUpdate2();
                myStartActivity(MainActivity.class);
            }
        });



    }

    private void profileUpdate2(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(user.getUid())
                .update("nickname",((EditText)findViewById(R.id.u_nickname)).getText().toString(),
                        "age",((EditText)findViewById(R.id.u_age)).getText().toString(),
                        "mbti",((EditText)findViewById(R.id.u_mbti)).getText().toString(),
                        "address",((EditText)findViewById(R.id.u_address)).getText().toString(),
                        "stateMessage",((EditText)findViewById(R.id.u_stateMessage)).getText().toString());
    }

    private void myStartActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivityForResult(intent, 1);
    }


    private void getProfileImage()
    {
        try {
            localFile = File.createTempFile("profileImage", "jpg");
            StorageReference imageRef = mStorageRef.child("profileImages/"+FirebaseAuth.getInstance().getUid()+".jpg");

            imageRef.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            Log.d(TAG, "getProfileImage:Success");
                            imageview.setImageBitmap(bitmap);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle failed download
                    // ...
                }
            });

        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    private void updateProfileImage(Uri profileImage ){

        StorageReference imageRef = mStorageRef.child("profileImages/"+FirebaseAuth.getInstance().getUid()+".jpg");

        imageRef.putFile(profileImage)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });
    }
}