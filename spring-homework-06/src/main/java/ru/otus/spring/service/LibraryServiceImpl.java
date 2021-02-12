
package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.models.*;
import ru.otus.spring.repositories.*;
import ru.otus.spring.service.exception.RelatedObjectNotFoundException;

import javax.persistence.EntityNotFoundException;
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
    private static final String RELATED_OBJECT_NOT_FOUND = "Связанная сущность %s с id = %d не найдена!";


    @Override
    @Transactional(readOnly = true)
    public List<Author> findAllAuthors() {
        return authorRepository.findAllAuthors();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Author> findAuthorById(Long id) {
        return authorRepository.findAuthorById(id);
    }

    @Override
    @Transactional
    public void insertAuthor(Author author) {
        authorRepository.insertAuthor(author);
    }

    @Override
    @Transactional
    public void updateAuthor(Author author) {
        authorRepository.updateAuthor(author);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Genre> findAllGenres() {
        return genreRepository.findAllGenres();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Genre> findGenreById(long id) {
        return genreRepository.findGenreById(id);
    }

    @Override
    @Transactional
    public void insertGenre(Genre genre) {
        genreRepository.insertGenre(genre);
    }

    @Override
    @Transactional
    public void updateGenre(Genre genre) {
        genreRepository.updateGenre(genre);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findAllBooks() {
        return bookRepository.findAllBooks();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Book> findBookById(long id) {
        return bookRepository.findBookById(id);
    }

    @Override
    @Transactional
    public void insertBook(Book book, boolean createRelatedObjects) {
        checkOrSaveRelatedObjects(book, createRelatedObjects);
        bookRepository.insertBook(book);
    }

    @Override
    @Transactional
    public void updateBook(Book book, boolean createRelatedObjects) {
        checkOrSaveRelatedObjects(book, createRelatedObjects);
        bookRepository.updateBook(book);
    }

    @Override
    @Transactional
    public void deleteBook(long id) {
        bookRepository.deleteBookById(id);
    }

    @Override
    @Transactional
    public Long saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public Long saveComment(Long id, Long bookId, Long userId, String commentText) {
        Comment comment;
        if (id != null){
            comment = commentRepository.findById(id).orElse(new Comment());
        }
        else {
            comment = new Comment();
        }

        if(bookId != null){
            var book = bookRepository.findBookById(bookId)
                    .orElseThrow(() -> new EntityNotFoundException(String.format("книга с id = %d не найдена", bookId)));
            comment.setBook(book);
        }
        if(userId != null){
            var user = userRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException(String.format("юзер с id = %d не найден", userId)));
            comment.setUser(user);
        }
        comment.setComment(commentText);
        return commentRepository.save(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> findAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public List<Comment> findAllCommentsByBookId(Long bookId) {
        return commentRepository.findAllByBookId(bookId);
    }

    /**
     * метод проверки существования связанного объекта
     * в текущей реализации объект если не существует, добавляется.
     *
     * @param book собственно книга со связанными сущностями
     */

    private void checkOrSaveRelatedObjects(Book book, boolean createRelatedObjects) {
        if (!authorRepository.checkAuthorExistsById(book.getAuthor().getId())) {
            if (createRelatedObjects) {
                authorRepository.insertAuthor(book.getAuthor());
            } else
                throw new RelatedObjectNotFoundException(String.format(RELATED_OBJECT_NOT_FOUND, "author", book.getAuthor().getId()));
        }
        for(Genre genre : book.getGenre()) {
            if (!genreRepository.checkGenreExistsById(genre.getId())) {
                if (createRelatedObjects) {
                    genreRepository.insertGenre(genre);
                } else
                    throw new RelatedObjectNotFoundException(String.format(RELATED_OBJECT_NOT_FOUND, "genre", genre.getId()));
            }
        }
    }

}
