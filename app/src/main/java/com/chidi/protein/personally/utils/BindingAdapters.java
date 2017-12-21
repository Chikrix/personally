package com.chidi.protein.personally.utils;

import android.databinding.BindingAdapter;
import android.widget.ImageView;
import com.bumptech.glide.Glide;

public class BindingAdapters {

  @BindingAdapter("app:loadImage")
  public static void setNewsImage(ImageView view, String newsImageUrl) {
    Glide.with(view.getContext())
        .load(newsImageUrl)
        .into(view);
  }
}
