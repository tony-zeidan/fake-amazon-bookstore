package fakeamazon.bookstore.demo.repository;

import fakeamazon.bookstore.demo.model.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepositoryImpl implements BookRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    public BookRepositoryImpl(){}

    /**
     * Allows for paging and filtering for the BookRepository.
     * @param name filters the inventory based on the book's name
     * @param isbn filters the inventory based on the book's isbn
     * @param description filters the inventory based on the book's description
     * @param publisher filters the inventory based on the book's publisher
     * @param pageable the current page and size that is being requested
     * @return The books that match the filter for the page and size
     */
    @Override
    public Page<Book> getFilterBooks(String name, String isbn, String description, String publisher, Pageable pageable) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Book> cq = cb.createQuery(Book.class);
        Root<Book> book = cq.from(Book.class);
        List<Predicate> predicates = new ArrayList<Predicate>();

        if (!name.equals("")) predicates.add(cb.like(cb.upper(book.get("name")), "%" + name.toUpperCase() + "%"));
        if (!isbn.equals("")) predicates.add(cb.like(cb.upper(book.get("isbn")), "%" + isbn.toUpperCase() + "%"));
        if (!description.equals("")) predicates.add(cb.like(cb.upper(book.get("description")), "%" + description.toUpperCase() + "%"));
        if (!publisher.equals("")) predicates.add(cb.like(cb.upper(book.get("publisher")), "%" + publisher.toUpperCase() + "%"));

        cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        // This query fetches the Books as per the Page Limit
        List<Book> result = em.createQuery(cq).setFirstResult((int) pageable.getOffset()).setMaxResults(pageable.getPageSize()).getResultList();

        int count = em.createQuery(cq).getResultList().size();

        return new PageImpl<>(result, pageable, count);
    }
}
