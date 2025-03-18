package com.example.watchme.app.data.network.responses

import android.graphics.Region
import androidx.compose.ui.text.intl.Locale
import com.google.gson.annotations.SerializedName
import java.util.Locale.*

data class MovieProvidersResponse (
    @SerializedName("results") val providers: Map<String, TypeProvider>
)

data class TypeProvider(
    @SerializedName("buy") val buy: List<MovieProvider>,
    @SerializedName("rent") val rent: List<MovieProvider>
)

data class MovieProvider(
    @SerializedName("logo_path") val logo: String,
    @SerializedName("provider_name") val providerName: String,
    @SerializedName("provider_id") val providerId: Int
)

