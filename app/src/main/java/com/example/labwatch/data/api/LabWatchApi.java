package com.example.labwatch.data.api;
import com.example.labwatch.data.models.NodesResponse;
import com.example.labwatch.data.models.Telemetry;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LabWatchApi {

    @GET("nodes")
    Call<NodesResponse> fetchNodes();

    // Последняя телеметрия узла
    @GET("nodes/{id}/latest")
    Call<Telemetry> fetchLatest(@Path("id") String nodeId);

}
