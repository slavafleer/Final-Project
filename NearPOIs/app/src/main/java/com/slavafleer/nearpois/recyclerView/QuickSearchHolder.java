package com.slavafleer.nearpois.recyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.slavafleer.nearpois.QuickSearch;
import com.slavafleer.nearpois.R;

/**
 * Holder for Quick Search Recycler.
 */
public class QuickSearchHolder extends RecyclerView.ViewHolder {

    private ImageView imageViewItemQuickSearch;
    private String type;

    public QuickSearchHolder(View itemView) {
        super(itemView);

        imageViewItemQuickSearch = (ImageView) itemView.findViewById(R.id.imageViewItemQuickSearch);
    }

    // Bind Data Object to the Views.
    public void bindQuickSearch(QuickSearch quickSearch) {

        type = quickSearch.getType();
        int imageId = quickSearch.getImageId();

        imageViewItemQuickSearch.setImageResource(imageId);
    }
}
