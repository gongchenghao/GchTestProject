package gcg.testproject.activity.SelectVideo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import gcg.testproject.R;

/**
 * Created by Administrator on 2017/5/22 0022.
 */

public class VideoSelectAdapter extends  RecyclerView.Adapter<VideoSelectAdapter.VideoViewHolder>{
    private Context mContext;
    private ArrayList<VideoItem> videoItems;
    private LayoutInflater mInflater;

    //保存选中的视频
    private ArrayList<VideoItem> mSelectImages = new ArrayList<>();
    private OnVideoSelectListener mSelectListener;
    private OnItemClickListener mItemClickListener;
    private int mMaxCount;
    private boolean isSingle;

    /**
     * @param maxCount 图片的最大选择数量，小于等于0时，不限数量，isSingle为false时才有用。
     * @param isSingle 是否单选
     */
    public VideoSelectAdapter(Context context, int maxCount, boolean isSingle, ArrayList<VideoItem> videoItems) {
        mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        mMaxCount = maxCount;
        this.isSingle = isSingle;
        this.videoItems = videoItems;
    }

    @Override
    public int getItemCount() {
        return videoItems == null ? 0 : videoItems.size();
    }


    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(com.donkingliang.imageselector.R.layout.adapter_images_item, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final VideoViewHolder holder, int position) {
        final VideoItem videoItem = videoItems.get(position);
        MyVideoThumbLoader loader = new MyVideoThumbLoader(mContext);

        //获取视频文件缩略图
        loader.showThumbByAsynctack(videoItem.data,holder.ivImage);

//        Glide.with(mContext).load(new File(image.getPath())).diskCacheStrategy(DiskCacheStrategy.NONE).into(holder.ivImage);

        setItemSelect(holder, mSelectImages.contains(videoItem));
        //点击选中/取消选中图片
        holder.ivSelectIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelectImages.contains(videoItem)) {
                    //如果图片已经选中，就取消选中
                    unSelectImage(videoItem);
                    setItemSelect(holder, false);
                } else if (isSingle) {
                    //如果是单选，就先清空已经选中的图片，再选中当前图片
                    clearImageSelect();
                    selectImage(videoItem);
                    setItemSelect(holder,true);
                } else if (mMaxCount <= 0 || mSelectImages.size() < mMaxCount) {
                    //如果不限制图片的选中数量，或者图片的选中数量
                    // 还没有达到最大限制，就直接选中当前图片。
                    selectImage(videoItem);
                    setItemSelect(holder, true);
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mItemClickListener != null){
                    mItemClickListener.OnItemClick(videoItem,holder.getAdapterPosition());
                }
            }
        });
    }

    /**
     * 选中图片
     * @param videoItem
     */
    private void selectImage(VideoItem videoItem){
        mSelectImages.add(videoItem);
        if (mSelectListener != null) {
            mSelectListener.OnVideoSelect(videoItem, true, mSelectImages.size());
        }
    }

    /**
     * 取消选中图片
     * @param
     */
    private void unSelectImage(VideoItem videoItem){
        mSelectImages.remove(videoItem);
        if (mSelectListener != null) {
            mSelectListener.OnVideoSelect(videoItem, false, mSelectImages.size());
        }
    }



    public ArrayList<VideoItem> getData() {
        return videoItems;
    }

    public void refresh(ArrayList<VideoItem> data) {
        videoItems = data;
        notifyDataSetChanged();
    }

    /**
     * 设置图片选中和未选中的效果
     */
    private void setItemSelect(VideoViewHolder holder, boolean isSelect) {
        if (isSelect) {
            holder.ivSelectIcon.setImageResource(R.drawable.icon_image_select);
            holder.ivMasking.setAlpha(0.5f);
        } else {
            holder.ivSelectIcon.setImageResource(R.drawable.icon_image_un_select);
            holder.ivMasking.setAlpha(0.2f);
        }
    }

    private void clearImageSelect() {
        if (videoItems != null && mSelectImages.size() == 1) {
            int index = videoItems.indexOf(mSelectImages.get(0));
            if(index != -1){
                mSelectImages.clear();
                notifyItemChanged(index);
            }
        }
    }

    public ArrayList<VideoItem> getSelectVideo() {
        return mSelectImages;
    }

    public void setOnVideoSelectListener(OnVideoSelectListener listener) {
        this.mSelectListener = listener;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    static class VideoViewHolder extends RecyclerView.ViewHolder {

        ImageView ivImage;
        ImageView ivSelectIcon;
        ImageView ivMasking;

        public VideoViewHolder(View itemView) {
            super(itemView);
            ivImage = (ImageView) itemView.findViewById(com.donkingliang.imageselector.R.id.iv_image);
            ivSelectIcon = (ImageView) itemView.findViewById(com.donkingliang.imageselector.R.id.iv_select);
            ivMasking = (ImageView) itemView.findViewById(com.donkingliang.imageselector.R.id.iv_masking);
        }
    }

    public interface OnVideoSelectListener {
        void OnVideoSelect(VideoItem videoItem, boolean isSelect, int selectCount);
    }

    public interface OnItemClickListener{
        void OnItemClick(VideoItem videoItem, int position);
    }
}
