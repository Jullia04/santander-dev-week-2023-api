package me.dio.service;

import me.dio.domain.model.News;
import me.dio.domain.repository.NewsRepository;
import me.dio.exception.NewsNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NewsService {

    private final NewsRepository newsRepository;

    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    // Buscar todas as notícias
    public List<News> findAll() {
        return newsRepository.findAll();
    }

    // Buscar uma notícia por ID
    public News findById(Long id) {
        return newsRepository.findById(id)
                .orElseThrow(() -> new NewsNotFoundException("News not found with ID: " + id));
    }

    // Criar uma nova notícia
    @Transactional
    public News create(News news) {
        return newsRepository.save(news);
    }

    // Atualizar uma notícia existente
    @Transactional
    public News update(Long id, News news) {
        News existingNews = findById(id);  // Verifica se a notícia existe
        existingNews.setIcon(news.getIcon());  // Atualiza o campo de ícone
        existingNews.setDescription(news.getDescription());  // Atualiza a descrição
        // Adicione aqui outros campos que você possa ter para atualização

        return newsRepository.save(existingNews);  // Salva a notícia atualizada
    }

    // Deletar uma notícia por ID
    @Transactional
    public void delete(Long id) {
        News news = findById(id);  // Verifica se a notícia existe
        newsRepository.delete(news);  // Deleta a notícia
    }
}
