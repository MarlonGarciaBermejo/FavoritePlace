package com.firstapp.favoriteplace

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.firestore.DocumentId

data class Places(
    @DocumentId val documentId: String? = null,
    val nameOfPlaces: String? = "",
    val placeInfo: String? = "") : Parcelable
{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(documentId)
        parcel.writeString(nameOfPlaces)
        parcel.writeString(placeInfo)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Places> {
        override fun createFromParcel(parcel: Parcel): Places {
            return Places(parcel)
        }

        override fun newArray(size: Int): Array<Places?> {
            return arrayOfNulls(size)
        }
    }
}

