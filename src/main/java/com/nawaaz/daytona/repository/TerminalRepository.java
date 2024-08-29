package com.nawaaz.daytona.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nawaaz.daytona.entity.Terminal;

@Repository
public interface TerminalRepository extends JpaRepository<Terminal, String> {
}