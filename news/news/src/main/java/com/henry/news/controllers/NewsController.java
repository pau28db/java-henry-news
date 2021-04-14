package com.henry.news.controllers;


import com.henry.news.models.News;
import com.henry.news.models.PaginationResponse;
import com.henry.news.services.NewsService;
import com.henry.news.utils.PostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/news")
public class NewsController {

@Autowired
    private NewsService newsService;

    @PostMapping
    public ResponseEntity<?> addNews(@RequestBody News noticia) {
        final PostResponse postResponse = newsService.addNews(noticia);
        return new ResponseEntity(postResponse.getUrl(), postResponse.getStatus());
    }

    @PutMapping("/{id}/writer/{writerID}")
    private String addWriter(@PathVariable Integer id, @PathVariable Integer writerID) {
        newsService.addWriter(id, writerID);
        return ("Agregado writer con id: " + writerID);
    }

   @GetMapping
   public PaginationResponse<News> getAll(@RequestParam(value = "size", defaultValue = "20") Integer size,
                                          @RequestParam(value = "page", defaultValue = "0") Integer page) {
       return newsService.getAll(page, size);
   }

    @DeleteMapping("/{id}")
    public String deleteNews(@PathVariable Integer id){
        newsService.deleteWriterByid(id);
        return ("Noticia Borrada con id: " + id);
    }
}
