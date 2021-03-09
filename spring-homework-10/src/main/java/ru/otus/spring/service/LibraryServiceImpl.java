
package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.dto.AuthorDto;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.CommentDto;
import ru.otus.spring.dto.GenreDto;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Comment;
import ru.otus.spring.models.Genre;
import ru.otus.spring.repositories.*;
import ru.otus.spring.service.exception.EntityNotFoundException;
import ru.otus.spring.service.exception.RelatedObjectNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private static final String RELATED_OBJECT_NOT_FOUND = "Связанная сущность %s с id = %s не найдена!";

    @Override
    public List<AuthorDto> findAllAuthors() {
        return modelMapper.map(authorRepository.findAll(), new TypeToken<ArrayList<AuthorDto>>() {
        }.getType());
    }

    @Override
    public Optional<AuthorDto> findAuthorById(String id) {
        return modelMapper.map(authorRepository.findById(id), new TypeToken<Optional<AuthorDto>>() {
        }.getType());
    }

    @Override
    @Transactional
    public void insertAuthor(AuthorDto author) {
        authorRepository.save(modelMapper.map(author, Author.class));
    }

    @Override
    @Transactional
    public void updateAuthor(Author author) {
        authorRepository.save(author);
    }

    @Override
    public List<GenreDto> findAllGenres() {
        return modelMapper.map(genreRepository.findAll(), new TypeToken<ArrayList<GenreDto>>() {
        }.getType());
    }

    @Override
    public Optional<GenreDto> findGenreById(String id) {
        return modelMapper.map(genreRepository.findById(id), new TypeToken<Optional<GenreDto>>() {
        }.getType());
    }

    @Override
    @Transactional
    public void insertGenre(Genre genre) {
        genreRepository.save(genre);
    }

    @Override
    @Transactional
    public void updateGenre(Genre genre) {
        genreRepository.save(genre);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> findAllBooks() {
        return modelMapper.map(bookRepository.findAll(), new TypeToken<ArrayList<BookDto>>() {
        }.getType());
    }

    @Override
    @Transactional(readOnly = true)
    public BookDto findBookById(String id) {
        return modelMapper.map(bookRepository.findById(id).orElseThrow(), BookDto.class);
    }

    @Override
    @Transactional
    public BookDto insertBook(BookDto book, boolean createRelatedObjects) {
        return saveBook(book, createRelatedObjects);
    }

    @Override
    @Transactional
    public BookDto updateBook(BookDto bookDto, boolean createRelatedObjects) {

        return saveBook(bookDto, createRelatedObjects);
    }

    private BookDto saveBook(BookDto bookDto, boolean createRelatedObjects) {
        Book book = modelMapper.map(bookDto, Book.class);
        checkOrSaveRelatedObjects(book, createRelatedObjects);
        return modelMapper.map(bookRepository.save(book), BookDto.class);
    }

    @Override
    @Transactional
    public void deleteBook(String id) {
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional
    public String saveComment(Comment comment) {
        return commentRepository.save(comment).getId();
    }

    @Override
    @Transactional
    public String saveComment(String id, String bookId, String userId, String commentText) {
        Comment comment;
        if (id != null) {
            comment = commentRepository.findById(id).orElse(new Comment());
        } else {
            comment = new Comment();
        }

        if (bookId != null) {
            var book = bookRepository.findById(bookId)
                    .orElseThrow(() -> new EntityNotFoundException(String.format("книга с id = %s не найдена", bookId)));
            comment.setBook(book);
        }
        if (userId != null) {
            var user = userRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException(String.format("юзер с id = %s не найден", userId)));
            comment.setUser(user);
        }
        comment.setCommentText(commentText);
        Comment newComment = commentRepository.save(comment);
        return newComment.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> findAllComments() {
        return modelMapper.map(commentRepository.findAll(), new TypeToken<ArrayList<CommentDto>>() {
        }.getType());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> findAllCommentsByBookId(String bookId) {
        return modelMapper.map(commentRepository.findByBookId(bookId), new TypeToken<ArrayList<CommentDto>>() {
        }.getType());
    }

    /**
     * метод проверки существования связанного объекта
     * в текущей реализации объект если не существует, добавляется.
     *
     * @param book собственно книга со связанными сущностями
     */

    private void checkOrSaveRelatedObjects(Book book, boolean createRelatedObjects) {

        if (!authorRepository.existsById(book.getAuthor().getId())) {
            if (createRelatedObjects) {
                authorRepository.save(book.getAuthor());
            } else
                throw new RelatedObjectNotFoundException(String.format(RELATED_OBJECT_NOT_FOUND, "author", book.getAuthor().getId()));
        }
        for (Genre genre : book.getGenre()) {
            if (!genreRepository.existsById(genre.getId())) {
                if (createRelatedObjects) {
                    genreRepository.save(genre);
                } else
                    throw new RelatedObjectNotFoundException(String.format(RELATED_OBJECT_NOT_FOUND, "genre", genre.getId()));
            }
        }
    }

}
