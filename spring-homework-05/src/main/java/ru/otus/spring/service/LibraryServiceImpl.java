
package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Genre;
import ru.otus.spring.repositories.AuthorRepository;
import ru.otus.spring.repositories.BookRepository;
import ru.otus.spring.repositories.GenreRepository;
import ru.otus.spring.service.exception.RelatedObjectNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;
    private static final String RELATED_OBJECT_NOT_FOUND = "Связанная сущность %s с id = %d не найдена!";


    @Override
    public List<Author> findAllAuthors() {
        return authorRepository.findAllAuthors();
    }

    @Override
    public Optional<Author> findAuthorById(Long id) {
        return authorRepository.findAuthorById(id);
    }

    @Override
    public void insertAuthor(Author author) {
        authorRepository.insertAuthor(author);
    }

    @Override
    public void updateAuthor(Author author) {
        authorRepository.updateAuthor(author);
    }

    @Override
    public List<Genre> findAllGenres() {
        return genreRepository.findAllGenres();
    }

    @Override
    public Optional<Genre> findGenreById(long id) {
        return genreRepository.findGenreById(id);
    }

    @Override
    public void insertGenre(Genre genre) {
        genreRepository.insertGenre(genre);
    }

    @Override
    public void updateGenre(Genre genre) {
        genreRepository.updateGenre(genre);
    }

    @Override
    public List<Book> findAllBooks() {
        return bookRepository.findAllBooks();
    }

    @Override
    public Optional<Book> findBookById(long id) {
        return bookRepository.findBookById(id);
    }

    @Override
    public void insertBook(Book book, boolean createRelatedObjects) {
        checkOrSaveRelatedObjects(book, createRelatedObjects);
        bookRepository.insertBook(book);
    }

    @Override
    public void updateBook(Book book, boolean createRelatedObjects) {
        checkOrSaveRelatedObjects(book, createRelatedObjects);
        bookRepository.updateBook(book);
    }

    @Override
    public void deleteBook(long id) {
        bookRepository.deleteBookById(id);

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
        if (!genreRepository.checkGenreExistsById(book.getGenre().getId())) {
            if (createRelatedObjects) {
                genreRepository.insertGenre(book.getGenre());
            } else
                throw new RelatedObjectNotFoundException(String.format(RELATED_OBJECT_NOT_FOUND, "genre", book.getGenre().getId()));
        }
    }

}
