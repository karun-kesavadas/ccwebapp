package com.neu.ccwebapp.web;

import com.neu.ccwebapp.domain.Book;
import com.neu.ccwebapp.domain.CurrentTime;
import com.neu.ccwebapp.domain.Image;
import com.neu.ccwebapp.domain.User;
import com.neu.ccwebapp.exceptions.BookNotFoundException;
import com.neu.ccwebapp.exceptions.ImageNotFoundException;
import com.neu.ccwebapp.exceptions.UserExistsException;
import com.neu.ccwebapp.service.BookService;
import com.neu.ccwebapp.service.ImageService;
import com.neu.ccwebapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
public class AppController {
    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private ImageService imageService;

    @GetMapping("/")
    public CurrentTime getCurrentTime() {
        return new CurrentTime();
    }

    @PostMapping("/user/register")
    public void registerUser(@Valid @RequestBody User user) {
        try
        {
            userService.registerUser(user);
        }
        catch (UserExistsException e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage(),e);
        }
    }

    @GetMapping("/book")
    public List<Book> getBook() {
        return bookService.getBook();
    }

    @PostMapping("/book")
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.createBook(book));
    }

    @PutMapping("/book")
    public ResponseEntity updateBook(@Valid @RequestBody Book book)
    {
        try
        {
            bookService.updateBook(book);
        }
        catch (BookNotFoundException e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage(),e);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/book/{id}")
    public Book getBookById(@Valid @PathVariable UUID id)
    {
        try
        {
            return bookService.getBookById(id);
        }
        catch (BookNotFoundException e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(),e);
        }
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity deleteBook(@Valid @PathVariable UUID id) {
        bookService.deleteBook(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }



    @PostMapping("/book/{idBook}/image")
    public void addBookImage(@Valid @PathVariable UUID idBook,@Valid @RequestBody Image image ){

        try {
            imageService.addBookImage(idBook, image);
        }
        catch (BookNotFoundException e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(),e);
        }

    }



    @GetMapping("/book/{idBook}/image/{idImage}")
    public Image getImageById(@Valid @PathVariable UUID idBook, @Valid @PathVariable UUID idImage){

        try
        {
            return imageService.getImageById(idBook,idImage);
        }
        catch (ImageNotFoundException e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(),e);
        }


    }


    @PutMapping("/book/{idBook}/image/{idImage}")
    public void updateImage(@Valid @PathVariable UUID idBook, @Valid @PathVariable UUID idImage,@Valid @RequestBody Image image){

        try
        {
             imageService.updateImage(idBook,idImage,image);
        }
        catch (ImageNotFoundException e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(),e);
        }
    }


    @DeleteMapping("/book/{idBook}/image/{idImage}")
    public void deleteImage(@Valid @PathVariable UUID idBook, @Valid @PathVariable UUID idImage){

        try
        {
            imageService.deleteImage(idBook,idImage);
        }
        catch (ImageNotFoundException e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(),e);
        }


    }




}
