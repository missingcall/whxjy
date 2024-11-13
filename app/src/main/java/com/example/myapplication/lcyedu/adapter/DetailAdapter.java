package com.example.myapplication.lcyedu.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.lcyedu.Constants;
import com.example.myapplication.lcyedu.bean.CourseViewResult;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.adapter.recyclerview.XRecyclerAdapter;

import java.util.List;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/*废弃了 自己写demo用的*/

@Deprecated
public class DetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected int mSelectPosition = -1;

    public static final int TYPE_CHAPTER = 0;
    public static final int TYPE_PERIOD = 1;
    private List mDatas;
    private boolean mIsChapter = true;

    DetailChapterViewHolder mDetailChapterViewHolder;
    DetailPeriodViewHolder mDetailPeriodViewHolder;

    public DetailAdapter(List data) {
        this.mDatas = data;
    }

    public void setSelected(int position) {
        //TODO 还没实现的选择器

    }



    public static class DetailChapterViewHolder extends RecyclerView.ViewHolder {
        public final TextView title;

        public DetailChapterViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.tv_tag_chapter);
        }
    }

    public static class DetailPeriodViewHolder extends RecyclerView.ViewHolder {
        public final TextView title;

        public DetailPeriodViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.tv_tag_period);
        }


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(viewType == TYPE_CHAPTER ? R.layout.adapter_flexbox_layout_item_chapter : R.layout.adapter_flexbox_layout_item_period, parent, false);

        return viewType == TYPE_CHAPTER ? new DetailChapterViewHolder(itemView) : new DetailPeriodViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        Log.d(Constants.EDU_DETAIL_ACTIVITY_LOG, "bindData: " + position);
        if (holder instanceof DetailChapterViewHolder) {
            String chapterName = ((CourseViewResult.DataDTO.ChapterListDTO) mDatas.get(position)).getChapterName();
            ((DetailChapterViewHolder) holder).title.setText(chapterName);
        } else {
            CourseViewResult.DataDTO.ChapterListDTO.PeriodListDTO periodListDTO = (CourseViewResult.DataDTO.ChapterListDTO.PeriodListDTO) mDatas.get(position);
            String periodName = periodListDTO.getPeriodName();
            ((DetailPeriodViewHolder) holder).title.setText(periodName);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //章节点击不进行操作,课程点击播放对应课程,写个回调让外面用
                    mClickListener.onItemClick(holder.itemView , position);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(mDatas.get(position) instanceof CourseViewResult.DataDTO.ChapterListDTO){
            return TYPE_CHAPTER;
        }else {
            return TYPE_PERIOD;
        }
    }

    private OnItemClickListener mClickListener;

    public interface OnItemClickListener<T> {
        /**
         * 条目点击
         *
         * @param itemView 条目
         * @param position 索引
         */
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mClickListener =onItemClickListener;
    }

}
