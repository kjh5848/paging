package com.example.paging.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Page<User> listItems(String search, String category, Pageable pageable) {
        if (!search.isEmpty() && !category.isEmpty()) {
            return userRepository.findByNameContainingAndCategoryContaining(search, category, pageable);
        } else if (!search.isEmpty()) {
            return userRepository.findByNameContaining(search, pageable);
        } else if (!category.isEmpty()) {
            return userRepository.findByCategoryContaining(category, pageable);
        } else {
            return userRepository.findAll(pageable);
        }
    }

    public Page<User> listUsers(String search, String category, String price, Pageable pageable) {
        return userRepository.searchAllFields(search, category, price, pageable);
    }

    //모든 검색
    public Page<User> allListUsers(String keyword, Pageable pageable) {
        return userRepository.searchAllFields(keyword, pageable);
    }

    //드롭박스 정렬할때
    public Page<User> 드롭박스정렬(String keyword, String sortField, String sortDir, Pageable pageable) {
        Sort sort = Sort.by(sortField);
        if ("desc".equals(sortDir)) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        return userRepository.searchAllFields(keyword, pageable);
    }
}