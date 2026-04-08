package com.app.masterplan.framework.storage.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore

val Context.dataStore: DataStore<TokenStorageDto> by dataStore(
    fileName = "settings.json",
    serializer = TokenSerializer,
)