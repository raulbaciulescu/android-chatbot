package com.university.androidchatbot.repository;

import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.Preferences;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class UserPreferencesRepository_Factory implements Factory<UserPreferencesRepository> {
  private final Provider<DataStore<Preferences>> dataStoreProvider;

  public UserPreferencesRepository_Factory(Provider<DataStore<Preferences>> dataStoreProvider) {
    this.dataStoreProvider = dataStoreProvider;
  }

  @Override
  public UserPreferencesRepository get() {
    return newInstance(dataStoreProvider.get());
  }

  public static UserPreferencesRepository_Factory create(
      Provider<DataStore<Preferences>> dataStoreProvider) {
    return new UserPreferencesRepository_Factory(dataStoreProvider);
  }

  public static UserPreferencesRepository newInstance(DataStore<Preferences> dataStore) {
    return new UserPreferencesRepository(dataStore);
  }
}
