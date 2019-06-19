package com.example.refillpoints.presentation.refill_points.list.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.base.decorators.LineDividerDecorator
import com.example.refillpoints.R
import com.example.refillpoints.domain.models.RefillPointModel
import com.example.refillpoints.presentation.refill_points.list.adapter.RefillPointsAdapter
import com.example.refillpoints.presentation.refill_points.view.RefillPointsPageView
import kotlinx.android.synthetic.main.fragment_refill_points_list.*

class RefillPointsListFragment: Fragment(), RefillPointsPageView {

    companion object {
        fun newInstance() = RefillPointsListFragment()
    }

    private lateinit var adapter: RefillPointsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = RefillPointsAdapter { position ->
            // TODO: 19.06.19 need process click and navigate to detailed screen!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_refill_points_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        rvItems.adapter = adapter
        rvItems.layoutManager = LinearLayoutManager(requireContext())
        rvItems.addItemDecoration(LineDividerDecorator(ContextCompat.getColor(requireContext(), R.color.dark_divider), resources.getDimension(R.dimen.refill_point_item_decorator_height)))
    }

    override fun showRefillPoints(points: List<RefillPointModel>) {
        adapter.clear()
        adapter.addAll(points)
    }
}