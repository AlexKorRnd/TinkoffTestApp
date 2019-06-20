package com.example.refillpoints.presentation.details.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.base.extensions.browse
import com.example.core.base.extensions.makeDial
import com.example.core.base.extensions.screenDensityName
import com.example.core.utils.ConstructPictureUrl
import com.example.core.utils.ImageLoader
import com.example.refillpoints.BuildConfig
import com.example.refillpoints.R
import com.example.refillpoints.domain.models.PartnerModel
import com.example.refillpoints.domain.models.RefillPointModel
import com.example.refillpoints.presentation.details.adapter.ClickableItem
import com.example.refillpoints.presentation.details.adapter.ClickableType
import com.example.refillpoints.presentation.details.adapter.DetailedAdapterItem
import com.example.refillpoints.presentation.details.adapter.RefillPointDetailedAdapter
import com.example.refillpoints.presentation.details.presenter.RefillPointDetailedInfoPresenter
import kotlinx.android.synthetic.main.activity_refill_point_detail_info.*
import kotlinx.android.synthetic.main.content_toolbar.*

class RefillPointDetailInfoActivity: AppCompatActivity(), RefillPointDetailedView {

    companion object {
        fun createIntent(context: Context, data: RefillPointModel): Intent =
                Intent(context, RefillPointDetailInfoActivity::class.java).apply {
                    putExtra(EXTRA_REFILL_POINT, data)
                }

        private val EXTRA_REFILL_POINT = "${BuildConfig.APPLICATION_ID}.extras.REFILL_POINT"
    }

    private lateinit var presenter: RefillPointDetailedInfoPresenter
    private lateinit var detailedData: RefillPointModel
    private lateinit var adapter: RefillPointDetailedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_refill_point_detail_info)

        setupToolbar()
        setTitle(R.string.refill_points_screen_detailed_title)

        adapter = RefillPointDetailedAdapter { position ->
            processClick(adapter.getItem(position) as ClickableItem)
        }
        detailedData = intent.getParcelableExtra(EXTRA_REFILL_POINT)

        setupRecyclerView()

        presenter = RefillPointDetailedInfoPresenter(this)
        presenter.processData(detailedData)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        val supportActionBar = supportActionBar ?: return
        supportActionBar.setDisplayHomeAsUpEnabled(true)
        supportActionBar.setHomeButtonEnabled(true)
        supportActionBar.setDisplayUseLogoEnabled(false)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupRecyclerView() {
        rvAdditionalInfo.adapter = adapter
        rvAdditionalInfo.layoutManager = LinearLayoutManager(this)
    }

    private fun processClick(item: ClickableItem) {
        when(item.clickableType) {
            ClickableType.PHONE -> {
                item.text?.let { phone ->
                    makeDial(phone)
                }
            }
            ClickableType.WEBSITE -> {
                item.text?.let { url ->
                    browse(url)
                }
            }
        }
    }

    override fun showItems(items: List<DetailedAdapterItem>) {
        adapter.clear()
        adapter.addAll(items)
        ImageLoader.load(ivIcon, ConstructPictureUrl.construct(PartnerModel.PICTURE_URL_PREFIX, screenDensityName(), detailedData.partner.picture), true)
        tvTitle.text = detailedData.partner.name
        tvText.text = detailedData.fullAddress
    }
}