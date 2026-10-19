package com.example.labwatch.data.models;
import com.google.gson.annotations.SerializedName;
import java.util.Collections;
import java.util.List;
public class NodesResponse {
    @SerializedName("nodes")
    private List<String> nodeIds;

    // Метод для тестов
    public NodesResponse(List<String> nodeIds) {
        this.nodeIds = nodeIds;
    }
    public List<String> getNodeIds() {
        return nodeIds != null ? nodeIds : Collections.emptyList();
    }
}
