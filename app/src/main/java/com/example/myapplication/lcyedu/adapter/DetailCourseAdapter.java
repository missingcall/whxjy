package com.example.myapplication.lcyedu.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.lcyedu.Constants;
import com.example.myapplication.lcyedu.bean.CourseViewResult;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.adapter.recyclerview.XRecyclerAdapter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DetailCourseAdapter extends BaseRecyclerAdapter<Object> {

    public static final int TYPE_CHAPTER = 0;
    public static final int TYPE_PERIOD = 1;
    private List mDatas;



    public DetailCourseAdapter(List data) {
        super(data);
        mDatas = data;
    }


    @Override
    protected int getItemLayoutId(int viewType) {
        if(viewType == TYPE_CHAPTER){
//            Log.d(Constants.EDU_DETAIL_ACTIVITY_LOG, "getItemLayoutId: " +TYPE_CHAPTER);
            return R.layout.adapter_flexbox_layout_item_chapter;
        }else {
            return R.layout.adapter_flexbox_layout_item_period;
        }
    }


    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, Object item) {
//        Log.d(Constants.EDU_DETAIL_ACTIVITY_LOG, "bindData: " + position);
        if (holder.getItemViewType() == TYPE_CHAPTER ) {
            String chapterName = ((CourseViewResult.DataDTO.ChapterListDTO) mDatas.get(position)).getChapterName();
            holder.text(R.id.tv_tag_chapter,chapterName);
            holder.itemView.setClickable(false);
        } else {
            CourseViewResult.DataDTO.ChapterListDTO.PeriodListDTO periodListDTO = (CourseViewResult.DataDTO.ChapterListDTO.PeriodListDTO) mDatas.get(position);
            String periodName = periodListDTO.getPeriodName();
            holder.text(R.id.tv_tag_period,periodName);
            holder.select(R.id.tv_tag_period, getSelectPosition() == position);
        }
    }


    @Override
    public int getItemViewType(int position) {
        if(mDatas.get(position) instanceof CourseViewResult.DataDTO.ChapterListDTO){
            return TYPE_CHAPTER;
        }else {
            return TYPE_PERIOD;
        }
    }

}
