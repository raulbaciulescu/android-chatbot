package com.university.androidchatbot.repository;

import com.university.androidchatbot.api.interceptors.TokenInterceptor;
import com.university.androidchatbot.feature.splash.SessionManager;
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
public final class TokenInterceptor_Factory implements Factory<TokenInterceptor> {
  private final Provider<SessionManager> sessionManagerProvider;

  public TokenInterceptor_Factory(Provider<SessionManager> sessionManagerProvider) {
    this.sessionManagerProvider = sessionManagerProvider;
  }

  @Override
  public TokenInterceptor get() {
    return newInstance(sessionManagerProvider.get());
  }

  public static TokenInterceptor_Factory create(Provider<SessionManager> sessionManagerProvider) {
    return new TokenInterceptor_Factory(sessionManagerProvider);
  }

  public static TokenInterceptor newInstance(SessionManager sessionManager) {
    return new TokenInterceptor(sessionManager);
  }
}
