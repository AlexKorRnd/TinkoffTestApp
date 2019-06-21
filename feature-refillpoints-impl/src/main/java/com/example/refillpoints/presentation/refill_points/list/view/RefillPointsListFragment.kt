package com.example.refillpoints.presentation.refill_points.list.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.base.decorators.LineDividerDecorator
import com.example.refillpoints.R
import com.example.refillpoints.domain.models.RefillPointModel
import com.example.refillpoints.presentation.Router
import com.example.refillpoints.presentation.refill_points.list.adapter.RefillPointsAdapter
import com.example.refillpoints.presentation.refill_points.presenter.RefillPointsPresenter
import com.example.refillpoints.presentation.refill_points.view.RefillPointsActivity
import com.example.refillpoints.presentation.refill_points.view.RefillPointsPageView
import kotlinx.android.synthetic.main.fragment_refill_points_list.*

class RefillPointsListFragment : Fragment(), RefillPointsPageView {

    companion object {
        fun newInstance() = RefillPointsListFragment()
    }

    private lateinit var adapter: RefillPointsAdapter

    private val parentPresenter: RefillPointsPresenter?
        get() = (activity as? RefillPointsActivity)?.presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = RefillPointsAdapter(object: RefillPointsAdapter.Callback {
            override fun onItemClick(position: Int, ivIcon: ImageView) {
                val activity = activity ?: return
                val item = adapter.getItem(position) ?: return
                parentPresenter?.updateRefillPointSeenStatus(item, true)
                Router.openDetailedScreen(activity, ivIcon, item)
            }
        })
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

    override fun showUpdatedRefillPointSeenStatus(point: RefillPointModel) {
        adapter.updateItem(point)
    }
}