package com.emotionmusicnote.history.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LogHistoryRepository extends JpaRepository<LogHistory, Long> {

}
