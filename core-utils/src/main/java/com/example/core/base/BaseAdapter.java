package com.example.core.base;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<ModelT, ViewHolderT extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<ViewHolderT> {
    protected final List<ModelT> items = new ArrayList<>();

    @Override
    public int getItemCount() {
        return items.size();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    @NonNull
    public List<ModelT> getItems() {
        return items;
    }

    @Nullable
    public ModelT getItem(int position) {
        // TODO: 15.03.2016 or throw runtime exception?
        return position < 0 || position >= items.size() ? null : items.get(position);
    }

    public <T extends ModelT> void add(@NonNull final T item) {
        add(item, items.size());
    }

    public <T extends ModelT> void add(@NonNull final T item, int startPosition) {
        if (startPosition > items.size()) {
            startPosition = items.size();
        }
        final int initialSize = this.items.size();
        items.add(startPosition, item);
        if (initialSize == 0) {
            notifyDataSetChanged();
        } else {
            notifyItemInserted(startPosition);
        }
    }

    public <T extends ModelT> void addAll(final List<T> items) {
        addAll(items, this.items.size());
    }

    public <T extends ModelT> void addAll(final List<T> items, int startPosition) {
        if (items == null || items.size() == 0) {
            return;
        }
        final int initialSize = this.items.size();
        this.items.addAll(startPosition, items);
        if (initialSize == 0) {
            notifyDataSetChanged();
        } else {
            notifyItemRangeInserted(startPosition, items.size());
        }
    }

    @Nullable
    public <T extends ModelT> ModelT replace(@NonNull final T item, int position) {
        if (position > items.size()) {
            position = items.size();
        }
        final ModelT oldItem = items.remove(position);
        items.add(position, item);
        notifyItemChanged(position);
        return oldItem;
    }

    @Nullable
    public <T extends ModelT> ModelT replaceWithPayload(@NonNull final T item, int position, Object payload) {
        if (position > items.size()) {
            position = items.size();
        }
        final ModelT oldItem = items.remove(position);
        items.add(position, item);
        notifyItemChanged(position, payload);
        return oldItem;
    }

    public <T extends ModelT> void replaceAll(final List<T> items) {
        clear();
        addAll(items);
    }

    public void clear() {
        final int size = items.size();
        items.clear();
        notifyItemRangeRemoved(0, size);
    }

    @Nullable
    public ModelT remove(int position) {
        if (position < 0 || position >= items.size()) {
            return null;
        }
        final ModelT removed = items.remove(position);
        if (items.isEmpty()) {
            notifyDataSetChanged();
        } else {
            notifyItemRemoved(position);
        }
        return removed;
    }

    @Nullable
    public <T> void removeAll(final List<T> items) {
        for (T item: items) {
            final int index = items.indexOf(item);
            if (index >= 0) {
                remove(index);
            }
        }
    }
}