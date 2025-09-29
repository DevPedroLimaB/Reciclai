package com.example.reciclai.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reciclai.model.Reward
import com.example.reciclai.model.UserReward
import com.example.reciclai.repository.RewardRepository
import com.example.reciclai.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RewardViewModel @Inject constructor(
    private val rewardRepository: RewardRepository
) : ViewModel() {

    private val _rewards = MutableStateFlow<Resource<List<Reward>>?>(null)
    val rewards: StateFlow<Resource<List<Reward>>?> = _rewards.asStateFlow()

    private val _userRewards = MutableStateFlow<Resource<List<UserReward>>?>(null)
    val userRewards: StateFlow<Resource<List<UserReward>>?> = _userRewards.asStateFlow()

    private val _redeemState = MutableStateFlow<Resource<UserReward>?>(null)
    val redeemState: StateFlow<Resource<UserReward>?> = _redeemState.asStateFlow()

    fun getRewards(category: String? = null, available: Boolean? = null) {
        viewModelScope.launch {
            _rewards.value = Resource.Loading()
            _rewards.value = rewardRepository.getRewards(category, available)
        }
    }

    fun getUserRewards(used: Boolean? = null) {
        viewModelScope.launch {
            _userRewards.value = Resource.Loading()
            _userRewards.value = rewardRepository.getUserRewards(used)
        }
    }

    fun redeemReward(rewardId: String) {
        viewModelScope.launch {
            _redeemState.value = Resource.Loading()
            _redeemState.value = rewardRepository.redeemReward(rewardId)
        }
    }

    fun clearRedeemState() {
        _redeemState.value = null
    }
}