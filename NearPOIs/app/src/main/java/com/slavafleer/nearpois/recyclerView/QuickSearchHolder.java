package com.slavafleer.nearpois.recyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.slavafleer.nearpois.QuickSearch;
import com.slavafleer.nearpois.R;

/**
 * Holder for Quick Search Recycler.
 */
public class QuickSearchHolder extends RecyclerView.ViewHolder implements
        View.OnClickListener {

    private OnClickListener onClickListener;
    private Context context;

    private ImageView imageViewItemQuickSearch;
    private String type;

    public QuickSearchHolder(Context context, View itemView) {
        super(itemView);

        this.context = context;
        onClickListener = (OnClickListener) context;

        imageViewItemQuickSearch = (ImageView) itemView.findViewById(R.id.imageViewItemQuickSearch);

        imageViewItemQuickSearch.setOnClickListener(this);
    }

    // Bind Data Object to the Views.
    public void bindQuickSearch(QuickSearch quickSearch) {

        type = quickSearch.getType();
        int imageId = quickSearch.getImageId();

        imageViewItemQuickSearch.setImageResource(imageId);
    }

    @Override
    public void onClick(View v) {
        onClickListener.onQuickSearchClick(type);
    }

    public interface OnClickListener {

        void onQuickSearchClick(String type);
    }
}
