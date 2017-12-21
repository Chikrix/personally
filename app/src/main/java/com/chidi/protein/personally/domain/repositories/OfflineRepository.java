package com.chidi.protein.personally.domain.repositories;

public class OfflineRepository {
  private OnlineRepository onlineRepository;
  private static OfflineRepository offlineRepository;

  private OfflineRepository() {
    onlineRepository = OnlineRepository.getOnlineRepositoryInstance();
  }

  public static OfflineRepository getOfflineRepositoryInstance() {
    return offlineRepository;
  }
}
