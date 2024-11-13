package com.example.myapplication.lcyedu.ui.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.lcyedu.bean.UpdateResult;

/**
 * Created by damon on 2018/7/3.
 */

public class VersionUpdateDialog extends CommonDialog {

    UpdateResult updateResult;
    private TextView mTvVersionCode;
    private TextView mTvUpdateVersion;
    private ImageView mIvUpdate;

    public VersionUpdateDialog(Context context, int themeResId) {
        super(context, themeResId);
        initView();
    }

    public VersionUpdateDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView();
    }

    public VersionUpdateDialog(Context context , UpdateResult updateResult) {
        super(context);
        this.updateResult = updateResult;
        initView();
    }

    public VersionUpdateDialog(Context context, float alpha, int gravity) {
        super(context, alpha, gravity);
        initView();
    }

    private void initView(){
        setCancelable(false);
        View view = View.inflate(getContext(), R.layout.dialog_version_update, null);
        mTvVersionCode = view.findViewById(R.id.version_code);
        mTvUpdateVersion = view.findViewById(R.id.tv_update_content);
//        mTvVersionCode.setText("v"+result.getVersion());
//        mTvUpdateVersion.setText(result.getRemark());
        mIvUpdate = view.findViewById(R.id.iv_update);
        mIvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //

                Intent intent= new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(updateResult.getData().getAppUrl());
                intent.setData(content_url);
                getContext().startActivity(intent);
//                WebActivity.launchActivity(mContext,result.getUrl(),"版本更新");
            }
        });
        if (updateResult.getData().isForceUpdate()){
            view.findViewById(R.id.iv_close).setVisibility(View.GONE);
        }
        view.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
        setContentView(view);
    }


    public ImageView getUpdateButton(){
        return mIvUpdate;
    }

}
