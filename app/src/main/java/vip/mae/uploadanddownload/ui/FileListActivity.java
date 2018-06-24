package vip.mae.uploadanddownload.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import vip.mae.uploadanddownload.Apis;
import vip.mae.uploadanddownload.entity.FileList;
import vip.mae.uploadanddownload.R;
import vip.mae.uploadanddownload.utils.HttpDownloader;

public class FileListActivity extends AppCompatActivity {

    private RecyclerView rlv_file;
    private List<FileList.DataBean> data = new ArrayList<>();
    private FileListAdapter adapter;
    private String TAG = "=====";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_list);

        rlv_file = findViewById(R.id.rlv_file);
        rlv_file.setLayoutManager(new LinearLayoutManager(this));
        rlv_file.addItemDecoration(new DividerItemDecoration(getBaseContext(), DividerItemDecoration.VERTICAL));
        adapter = new FileListAdapter();

        OkGo.<String>post(Apis.FileList).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {

                FileList list = (new Gson()).fromJson(response.body(), FileList.class);
                if (list.getCode() == 0) {
                    data = list.getData();
                    rlv_file.setAdapter(adapter);
                } else {
                    Toast.makeText(FileListActivity.this, list.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private class FileListAdapter extends RecyclerView.Adapter<FileListAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(getBaseContext()).inflate(R.layout.cell_file, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            holder.tv_name.setText(data.get(position).getFile_name());
            holder.tv_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d(TAG, "run: "+Apis.DownLoad + data.get(position).getFile_name());
                            final int i = HttpDownloader.downloadFiles(Apis.DownLoad + data.get(position).getFile_name(), "1212", data.get(position).getFile_name());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    switch (i) {
                                        case -1:
                                            Toast.makeText(FileListActivity.this, "下载失败", Toast.LENGTH_SHORT).show();
                                            break;
                                        case 0:
                                            Toast.makeText(FileListActivity.this, "下载成功", Toast.LENGTH_SHORT).show();
                                            break;
                                        case 1:
                                            Toast.makeText(FileListActivity.this, "文件已存在", Toast.LENGTH_SHORT).show();
                                            break;
                                    }
                                }
                            });

                            //-1：下载失败，0：下载成功，1：文件已存在

                        }
                    }).start();


                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        /**
         * 下载文件
         *
         * @param url         下载地址
         * @param destFileDir 保存文件路径
         */
        private void downLoad(String url, String destFileDir) {
            OkGo.<File>get("https://testin-ee.oss-cn-hangzhou.aliyuncs.com/log--1-d83cafe707c346adbc7522fcfabc7b59.log")//
                    .tag(this)//
                    .execute(new FileCallback(destFileDir) {
                        @Override
                        public void onSuccess(Response<File> response) {


                        }


                        @Override
                        public void downloadProgress(Progress progress) {
                            super.downloadProgress(progress);
                            Log.d(TAG, "downloadProgress: " + progress.currentSize + "/" + progress.totalSize);
                        }


                    });
        }


        class ViewHolder extends RecyclerView.ViewHolder {
            private TextView tv_name;

            public ViewHolder(View itemView) {
                super(itemView);
                tv_name = itemView.findViewById(R.id.tv_name);
            }
        }
    }
}
