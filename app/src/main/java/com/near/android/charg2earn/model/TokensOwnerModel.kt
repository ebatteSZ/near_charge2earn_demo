package com.near.android.charg2earn.model

import com.google.gson.annotations.SerializedName

class TokensOwnerModel(
    @SerializedName("token_id")
    val tokenId: String,
    @SerializedName("metadata")
    val metadata: MetadataModel
)

class MetadataModel(
    @SerializedName("title")
    val title: String
)