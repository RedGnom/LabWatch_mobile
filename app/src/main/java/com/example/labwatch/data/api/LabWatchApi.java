package com.example.labwatch.data.api;
import com.example.labwatch.data.models.NodesResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface LabWatchApi {

    @GET("nodes")
    Call<NodesResponse> fetchNodes();


}
