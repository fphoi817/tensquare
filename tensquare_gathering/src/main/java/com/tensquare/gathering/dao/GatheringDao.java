package com.tensquare.gathering.dao;

import com.tensquare.gathering.pojo.Gathering;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GatheringDao extends JpaRepository<Gathering, String> {
}
