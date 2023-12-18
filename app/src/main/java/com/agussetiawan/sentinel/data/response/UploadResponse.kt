package com.playdeadrespawn.trashtech.data.response


import com.google.gson.annotations.SerializedName

data class UploadResponse(
    @SerializedName("prediction")
    val prediction: String,
    @SerializedName("uploaded_url")
    val uploadedUrl: String
)