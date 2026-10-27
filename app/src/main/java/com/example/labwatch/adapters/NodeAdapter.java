package com.example.labwatch.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.labwatch.R;
import com.example.labwatch.data.models.Node;
import java.util.ArrayList;
import java.util.List;

public class NodeAdapter extends RecyclerView.Adapter<NodeAdapter.ViewHolder> {

    // Интерфейс для кликов по серверу
    public interface OnNodeClickListener {
        void onNodeClick(Node node);
    }

    private final List<Node> nodes = new ArrayList<>();
    private final OnNodeClickListener listener;

    public NodeAdapter(OnNodeClickListener listener) {
        this.listener = listener;
    }

    // Обновление данных
    public void update(List<Node> newNodes) {
        nodes.clear();
        nodes.addAll(newNodes);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_server_node, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Node node = nodes.get(position);
        holder.name.setText(node.getDisplayName());
        holder.icon.setImageResource(R.drawable.server_icon);
        holder.itemView.setOnClickListener(v -> listener.onNodeClick(node));
    }

    @Override
    public int getItemCount() {
        return nodes.size();
    }

    // Элементы в одной карточке узла
    static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView icon;
        final TextView name;

        ViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.nodeIcon); // Отображение основной иконки сервера
            name = itemView.findViewById(R.id.nodeName); // Имя сервера
        }
    }
}