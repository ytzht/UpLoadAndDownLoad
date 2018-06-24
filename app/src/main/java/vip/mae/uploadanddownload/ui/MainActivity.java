package vip.mae.uploadanddownload.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.io.File;

import cn.qqtheme.framework.picker.FilePicker;
import vip.mae.uploadanddownload.Apis;
import vip.mae.uploadanddownload.R;
import vip.mae.uploadanddownload.entity.ResultData;

public class MainActivity extends AppCompatActivity {

    private KProgressHUD hud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initView();

    }

    private void initView() {
        hud = new KProgressHUD(MainActivity.this);
        hud.setCancellable(false);

    }

    public void uploadSelect(View view) {
        FilePicker picker = new FilePicker(MainActivity.this, FilePicker.FILE);
        picker.setOnFilePickListener(new FilePicker.OnFilePickListener() {
            @Override
            public void onFilePicked(String currentPath) {
//                Toast.makeText(MainActivity.this, currentPath, Toast.LENGTH_SHORT).show();
                OkGo.<String>post(Apis.UpLoad)
                        .tag(this)
                        .isMultipart(true)
                        .params("file", new File(currentPath))
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                ResultData data = (new Gson()).fromJson(response.body(), ResultData.class);
                                if (data.getCode() == 0){
                                    Toast.makeText(MainActivity.this, data.getMsg(), Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(MainActivity.this, data.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        picker.show();
    }

    public void canDownloadList(View view) {
        startActivity(new Intent(MainActivity.this, FileListActivity.class));
    }
}
