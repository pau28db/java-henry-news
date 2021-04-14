package com.henry.news.services;

import com.henry.news.models.News;
import com.henry.news.models.PaginationResponse;
import com.henry.news.models.Writer;
import com.henry.news.repositories.NewsRepository;
import com.henry.news.utils.EntityURLBuilder;
import com.henry.news.utils.PostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Objects;

@Service
public class NewsService {

    private static final String NEWS_PATH = "news";
    private NewsRepository newsRepository;
    private WriterService writerService;

    @Autowired
    public NewsService(NewsRepository newsRepository, WriterService writerService){
        this.newsRepository = newsRepository;
        this.writerService = writerService;
    }
    public PostResponse addNews(News article){
        final News newsSaved = newsRepository.save(article);
        return PostResponse.builder()
                .status(HttpStatus.CREATED)
                .url(EntityURLBuilder.buildURL(NEWS_PATH, newsSaved.getId().toString()))
                .build();
    }

    public News getNewsById(Integer id) {
        return newsRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    public News getAllNews(){
        return (News) newsRepository.findAll();
    }
    public void addWriter(Integer id, Integer writerID) {
        News noticias = getNewsById(id);
        Writer writer = writerService.getWriter(writerID);
        noticias.setOwner(writer);
        newsRepository.save(noticias);

    }

    public void deleteWriterByid(Integer id) {
        newsRepository.deleteById(id);
    }

    public PaginationResponse<News> getAll(Integer page, Integer size) {
            if (!Objects.isNull(page) && !Objects.isNull(size)) {
                Pageable pageable = PageRequest.of(page, size);
                Page<News> newsPage = newsRepository.findAll(pageable);
                return new PaginationResponse<>(newsPage.getContent(),
                        newsPage.getTotalPages(),
                        newsPage.getTotalElements());
            }
            List<News> newsList = newsRepository.findAll();
            return new PaginationResponse<>(newsList, 1, (long) newsList.size());
        }
}

