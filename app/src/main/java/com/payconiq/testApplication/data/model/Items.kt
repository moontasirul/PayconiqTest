package com.payconiq.testApplication

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class Items (

  @SerializedName("login"               ) var login             : String?  = null,
  @SerializedName("id"                  ) var id                : Int?     = null,
  @SerializedName("node_id"             ) var nodeId            : String?  = null,
  @SerializedName("avatar_url"          ) var avatarUrl         : String?  = null,
  @SerializedName("gravatar_id"         ) var gravatarId        : String?  = null,
  @SerializedName("url"                 ) var url               : String?  = null,
  @SerializedName("html_url"            ) var htmlUrl           : String?  = null,
  @SerializedName("followers_url"       ) var followersUrl      : String?  = null,
  @SerializedName("following_url"       ) var followingUrl      : String?  = null,
  @SerializedName("gists_url"           ) var gistsUrl          : String?  = null,
  @SerializedName("starred_url"         ) var starredUrl        : String?  = null,
  @SerializedName("subscriptions_url"   ) var subscriptionsUrl  : String?  = null,
  @SerializedName("organizations_url"   ) var organizationsUrl  : String?  = null,
  @SerializedName("repos_url"           ) var reposUrl          : String?  = null,
  @SerializedName("events_url"          ) var eventsUrl         : String?  = null,
  @SerializedName("received_events_url" ) var receivedEventsUrl : String?  = null,
  @SerializedName("type"                ) var type              : String?  = null,
  @SerializedName("site_admin"          ) var siteAdmin         : Boolean? = null,
  @SerializedName("score"               ) var score             : Int?     = null

):Parcelable {
  constructor(parcel: Parcel) : this(
    parcel.readString(),
    parcel.readValue(Int::class.java.classLoader) as? Int,
    parcel.readString(),
    parcel.readString(),
    parcel.readString(),
    parcel.readString(),
    parcel.readString(),
    parcel.readString(),
    parcel.readString(),
    parcel.readString(),
    parcel.readString(),
    parcel.readString(),
    parcel.readString(),
    parcel.readString(),
    parcel.readString(),
    parcel.readString(),
    parcel.readString(),
    parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
    parcel.readValue(Int::class.java.classLoader) as? Int
  ) {
  }

  override fun writeToParcel(parcel: Parcel, flags: Int) {
    parcel.writeString(login)
    parcel.writeValue(id)
    parcel.writeString(nodeId)
    parcel.writeString(avatarUrl)
    parcel.writeString(gravatarId)
    parcel.writeString(url)
    parcel.writeString(htmlUrl)
    parcel.writeString(followersUrl)
    parcel.writeString(followingUrl)
    parcel.writeString(gistsUrl)
    parcel.writeString(starredUrl)
    parcel.writeString(subscriptionsUrl)
    parcel.writeString(organizationsUrl)
    parcel.writeString(reposUrl)
    parcel.writeString(eventsUrl)
    parcel.writeString(receivedEventsUrl)
    parcel.writeString(type)
    parcel.writeValue(siteAdmin)
    parcel.writeValue(score)
  }

  override fun describeContents(): Int {
    return 0
  }

  companion object CREATOR : Parcelable.Creator<Items> {
    override fun createFromParcel(parcel: Parcel): Items {
      return Items(parcel)
    }

    override fun newArray(size: Int): Array<Items?> {
      return arrayOfNulls(size)
    }
  }
}