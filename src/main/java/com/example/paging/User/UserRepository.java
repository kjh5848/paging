package com.example.paging.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> findByNameContainingAndCategoryContaining(String name, String category, Pageable pageable);

    Page<User> findByNameContaining(String name, Pageable pageable);

    Page<User> findByCategoryContaining(String category, Pageable pageable);

    @Query("SELECT u FROM User u WHERE " +
            "(:search IS NULL OR u.name LIKE %:search%) AND " +
            "(:category IS NULL OR u.category LIKE %:category%) AND " +
            "(:price IS NULL OR CAST(u.price AS string) LIKE %:price%)")
    Page<User> searchAllFields(@Param("search") String search,
                               @Param("category") String category,
                               @Param("price") String price,
                               Pageable pageable);

    @Query("SELECT u FROM User u WHERE " +
            "(:keyword IS NULL OR u.name LIKE %:keyword% OR u.category LIKE %:keyword% OR CAST(u.price AS string) LIKE %:keyword%)")
    Page<User> searchAllFields(@Param("keyword") String keyword, Pageable pageable);
}


