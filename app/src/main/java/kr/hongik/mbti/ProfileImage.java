package kr.hongik.mbti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

    /**
     * ProfileImage를 ImageView에 보여줌
     *  cache가 있으면 cache 파일 사용
     *  cache가 없으면 firebase Strorage에서 다운로드
     * @param iv
     */
    public void showProfileImage(ImageView iv)
    {
        Log.d(TAG, "showProfileImage : Using cache");
        downloadProfileImage(iv);
    }

    /**
     * firebase Storage에 profileImage 업로드
     * @param profileImageUri
     */
    public void uploadProfileImage(Uri profileImageUri ){

        StorageReference imageRef = mStorageRef.child("profileImages/"+FirebaseAuth.getInstance().getUid()+".jpg");

        imageRef.putFile(profileImageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.d(TAG, this.getClass().getEnclosingMethod().getName()+":Success");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.d(TAG, this.getClass().getEnclosingMethod().getName()+":Fail");
                        // Handle unsuccessful uploads
                        // ...
                    }
                });

    }

    /**
     * firebase Storage에서 profileImage 다운로드 후 ImageView에 띄워줌
     * @param iv
     */
    public void downloadProfileImage(ImageView iv){
        try {
            localFile = File.createTempFile("profileImage", "jpg");
            StorageReference imageRef = mStorageRef.child("profileImages/"+FirebaseAuth.getInstance().getUid()+".jpg");

            imageRef.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            Log.d(TAG, this.getClass().getEnclosingMethod().getName()+":Success");
                            iv.setImageBitmap(bitmap);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.d(TAG, this.getClass().getEnclosingMethod().getName()+":Fail");
                    // Handle failed download
                    // ...
                }
            });

        }
        catch (IOException e){
            e.printStackTrace();
        }
    }


}
