package com.noreplypratap.data.mappers

import com.noreplypratap.data.model.remote.NewsArticleDTO
import com.noreplypratap.data.model.local.NewsArticleEntity
import com.noreplypratap.domain.model.NewsArticle


fun NewsArticle.toNewsArticleDTO() : NewsArticleDTO {
    return NewsArticleDTO(id,author,content,description,publishedAt,null,title,url,urlToImage)
}

fun NewsArticleDTO.toNewsArticle() : NewsArticle {
    return NewsArticle(id,author,content,description,publishedAt,title,url,urlToImage, source = source?.name)
}

fun NewsArticle.toNewsArticleEntity() : NewsArticleEntity {
    return NewsArticleEntity(id,author,content,description,publishedAt,title,url,urlToImage,source)
}

fun NewsArticleEntity.toNewsArticle() : NewsArticle {
    return NewsArticle(id,author,content,description,publishedAt,title,url,urlToImage,source)
}