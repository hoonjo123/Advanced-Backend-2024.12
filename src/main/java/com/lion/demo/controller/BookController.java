package com.lion.demo.controller;

import com.lion.demo.entity.Book;
import com.lion.demo.service.BookService;
import com.lion.demo.service.CsvFileReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {
    @Autowired private CsvFileReaderService csvFileReaderService;
    @Autowired private BookService bookService;

    @GetMapping("/list")
    public String list(@RequestParam(name="p", defaultValue = "1") int page,
                       @RequestParam(name="f", defaultValue = "title") String field,
                       @RequestParam(name="q", defaultValue = "") String query,
                       Model model) {
//        List<Book> bookList = bookService.getBooksByPage(page);
        List<Book> bookList = bookService.getBookList(page, field, query);
        model.addAttribute("bookList", bookList);
        return "book/list";
    }

    @GetMapping("/detail/{bid}")
    public String detail(@PathVariable long bid, Model model) {
        Book book = bookService.findByBid(bid);
        model.addAttribute("book", book);
        return "book/detail";
    }

    @GetMapping("/insert")
    public String insertForm() {
        return "book/insert";
    }

    @PostMapping("/insert")
    public String insertProc(Book book) {
        bookService.insertBook(book);
        return "redirect:/book/list";
    }

    // 초기 데이터
    @GetMapping("/yes24")
    public String yes24() {
        csvFileReaderService.csvFileToH2();
        return "redirect:/book/list";
    }

}
