package com.chidi.protein.personally.modules.home.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.chidi.protein.personally.R;
import com.chidi.protein.personally.databinding.NewsItemBinding;
import com.chidi.protein.personally.domain.models.Article;
import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsArticlesViewHolder> {

  private List<Article> articles = new ArrayList<>();

  public NewsAdapter() {

  }

  @Override
  public NewsArticlesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    NewsItemBinding articleBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
        R.layout.news_item, parent, false);

    return new NewsArticlesViewHolder(articleBinding);
  }

  @Override
  public void onBindViewHolder(NewsArticlesViewHolder holder, int position) {
    Article match = articles.get(position);
    holder.setFootballMatch(match);
  }

  public void updateMatchList(List<Article> articles) {
    this.articles = articles;
    notifyDataSetChanged();
  }

  @Override public int getItemCount() {
    return articles.size();
  }

  class NewsArticlesViewHolder extends RecyclerView.ViewHolder {
    private NewsItemBinding binding;

    public NewsArticlesViewHolder(NewsItemBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    public void setFootballMatch(Article article) {
      binding.setArticle(article);
      binding.executePendingBindings();
    }

  }
}
