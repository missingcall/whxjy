package com.example.myapplication.lcyedu.ui.holder;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


public class MyRecyclerViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;

    public MyRecyclerViewHolder(View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
    }

    public <T extends View> T findViewById(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getView(int viewId) {
        return findViewById(viewId);
    }

    public TextView getTextView(int viewId) {
        return (TextView) getView(viewId);
    }

    public Button getButton(int viewId) {
        return (Button) getView(viewId);
    }

    public ImageView getImageView(int viewId) {
        return (ImageView) getView(viewId);
    }

    public ImageButton getImageButton(int viewId) {
        return (ImageButton) getView(viewId);
    }

    public EditText getEditText(int viewId) {
        return (EditText) getView(viewId);
    }

    public final Context getContext() {
        return itemView.getContext();
    }

    /**
     * 寻找控件
     *
     * @param id
     * @return
     */
    public View findView(@IdRes int id) {
        return id == 0 ? itemView : findViewById(id);
    }

    /**
     * 设置文字
     *
     * @param id
     * @param sequence
     * @return
     */
    public MyRecyclerViewHolder text(int id, CharSequence sequence) {
        View view = findView(id);
        if (view instanceof TextView) {
            ((TextView) view).setText(sequence);
        }
        return this;
    }

    /**
     * 设置文字
     *
     * @param id
     * @param stringRes
     * @return
     */
    public MyRecyclerViewHolder text(@IdRes int id, @StringRes int stringRes) {
        View view = findView(id);
        if (view instanceof TextView) {
            ((TextView) view).setText(stringRes);
        }
        return this;
    }

    /**
     * 设置文字的颜色
     *
     * @param id
     * @param colorId
     * @return
     */
    public MyRecyclerViewHolder textColorId(@IdRes int id, @ColorRes int colorId) {
        View view = findView(id);
        if (view instanceof TextView) {
            ((TextView) view).setTextColor(ContextCompat.getColor(view.getContext(), colorId));
        }
        return this;
    }

    /**
     * 设置图片
     *
     * @param id
     * @param imageId
     * @return
     */
    public MyRecyclerViewHolder image(@IdRes int id, @DrawableRes int imageId) {
        View view = findView(id);
        if (view instanceof ImageView) {
            ((ImageView) view).setImageResource(imageId);
        }
        return this;
    }

    /**
     * 设置图片
     *
     * @param id
     * @param drawable
     * @return
     */
    public MyRecyclerViewHolder image(@IdRes int id, Drawable drawable) {
        View view = findView(id);
        if (view instanceof ImageView) {
            ((ImageView) view).setImageDrawable(drawable);
        }
        return this;
    }

    /**
     * 设置图片
     *
     * @param id
     * @param uri 图片资源
     * @return
     */
    public MyRecyclerViewHolder image(@IdRes int id, Object uri) {
        View view = findView(id);
        if (view instanceof ImageView) {
            //TODO 先注释掉 加载图片到时候在搞
//            ImageLoader.get().loadImage((ImageView) view, uri);
        }
        return this;
    }

    /**
     * 设置图片的等级
     *
     * @param id
     * @param level
     * @return
     */
    public MyRecyclerViewHolder imageLevel(@IdRes int id, int level) {
        View view = findView(id);
        if (view instanceof ImageView) {
            ((ImageView) view).setImageLevel(level);
        }
        return this;
    }

    /**
     * 给图片着色
     *
     * @param id
     * @param tint 颜色
     * @return
     */
    public MyRecyclerViewHolder tint(@IdRes int id, ColorStateList tint) {
        View view = findView(id);
        if (view instanceof ImageView) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ((ImageView) view).setImageTintList(tint);
            }
        }
        return this;
    }

    /**
     * 设置布局内控件的点击事件【包含索引】
     *
     * @param id
     * @param listener
     * @param position
     * @return
     */
    public <T> MyRecyclerViewHolder viewClick(@IdRes int id, final OnViewItemClickListener<T> listener, final T item, final int position) {
        View view = findView(id);
        if (listener != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onViewItemClick(v, item, position);
                }
            });
        }
        return this;
    }

    /**
     * 设置控件的点击监听
     *
     * @param id
     * @param listener
     * @return
     */
    public MyRecyclerViewHolder click(@IdRes int id, final View.OnClickListener listener) {
        View view = findView(id);
        if (listener != null) {
            view.setOnClickListener(listener);
        }
        return this;
    }

    /**
     * 设置控件是否可显示
     *
     * @param id
     * @param visibility
     * @return
     */
    public MyRecyclerViewHolder visible(@IdRes int id, int visibility) {
        View view = findView(id);
        view.setVisibility(visibility);
        return this;
    }

    /**
     * 设置输入框是否可编辑
     *
     * @param id
     * @param enable
     * @return
     */
    public MyRecyclerViewHolder enable(@IdRes int id, boolean enable) {
        View view = findView(id);
        view.setEnabled(enable);
        if (view instanceof EditText) {
            view.setFocusable(enable);
            view.setFocusableInTouchMode(enable);
        }
        return this;
    }

    /**
     * 这是控件选中状态
     *
     * @param id
     * @param checked
     * @return
     */
    public MyRecyclerViewHolder checked(@IdRes int id, boolean checked) {
        View view = findView(id);
        if (view instanceof Checkable) {
            ((Checkable) view).setChecked(checked);
        }
        return this;
    }

    /**
     * 设置控件选择监听
     *
     * @param id
     * @param listener
     * @return
     */
    public MyRecyclerViewHolder checkedListener(@IdRes int id, CompoundButton.OnCheckedChangeListener listener) {
        View view = findView(id);
        if (view instanceof CompoundButton) {
            ((CompoundButton) view).setOnCheckedChangeListener(listener);
        }
        return this;
    }

    /**
     * 设置控件是否选中
     *
     * @param id
     * @param selected
     * @return
     */
    public MyRecyclerViewHolder select(@IdRes int id, boolean selected) {
        View view = findView(id);
        view.setSelected(selected);
        return this;
    }

    /**
     * 设置文字变化监听
     *
     * @param id
     * @param watcher
     * @return
     */
    public MyRecyclerViewHolder textListener(@IdRes int id, TextWatcher watcher) {
        View view = findView(id);
        if (view instanceof TextView) {
            ((TextView) view).addTextChangedListener(watcher);
        }
        return this;
    }

    /**
     * 设置背景
     *
     * @param viewId
     * @param resId
     * @return
     */
    public MyRecyclerViewHolder backgroundResId(int viewId, @DrawableRes int resId) {
        View view = findView(viewId);
        view.setBackgroundResource(resId);
        return this;
    }

    /**
     * 清除控件缓存
     */
    public void clearViews() {
        if (mViews != null) {
            mViews.clear();
        }
    }

    /**
     * 列表条目点击监听
     */
    public interface OnItemClickListener<T> {
        /**
         * 条目点击
         *
         * @param itemView 条目
         * @param item     数据
         * @param position 索引
         */
        void onItemClick(View itemView, T item, int position);
    }

    /**
     * 列表条目长按监听
     */
    public interface OnItemLongClickListener<T> {
        /**
         * 条目长按
         *
         * @param itemView 条目
         * @param item     数据
         * @param position 索引
         */
        void onItemLongClick(View itemView, T item, int position);
    }


    /**
     * 布局内控件点击事件
     */
    public interface OnViewItemClickListener<T> {
        /**
         * 控件被点击
         *
         * @param view     被点击的控件
         * @param item     数据
         * @param position 索引
         */
        void onViewItemClick(View view, T item, int position);
    }
}
