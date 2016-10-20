package cn.uicai.fulicenter.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.uicai.fulicenter.R;
import cn.uicai.fulicenter.bean.CategoryChildBean;
import cn.uicai.fulicenter.bean.CategoryGroupBean;
import cn.uicai.fulicenter.utils.ImageLoader;

public class CategoryAdapter extends BaseExpandableListAdapter {

    Context mcontext;
    ArrayList<CategoryGroupBean> mGroupList;
    ArrayList<ArrayList<CategoryChildBean>> mChildList;

    public CategoryAdapter(Context context, ArrayList<CategoryGroupBean> GroupList,
                           ArrayList<ArrayList<CategoryChildBean>> ChildList) {
        mcontext = context;
        mGroupList = new ArrayList<>();
        mGroupList.addAll(GroupList);
        mChildList = new ArrayList<>();
        mChildList.addAll(ChildList);
    }

    /**
     * 获取大类的数量
     *
     * @return
     */
    @Override
    public int getGroupCount() {
        return mGroupList != null ? mGroupList.size() : 0;
    }

    /**
     * 获取小类的数量
     *
     * @param groupPosition
     * @return
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return mChildList != null && mChildList.get(groupPosition) != null ?
                mChildList.get(groupPosition).size() : 0;
    }

    /**
     * 获取大类的对象
     *
     * @param groupPosition
     * @return
     */
    @Override
    public CategoryGroupBean getGroup(int groupPosition) {
        return mGroupList != null ? mGroupList.get(groupPosition) : null;
    }

    /**
     * 获取小类的对象
     *
     * @param groupPosition
     * @param childPosition
     * @return
     */
    @Override
    public CategoryChildBean getChild(int groupPosition, int childPosition) {
        return mChildList != null && mChildList.get(groupPosition) != null ?
                mChildList.get(groupPosition).get(childPosition) : null;
    }


    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }


    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * 绑定与获取数据
     *
     * @param groupPosition
     * @param isExpanded
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mcontext, R.layout.item_category_group, null);
            holder = new GroupViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (GroupViewHolder) convertView.getTag();
        }
        CategoryGroupBean group = getGroup(groupPosition);
        if (group != null) {
            ImageLoader.downloadImg(mcontext, holder.ivGroupThumb, group.getImageUrl());
            holder.ivGroupName.setText(group.getName());
            holder.ivIndicator.setImageResource(isExpanded ? R.mipmap.expand_off : R.mipmap.expand_on);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mcontext, R.layout.item_category_child, null);
            holder = new ChildViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }
        CategoryChildBean group = getChild(groupPosition, childPosition);
        if (group != null) {
            ImageLoader.downloadImg(mcontext, holder.ivChildThumb, group.getImageUrl());
            holder.ivChildName.setText(group.getName());
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    class GroupViewHolder {
        @BindView(R.id.iv_group_thumb)
        ImageView ivGroupThumb;
        @BindView(R.id.iv_group_name)
        TextView ivGroupName;
        @BindView(R.id.iv_indicator)
        ImageView ivIndicator;

        GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    class ChildViewHolder {
        @BindView(R.id.iv_child_thumb)
        ImageView ivChildThumb;
        @BindView(R.id.iv_child_name)
        TextView ivChildName;
        @BindView(R.id.layout_category_group)
        RelativeLayout LayoutCategoryGroup;
        ChildViewHolder(View view) {

            ButterKnife.bind(this, view);
        }
    }
}
