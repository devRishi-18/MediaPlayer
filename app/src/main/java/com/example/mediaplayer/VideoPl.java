package com.example.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;

public class VideoPl extends AppCompatActivity {

    ListView listView;
    String[] items;
    //SelectListener listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_pl);

        listView=findViewById(R.id.listviewVideo);
        Dexter.withContext(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        final ArrayList<File> myVideos = fetchvideos(Environment.getExternalStorageDirectory());
//                        final ArrayList<File> paths= fetchvideopaths(Environment.getExternalStorageDirectory());
                        items = new String[myVideos.size()];
                        for(int i=0;i<items.length;i++)
                        {
                            items[i] = myVideos.get(i).getName();
                        }
                        customAdapter customAdapter=new customAdapter();
                        listView.setAdapter(customAdapter);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Intent intent = new Intent(VideoPl.this,PlayVideo.class);
                                intent.putExtra("Video", myVideos.get(i).getAbsolutePath());
                                startActivity(intent);
                            }
                        });
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

//    private ArrayList<File> fetchvideopaths(File file) {
//        ArrayList<File> arrayList=new ArrayList<>();
//        File[] videos= file.listFiles();
//        if(videos !=null){
//            for(File myfile: videos){
//                if(!myfile.isHidden() && myfile.isDirectory()){
//                    arrayList.addAll(fetchvideos(new File(myfile.getAbsolutePath())));
//                }
//                else{
//                    if(myfile.getName().endsWith(".mp4") && !myfile.getName().startsWith(".")){
//                        arrayList.add(new File(myfile.getAbsolutePath()));
//                    }
//                }
//            }
//        }
//        return arrayList;
//    }

    public ArrayList<File> fetchvideos(File file){
        ArrayList arrayList = new ArrayList();
        File[] songs =  file.listFiles();
        if(songs !=null){
            for(File myfile: songs){
                if(!myfile.isHidden() && myfile.isDirectory()){
                    arrayList.addAll(fetchvideos(myfile));
                }
                else{
                    if(myfile.getName().endsWith(".mp4") && !myfile.getName().startsWith(".")){
                        arrayList.add(myfile);
                    }
                }
            }
        }
        return arrayList;
    }

    class customAdapter extends BaseAdapter
    {
        @Override
        public int getCount() {
            return items.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View myView= getLayoutInflater().inflate(R.layout.custom_list,null);
            TextView textvideo= myView.findViewById(R.id.txtvideoname);
            textvideo.setSelected(true);
            textvideo.setText(items[i]);

            return myView;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(VideoPl.this,MainActivity.class);
        startActivity(intent);
    }
}