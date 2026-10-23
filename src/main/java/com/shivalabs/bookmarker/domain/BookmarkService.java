package com.shivalabs.bookmarker.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@Transactional
public class BookmarkService {
    private final BookmarkRepository repository;
    private final BookmarkMapper bookmarkMapper;

    public BookmarkService(BookmarkRepository repository, BookmarkMapper bookmarkMapper) {
        this.repository = repository;
        this.bookmarkMapper = bookmarkMapper;
    }

    @Transactional(readOnly = true)
    public BookmarkDTO getBookmarks(Integer page){
        int pageNo = page < 1 ? 0 : page-1;
        Pageable pageable = PageRequest.of(pageNo,10, Sort.Direction.DESC,"createdAt");
        Page<BookmarkDTO> bookmarkPage = repository.findBy(pageable);
        return new BookmarkDTO(bookmarkPage);
    }

    @Transactional(readOnly = true)
    public BookmarkDTO searchBookmarks(String query,Integer page){
        int pageNo = page < 1 ? 0 : page - 1;
        Pageable pageable = PageRequest.of(pageNo,10,Sort.Direction.DESC,"createdAt");
        Page<BookmarkDTO> bookmarkPage = repository.searchBookmarks(query, pageable);
        //Page<BookmarkDTO> bookmarkPage = repository.findByTitleContainsIgnoreCase(query, pageable);
        return new BookmarkDTO(bookmarkPage);
    }

    public BookmarkDTO createBookmark(CreateBookmarkRequest request) {
        Bookmark bookmark = new Bookmark(null, request.getTitle(), request.getUrl(), Instant.now());
        Bookmark savedBookmark = repository.save(bookmark);
        return bookmarkMapper.toDTO(savedBookmark);
    }
}
