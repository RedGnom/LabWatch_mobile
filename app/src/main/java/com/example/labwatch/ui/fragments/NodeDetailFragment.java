package com.example.labwatch.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.labwatch.R;
import com.example.labwatch.data.api.ApiClient;
import com.example.labwatch.data.models.Node;
import com.example.labwatch.data.models.Telemetry;
import com.google.android.material.snackbar.Snackbar;
import java.util.Locale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Экран узла: последняя телеметрия
public class NodeDetailFragment extends Fragment {

    private static final String ARG_NODE_ID = "node_id";

    private String nodeId;
    private TextView cpuTempText;
    private TextView caseTempText;
    private TextView humidityText;
    private TextView currentText;
    private TextView voltageText;
    private TextView fanRpmText;
    private TextView receivedAtText;

    // Создание фрагмента с id узла через Arguments (пересоздание фрагментом системой безопасно)
    public static NodeDetailFragment newInstance(String nodeId) {
        NodeDetailFragment fragment = new NodeDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NODE_ID, nodeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_node_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nodeId = getArguments() != null ? getArguments().getString(ARG_NODE_ID) : null;
        if (nodeId == null) {
            Toast.makeText(requireContext(), "Узел не выбран", Toast.LENGTH_SHORT).show();
            return;
        }

        TextView title = view.findViewById(R.id.nodeDetailTitle);
        title.setText(new Node(nodeId).getDisplayName());

        cpuTempText = view.findViewById(R.id.cpuTempValue);
        caseTempText = view.findViewById(R.id.caseTempValue);
        humidityText = view.findViewById(R.id.humidityValue);
        currentText = view.findViewById(R.id.currentValue);
        voltageText = view.findViewById(R.id.voltageValue);
        fanRpmText = view.findViewById(R.id.fanRpmValue);
        receivedAtText = view.findViewById(R.id.receivedAtText);

        // Загружаем последнюю телеметрию
        loadLatest();
    }

    private void loadLatest() {
        ApiClient.getApi().fetchLatest(nodeId).enqueue(new Callback<Telemetry>() {
            @Override
            public void onResponse(@NonNull Call<Telemetry> call, @NonNull Response<Telemetry> response) {
                if (!isAdded()) return; // Фрагмент мог быть закрыт

                Telemetry t = response.body();
                if (response.isSuccessful() && t != null) {
                    cpuTempText.setText(String.format(Locale.getDefault(), "%.1f °C", t.getCpuTempC()));
                    caseTempText.setText(String.format(Locale.getDefault(), "%.1f °C", t.getCaseTempC()));
                    humidityText.setText(String.format(Locale.getDefault(), "%.1f %%", t.getHumidityPct()));
                    currentText.setText(String.format(Locale.getDefault(), "%.2f А", t.getCurrentA()));
                    voltageText.setText(String.format(Locale.getDefault(), "%.2f В", t.getVoltageV()));
                    fanRpmText.setText(String.format(Locale.getDefault(), "%d об/мин", t.getFanRpm()));
                    receivedAtText.setText("Обновлено: " + t.getReceivedAt());
                } else {
                    Toast.makeText(requireContext(), "Ошибка: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Telemetry> call, @NonNull Throwable t) {
                if (!isAdded()) return;

                Snackbar.make(requireView(), "Нет связи с сервером", Snackbar.LENGTH_LONG)
                        .setAnchorView(requireActivity().findViewById(R.id.bottomNav))
                        .setAction("Повторить", v -> loadLatest())
                        .show();
            }
        });
    }
}
