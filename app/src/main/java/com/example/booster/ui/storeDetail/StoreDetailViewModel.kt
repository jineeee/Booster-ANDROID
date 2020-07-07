package com.example.booster.ui.storeDetail

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.booster.data.datasource.model.StoreDetailData

class StoreDetailViewModel : ViewModel() {

    private var _storeDetail = MutableLiveData<StoreDetailData>() //변경 가능한 mutableLiveData 변수
    val storeDetail : LiveData<StoreDetailData> get() = _storeDetail //LiveData 변수인 newsList는 변경이 안되므로 변경 가능한 _newsList를 가져옴

    val viewModel =MutableLiveData<StoreDetailViewModel>()

    val dummy = StoreDetailData( subway = 2 )

//    val subway = MutableLiveData<Int>()

    init {
        _storeDetail.postValue(dummy)
//        subway.value = dummy.subway
    }
}
