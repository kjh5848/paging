package com.example.paging.User;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@org.springframework.stereotype.Controller
public class UserController {
    private final UserService userService;

    @GetMapping("/")
    public String listUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<User> users = userService.드롭박스정렬(keyword, sortField, sortDir, pageable);

        int totalPages = users.getTotalPages();
        int currentPage = users.getNumber();

        int start = Math.max(1, currentPage - 2);
        int end = Math.min(totalPages, currentPage + 2);
        if (end - start < 4) {
            start = Math.max(1, end - 4);
        }
        if (end > totalPages) {
            end = totalPages;
        }

        model.addAttribute("users", users);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("keyword", keyword);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("start", start);
        model.addAttribute("end", end);
        return "index";
    }

}
