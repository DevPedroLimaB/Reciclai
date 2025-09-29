package com.example.reciclai.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reciclai.model.CommunityChallenge
import com.example.reciclai.model.LeaderboardEntry
import com.example.reciclai.repository.CommunityRepository
import com.example.reciclai.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val communityRepository: CommunityRepository
) : ViewModel() {

    private val _leaderboard = MutableStateFlow<Resource<List<LeaderboardEntry>>?>(null)
    val leaderboard: StateFlow<Resource<List<LeaderboardEntry>>?> = _leaderboard.asStateFlow()

    private val _challenges = MutableStateFlow<Resource<List<CommunityChallenge>>?>(null)
    val challenges: StateFlow<Resource<List<CommunityChallenge>>?> = _challenges.asStateFlow()

    private val _joinChallengeState = MutableStateFlow<Resource<Unit>?>(null)
    val joinChallengeState: StateFlow<Resource<Unit>?> = _joinChallengeState.asStateFlow()

    fun getLeaderboard(period: String = "monthly", limit: Int = 100) {
        viewModelScope.launch {
            _leaderboard.value = Resource.Loading()
            _leaderboard.value = communityRepository.getLeaderboard(period, limit)
        }
    }

    fun getChallenges(active: Boolean? = null) {
        viewModelScope.launch {
            _challenges.value = Resource.Loading()
            _challenges.value = communityRepository.getChallenges(active)
        }
    }

    fun joinChallenge(challengeId: String) {
        viewModelScope.launch {
            _joinChallengeState.value = Resource.Loading()
            _joinChallengeState.value = communityRepository.joinChallenge(challengeId)
        }
    }

    fun clearJoinChallengeState() {
        _joinChallengeState.value = null
    }
}