package me.dio.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.dio.controller.dto.NewsDto;
import me.dio.service.NewsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/news")
@Tag(name = "News Controller", description = "RESTful API for managing news.")
public record NewsController(NewsService newsService) {

    // Get all news
    @GetMapping
    @Operation(summary = "Get all news", description = "Retrieve a list of all news articles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful")
    })
    public ResponseEntity<List<NewsDto>> findAll() {
        var newsList = newsService.findAll();
        var newsDtoList = newsList.stream().map(NewsDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(newsDtoList);
    }

    // Get news by ID
    @GetMapping("/{id}")
    @Operation(summary = "Get news by ID", description = "Retrieve a specific news article based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "News not found")
    })
    public ResponseEntity<NewsDto> findById(@PathVariable Long id) {
        var news = newsService.findById(id);
        return ResponseEntity.ok(new NewsDto(news));
    }

    // Create new news
    @PostMapping
    @Operation(summary = "Create new news", description = "Create a new news article and return its data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "News created successfully"),
            @ApiResponse(responseCode = "422", description = "Invalid news data provided")
    })
    public ResponseEntity<NewsDto> create(@RequestBody NewsDto newsDto) {
        var news = newsService.create(newsDto.toModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(news.getId())
                .toUri();
        return ResponseEntity.created(location).body(new NewsDto(news));
    }

    // Update existing news
    @PutMapping("/{id}")
    @Operation(summary = "Update news", description = "Update the data of an existing news article based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "News updated successfully"),
            @ApiResponse(responseCode = "404", description = "News not found"),
            @ApiResponse(responseCode = "422", description = "Invalid news data provided")
    })
    public ResponseEntity<NewsDto> update(@PathVariable Long id, @RequestBody NewsDto newsDto) {
        var updatedNews = newsService.update(id, newsDto.toModel());
        return ResponseEntity.ok(new NewsDto(updatedNews));
    }

    // Delete news by ID
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete news", description = "Delete an existing news article based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "News deleted successfully"),
            @ApiResponse(responseCode = "404", description = "News not found")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        newsService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
