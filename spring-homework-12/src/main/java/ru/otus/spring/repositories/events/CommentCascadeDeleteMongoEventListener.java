package ru.otus.spring.repositories.events;

import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.spring.models.Book;
import ru.otus.spring.repositories.CommentRepository;

@RequiredArgsConstructor
@Component
public class CommentCascadeDeleteMongoEventListener extends AbstractMongoEventListener<Book> { //вроде нужно только для комментов
    private final CommentRepository bookRepository;

    @Override
    public void onAfterDelete(AfterDeleteEvent<Book> event) {
        Document bookDocumentToDelete = event.getSource();
        bookRepository.deleteByBookId(bookDocumentToDelete.get("_id").toString());
    }
}
