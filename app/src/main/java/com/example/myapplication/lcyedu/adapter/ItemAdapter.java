/*
 * Copyright (C) 2019 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.myapplication.lcyedu.adapter;

import android.content.Intent;
import android.net.Uri;
import android.util.SparseBooleanArray;

import com.example.myapplication.R;
import com.example.myapplication.lcyedu.bean.CourseListResult;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * @author xuexiang
 * @since 2019-11-23 01:32
 */
public class ItemAdapter extends BaseRecyclerAdapter<CourseListResult.DataDTO.ListDTO> {

    private boolean mIsMultiSelectMode;
    private boolean mCancelable;

    /**
     * 多选的状态记录
     */
    private SparseBooleanArray mMultiSelectStatus = new SparseBooleanArray();

    public ItemAdapter(List list) {
        super(list);
    }

    public ItemAdapter setIsMultiSelectMode(boolean isMultiSelectMode) {

        return this;
    }

    public ItemAdapter setCancelable(boolean cancelable) {
        mCancelable = cancelable;
        return this;
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.adapter_news_card_view_list_item;
    }


    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, CourseListResult.DataDTO.ListDTO item) {
        holder.text(R.id.tv_course_item_name, item.getCourseName());
        if(item.getIsFree() == 1){
            //免费
            holder.text(R.id.tv_course_item_isFree, "免费");
        }else{
            //收费
            holder.text(R.id.tv_course_item_isFree, "VIP");
        }
        holder.image(R.id.iv_course_item_logo,Uri.parse(item.getCourseLogo()));
        holder.select(R.id.ll_course_item, getSelectPosition() == position);
    }


    /**
     * 选择
     *
     * @param position 选中索引
     * @return
     */
    public boolean select(int position) {
        return mIsMultiSelectMode ? multiSelect(position) : singleSelect(position);
    }

    /**
     * 多选
     *
     * @param positions
     */
    public void multiSelect(int... positions) {
        if (!mIsMultiSelectMode) {
            return;
        }
        for (int position : positions) {
            multiSelect(position);
        }
    }

    /**
     * 多选
     *
     * @param position
     */
    private boolean multiSelect(int position) {
        if (!mIsMultiSelectMode) {
            return false;
        }
        mMultiSelectStatus.append(position, !mMultiSelectStatus.get(position));
        notifyItemChanged(position);
        return true;
    }

    /**
     * 单选
     *
     * @param position
     */
    private boolean singleSelect(int position) {
        return singleSelect(position, mCancelable);
    }

    /**
     * 单选
     *
     * @param position
     * @param cancelable
     */
    private boolean singleSelect(int position, boolean cancelable) {
        if (position == getSelectPosition()) {
            if (cancelable) {
                setSelectPosition(-1);
                return true;
            }
        } else {
            setSelectPosition(position);
            return true;
        }
        return false;
    }


    /**
     * @return 获取选中的内容
     */
    public CourseListResult.DataDTO.ListDTO getSelectContent() {
        CourseListResult.DataDTO.ListDTO value = getSelectItem();
        return value;
    }




    /**
     * 清除选中
     */
    public void clearSelection() {
        if (mIsMultiSelectMode) {
            clearMultiSelection();
        } else {
            clearSingleSelection();
        }
    }

    private void clearMultiSelection() {
        mMultiSelectStatus.clear();
        notifyDataSetChanged();
    }

    private void clearSingleSelection() {
        setSelectPosition(-1);
    }


}
