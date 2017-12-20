package com.chidi.protein.personally.domain.repositories;

import com.chidi.protein.personally.domain.models.NewsModel;

public class OfflineRepository {
  private OnlineRepository onlineRepository;
  private static OfflineRepository offlineRepository;

  private OfflineRepository() {
    onlineRepository = OnlineRepository.getOnlineRepositoryInstance();
  }

  public NewsModel fetchNewsItems(String query) {
    NewsModel nm = onlineRepository.fetchNewsItems(query);
    return nm;
  }

  public static OfflineRepository getOfflineRepositoryInstance() {
    return offlineRepository;
  }
}
