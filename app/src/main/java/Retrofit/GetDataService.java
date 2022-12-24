package Retrofit;

import model.VideoDetails;
import model.VideoStats.VideoStats;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetDataService {
    @GET("search")
    Call<VideoDetails> getVideoData(
        @Query("part") String part,
        @Query("channelId") String channelId,
        @Query("maxResults") String maxResults,
        @Query("order") String order,
        @Query("key") String key

    );
    @GET("videos")
    Call<VideoStats> getVideoStats(
            @Query("part") String part,
            @Query("key") String key,
            @Query("id") String id

    );

}
