package info.nightscout.androidaps.networking.nightscout

import info.nightscout.androidaps.networking.nightscout.data.NightscoutCollection
import info.nightscout.androidaps.networking.nightscout.requests.EntryRequestBody
import info.nightscout.androidaps.networking.nightscout.responses.ArrayOfData
import info.nightscout.androidaps.networking.nightscout.responses.DummyResponse
import info.nightscout.androidaps.networking.nightscout.responses.LastModifiedResponse
import info.nightscout.androidaps.networking.nightscout.responses.StatusResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*

/**
 * Created by adrian on 2019-12-23.
 */

interface INightscoutService {

    @GET("/api/v3/status")
    // used to get the raw response for more error checking. E.g. to give the user better feedback after new settings.
    fun statusVerbose(): Single<Response<StatusResponse>>

    @GET("/api/v3/status")
    fun statusSimple(): Single<StatusResponse>

    @GET("/api/v3/lastModified")
    fun lastModified(): Single<LastModifiedResponse>

    @POST("/api/v3/{collection}")
    fun insertEntry(
        @Path("collection") collection: NightscoutCollection,
        @Body entryRequestBody: EntryRequestBody
    ): Single<Response<DummyResponse>>

    @DELETE("/api/v3/{collection}/{identifier}")
    fun deleteEntry(
        @Path("collection") collection: NightscoutCollection,
        @Path("identifier") identifier: String
    ): Single<Response<DummyResponse>>

    @PUT("/api/v3/{collection}/{identifier}")
    fun updateEntry(
        @Path("collection") collection: NightscoutCollection,
        @Path("identifier") identifier: String,
        @Body entryRequestBody: EntryRequestBody
    ): Single<Response<DummyResponse>>

    @GET("/api/v3/{collection}")
    fun getByDate(
        @Path("collection") collection: NightscoutCollection,
        @Query("date\$gte") from: Long,
        @Query("sort") sort : String,
        @Query("limit") limit: Int
    ): Single<Response<ArrayOfData>>

    @GET("/api/v3/{collection}/history/{lastModified}")
    fun getByLastModified(
        @Path("collection") collection: NightscoutCollection,
        @Path("lastModified") from: Long,
        @Query("sort") sort : String,
        @Query("limit") limit: Int
    ): Single<Response<ArrayOfData>>
}