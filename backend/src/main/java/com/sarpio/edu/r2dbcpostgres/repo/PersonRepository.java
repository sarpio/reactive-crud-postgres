package com.sarpio.edu.r2dbcpostgres.repo;

import com.sarpio.edu.r2dbcpostgres.model.Person;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface PersonRepository extends R2dbcRepository<Person, Long> {

    Flux<Person> findAllBy(Pageable pageable);

    @Query("SELECT * FROM persons WHERE " +
            "first_name ILIKE '%' || :filter || '%' OR " +
            "last_name ILIKE '%' || :filter || '%' OR  " +
            "email ILIKE '%' || :filter || '%' OR " +
            "phone ILIKE '%' || :filter || '%' OR " +
            "job ILIKE '%' || :filter || '%' " +
            "ORDER BY CASE WHEN :#{#pageable.sort.isEmpty()} THEN null ELSE :#{#pageable.sort.toString()} END LIMIT :#{#pageable.pageSize} OFFSET :#{#pageable.offset}")
    Flux<Person> findPersonByFilter(String filter, Pageable pageable);

    //    @Query("SELECT * FROM persons p WHERE p.email = :email")
    Flux<Person> findByEmail(String email);

    @Modifying
    @Query("UPDATE persons SET id = :id, first_name = :firstName, last_name = :lastName, email = :email, job = :job, phone = :phone WHERE id = :id")
    Mono<Integer> updatePersonData(
            @Param("id") Long id,
            @Param("firstName")
            String firstName,
            @Param("lastName")
            String lastName,
            @Param("email")
            String email,
            @Param("job")
            String job,
            @Param("phone")
            String phone);

    @Query("SELECT * FROM persons WHERE id = :id")
    Mono<Person> findPersonById(@Param("id") Long id);
}
