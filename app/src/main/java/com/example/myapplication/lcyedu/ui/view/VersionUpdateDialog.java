package com.chongai.live.widget.dialog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.haigou.live.R;
import com.chongai.live.bean.result.VersionResult;

/**
 * Created by damon on 2018/7/3.
 */

public class VersionUpdateDialog extends CommonDialog {

    private VersionResult result;
    private TextView mTvVersionCode;
    private TextView mTvUpdateVersion;
    private ImageView mIvUpdate;

    public VersionUpdateDialog(Context context, int themeResId,VersionResult result) {
        super(context, themeResId);
        initView();
        this.result = result;
    }

    public VersionUpdateDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView();
    }

    public VersionUpdateDialog(Context context,VersionResult result) {
        super(context);
        this.result =result;
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
        mTvVersionCode.setText("v"+result.getVersion());
        mTvUpdateVersion.setText(result.getRemark());
        mIvUpdate = view.findViewById(R.id.iv_update);
        mIvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //

                Intent intent= new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(result.getUrl());
                intent.setData(content_url);
                getContext().startActivity(intent);
//                WebActivity.launchActivity(mContext,result.getUrl(),"版本更新");
            }
        });
        if ("1".equals(result.getIs_force())){
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
