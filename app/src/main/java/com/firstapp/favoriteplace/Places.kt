package com.firstapp.favoriteplace

import com.google.firebase.firestore.DocumentId

data class Places(
    @DocumentId val documentId: String? = null,
    val nameOfPlaces: String? = "",
    val placeInfo: String? = ""
) {

}