package com.example.reciclai.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reciclai.model.Content
import com.example.reciclai.repository.ContentRepository
import com.example.reciclai.util.Resource
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

    private val _contentList = MutableStateFlow<Resource<List<Content>>?>(null)
    val contentList: StateFlow<Resource<List<Content>>?> = _contentList.asStateFlow()

    private val _selectedContent = MutableStateFlow<Resource<Content>?>(null)
    val selectedContent: StateFlow<Resource<Content>?> = _selectedContent.asStateFlow()

    fun getContent(category: String? = null, tags: String? = null, page: Int = 1, limit: Int = 10) {
        viewModelScope.launch {
            _contentList.value = Resource.Loading()
            _contentList.value = contentRepository.getContent(category, tags, page, limit)
        }
    }

    fun getContentById(id: String) {
        viewModelScope.launch {
            _selectedContent.value = Resource.Loading()
            _selectedContent.value = contentRepository.getContentById(id)
        }
    }

    fun clearSelectedContent() {
        _selectedContent.value = null
    }
}