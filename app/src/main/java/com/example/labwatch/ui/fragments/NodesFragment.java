package com.example.labwatch.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.labwatch.R;
import com.example.labwatch.adapters.NodeAdapter;
import com.example.labwatch.data.api.ApiClient;
import com.example.labwatch.data.models.Node;
import com.example.labwatch.data.models.NodesResponse;
import java.util.List;
import java.util.stream.Collectors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.google.android.material.snackbar.Snackbar;


// Отображение фрагмента с узлами
public class NodesFragment extends Fragment {

    private NodeAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nodes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.nodesRecyclerView);
        // Сетка из 2 колонок: крупные карточки серверов
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));

        adapter = new NodeAdapter(this::openNodeDetails);
        recyclerView.setAdapter(adapter);

        // Загружаем данные
        loadNodes();
    }

    // Переход к экрану узла с его данными (в back stack для кнопки «назад»)
    private void openNodeDetails(Node node) {
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentContainer, NodeDetailFragment.newInstance(node.getId()))
                .addToBackStack(null)
                .commit();
    }

    private void loadNodes() {
        ApiClient.getApi().fetchNodes().enqueue(new Callback<NodesResponse>() {
            @Override
            public void onResponse(@NonNull Call<NodesResponse> call, @NonNull Response<NodesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Node> nodes = response.body().getNodeIds().stream()
                            .map(Node::new)
                            .collect(Collectors.toList());
                    adapter.update(nodes); // <-- Обновляем список
                } else {
                    Toast.makeText(requireContext(), "Ошибка: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<NodesResponse> call, @NonNull Throwable t) {
                Snackbar.make(requireView(), "Нет связи с сервером", Snackbar.LENGTH_LONG)
                        .setAnchorView(requireActivity().findViewById(R.id.bottomNav))
                        .setAction("Повторить", v -> loadNodes())
                        .show();
            }
        });
    }
}