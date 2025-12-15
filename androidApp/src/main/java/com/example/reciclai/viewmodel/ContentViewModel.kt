package com.example.reciclai.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reciclai.shared.model.Content
import com.example.reciclai.shared.repository.ContentRepository
import com.example.reciclai.shared.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContentViewModel @Inject constructor(
    private val contentRepository: ContentRepository
) : ViewModel() {

    private val _contentState = MutableStateFlow<Resource<List<Content>>>(Resource.Loading())
    val contentState: StateFlow<Resource<List<Content>>> = _contentState.asStateFlow()

    init {
        loadContent()
    }

    fun loadContent(category: String? = null, page: Int = 0, limit: Int = 20) {
        viewModelScope.launch {
            _contentState.value = Resource.Loading()
            val result = contentRepository.getContent(category, page, limit)
            _contentState.value = result
        }
    }

    fun refresh() {
        loadContent()
    }
}

