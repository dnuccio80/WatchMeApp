package com.example.watchme.app.data.network.responses

import android.util.Log
import com.example.watchme.app.ui.dataClasses.MediaProviderDataClass
import com.example.watchme.app.ui.dataClasses.ProvidersDataClass
import com.example.watchme.app.ui.dataClasses.TypeProviderDataClass
import com.google.gson.annotations.SerializedName

data class MediaProvidersResponse(
    @SerializedName("results") val providers: Map<String, TypeProviderResponse>
)

fun MediaProvidersResponse.toMediaProviderDataClass(): MediaProviderDataClass {
    return MediaProviderDataClass(
        providers = providers.mapValues {
            it.value.toTypeProvidersDataClass()
        }
    )
}

data class TypeProviderResponse(
    @SerializedName("buy") val buy: List<MediaProvider>? = emptyList(),
    @SerializedName("rent") val rent: List<MediaProvider>? = emptyList()
)

fun TypeProviderResponse.toTypeProvidersDataClass(): TypeProviderDataClass {
    return TypeProviderDataClass(
        buy = buy?.map { it.toProvidersDataClass() } ?: emptyList(),
        rent = rent?.map { it.toProvidersDataClass() } ?: emptyList()
    )
}

data class MediaProvider(
    @SerializedName("logo_path") val logo: String,
    @SerializedName("provider_name") val providerName: String,
    @SerializedName("provider_id") val providerId: Int
)

fun MediaProvider.toProvidersDataClass(): ProvidersDataClass {
    return ProvidersDataClass(
        logo = logo,
        providerName = providerName,
        providerId = providerId
    )
}

