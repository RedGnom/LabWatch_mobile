package com.example.labwatch.data.models;

import com.google.gson.annotations.SerializedName;

// Последняя телеметрия узла (GET nodes/:id/latest)
public class Telemetry {
    @SerializedName("node_id")      private String nodeId;
    @SerializedName("cpu_temp_c")   private double cpuTempC;
    @SerializedName("case_temp_c")  private double caseTempC;
    @SerializedName("humidity_pct") private double humidityPct;
    @SerializedName("current_a")    private double currentA;
    @SerializedName("voltage_v")    private double voltageV;
    @SerializedName("fan_rpm")      private int fanRpm;
    @SerializedName("received_at")  private String receivedAt;

    public String getNodeId() { return nodeId; }
    public double getCpuTempC() { return cpuTempC; }
    public double getCaseTempC() { return caseTempC; }
    public double getHumidityPct() { return humidityPct; }
    public double getCurrentA() { return currentA; }
    public double getVoltageV() { return voltageV; }
    public int getFanRpm() { return fanRpm; }
    public String getReceivedAt() { return receivedAt; }
}
