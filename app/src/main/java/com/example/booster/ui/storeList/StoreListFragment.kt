package com.example.booster.ui.storeList

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a2nd_seminar.ui.ItemDecorator
import com.example.booster.R
import com.example.booster.databinding.FragmentStoreListBinding
import com.example.booster.ui.storeDetail.MapActivity
import com.example.booster.ui.storeDetail.StoreDetailActivity
import com.example.booster.ui.storeDetail.StoreDetailViewModel
import kotlinx.android.synthetic.main.fragment_store_list.*

class StoreListFragment : Fragment() {

    private lateinit var viewModel: StoreListViewModel
    private lateinit var viewModel2: StoreDetailViewModel
    lateinit var adapter: StoreListAdapter
    lateinit var binding: FragmentStoreListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_store_list, container, false)
        binding.lifecycleOwner = this@StoreListFragment
        return binding.root
//        val rootView = inflater.inflate(R.layout.fragment_store_list, container, false)
//        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(StoreListViewModel::class.java)
        viewModel2 = ViewModelProvider(this).get(StoreDetailViewModel::class.java)

        initRv()
        setClick()
        viewModel.getOrderList(1)
//        setAppBar()
    }

//    private fun setAppBar(){
//        frag_store_list_appBar.addOnOffsetChangedListener(OnOffsetChangedListener { frag_store_list_appBar, verticalOffset ->
//            if (frag_store_list_appBar.totalScrollRange == 0 || verticalOffset == 0) {
//                frag_store_list_iv_map.alpha = 1f
//                return@OnOffsetChangedListener
//            }
//            val ratio = verticalOffset.toFloat() / frag_store_list_appBar.totalScrollRange.toFloat()
//            frag_store_list_iv_map.alpha = 1f- abs(ratio)
//        })
//    }

    private fun setClick() {
        frag_store_list_ll_univ.setOnClickListener {
            val univListDialog = StoreListDialongFragment()
            univListDialog.show(
                requireActivity().supportFragmentManager,
                "schedule_dialog_fragment"
            )
        }

        frag_store_list_iv_map.setOnClickListener {
            val intent = Intent(context, MapActivity::class.java)
            intent.putExtra("univ", frag_store_list_tv_univ.text)
            startActivity(intent)
        }
    }

    private fun initRv() {
        adapter = StoreListAdapter(requireContext(),
            object : StoreListViewHolder.onClickStoreItemListener {
                override fun onClickStoreItem(position: Int, storeIdx: Int) {
                    val intent = Intent(requireContext(), StoreDetailActivity::class.java)
                    intent.putExtra("storeIdx", storeIdx)
                    startActivity(intent)
                }
            },
            object : StoreListViewHolder.onClickFavListener {
                override fun onClickFav(position: Int, imageView: ImageView, fav: Int, storeIdx: Int) {
                    if (fav == 1) {
                        imageView.setImageResource(R.drawable.store_ic_inactive_star)
//                        adapter.data[position].store_favorite = 0
                    } else {
                        imageView.setImageResource(R.drawable.store_ic_active_star)
//                        adapter.data[position].store_favorite = 1
                    }
                    viewModel2.putStoreFav(storeIdx)
                    Toast.makeText(requireContext(),
                        storeIdx.toString() + " : " + fav.toString(), Toast.LENGTH_SHORT).show()
                }
            })
        frag_store_list_rv.adapter = adapter
        frag_store_list_rv.layoutManager = LinearLayoutManager(requireContext())
        frag_store_list_rv.addItemDecoration(ItemDecorator(24))

        viewModel.storeList.observe(viewLifecycleOwner, Observer {
            adapter.data = it
            adapter.notifyDataSetChanged()
        })

//        viewModel2._favStatus.observe(viewLifecycleOwner, Observer {
//            Log.e("result -> ", it.message)
//            if(it.status==200){
//                item_store_search_iv_fav.setImageResource(R.drawable.store_ic_inactive_star)
//            } else if(it.status==201){
//                item_store_search_iv_fav.setImageResource(R.drawable.store_ic_active_star)
//            }
//        })
    }
}
