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


/**
 * profileImage를 처리하기 위한 클래스
 * @author 장혜리
 **/

public class ProfileImage {

    File localFile;
    private StorageReference mStorageRef;
    final String TAG = ProfileImage.class.getName();

    public ProfileImage(){

        mStorageRef = FirebaseStorage.getInstance().getReference();

    }


    public void getProfileImage(ImageView iv)
    {
        Log.d(TAG, "getProfileImage");
        try {
            localFile = File.createTempFile("profileImage", "jpg");
            StorageReference imageRef = mStorageRef.child("profileImages/"+FirebaseAuth.getInstance().getUid()+".jpg");

            imageRef.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            Log.d(TAG, "getProfileImage:Success");
                            iv.setImageBitmap(bitmap);
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

    public void updateProfileImage(Uri profileImage ){
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
